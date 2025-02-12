package cs3500.pokerpolygons.controller;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.PokerPolygonsSimple;
import cs3500.pokerpolygons.model.hw02.Ranks;
import cs3500.pokerpolygons.model.hw02.StandardPlayingCard;
import cs3500.pokerpolygons.model.hw02.Suits;
import cs3500.pokerpolygons.view.PokerPolygonsTextualView;
import cs3500.pokerpolygons.view.PokerTrianglesTextualViewSimple;
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
            + "Mock view state" + System.lineSeparator()
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
            + "Mock view state" + System.lineSeparator()
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

  /**
   * Testing to see what happens when the user inputs zero as an argument for
   * placing a card on the board.
   */
  @Test
  public void testZeroAsInputToCommand() {
    StringReader input = new StringReader("place 0 3 1 q");
    StringBuilder output = new StringBuilder();

    StringBuilder modelLog = new StringBuilder();
    PokerPolygonsSimple<PlayingCard> mockModel = new PokerPolygonsSimple<>(modelLog);
    List<PlayingCard> deck = createDummyDeck();

    // Using the mock view
    PokerTrianglesTextualViewSimple<PlayingCard> view =
            new PokerTrianglesTextualViewSimple<>(mockModel);

    // Create the controller and start the game
    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);
    controller.playGame(mockModel, view, deck, false, 4);

    // Construct the expected output with the correct sequence
    String expectedOutput =
            "Mock view state" + System.lineSeparator() +  // Initial board print
                    "Score: 0" + System.lineSeparator() +
                    "Mock view state" + System.lineSeparator() +  // Retry board print
                    "Score: 0" + System.lineSeparator() +
                    "Game quit!" + System.lineSeparator() +  // Quit message
                    "State of game when quit:" + System.lineSeparator() +
                    "Mock view state" + System.lineSeparator() +  // Final quit board print
                    "Score: 0";

    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Testing to see what happens when the user inputs zero as an argument for
   * discarding a card from the hand.
   */
  @Test
  public void testZeroAsInputToDiscard() {
    StringReader input = new StringReader("discard 0 q");
    StringBuilder output = new StringBuilder();

    StringBuilder modelLog = new StringBuilder();
    PokerPolygonsSimple<PlayingCard> mockModel = new PokerPolygonsSimple<>(modelLog);
    List<PlayingCard> deck = createDummyDeck();

    // Using the mock view
    PokerTrianglesTextualViewSimple<PlayingCard> view =
            new PokerTrianglesTextualViewSimple<>(mockModel);

    // Create the controller and start the game
    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);
    controller.playGame(mockModel, view, deck, false, 4);

    // Construct the expected output with the correct sequence
    String expectedOutput =
            "Mock view state" + System.lineSeparator() +  // Initial board print
                    "Score: 0" + System.lineSeparator() +
                    "Mock view state" + System.lineSeparator() +  // Retry board print
                    "Score: 0" + System.lineSeparator() +
                    "Game quit!" + System.lineSeparator() +  // Quit message
                    "State of game when quit:" + System.lineSeparator() +
                    "Mock view state" + System.lineSeparator() +  // Final quit board print
                    "Score: 0";

    assertEquals(expectedOutput, output.toString());
  }

  @Test
  public void testControllerWithMockModelAndView() {
    StringReader input =
            new StringReader("place 1 1 1 place 2 2 2 place 3 3 3 place 4 4 4 place 5 5 5 q");
    StringBuilder log = new StringBuilder();
    StringBuilder output = new StringBuilder();

    PokerPolygons<PlayingCard> model = new PokerPolygonsSimple<>(log);
    List<PlayingCard> deck = model.getNewDeck();

    // Using the mock model and mock view, but real controller
    PokerTrianglesTextualViewSimple<PlayingCard> view =
            new PokerTrianglesTextualViewSimple<>(model);
    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);

    controller.playGame(model, view, deck, false, 5);

    // Expected log interactions with mock model
    String expectedLog =
            "getNewDeck() called. " +
                    "startGame(deckSize = 0, shuffle = false, handSize = 5) called. " +
                    "getScore() called. " +
                    "placeCardInPosition(0, 0, 0) called. " +
                    "isGameOver() called. " +
                    "getScore() called. " +
                    "isGameOver() called. " +
                    "placeCardInPosition(1, 1, 1) called. " +
                    "isGameOver() called. " +
                    "getScore() called. " +
                    "isGameOver() called. " +
                    "placeCardInPosition(2, 2, 2) called. " +
                    "isGameOver() called. " +
                    "getScore() called. " +
                    "isGameOver() called. " +
                    "placeCardInPosition(3, 3, 3) called. " +
                    "isGameOver() called. " +
                    "getScore() called. " +
                    "isGameOver() called. " +
                    "placeCardInPosition(4, 4, 4) called. " +
                    "isGameOver() called. " +
                    "getScore() called. " +
                    "isGameOver() called. " +
                    "getScore() called. ";

    assertEquals(expectedLog, log.toString());

    // Expected output verification
    String expectedOutput =
            "Mock view state" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Mock view state" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Mock view state" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Mock view state" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Mock view state" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Mock view state" + System.lineSeparator() +
                    "Score: 0" + System.lineSeparator() +
                    "Game quit!" + System.lineSeparator() +
                    "State of game when quit:" + System.lineSeparator() +
                    "Mock view state" + System.lineSeparator() +
                    "Score: 0";

    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Tests that the controller ignores invalid input (nonsense),
   * waits for valid input, and correctly places a card after receiving a proper command.
   *
   */
  @Test
  public void testPlaceCardIgnoresNonsenseThenPlacesCard() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple model = new PokerPolygonsSimple(log);
    PokerPolygonsTextualView view = new PokerTrianglesTextualViewSimple<>(model);

    Readable input = new StringReader("nonsense nonsense -1 place nonsense 1 2 3\n\n q");
    Appendable output = new StringBuilder();
    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);

    controller.playGame(model, view, new ArrayList<>(), false, 5);

    String expectedLog =
            "startGame(deckSize = 0, shuffle = false, handSize = 5) called. " +
                    "getScore() called. " +
                    "isGameOver() called. " +
                    "isGameOver() called. " +
                    "isGameOver() called. " +
                    "placeCardInPosition(0, 1, 2) called. " +
                    "isGameOver() called. " +
                    "getScore() called. " +
                    "isGameOver() called. " +
                    "getScore() called. ";

    assertEquals(expectedLog, model.getLog());
  }

  /**
   * Tests that the controller ignores invalid input (nonsense),
   * waits for valid input, and correctly discards a card after receiving a proper command.
   */
  @Test
  public void testDiscardIgnoresNonsenseThenDiscardsCard() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple model = new PokerPolygonsSimple(log);
    PokerPolygonsTextualView view = new PokerTrianglesTextualViewSimple<>(model);

    Readable input = new StringReader("random gibberish -5 discard blah 2\n\n q");
    Appendable output = new StringBuilder();
    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(input, output);

    controller.playGame(model, view, new ArrayList<>(), false, 5);

    String expectedLog =
            "startGame(deckSize = 0, shuffle = false, handSize = 5) called. " +
                    "getScore() called. " +
                    "isGameOver() called. " +
                    "isGameOver() called. " +
                    "isGameOver() called. " +
                    "discardCard(1) called" +
                    "getScore() called. " +
                    "isGameOver() called. " +
                    "getScore() called. ";

    assertEquals(expectedLog, model.getLog());
  }


}
