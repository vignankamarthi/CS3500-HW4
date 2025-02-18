package cs3500.pokerpolygons;

import cs3500.pokerpolygons.controller.PokerPolygonsTextualController;
import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;
import cs3500.pokerpolygons.view.PokerPolygonsTextualView;
import cs3500.pokerpolygons.view.PokerTrianglesTextualView;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Integration tests for PokerPolygons using fully implemented
 * model (PokerTriangles), view (PokerTrianglesTextualView),
 * and controller (PokerPolygonsTextualController).
 */
public class PokerPolygonsIntegrationTests {

  /**
   * Tests that the controller quits properly when the input is "q" (lowercase).
   */
  @Test
  public void testQuitCommandLowercaseQ() {
    StringReader input = new StringReader("q");
    StringBuilder output = new StringBuilder();

    // Using the public interface type for the model
    PokerPolygons<PlayingCard> model = new PokerTriangles(5);
    List<PlayingCard> deck = model.getNewDeck();

    // Use the full textual view implementation.
    PokerPolygonsTextualView view = new PokerTrianglesTextualView(model);

    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);
    controller.playGame(model, view, deck, false, 4);

    String expected =
            " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 48" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♡, 2♠" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Game quit!" + System.lineSeparator() +
                    "State of game when quit:" + System.lineSeparator() +
                    " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 48" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♡, 2♠" + System.lineSeparator() +
                    "Score: 0";

    assertEquals(expected, output.toString());
  }

  /**
   * Tests that the controller quits properly when the input is "Q" (uppercase).
   */
  @Test
  public void testQuitCommandUppercaseQ() {
    StringReader input = new StringReader("Q");
    StringBuilder output = new StringBuilder();

    PokerPolygons<PlayingCard> model = new PokerTriangles(5);
    List<PlayingCard> deck = model.getNewDeck();

    PokerPolygonsTextualView view = new PokerTrianglesTextualView(model);
    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);
    controller.playGame(model, view, deck, false, 4);

    String expected =
            " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 48" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♡, 2♠" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Game quit!" + System.lineSeparator() +
                    "State of game when quit:" + System.lineSeparator() +
                    " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 48" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♡, 2♠" + System.lineSeparator() +
                    "Score: 0";

    assertEquals(expected, output.toString());
  }

  /**
   * Tests a successful "place" command.
   * User input: "place 2 3 1 q"
   * Expected: The model processes placeCardInPosition with indices converted
   * from 1-indexed to 0-indexed (i.e. (2-1, 3-1, 1-1) = (1, 2, 0)).
   */
  @Test
  public void testPlaceCommandValidInput() {
    StringReader input = new StringReader("place 2 3 1 q");
    StringBuilder output = new StringBuilder();

    PokerPolygons<PlayingCard> model = new PokerTriangles(5);
    List<PlayingCard> deck = model.getNewDeck();

    PokerPolygonsTextualView view = new PokerTrianglesTextualView(model);
    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);

    controller.playGame(model, view, deck, false, 4);

    // Construct expected output: initial state + move + updated state + quit
    String expectedOutput =
            " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 48" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♡, 2♠" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " 2♢  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 47" + System.lineSeparator() +
                    "Hand: 2♣, 2♡, 2♠, 3♣" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Game quit!" + System.lineSeparator() +
                    "State of game when quit:" + System.lineSeparator() +
                    " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " 2♢  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 47" + System.lineSeparator() +
                    "Hand: 2♣, 2♡, 2♠, 3♣" + System.lineSeparator() +
                    "Score: 0";

    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Tests a successful "discard" command.
   * User input: "discard 3 q"
   * Expected: The model processes discardCard with index (3-1)=2.
   */
  @Test
  public void testDiscardCommandValidInput() {
    StringReader input = new StringReader("discard 3 q");
    StringBuilder output = new StringBuilder();

    PokerPolygons<PlayingCard> model = new PokerTriangles(5);
    List<PlayingCard> deck = model.getNewDeck();

    PokerPolygonsTextualView view = new PokerTrianglesTextualView(model);
    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);
    controller.playGame(model, view, deck, false, 4);
    String expectedOutput =
            " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 48" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♡, 2♠" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 47" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♠, 3♣" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Game quit!" + System.lineSeparator() +
                    "State of game when quit:" + System.lineSeparator() +
                    " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 47" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♠, 3♣" + System.lineSeparator() +
                    "Score: 0";

    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Testing to see what happens when the user inputs zero as an argument for
   * placing a card on the board.
   */
  @Test
  public void testZeroAsInputToCommand() {
    // Input sequence: "place 0 3 1 q"
    StringReader input = new StringReader("place 0 3 1 q");
    StringBuilder output = new StringBuilder();

    PokerPolygons<PlayingCard> model = new PokerTriangles(5);
    List<PlayingCard> deck = model.getNewDeck();

    PokerPolygonsTextualView view = new PokerTrianglesTextualView(model);
    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);
    controller.playGame(model, view, deck, false, 4);

    // Construct expected output: initial board state + invalid move retry + final quit state
    String expectedOutput =
            " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 48" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♡, 2♠" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Game quit!" + System.lineSeparator() +
                    "State of game when quit:" + System.lineSeparator() +
                    " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 48" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♡, 2♠" + System.lineSeparator() +
                    "Score: 0";

    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Testing to see what happens when the user inputs zero as an argument for
   * discarding a card form the hand.
   */
  @Test
  public void testZeroAsInputToDiscard() {
    // Input sequence: "discard 0 q"
    StringReader input = new StringReader("discard 0 q");
    StringBuilder output = new StringBuilder();

    PokerPolygons<PlayingCard> model = new PokerTriangles(5);
    List<PlayingCard> deck = model.getNewDeck();

    PokerPolygonsTextualView view = new PokerTrianglesTextualView(model);
    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);
    controller.playGame(model, view, deck, false, 4);

    // Construct expected output: initial board state + invalid move retry + final quit state
    String expectedOutput =
            " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 48" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♡, 2♠" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Invalid move. Play again. Card index out of bounds of the hand: 0"
                    + System.lineSeparator() +
                    " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 48" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♡, 2♠" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Game quit!" + System.lineSeparator() +
                    "State of game when quit:" + System.lineSeparator() +
                    " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 48" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♡, 2♠" + System.lineSeparator() +
                    "Score: 0";

    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Tests playing the game to completion using a 5x5 board and making sure
   * the board, messages, and scoring renders properly.
   */
  @Test
  public void testPlayGameToCompletion() {
    StringReader input = new StringReader(
            "place 1 1 1 " +  // Place first card in the first row
                    "place 1 2 1 " +  // Place another in the second row
                    "place 1 2 2 " +
                    "place 1 3 1 " +
                    "place 1 3 2 " +
                    "place 1 3 3 " +
                    "place 1 4 1 " +
                    "place 1 4 2 " +
                    "place 1 4 3 " +
                    "place 1 4 4 " +
                    "place 1 5 1 " +
                    "place 1 5 2 " +
                    "place 1 5 3 " +
                    "place 1 5 4 " +
                    "place 1 5 5 " +
                    "q" // Quit after filling the board
    );
    StringBuilder output = new StringBuilder();

    PokerPolygons<PlayingCard> model = new PokerTriangles(5);
    List<PlayingCard> deck = model.getNewDeck();

    PokerPolygonsTextualView view = new PokerTrianglesTextualView(model);
    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);
    controller.playGame(model, view, deck, false, 5); // Hand size is 5

    String expectedOutput =
            " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 47" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♡, 2♠, 3♣" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +

                    " 2♣" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 46" + System.lineSeparator() +
                    "Hand: 2♢, 2♡, 2♠, 3♣, 3♢" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +

                    " 2♣" + System.lineSeparator() +
                    " 2♢  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 45" + System.lineSeparator() +
                    "Hand: 2♡, 2♠, 3♣, 3♢, 3♡" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +

                    " 2♣" + System.lineSeparator() +
                    " 2♢  2♡" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 44" + System.lineSeparator() +
                    "Hand: 2♠, 3♣, 3♢, 3♡, 3♠" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +

                    " 2♣" + System.lineSeparator() +
                    " 2♢  2♡" + System.lineSeparator() +
                    " 2♠  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 43" + System.lineSeparator() +
                    "Hand: 3♣, 3♢, 3♡, 3♠, 4♣" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +

                    " 2♣" + System.lineSeparator() +
                    " 2♢  2♡" + System.lineSeparator() +
                    " 2♠  3♣  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 42" + System.lineSeparator() +
                    "Hand: 3♢, 3♡, 3♠, 4♣, 4♢" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +

                    " 2♣" + System.lineSeparator() +
                    " 2♢  2♡" + System.lineSeparator() +
                    " 2♠  3♣  3♢" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 41" + System.lineSeparator() +
                    "Hand: 3♡, 3♠, 4♣, 4♢, 4♡" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +

                    " 2♣" + System.lineSeparator() +
                    " 2♢  2♡" + System.lineSeparator() +
                    " 2♠  3♣  3♢" + System.lineSeparator() +
                    " 3♡  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 40" + System.lineSeparator() +
                    "Hand: 3♠, 4♣, 4♢, 4♡, 4♠" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +

                    " 2♣" + System.lineSeparator() +
                    " 2♢  2♡" + System.lineSeparator() +
                    " 2♠  3♣  3♢" + System.lineSeparator() +
                    " 3♡  3♠  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 39" + System.lineSeparator() +
                    "Hand: 4♣, 4♢, 4♡, 4♠, 5♣" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +

                    " 2♣" + System.lineSeparator() +
                    " 2♢  2♡" + System.lineSeparator() +
                    " 2♠  3♣  3♢" + System.lineSeparator() +
                    " 3♡  3♠  4♣  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 38" + System.lineSeparator() +
                    "Hand: 4♢, 4♡, 4♠, 5♣, 5♢" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +

                    " 2♣" + System.lineSeparator() +
                    " 2♢  2♡" + System.lineSeparator() +
                    " 2♠  3♣  3♢" + System.lineSeparator() +
                    " 3♡  3♠  4♣  4♢" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 37" + System.lineSeparator() +
                    "Hand: 4♡, 4♠, 5♣, 5♢, 5♡" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +

                    " 2♣" + System.lineSeparator() +
                    " 2♢  2♡" + System.lineSeparator() +
                    " 2♠  3♣  3♢" + System.lineSeparator() +
                    " 3♡  3♠  4♣  4♢" + System.lineSeparator() +
                    " 4♡  __  __  __  __" + System.lineSeparator() +
                    "Deck: 36" + System.lineSeparator() +
                    "Hand: 4♠, 5♣, 5♢, 5♡, 5♠" + System.lineSeparator() +
                    "Score: 10" + System.lineSeparator() +

                    " 2♣" + System.lineSeparator() +
                    " 2♢  2♡" + System.lineSeparator() +
                    " 2♠  3♣  3♢" + System.lineSeparator() +
                    " 3♡  3♠  4♣  4♢" + System.lineSeparator() +
                    " 4♡  4♠  __  __  __" + System.lineSeparator() +
                    "Deck: 35" + System.lineSeparator() +
                    "Hand: 5♣, 5♢, 5♡, 5♠, 6♣" + System.lineSeparator() +
                    "Score: 10" + System.lineSeparator() +

                    " 2♣" + System.lineSeparator() +
                    " 2♢  2♡" + System.lineSeparator() +
                    " 2♠  3♣  3♢" + System.lineSeparator() +
                    " 3♡  3♠  4♣  4♢" + System.lineSeparator() +
                    " 4♡  4♠  5♣  __  __" + System.lineSeparator() +
                    "Deck: 34" + System.lineSeparator() +
                    "Hand: 5♢, 5♡, 5♠, 6♣, 6♢" + System.lineSeparator() +
                    "Score: 10" + System.lineSeparator() +

                    " 2♣" + System.lineSeparator() +
                    " 2♢  2♡" + System.lineSeparator() +
                    " 2♠  3♣  3♢" + System.lineSeparator() +
                    " 3♡  3♠  4♣  4♢" + System.lineSeparator() +
                    " 4♡  4♠  5♣  5♢  __" + System.lineSeparator() +
                    "Deck: 33" + System.lineSeparator() +
                    "Hand: 5♡, 5♠, 6♣, 6♢, 6♡" + System.lineSeparator() +
                    "Score: 10" + System.lineSeparator() +

                    "Game over. Score: 37" + System.lineSeparator();

    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Integration test to ensures the controller ignores invalid input,
   * waits for valid input, and correctly places a card after receiving a valid command.
   */
  @Test
  public void testIntegrationPlaceCardIgnoresNonsenseThenPlacesCard() {
    StringReader input = new StringReader("nonsense place nonsense 1 1 1 q");
    StringBuilder output = new StringBuilder();

    PokerPolygons<PlayingCard> model = new PokerTriangles(5);
    List<PlayingCard> deck = model.getNewDeck();
    PokerPolygonsTextualView view = new PokerTrianglesTextualView(model);
    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);

    controller.playGame(model, view, deck, false, 5);

    String expectedOutput =
            " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 47" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♡, 2♠, 3♣" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Invalid move. Play again. Unrecognized command: nonsense" +
                    System.lineSeparator() +
                    " 2♣" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 46" + System.lineSeparator() +
                    "Hand: 2♢, 2♡, 2♠, 3♣, 3♢" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Game quit!" + System.lineSeparator() +
                    "State of game when quit:" + System.lineSeparator() +
                    " 2♣" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 46" + System.lineSeparator() +
                    "Hand: 2♢, 2♡, 2♠, 3♣, 3♢" + System.lineSeparator() +
                    "Score: 0";

    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Integration test to ensures the controller ignores invalid input,
   * waits for valid input, and correctly discards a card after receiving a valid command.
   */
  @Test
  public void testIntegrationDiscardIgnoresNonsenseThenDiscardsCard() {
    StringReader input = new StringReader("random gibberish -5 discard blah 2\n\n q");
    StringBuilder output = new StringBuilder();

    PokerPolygons<PlayingCard> model = new PokerTriangles(5);
    List<PlayingCard> deck = model.getNewDeck();
    PokerPolygonsTextualView view = new PokerTrianglesTextualView(model);
    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);

    controller.playGame(model, view, deck, false, 5);

    String expectedOutput =
            " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 47" + System.lineSeparator() +
                    "Hand: 2♣, 2♢, 2♡, 2♠, 3♣" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Invalid move. Play again. Unrecognized command: random" +
                    System.lineSeparator() +
                    "Invalid move. Play again. Unrecognized command: gibberish" +
                    System.lineSeparator() +
                    "Invalid move. Play again. Unrecognized command: -5" + System.lineSeparator() +
                    " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 46" + System.lineSeparator() +
                    "Hand: 2♣, 2♡, 2♠, 3♣, 3♢" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Game quit!" + System.lineSeparator() +
                    "State of game when quit:" + System.lineSeparator() +
                    " __" + System.lineSeparator() +
                    " __  __" + System.lineSeparator() +
                    " __  __  __" + System.lineSeparator() +
                    " __  __  __  __" + System.lineSeparator() +
                    " __  __  __  __  __" + System.lineSeparator() +
                    "Deck: 46" + System.lineSeparator() +
                    "Hand: 2♣, 2♡, 2♠, 3♣, 3♢" + System.lineSeparator() +
                    "Score: 0";

    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Tests that the controller constructor throws an IllegalArgumentException
   * when passed a null Readable.
   */
  @Test
  public void testConstructorThrowsExceptionForNullReadable() {
    try {
      PokerPolygons<PlayingCard> model = new PokerTriangles(5);
      PokerPolygonsTextualView view = new PokerTrianglesTextualView(model);
      new PokerPolygonsTextualController(null,
              new StringBuilder()).playGame(model, view,
              model.getNewDeck(), false, 5);
    } catch (IllegalArgumentException e) {
      assertEquals("Readable (input) and Appendable (output) cannot be null",
              e.getMessage());
    }
  }

  /**
   * Tests that the controller constructor throws an IllegalArgumentException
   * when passed a null Appendable.
   */
  @Test
  public void testConstructorThrowsExceptionForNullAppendable() {
    try {
      PokerPolygons<PlayingCard> model = new PokerTriangles(5);
      PokerPolygonsTextualView view = new PokerTrianglesTextualView(model);
      new PokerPolygonsTextualController(new java.io.StringReader(""),
              null).playGame(model, view, model.getNewDeck(), false, 5);
    } catch (IllegalArgumentException e) {
      assertEquals("Readable (input) and Appendable (output) cannot be null",
              e.getMessage());
    }
  }

  /**
   * Tests that the controller constructor throws an IllegalArgumentException
   * when both Readable and Appendable are null.
   */
  @Test
  public void testConstructorThrowsExceptionForBothNull() {
    try {
      PokerPolygons<PlayingCard> model = new PokerTriangles(5);
      PokerPolygonsTextualView view = new PokerTrianglesTextualView(model);
      new PokerPolygonsTextualController(null, null).playGame(model, view,
              model.getNewDeck(), false, 5);
    } catch (IllegalArgumentException e) {
      assertEquals("Readable (input) and Appendable (output) cannot be null",
              e.getMessage());
    }
  }

  /**
   * Tests that playGame() throws an IllegalArgumentException when passed a null model.
   */
  @Test
  public void testPlayGameThrowsExceptionForNullModel() {
    try {
      PokerPolygonsTextualController controller =
              new PokerPolygonsTextualController(new java.io.StringReader(""),
                      new StringBuilder());
      PokerPolygonsTextualView view =
              new PokerTrianglesTextualView(new PokerTriangles(5));
      controller.playGame(null, view,
              new PokerTriangles(5).getNewDeck(), false, 5);
    } catch (IllegalArgumentException e) {
      assertEquals("Model or view cannot be null.", e.getMessage());
    }
  }

  /**
   * Tests that playGame() throws an IllegalStateException when input cannot be read from the
   * Readable while the game is in progress.
   */
  @Test
  public void testPlayGameThrowsExceptionWhenReadableFails() {
    try {
      Reader failingReader = new Reader() {
        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
          throw new IOException("Readable input failure");
        }

        @Override
        public void close() throws IOException {
        }
      };

      PokerPolygons<PlayingCard> model = new PokerTriangles(5);
      PokerPolygonsTextualView view = new PokerTrianglesTextualView(model);
      PokerPolygonsTextualController controller = new PokerPolygonsTextualController(failingReader,
              new StringBuilder());
      List<PlayingCard> deck = model.getNewDeck();

      controller.playGame(model, view, deck, false, 5);
    } catch (IllegalStateException e) {
      assertEquals("Failed to read input.", e.getMessage());
    }
  }

  /**
   * Tests that playGame() throws an IllegalStateException when output cannot be appended to the
   * Appendable while the game is in progress.
   */
  @Test
  public void testPlayGameThrowsExceptionWhenAppendableFails() {
    try {
      Appendable failingAppendable = new Appendable() {
        @Override
        public Appendable append(CharSequence csq) throws IOException {
          throw new IOException("Appendable output failure");
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) throws IOException {
          throw new IOException("Appendable output failure");
        }

        @Override
        public Appendable append(char c) throws IOException {
          throw new IOException("Appendable output failure");
        }
      };

      PokerPolygons<PlayingCard> model = new PokerTriangles(5);
      PokerPolygonsTextualView view = new PokerTrianglesTextualView(model);
      PokerPolygonsTextualController controller = new PokerPolygonsTextualController(new
              StringReader("q"), failingAppendable);
      List<PlayingCard> deck = model.getNewDeck();

      controller.playGame(model, view, deck, false, 5);
    } catch (IllegalStateException e) {
      assertEquals("Failed to append output.", e.getMessage());
    }
  }
}