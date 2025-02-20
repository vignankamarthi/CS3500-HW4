package cs3500.pokerpolygons.model.hw04;


import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.Ranks;
import cs3500.pokerpolygons.model.hw02.Suits;
import cs3500.pokerpolygons.model.hw02.StandardPlayingCard;
import cs3500.pokerpolygons.view.PokerTrianglesTextualView;

import static org.junit.Assert.assertEquals;

/**
 * In-depth testing for LoosePokerTrianglesScoring class including exceptions, general behavior,
 * and edge case behavior.
 */
public class LoosePokerTrianglesScoringTest {

  /**
   * Tests that a middle straight flush (5-6-7-8-9 of DIAMONDS)
   * is correctly scored as 75 points using Looser PokerTriangles rules.
   */
  @Test
  public void testScoreForMidStraightFlush() {
    LoosePokerTriangles game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Shuffle deck before adding custom straight flush cards.
    Collections.shuffle(deck);

    // Insert a middle straight flush (5-6-7-8-9 of DIAMONDS, consecutive for traditional, skipped for loose).
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.DIAMONDS));

    game.startGame(deck, false, 5);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    // Middle straight flush should score 75 (traditional consecutive, also skipped run).
    assertEquals(75, game.getScore());
  }

  /**
   * Tests that four of a kind is correctly scored as 50 points.
   */
  @Test
  public void testScoreForFourOfAKind() {
    LoosePokerTriangles game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Insert four Aces and one filler card.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    assertEquals(50, game.getScore());
  }

  /**
   * Tests that a full house is correctly scored as 25 points.
   */
  @Test
  public void testScoreForFullHouse() {
    LoosePokerTriangles game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Insert three Aces and two Jacks.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.CLUBS));

    game.startGame(deck, false, 5);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    assertEquals(25, game.getScore());
  }

  /**
   * Tests that a flush (all cards of the same suit, not straight) is correctly scored as 20 points.
   */
  @Test
  public void testScoreForFlush() {
    LoosePokerTriangles game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    assertEquals(20, game.getScore());
  }

  /**
   * Tests that a straight (cards in consecutive order, but not flush) is
   * correctly scored as 15 points.
   */
  @Test
  public void testScoreForStraight() {
    LoosePokerTriangles game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Insert 5-6-7-8-9 of mixed suits for a straight.
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    assertEquals(15, game.getScore());
  }

  /**
   * Tests that three of a kind is correctly scored as 10 points.
   */
  @Test
  public void testScoreForThreeOfAKind() {
    LoosePokerTriangles game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Insert three Aces and two filler cards.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    assertEquals(10, game.getScore());
  }

  /**
   * Tests that two pair is correctly scored as 5 points.
   */
  @Test
  public void testScoreForTwoPair() {
    LoosePokerTriangles game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    assertEquals(5, game.getScore());
  }

  /**
   * Tests that a single pair is correctly scored as 2 points.
   */
  @Test
  public void testScoreForPair() {
    LoosePokerTriangles game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    assertEquals(2, game.getScore());
  }

  /**
   * Tests that a High Card hand (no valid combination) is correctly scored as 0 points.
   */
  @Test
  public void testScoreForHighCard() {
    // Create a triangle with side length 5.
    LoosePokerTriangles game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    // Insert five cards that do not form any scoring combination.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    assertEquals(0, game.getScore());
  }

  /**
   * Tests a jumped straight (2-4-6-8-10 of mixed suits) and a normal straight (5-6-7-8-9 of mixed suits),
   * expecting 15 points each, totaling 30.
   */
  @Test
  public void testJumpedAndNormalStraight() {
    LoosePokerTriangles game = new LoosePokerTriangles(6);
    List<PlayingCard> deck = game.getNewDeck();

    // These cards form a jumped straight
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.HEARTS));

    // These cards form a normal straight
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.TEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.HEARTS));

    // Start the game with a hand size of 10 so that our custom cards are available.
    game.startGame(deck, false, 10);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 5, col);
    }

    // Print the board state using the textual view for manual verification.
    PokerTrianglesTextualView<PlayingCard> view = new PokerTrianglesTextualView<>(game);
    System.out.println("Board state:\n" + view.toString());

    // Jumped straight (15) + Normal straight (15) = 30 total.
    assertEquals(30, game.getScore());
  }

  /**
   * Tests a color flush (5 red cards, not straight) and a standard flush (6 hearts, not straight)
   * in a triangular game of side length 6.
   *
   * Expected total score: 20 (color flush) + 20 (standard flush) = 40.
   */
  @Test
  public void testColorAndStandardFlush() {
    LoosePokerTriangles game = new LoosePokerTriangles(6, new Random(8));

    // Obtain a fresh deck from the game.
    List<PlayingCard> deck = game.getNewDeck();

    // These cards form a "color flush"
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));

    // These cards form a standard flush.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.HEARTS));

    // Start the game with a hand size of 10 so that our custom cards are available.
    game.startGame(deck, false, 10);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 5, col);
    }

    // Print the board state using the textual view for manual verification.
    PokerTrianglesTextualView<PlayingCard> view = new PokerTrianglesTextualView<>(game);
    System.out.println("Board state:\n" + view.toString());

    // Expected scoring:
    // - Color flush in row 4 should score 20 points.
    // - Standard flush in row 5 should score 20 points.
    // Total expected score = 20 + 20 = 40.
    assertEquals(40, game.getScore());
  }


  /**
   * Tests a jumped straight (2-4-6-8-10 of mixed suits, with skipped increments)
   * and a color flush (5 red cards, not straight) in a triangular game of side length 6,
   * expecting 15 and 20 points respectively, totaling 35.
   */
  @Test
  public void testJumpedStraightAndColorFlush() {
    LoosePokerTriangles game = new LoosePokerTriangles(6, new Random(8));

    List<PlayingCard> deck = game.getNewDeck();

    // These cards form a "jumped straight" (2, 4, 6, 8, 10 of mixed suits, skipped by 2).
    deck.add(0, new StandardPlayingCard(Ranks.TWO, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.TEN, Suits.HEARTS));

    // These cards form a color flush: 5 red cards (hearts and diamonds) that do not form a straight.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.HEARTS));

    // Start the game with a hand size of 10 so that our custom cards are available.
    game.startGame(deck, false, 10);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 5, col);
    }

    // Print the board state using the textual view for manual verification.
    PokerTrianglesTextualView<PlayingCard> view = new PokerTrianglesTextualView<>(game);
    System.out.println("Board state:\n" + view.toString());

    // Jumped straight should score 15 points.
    // Color flush should score 20 points.
    // Total expected score = 15 + 20 = 35.
    assertEquals(35, game.getScore());
  }

  /**
   * Tests a jumped straight (2-4-6-8-10 of mixed suits) and a standard flush (5 hearts, not straight),
   * expecting 15 and 20 points respectively, totaling 35.
   */
  @Test
  public void testJumpedStraightAndStandardFlush() {
    LoosePokerTriangles game = new LoosePokerTriangles(6, new Random(8));
    List<PlayingCard> deck = game.getNewDeck();

    // Insert jumped straight cards (2, 4, 6, 8, 10 of mixed suits, skipped by 2)
    // These will be used to form a jumped straight.
    deck.add(0, new StandardPlayingCard(Ranks.TWO, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.TEN, Suits.HEARTS));

    // Insert standard flush cards (5 hearts, not forming a straight)
    // These will be used to form a flush.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.HEARTS));

    // Start the game with a hand size of 10 to ensure the custom cards are in hand.
    game.startGame(deck, false, 10);

    // Place the jumped straight cards in row 4 (which has exactly 5 cells).
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    // Place the standard flush cards in row 5 (which has 6 cells; we use the first 5).
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 5, col);
    }

    // Print the board state using the textual view for verification.
    PokerTrianglesTextualView<PlayingCard> view = new PokerTrianglesTextualView<>(game);
    System.out.println("Board state:\n" + view.toString());

    // Expected scoring:
    // Jumped straight should score 15 points.
    // Standard flush should score 20 points.
    // Total expected score = 15 + 20 = 35.
    assertEquals(35, game.getScore());
  }

  /**
   * Tests a normal straight (5-6-7-8-9 of mixed suits and colors) and a color flush (5 red cards, not straight),
   * expecting 15 and 20 points, totaling 35.
   */
  @Test
  public void testNormalStraightAndColorFlush() {
    LoosePokerTriangles game = new LoosePokerTriangles(6, new Random(8));
    List<PlayingCard> deck = game.getNewDeck();

    // Insert normal straight (5-6-7-8-9, mixed suits, consecutive).
    deck.add(0, new StandardPlayingCard(Ranks.FIVE,   Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX,    Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN,  Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT,  Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE,   Suits.HEARTS));

    // Insert color flush (5 red cards: hearts/diamonds, not straight).
    deck.add(0, new StandardPlayingCard(Ranks.ACE,    Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.KING,   Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN,  Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK,   Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR,   Suits.HEARTS));

    // Start the game with enough hand size to hold these 10 custom cards.
    game.startGame(deck, false, 10);

    // Place normal straight (5-6-7-8-9) in row 4 (which has 5 slots in a side=6 triangle).
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    // Place color flush (5 red cards) in row 5 (which has 6 slots; we’ll use 5).
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 5, col);
    }

    // Print the board state using the textual view.
    PokerTrianglesTextualView<PlayingCard> view = new PokerTrianglesTextualView<>(game);
    System.out.println("Board state:\n" + view.toString());

    // Normal straight (15) + Color flush (20) = 35 total.
    assertEquals(35, game.getScore());
  }

  /**
   * Tests a normal straight (5-6-7-8-9 of mixed suits and colors) and a standard flush (5 hearts, not straight),
   * expecting 15 and 20 points, totaling 35.
   */
  @Test
  public void testNormalStraightAndStandardFlush() {
    LoosePokerTriangles game = new LoosePokerTriangles(6, new Random(8));
    List<PlayingCard> deck = game.getNewDeck();

    // Insert normal straight (5-6-7-8-9, mixed suits and colors, consecutive)
    deck.add(0, new StandardPlayingCard(Ranks.FIVE,   Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX,    Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN,  Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT,  Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE,   Suits.HEARTS));

    // Insert standard flush (5 hearts, not straight)
    deck.add(0, new StandardPlayingCard(Ranks.ACE,    Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.KING,   Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN,  Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK,   Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR,   Suits.HEARTS));

    // Start the game with enough hand size to hold our custom inserts
    game.startGame(deck, false, 10);

    // Place normal straight (5-6-7-8-9) in row 4 (which has 5 cells in a side=6 triangle)
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    // Place standard flush (5 hearts) in row 5 (which has 6 cells, but we only use 5 of them)
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 5, col);
    }

    // Print the board state for clarity
    PokerTrianglesTextualView<PlayingCard> view = new PokerTrianglesTextualView<>(game);
    System.out.println("Board state:\n" + view.toString());

    // Normal straight (15) + Standard flush (20) = 35 total
    assertEquals(35, game.getScore());
  }

  /**
   * Tests a jumped straight (2-4-6-8-10 of mixed suits) and three of a kind (three Aces, two fillers),
   * expecting 15 and 10 points, totaling 25.
   */
  @Test
  public void testJumpedStraightAndThreeOfAKind() {
    LoosePokerTriangles game = new LoosePokerTriangles(6, new Random(8));
    List<PlayingCard> deck = game.getNewDeck();

    // Insert jumped straight (2-4-6-8-10, skipping by +2 each time)
    deck.add(0, new StandardPlayingCard(Ranks.TWO,   Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR,  Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SIX,   Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.TEN,   Suits.HEARTS));

    // Insert three of a kind (three Aces) + two filler cards
    deck.add(0, new StandardPlayingCard(Ranks.ACE,   Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE,   Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE,   Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR,  Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE,  Suits.HEARTS));

    // Start the game with enough hand size to hold our custom inserts
    game.startGame(deck, false, 10);

    // Place jumped straight (2-4-6-8-10) in row 4
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    // Place three-of-a-kind (Aces) in row 5
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 5, col);
    }

    // Print the board state for clarity
    PokerTrianglesTextualView<PlayingCard> view = new PokerTrianglesTextualView<>(game);
    System.out.println("Board state:\n" + view.toString());

    // Jumped straight (15) + Three of a kind (10) = 25 total
    assertEquals(25, game.getScore());
  }

  /**
   * Tests a color flush (5 red cards, not straight) and three of a kind (three Aces, two fillers),
   * expecting 20 and 10 points, totaling 30.
   */
  @Test
  public void testColorFlushAndThreeOfAKind() {
    LoosePokerTriangles game = new LoosePokerTriangles(6, new Random(8));
    List<PlayingCard> deck = game.getNewDeck();

    // Insert color flush cards (5 red cards: hearts/diamonds, not forming a straight)
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.HEARTS));

    // Insert three-of-a-kind cards (three Aces and two filler cards)
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.CLUBS));

    // Start the game
    game.startGame(deck, false, 10);

    // Place three-of-a-kind in row 4 (row index 4 has exactly 5 cells)
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    // Place color flush in row 5 (use the first 5 cells of row 5)
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 5, col);
    }

    // Print the board state using the textual view to ensure test accuracy.
    PokerTrianglesTextualView<PlayingCard> view = new PokerTrianglesTextualView<>(game);
    System.out.println("Board state:\n" + view.toString());

    // Flush (20) + 3-of-a-kind (10) = 30.
    assertEquals(30, game.getScore());
  }




}