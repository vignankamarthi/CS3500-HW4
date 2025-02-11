package cs3500.pokerpolygons.controller;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygonsSimple;
import cs3500.pokerpolygons.model.hw02.Ranks;
import cs3500.pokerpolygons.model.hw02.StandardPlayingCard;
import cs3500.pokerpolygons.model.hw02.Suits;
import cs3500.pokerpolygons.view.PokerPolygonsTextualView;
import cs3500.pokerpolygons.view.PokerTrianglesTextualViewSimple;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the PokerPolygonsTextualController class using the mocks (view and model).
 */
public class PokerPolygonsTextualControllerTest {

  /**
   * Tests that the controller quits properly when the input is "q" (lowercase).
   */
  @Test
  public void testQuitCommandLowercaseQ() {
    // Creating input containing "q"
    StringReader input = new StringReader("q");
    StringBuilder output = new StringBuilder();

    // Creating a mock model with a dummy log and preset score.
    StringBuilder modelLog = new StringBuilder();
    PokerPolygonsSimple<PlayingCard> mockModel = new PokerPolygonsSimple<>(modelLog);
    mockModel.setScore(0);

    // Using the mock textual view.
    PokerTrianglesTextualViewSimple<PlayingCard> view =
            new PokerTrianglesTextualViewSimple<>(mockModel);

    // Using an empty deck
    List<PlayingCard> deck = new ArrayList<>();

    // Creating the controller with the input and output.
    PokerPolygonsTextualController controller =
            new PokerPolygonsTextualController(input, output);

    // Calling playGame; the quit command should cause immediate output.
    controller.playGame(mockModel, view, deck, false, 4);

    // Building the expected output string.
    String expected = "Mock view state" + System.lineSeparator()
            + "Score: 0"  + System.lineSeparator()
            + "Game quit!"  + System.lineSeparator()
            + "State of game when quit:" + System.lineSeparator()
            + "Score: " + mockModel.getScore();

    assertEquals(expected, output.toString());
  }

  /**
   * Tests that the controller quits properly when the input is "Q" (uppercase).
   */
  @Test
  public void testQuitCommandUppercaseQ() {
    StringReader input = new StringReader("Q");
    StringBuilder output = new StringBuilder();

    StringBuilder modelLog = new StringBuilder();
    PokerPolygonsSimple<PlayingCard> mockModel = new PokerPolygonsSimple<>(modelLog);
    mockModel.setScore(0);

    // Using the mock textual view
    PokerTrianglesTextualViewSimple<PlayingCard> view =
            new PokerTrianglesTextualViewSimple<>(mockModel);

    // Using an empty deck
    List<PlayingCard> deck = new ArrayList<>();

    // Creating the controller with the input and output.
    PokerPolygonsTextualController controller =
            new PokerPolygonsTextualController(input, output);

    // Calling play game with an uppercase Q as the input
    controller.playGame(mockModel, view, deck, false, 4);

    // Building the expected output string.
    String expected = "Mock view state" + System.lineSeparator()
            + "Score: 0"  + System.lineSeparator()
            + "Game quit!"  + System.lineSeparator()
            + "State of game when quit:" + System.lineSeparator()
            + "Score: " + mockModel.getScore();

    assertEquals(expected, output.toString());
  }

  /**
   * Helper method to create a dummy deck with 10 cards.
   * It iterates over Ranks and Suits and returns the first 10 cards.
   */
  private List<PlayingCard> createDummyDeck() {
    List<PlayingCard> deck = new ArrayList<>();
    for (Ranks rank : Ranks.values()) {
      for (Suits suit : Suits.values()) {
        deck.add(new StandardPlayingCard(rank, suit));
        if (deck.size() == 10) {
          return deck;
        }
      }
    }
    return deck;
  }

  // TODO: Understand these tests, then add JuNit Test and fix them in your implementation
  /**
   * Tests a successful "place" command.
   * User input: "place 2 3 1 q"
   * Expected: The mock model log contains a call to placeCardInPosition(1, 2, 0)
   */
  @Test
  public void testPlaceCommandValidInput() {
    StringReader input = new StringReader("place 2 3 1 q");
    StringBuilder output = new StringBuilder();
    StringBuilder modelLog = new StringBuilder();
    PokerPolygonsSimple<PlayingCard> mockModel = new PokerPolygonsSimple<>(modelLog);
    mockModel.setScore(10);
    List<PlayingCard> deck = createDummyDeck();

    // Using the simple textual view
    PokerTrianglesTextualViewSimple<PlayingCard> view =
            new PokerTrianglesTextualViewSimple<>(mockModel);

    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);
    controller.playGame(mockModel, view, deck, false, 4);

    // Check that the log contains the expected call.
    // User entered 2,3,1 so converted indices: (2-1,3-1,1-1) = (1,2,0)
    assertTrue(modelLog.toString().contains("placeCardInPosition(1, 2, 0)"));
  }

  /**
   * Tests a successful "discard" command.
   * User input: "discard 3 q"
   * Expected: The mock model log contains a call to discardCard(2)
   */
  @Test
  public void testDiscardCommandValidInput() {
    StringReader input = new StringReader("discard 3 q");
    StringBuilder output = new StringBuilder();
    StringBuilder modelLog = new StringBuilder();
    PokerPolygonsSimple<PlayingCard> mockModel = new PokerPolygonsSimple<>(modelLog);
    mockModel.setScore(20);
    List<PlayingCard> deck = createDummyDeck();

    // Using the simple textual view
    PokerTrianglesTextualViewSimple<PlayingCard> view =
            new PokerTrianglesTextualViewSimple<>(mockModel);

    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);
    controller.playGame(mockModel, view, deck, false, 4);

    // Check that the log contains the expected call.
    // User entered 3 so index becomes (3-1)=2
    assertTrue(modelLog.toString().contains("discardCard(2)"));
  }


}
