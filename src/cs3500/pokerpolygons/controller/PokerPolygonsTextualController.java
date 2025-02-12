package cs3500.pokerpolygons.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.view.PokerPolygonsTextualView;

/**
 * A PokerPolygonsController to create a controller so the user
 * can play a PokerPolygons game with certain inputs corresponding with outputs defined
 * by the controller.
 */
public class PokerPolygonsTextualController implements PokerPolygonsController {
  private final Readable input;
  private final Appendable output;

  /**
   * Constructs a controller with fields that represent inputs and outputs.
   * @param input the Readable input source
   * @param output the Appendable output text
   * @throws IllegalArgumentException if either input or output is null
   */
  public PokerPolygonsTextualController(Readable input, Appendable output) {
    if (input == null || output == null) {
      throw new IllegalArgumentException("Readable (input) and Appendable (output) cannot be null");
    }
    this.input = input;
    this.output = output;
  }

  /**
   * Starts and plays the game using user input from the player.
   * @param model state of the game
   * @param view view for the model
   * @param deck the deck of cards used to play the game
   * @param shuffle true iff the deck should be shuffled.
   * @param handSize the starting hand size
   * @param <C> the type of the Card used in the model
   * @throws IllegalStateException if controller is unable to successfully receive input,
   *     transmit output, or if the game cannot be started
   * @throws IllegalArgumentException if the model or view are null
   */
  @Override
  public <C extends Card> void playGame(PokerPolygons<C> model, PokerPolygonsTextualView view,
                                        List<C> deck, boolean shuffle, int handSize) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model or view cannot be null.");
    }
    Scanner scanner = new Scanner(this.input);
    try {
      model.startGame(deck, shuffle, handSize);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to start game.", e);
    }
    safeAppend(view.toString() + System.lineSeparator());
    safeAppend("Score: " + model.getScore() + System.lineSeparator());
    try {
      while (scanner.hasNext()) {
        String command = scanner.next();
        if (command.equalsIgnoreCase("q")) {
          quitGame(model, view);
          return;
        }
        else if (command.equals("place")) {
          Integer cardIndex = readInputNumberOrQuit(scanner, model, view);
          Integer row = readInputNumberOrQuit(scanner, model, view);
          Integer col = readInputNumberOrQuit(scanner, model, view);
          if (cardIndex == null || row == null || col == null) {
            return;
          }
          int cardIdx0 = cardIndex - 1;
          int row0 = row - 1;
          int col0 = col - 1;
          placeCardInPosition(model, view, cardIdx0, row0, col0);
        } else if (command.equals("discard")) {
          Integer cardIndex = readNaturalNumber(scanner);
          if (cardIndex == null) {
            quitGame(model, view);
            return;
          }
          int cardIdx0 = cardIndex - 1;
          discardCard(model, view, cardIdx0);
        }
        else {
          safeAppend("Invalid move. Play again. Unrecognized command: " +
                  command + System.lineSeparator());
        }
        checkInputAvailable(scanner);
        if (model.isGameOver()) {
          return;
        }
      }
      checkInputAvailable(scanner);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to read input.", e);
    }
  }

  /**
   * Checks if input is available in the scanner and throws an exception if not.
   *
   * @param scanner the Scanner to check input availability
   * @throws IllegalStateException if input runs out unexpectedly
   */
  private void checkInputAvailable(Scanner scanner) {
    if (!scanner.hasNext()) {
      throw new IllegalStateException("Ran out of input unexpectedly.");
    }
  }

  /**
   * Repeatedly reads tokens from the scanner until a valid natural number (>= 1) is found,
   * or a quit command ("q" or "Q") is encountered.
   * If a non-numeric, non-quit token is entered, it waits silently for a valid input.
   *
   * @param scanner the Scanner to read input from
   * @return the valid natural number read, or null if a quit command was encountered.
   */
  private Integer readNaturalNumber(Scanner scanner) {
    while (scanner.hasNext()) {
      String token = scanner.next();
      if (token.equalsIgnoreCase("q")) {
        return null;
      }
      try {
        int num = Integer.parseInt(token);
        if (num < 0) {
          continue;
        }
        return num;
      } catch (NumberFormatException e) {
        // Silently ignore invalid numeric input.
        continue;
      }
    }
    return null;
  }

  /**
   * Handling when the user quits, by transmitting the quit message,
   * current game state, and final score.
   *
   * @param model the current game model
   * @param view the view representing the game state
   */
  private <C extends Card> void quitGame(PokerPolygons<C> model, PokerPolygonsTextualView view) {
    safeAppend("Game quit!\n"
            + "State of game when quit:\n"
            + view.toString() + System.lineSeparator()
            + "Score: " + model.getScore());
  }

  /**
   * Helper method to handle IO exceptions when appending to the output.
   *
   * @param s the string to append to the output log.
   * @throws IllegalStateException if the output cannot be transmitted.
   */
  private void safeAppend(String s) {
    try {
      this.output.append(s);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to transmit output.", e);
    }
  }

  /**
   * To handle correct messaging when the game is over.
   * @param model is the model of the game.
   */
  private <C extends Card> void handleGameOverMessage(PokerPolygons<C> model) {
    safeAppend("Game over. Score: " + model.getScore() + System.lineSeparator());
  }

  /**
   * Reads a natural number from the scanner or quits if "q" is entered.
   * If invalid input is encountered, it keeps scanning until a valid number or "q" is provided.
   *
   * @param scanner the Scanner to read input from
   * @param model the game model
   * @param view the view
   * @return the valid natural number read, or null if "q" was encountered.
   */
  private Integer readInputNumberOrQuit(Scanner scanner, PokerPolygons<?> model,
                                        PokerPolygonsTextualView view) {
    while (scanner.hasNext()) {
      String token = scanner.next();
      if (token.equalsIgnoreCase("q")) {
        quitGame(model, view);
        return null;
      }
      try {
        int num = Integer.parseInt(token);
        if (num > 0) {
          return num;
        } // Making sure to only return valid natural numbers
      } catch (NumberFormatException ignored) {
        // Continue looping until valid input
      }
    }
    return null;
  }

  /**
   * Attempts to place a card in the given position and handles exceptions.
   *
   * @param model the game model
   * @param view the game view
   * @param cardIdx0 the zero-based index of the card in hand
   * @param row0 the zero-based row index
   * @param col0 the zero-based column index
   */
  private <C extends Card> void placeCardInPosition(PokerPolygons<C> model,
                                                    PokerPolygonsTextualView view,
                                                    int cardIdx0, int row0, int col0) {
    try {
      model.placeCardInPosition(cardIdx0, row0, col0);
    } catch (IllegalArgumentException | IllegalStateException e) {
      safeAppend("Invalid move. Play again. " + e.getMessage() + System.lineSeparator());
      safeAppend(view.toString() + System.lineSeparator());
      safeAppend("Score: " + model.getScore() + System.lineSeparator());
      return;
    }

    // If the game is over after a successful move, handle the game-over case
    if (model.isGameOver()) {
      handleGameOverMessage(model);
    } else {
      safeAppend(view.toString() + System.lineSeparator());
      safeAppend("Score: " + model.getScore() + System.lineSeparator());
    }
  }

  /**
   * Attempts to discard a card at the given index and handles exceptions.
   *
   * @param model the game model
   * @param view the game view
   * @param cardIdx0 the zero-based index of the card in hand
   */
  private <C extends Card> void discardCard(PokerPolygons<C> model,
                                            PokerPolygonsTextualView view,
                                            int cardIdx0) {
    try {
      model.discardCard(cardIdx0);
    } catch (IllegalArgumentException | IllegalStateException e) {
      safeAppend("Invalid move. Play again. " + e.getMessage() + System.lineSeparator());
      safeAppend(view.toString() + System.lineSeparator());
      safeAppend("Score: " + model.getScore() + System.lineSeparator());
      return;
    }

    safeAppend(view.toString() + System.lineSeparator());
    safeAppend("Score: " + model.getScore() + System.lineSeparator());
  }

}