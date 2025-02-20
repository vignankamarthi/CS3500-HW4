package cs3500.pokerpolygons.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.Ranks;
import cs3500.pokerpolygons.model.hw02.StandardPlayingCard;
import cs3500.pokerpolygons.model.hw02.Suits;
import cs3500.pokerpolygons.model.hw04.PokerRectangles;

//TODO: Fix this test class.
/**
 * To test the String rendering of the PokerRectanglesView class.
 */
public class PokerRectanglesTextualViewTest {

  @Before
  public void setUp() {
    // Create an empty game (5 x 5 rectangle) using a seeded Random for deterministic behavior.
    PokerRectangles emptyGame = new PokerRectangles(5, 5, new Random(8));
    emptyGame.startGame(getSampleDeck(), true, 5);
    PokerRectanglesTextualView<PlayingCard> emptyView = new PokerRectanglesTextualView<>(emptyGame);

    // Create a populated game (5 x 5 rectangle) with no shuffling.
    PokerRectangles populatedGame = new PokerRectangles(5, 5, new Random(8));
    populatedGame.startGame(getSampleDeck(), false, 5);
    PokerRectanglesTextualView<PlayingCard> populatedView = new PokerRectanglesTextualView<>(populatedGame);
  }

  /**
   * Generates a sample deck with a fixed order.
   *
   * @return a list of playing cards in fixed order
   */
  private List<PlayingCard> getSampleDeck() {
    List<PlayingCard> deck = new ArrayList<>();
    for (Ranks rank : Ranks.values()) {
      for (Suits suit : Suits.values()) {
        deck.add(new StandardPlayingCard(rank, suit));
      }
    }
    return deck;
  }

  /**
   * Tests that an empty 5x5 board is rendered exactly as expected.
   * Expected output:
   * Each of the 5 rows has 5 cells displayed as "__", followed by a line for the deck and one for the hand.
   * Since no board cards are drawn from the deck at game start, the deck remains at 52 - handSize = 47.
   */
  @Test
  public void testEmptyBoardToString() {
    PokerRectangles game = new PokerRectangles(5, 5, new Random(8));
    List<PlayingCard> deck = getSampleDeck();
    game.startGame(deck, true, 5);
    PokerRectanglesTextualView<PlayingCard> view = new PokerRectanglesTextualView<>(game);

    // Expected board: 5 rows of 5 "__" tokens separated by spaces.
    // Then "Deck: 47" (since 5 cards are in hand) and "Hand: ..." with the 5 cards in hand.
    String expected =
            " __  __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 47\n" +
                    "Hand: J♢, 8♢, 3♠, 2♣, Q♡"; // Adjust the hand string as per your seeded deck output.

    String actual = view.toString();
    assertEquals(expected, actual);
  }

  /**
   * Tests that a populated 5x5 board is rendered exactly as expected.
   * This test uses a game started without shuffling so that the board is immediately populated.
   */
  @Test
  public void testPopulatedBoardToString() {
    PokerRectangles game = new PokerRectangles(5, 5, new Random(8));
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);
    PokerRectanglesTextualView<PlayingCard> view = new PokerRectanglesTextualView<>(game);

    // Populate the entire 5x5 board with the next 25 cards
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        if (!game.isGameOver() && !deck.isEmpty()) {
          int cardIdx = game.getHand().size() - 1; // Use the last card in hand
          game.placeCardInPosition(cardIdx, row, col); // Place it on the board
          if (!deck.isEmpty()) {
            game.getHand().add(deck.remove(0)); // Draw a new card from deck to hand
          }
        }
      }
    }


    String expected =
            " 3♣  3♢  3♡  3♠  4♣\n" +
                    " 4♢  4♡  4♠  5♣  5♢\n" +
                    " 5♡  5♠  6♣  6♢  6♡\n" +
                    " 6♠  7♣  7♢  7♡  7♠\n" +
                    " 8♣  8♢  8♡  8♠  9♣\n" +
                    "Deck: 22\n" +
                    "Hand: 2♣, 2♢, 2♡, 2♠, 9♢";

    String actual = view.toString();
    assertEquals(expected, actual);
  }

  /**
   * Tests that render correctly appends the game state to an empty Appendable.
   */
  @Test
  public void testRenderAppendsToEmptyAppendable() {
    PokerRectangles game = new PokerRectangles(5, 5, new Random(8));
    game.startGame(getSampleDeck(), true, 4);
    PokerRectanglesTextualView<PlayingCard> view = new PokerRectanglesTextualView<>(game);

    StringBuilder output = new StringBuilder();
    try {
      view.render(output);
    } catch (IOException e) {
      fail("IOException should not have been thrown.");
    }

    assertEquals(game.toString(), output.toString());
  }

  /**
   * Tests that render correctly appends the game state to an Appendable that already has content.
   */
  @Test
  public void testRenderAppendsToAppendable() {
    PokerRectangles game = new PokerRectangles(5, 5, new Random(8));
    game.startGame(getSampleDeck(), true, 4);
    PokerRectanglesTextualView<PlayingCard> view = new PokerRectanglesTextualView<>(game);

    StringBuilder output = new StringBuilder("State: ");
    try {
      view.render(output);
    } catch (IOException e) {
      fail("IOException should not have been thrown.");
    }

    assertEquals("State: " + game.toString(), output.toString());
  }

  /**
   * Tests that constructing a PokerRectanglesTextualView with a null model
   * throws an IllegalArgumentException.
   */
  @Test
  public void testConstructorThrowsExceptionForNullModel() {
    assertThrows(IllegalArgumentException.class, () -> new PokerRectanglesTextualView<PlayingCard>(null));
  }

  /**
   * To test that 10-value cards are formatted correctly on a 5x5 board.
   */
  @Test
  public void testTenCardsFormatting() {
    PokerRectangles game = new PokerRectangles(5, 5, new Random(8));
    List<PlayingCard> deck = new ArrayList<>();

    // Add 10s of each suit to the deck for testing
    deck.add(new StandardPlayingCard(Ranks.TEN, Suits.SPADES));
    deck.add(new StandardPlayingCard(Ranks.TEN, Suits.DIAMONDS));
    deck.add(new StandardPlayingCard(Ranks.TEN, Suits.HEARTS));
    deck.add(new StandardPlayingCard(Ranks.TEN, Suits.CLUBS));

    // Fill the rest of the deck with dummy cards
    for (int i = 0; i < 25 - 4; i++) { // 25 cells - 4 tens = 21 more cards
      deck.add(new StandardPlayingCard(Ranks.TWO, Suits.SPADES));   // 2♠ as filler
    }
    for (int i = 0; i < 5; i++) {
      deck.add(new StandardPlayingCard(Ranks.TWO, Suits.DIAMONDS)); // 2♢ for hand
    }

    game.startGame(deck, false, 5);

    // Populate the first 4 cells of the board with 10s (one per row, first column)
    for (int row = 0; row < 4; row++) {
      game.placeCardInPosition(0, row, 0);
      if (!deck.isEmpty()) {
        game.getHand().add(deck.remove(0));
      }
    }

    // Fill remaining board cells with dummy cards (2s)
    for (int row = 0; row < 5; row++) {
      for (int col = 1; col < 5; col++) { // Start from col 1, skip first column
        if (!game.isGameOver() && !deck.isEmpty()) {
          game.placeCardInPosition(0, row, col);
          if (!deck.isEmpty()) {
            game.getHand().add(deck.remove(0));
          }
        }
      }
    }

    PokerRectanglesTextualView<PlayingCard> view = new PokerRectanglesTextualView<>(game);

    String expected =
            "10♠  2♠  2♠  2♠  2♠\n" +
                    "10♢  2♠  2♠  2♠  2♠\n" +
                    "10♡  2♠  2♠  2♠  2♠\n" +
                    "10♣  2♠  2♠  2♠  2♠\n" +
                    " __  2♠  2♠  2♠  2♠\n" +
                    "Deck: 1\n" +
                    "Hand: 2♠, 2♢, 2♢, 2♢, 2♢";

    String actual = view.toString();
    assertEquals(expected, actual);
  }


}