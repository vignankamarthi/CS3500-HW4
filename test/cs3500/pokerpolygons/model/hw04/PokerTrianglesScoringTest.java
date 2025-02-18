package cs3500.pokerpolygons.model.hw04;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;
import cs3500.pokerpolygons.model.hw02.Ranks;
import cs3500.pokerpolygons.model.hw02.Suits;
import cs3500.pokerpolygons.model.hw02.StandardPlayingCard;

/**
 * In-depth testing for PokerTriangles class including exceptions, general behavior,
 * and edge case behavior.
 */
public class PokerTrianglesScoringTest {

  /**
   * Tests that a middle straight flush (5-6-7-8-9 of DIAMONDS)
   * is correctly scored as 75 points.
   */
  @Test
  public void testScoreForMidStraightFlush() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Shuffle deck before adding custom straight flush cards.
    Collections.shuffle(deck);

    // Insert a middle straight flush (5-6-7-8-9 of DIAMONDS) at the top of the deck.
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.DIAMONDS));

    // Start the game (shuffle = false to preserve order).
    game.startGame(deck, false, 5);

    // Place these 5 cards in row 4
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    // Middle straight flush should score 75.
    assertEquals(75, game.getScore());
  }

  /**
   * Tests that four of a kind is correctly scored as 50 points.
   */
  @Test
  public void testScoreForFourOfAKind() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Insert four Aces and one filler card.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));

    game.startGame(deck, false, 5);

    // Place them in row 4
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Insert three Aces and two Jacks.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.CLUBS));

    game.startGame(deck, false, 5);

    // Place them in row 4
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Insert five hearts that do not form a straight.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    // Place them in row 4
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Insert 5-6-7-8-9 of mixed suits for a straight.
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    // Place them in row 4
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Insert three Aces and two filler cards.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    // Place them in row 4
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    // Place them in row 4
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    // Place them in row 4
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    // Insert five cards that do not form any scoring combination.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.HEARTS));

    game.startGame(deck, false, 5);

    // Place them in row 4
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 4, col);
    }

    assertEquals(0, game.getScore());
  }

  /**
   * Row 3: Flush (20 pts), Row 4: Pair (2 pts) = total 22.
   */
  /**
   * Tests that a flush in row 5 (columns 1–5) and a pair in column 0 (rows 0–4)
   * yield a total score of 22 (flush = 20, pair = 2).
   */
  @Test
  public void testCompoundScoreFlushAndPair_Triangle() {
    // Use a triangle with side length 6.
    PokerTriangles game = new PokerTriangles(6);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    // For flush in row 5, we need 5 hearts.
    deck.add(0, new StandardPlayingCard(Ranks.ACE,   Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.KING,  Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK,  Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE,  Suits.HEARTS));

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.CLUBS));

    game.startGame(deck, false, 10);

    // Place the flush hand in row 5, but only use columns 1 through 5 (leaving column 0 free).
    for (int col = 1; col < 6; col++) {
      game.placeCardInPosition(0, 5, col);
    }
    // Place the pair hand in column 0 across rows 0 to 4.
    for (int row = 0; row < 5; row++) {
      game.placeCardInPosition(0, row, 0);
    }

    // Flush (20) + Pair (2) = 22 total.
    assertEquals(22, game.getScore());
  }

  /**
   * Row 4: Flush (20 pts), Column 4: Straight (15 pts) = total 35.
   */
  /**
   * Tests that a flush in row 5 (using columns 1–5) and a straight in column 0 (using rows 0–4)
   * yield a total score of 35 (flush = 20, straight = 15) in a triangular game with side length 6.
   */
  @Test
  public void testCompoundScoreFlushAndStraight_Triangle() {
    // Use a triangle with side length 6.
    PokerTriangles game = new PokerTriangles(6);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.TWO, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.CLUBS));

    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.CLUBS));

    game.startGame(deck, false, 10);

    for (int col = 1; col < 6; col++) {
      game.placeCardInPosition(0, 5, col);
    }
    // Place the straight hand in column 0 using rows 0 to 4.
    for (int row = 0; row < 5; row++) {
      game.placeCardInPosition(0, row, 0);
    }

    // Flush (20) + Straight (15) should equal 35.
    assertEquals(35, game.getScore());
  }

  /**
   * Row 4: Straight Flush (75 pts), Column 0: Full House (25 pts) = total 100.
   */
  @Test
  public void testCompoundScoreStraightFlushAndFullHouse_Triangle() {
    // Use a triangle with side length 6.
    PokerTriangles game = new PokerTriangles(6);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    // For the straight flush hand
    // Insert 5-6-7-8-9 of Hearts.
    deck.add(0, new StandardPlayingCard(Ranks.FIVE,  Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX,   Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE,  Suits.HEARTS));

    // For the full house hand
    // Insert three Aces and two Jacks.
    deck.add(0, new StandardPlayingCard(Ranks.ACE,   Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE,   Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE,   Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK,  Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK,  Suits.CLUBS));

    // Start the game with a hand size of 10 so that custom cards remain on top.
    game.startGame(deck, false, 10);

    // Place the straight flush in row 5
    for (int col = 1; col < 6; col++) {
      game.placeCardInPosition(0, 5, col);
    }
    // Place the full house in column 0
    for (int row = 0; row < 5; row++) {
      game.placeCardInPosition(0, row, 0);
    }

    // Straight flush (75) + Full House (25) = 100 total.
    assertEquals(100, game.getScore());
  }
}