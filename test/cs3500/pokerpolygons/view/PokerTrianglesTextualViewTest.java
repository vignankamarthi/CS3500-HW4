package cs3500.pokerpolygons.view;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;
import cs3500.pokerpolygons.model.hw02.Ranks;
import cs3500.pokerpolygons.model.hw02.StandardPlayingCard;
import cs3500.pokerpolygons.model.hw02.Suits;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

/**
 * To test the String rendering of the PokerTrianglesView class.
 */
public class PokerTrianglesTextualViewTest {
  private PokerTrianglesTextualView<PlayingCard> emptyView;
  private PokerTrianglesTextualView<PlayingCard> populatedView;

  @Before
  public void setUp() {
    Random seededRandom = new Random(8); // Ensure deterministic deck order

    // Create an empty game (side length 5) using a seeded deck
    PokerTriangles emptyGame = new PokerTriangles(5, new Random(8));
    emptyGame.startGame(getSampleDeck(), true, 4);
    emptyView = new PokerTrianglesTextualView<>(emptyGame);

    // Create a populated game (side length 7) using a seeded deck
    PokerTriangles populatedGame = new PokerTriangles(7, new Random(8));
    populatedGame.startGame(getSampleDeck(), false, 4);
    populatedView = new PokerTrianglesTextualView<>(populatedGame);
  }

  /**
   * Testing for an empty board with a random seed.
   */
  @Test
  public void testEmptyBoardToString() {
    String expected =
            " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 48\n" +
                    "Hand: J♢, 8♢, 3♠, 2♣";

    assertEquals(expected, emptyView.toString());
  }

  /**
   * Testing with a random board without randomizing.
   */
  @Test
  public void testPopulatedBoardToString() {
    String expected =
            " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    " __  __  __  __  __  __\n" +
                    " __  __  __  __  __  __  __\n" +
                    "Deck: 48\n" +
                    "Hand: 2♣, 2♢, 2♡, 2♠";

    assertEquals(expected, populatedView.toString());
  }

  /**
   * Generates a sample deck with a fixed order using a seeded Random.
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
   * Testing rendering on the diagonal.
   */
  @Test
  public void testDetectHandsOnDiagonal() {
    PokerTriangles game = new PokerTriangles(5, new Random(8));
    game.startGame(getSampleDeck(), true, 5);

    // Place cards diagonally
    game.placeCardInPosition(0, 0, 0); // First row, first column
    game.placeCardInPosition(1, 1, 1);
    game.placeCardInPosition(2, 2, 2);
    game.placeCardInPosition(3, 3, 3);
    game.placeCardInPosition(4, 4, 4);

    PokerTrianglesTextualView<PlayingCard> view = new PokerTrianglesTextualView<>(game);

    String expected =
            " J♢\n" +
                    " __  3♠\n" +
                    " __  __  Q♡\n" +
                    " __  __  __  5♣\n" +
                    " __  __  __  __  6♢\n" +
                    "Deck: 42\n" +
                    "Hand: 8♢, 2♣, 4♢, 5♢, 6♣";

    assertEquals(expected, view.toString());
  }

  /**
   * Testing formatting with 10 in the hand.
   */
  @Test
  public void testEmptyBoardHandFormattingWithTen() {
    PokerTriangles game = new PokerTriangles(5, new Random(8));

    // Construct a deck where we ensure a 10 is in the hand
    List<PlayingCard> deck = new ArrayList<>();
    deck.add(new StandardPlayingCard(Ranks.TEN, Suits.SPADES));
    deck.add(new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS));
    deck.add(new StandardPlayingCard(Ranks.THREE, Suits.DIAMONDS));
    deck.add(new StandardPlayingCard(Ranks.SEVEN, Suits.CLUBS));

    // Fill the rest of the deck with arbitrary cards
    for (Ranks rank : Ranks.values()) {
      for (Suits suit : Suits.values()) {
        if (deck.size() < 52) {
          deck.add(new StandardPlayingCard(rank, suit));
        }
      }
    }

    // Start the game with the controlled deck
    game.startGame(deck, true, 5);

    PokerTrianglesTextualView<PlayingCard> view = new PokerTrianglesTextualView<>(game);

    String expected =
            " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 47\n" +
                    "Hand: 10♣, 7♣, 2♡, 10♠, J♢";

    assertEquals(expected, view.toString());
  }

  /**
   * Testing the formatting behavior with tens all over the place.
   */
  @Test
  public void testBoardRenderingWithTensInFirstColumnAndMiddle() {
    // Ensure the 10s are at the front of the deck
    List<PlayingCard> deck = getSampleDeckWithLeadingTens();

    PokerTriangles game = new PokerTriangles(5, new Random(8));
    game.startGame(deck, false, 5); // No shuffle to maintain order

    // Place 10s in the first column (edge)
    game.placeCardInPosition(0, 0, 0); // First row, first column (10♠)
    game.placeCardInPosition(1, 1, 0); // Second row, first column (10♦)
    game.placeCardInPosition(2, 2, 0); // Third row, first column (10♣)
    game.placeCardInPosition(3, 3, 0); // Fourth row, first column (10♥)
    game.placeCardInPosition(4, 4, 0); // Fifth row, first column (10♠)

    // Place 10s in the middle
    game.placeCardInPosition(0, 2, 1); // Middle of row 3 (10♦)
    game.placeCardInPosition(0, 3, 2); // Middle of row 4 (10♣)
    game.placeCardInPosition(0, 4, 3); // Middle of row 5 (10♥)

    PokerTrianglesTextualView<PlayingCard> correctView = new PokerTrianglesTextualView<>(game);

    String expected =
            "10♠\n" +
                    "10♣  __\n" +
                    " 2♣ 10♢  __\n" +
                    " 2♡  __ 10♡  __\n" +
                    " 3♣  __  __  2♢  __\n" +
                    "Deck: 39\n" +
                    "Hand: 2♠, 3♢, 3♡, 3♠, 4♣";

    assertEquals(expected, correctView.toString());
  }


  /**
   * Test that a larger board properly renders when filled.
   */
  @Test
  public void testFilledLargeBoardView() {
    PokerTriangles largeGame = new PokerTriangles(10, new Random(8));
    largeGame.startGame(getLargeDeck(), false, 10);

    // Fill the board completely
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col <= row; col++) {
        largeGame.placeCardInPosition(0, row, col);
      }
    }

    PokerTrianglesTextualView<PlayingCard> largeView = new PokerTrianglesTextualView<>(largeGame);
    String boardString = largeView.toString();

    // Check that there are no "__" placeholders left (meaning the board is filled)
    assertFalse(boardString.contains("__"));
  }

  /**
   * Generates a large enough deck to support a size 10 board.
   */
  private List<PlayingCard> getLargeDeck() {
    List<PlayingCard> deck = new ArrayList<>();

    // Ensuring at least 100 cards to cover large boards and hand sizes
    for (int i = 0; i < 2; i++) { // Duplicate a standard deck to ensure enough cards
      for (Ranks rank : Ranks.values()) {
        for (Suits suit : Suits.values()) {
          deck.add(new StandardPlayingCard(rank, suit));
        }
      }
    }

    return deck;
  }


  /**
   * Generates a sample deck with 10s at the front to ensure correct placement.
   */
  private List<PlayingCard> getSampleDeckWithLeadingTens() {
    List<PlayingCard> deck = new ArrayList<>();

    // Ensure the 10s are at the front
    deck.add(new StandardPlayingCard(Ranks.TEN, Suits.SPADES));
    deck.add(new StandardPlayingCard(Ranks.TEN, Suits.DIAMONDS));
    deck.add(new StandardPlayingCard(Ranks.TEN, Suits.CLUBS));
    deck.add(new StandardPlayingCard(Ranks.TEN, Suits.HEARTS));

    // Add the rest of the deck
    for (Ranks rank : Ranks.values()) {
      if (rank != Ranks.TEN) { // Skip 10s as they are already added
        for (Suits suit : Suits.values()) {
          deck.add(new StandardPlayingCard(rank, suit));
        }
      }
    }
    return deck;
  }

  /**
   * Tests that render correctly appends the game state to an empty Appendable.
   */
  @Test
  public void testRenderAppendsToEmptyAppendable() {
    PokerTriangles game = new PokerTriangles(5, new Random(8));
    game.startGame(getSampleDeck(), true, 4);
    PokerTrianglesTextualView<PlayingCard> view = new PokerTrianglesTextualView<>(game);

    StringBuilder output = new StringBuilder();
    try {
      view.render(output);
    } catch (IOException e) {
      fail("IOException should not have been thrown.");
    }

    assertEquals(game.toString(), output.toString());
  }

  /**
   * Tests that render correctly appends the game state to an Appendable.
   */
  @Test
  public void testRenderAppendsToAppendable() {
    PokerTriangles game = new PokerTriangles(5, new Random(8));
    game.startGame(getSampleDeck(), true, 4);
    PokerTrianglesTextualView<PlayingCard> view = new PokerTrianglesTextualView<>(game);

    StringBuilder output = new StringBuilder("Game");
    try {
      view.render(output);
    } catch (IOException e) {
      fail("IOException should not have been thrown.");
    }

    assertEquals("Game" + game.toString(), output.toString());
  }


  /**
   * Tests that render throws an IllegalArgumentException when given a null Appendable.
   */
  @Test
  public void testRenderThrowsExceptionForNullAppendable() {
    PokerTriangles game = new PokerTriangles(5, new Random(8));
    game.startGame(getSampleDeck(), true, 4);
    PokerTrianglesTextualView<PlayingCard> view = new PokerTrianglesTextualView<>(game);

    assertThrows(IllegalArgumentException.class, () -> view.render(null));
  }

  /**
   * Tests that constructing a PokerTrianglesTextualView with a null model
   * throws an IllegalArgumentException.
   */
  @Test
  public void testConstructorThrowsExceptionForNullModel() {
    try {
      new PokerTrianglesTextualView<PlayingCard>(null);
      fail("Expected IllegalArgumentException to be thrown, but it was not.");
    } catch (IllegalArgumentException e) {
    }
  }

}