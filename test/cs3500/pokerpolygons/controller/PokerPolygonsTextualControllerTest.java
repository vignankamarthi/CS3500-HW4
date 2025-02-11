package cs3500.pokerpolygons.controller;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygonsSimple;
import cs3500.pokerpolygons.view.PokerTrianglesTextualViewSimple;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

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
    String expected = "Game quit!\n"
            + "State of game when quit:" + view.toString() + "\n"
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

    String expected = "Game quit!\n"
            + "State of game when quit:" + view.toString() + "\n"
            + "Score: " + mockModel.getScore();

    assertEquals(expected, output.toString());
  }
}