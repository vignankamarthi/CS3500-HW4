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

    safeAppend(view.toString() + "\n");
    safeAppend("Score: " + model.getScore() + "\n");

    try {
      while (scanner.hasNext()) {
        String command = scanner.next();
        if (command.equalsIgnoreCase("q")) {
          quitGame(model, view);
          return;
        } else if (command.equals("place")) {
          // Reading three natural numbers (1-indexed) for card index, row, and column.
          Integer cardIndex = readNaturalNumber(scanner);
          if (cardIndex == null) {
            quitGame(model, view);
            return;
          }
          Integer row = readNaturalNumber(scanner);
          if (row == null) {
            quitGame(model, view);
            return;
          }
          Integer col = readNaturalNumber(scanner);
          if (col == null) {
            quitGame(model, view);
            return;
          }

          int cardIdx0 = cardIndex - 1;
          int row0 = row - 1;
          int col0 = col - 1;

          // Invoking the placeCardInPosition method
          try {
            model.placeCardInPosition(cardIdx0, row0, col0);
          } catch (IllegalArgumentException e) {
            safeAppend("Invalid move. Play again. " + e.getMessage() + "\n");
            safeAppend(view.toString() + "\n");
            safeAppend("Score: " + model.getScore() + "\n");
            continue;
          }
          safeAppend(view.toString() + "\n");
          safeAppend("Score: " + model.getScore() + "\n");

        } else if (command.equals("discard")) {
          // Reading one natural number (1-indexed) for the card index.
          Integer cardIndex = readNaturalNumber(scanner);
          if (cardIndex == null) {
            quitGame(model, view);
            return;
          }
          int cardIdx0 = cardIndex - 1;

          // Invoking the discardCard method
          try {
            model.discardCard(cardIdx0);
          } catch (IllegalArgumentException e) {
            safeAppend("Invalid move. Play again. " + e.getMessage() + "\n");
            safeAppend(view.toString() + "\n");
            safeAppend("Score: " + model.getScore() + "\n");
            continue;
          }
          safeAppend(view.toString() + "\n");
          safeAppend("Score: " + model.getScore() + "\n");
        }
        // Invalid commands that are not "place", "discard", "Q/q", or a natural number
        else {
          safeAppend("Invalid move. Play again. Unrecognized command: " + command + "\n");
        }

        if (model.isGameOver()) {
          safeAppend(view.toString() + "\n");
          safeAppend("Game over. Score: " + model.getScore() + "\n");
          return;
        }
      }
    } catch (Exception e) {
      throw new IllegalStateException("Failed to receive input.", e);
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
        if (num < 1) continue;
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
}