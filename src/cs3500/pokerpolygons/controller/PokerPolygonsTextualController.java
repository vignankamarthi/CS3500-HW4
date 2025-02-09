package cs3500.pokerpolygons.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.view.PokerPolygonsTextualView;

/**
 * An implementation of the PokerPolygonsController to create a controller so a user
 * can play a PokerPolygons game with corresponding outputs defined by the controller.
 */
public class PokerPolygonsTextualController implements PokerPolygonsController {
  private Readable input;
  private Appendable output;
  private PokerPolygons model;

  /**
   * A constructor to create a controller that can take in inputs and outputs, and stores
   * them throughout while playing the game.
   */
  // TODO: Test the illegal arguments for this contstructor
  public PokerPolygonsTextualController(Readable input, Appendable output) {
    if (input == null || output == null) {
      throw new IllegalArgumentException("Readable (input) and Appendable (output) cannot be null");
    }
    this.input = input;
    this.output = output;

  }


  /**
   * Starts and plays the game using user input from the player.
   *
   * @param model    state of the game
   * @param view     view for the model
   * @param deck     the deck of cards used to play the game
   * @param shuffle  true iff the deck should be shuffled.
   * @param handSize the starting hand size
   * @throws IllegalStateException    if controller is unable to successfully receive input,
   *                                  transmit output, or if the game cannot be started
   * @throws IllegalArgumentException if the model or view are null
   */
  @Override
  public <C extends Card> void playGame(PokerPolygons<C> model, PokerPolygonsTextualView view,
                                        List<C> deck, boolean shuffle, int handSize) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model or view cannot be null.");
    }
    //TODO: IllegalStateException should be thrown ONLY if the controller cannot successfully receive input or transmit output

    Scanner scanner  = new Scanner(input);
    while (scanner.hasNext()) {
      String command = scanner.next();
      switch (command) {
        case "q":
        case "Q":
          safeAppend("Game quit!\n"
                  + "State of game when quit:"
                  + view.toString() + "\n"
                  + "Score: " + model.getScore());
      }


    }





  }

  /**
   * Helper method to handle IO exceptions throughout the implementation.
   * @param s is the string to append to the output log.
   */
  private void safeAppend(String s) {
    try {
      this.output.append(s);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to transmit output.", e);
    }
  }
}
