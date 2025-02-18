package cs3500.pokerpolygons.model.hw04;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.Ranks;
import cs3500.pokerpolygons.model.hw02.Suits;
import cs3500.pokerpolygons.model.hw02.StandardPlayingCard;

/**
 * In-depth testing for the PokerRectanglesScoring class for every type of hand and compound
 * scoring.
 * Valid scoring hands for rectangles include rows and columns (diagonals are not scored).
 */
public class PokerRectanglesScoringTest {

  /**
   * Tests that a middle straight flush (5-6-7-8-9 of DIAMONDS)
   * is correctly scored as 75 points.
   */
  @Test
  public void testScoreForMidStraightFlush() {
    PokerRectangles game = new PokerRectangles(5, 5);
    List<PlayingCard> deck = game.getNewDeck();

    // Shuffle deck before adding custom straight flush cards.
    Collections.shuffle(deck);

    // Insert a middle straight flush (5-6-7-8-9 of DIAMONDS) at the top of the deck.
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.DIAMONDS));

    // Start the game with shuffle disabled.
    game.startGame(deck, false, 5);
    // Place the five predetermined straight flush cards in row 0 (columns 0 to 4).
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    assertEquals(75, game.getScore());
  }

  /**
   * Tests that four of a kind is correctly scored as 50 points.
   */
  @Test
  public void testScoreForFourOfAKind() {
    PokerRectangles game = new PokerRectangles(5, 5);
    List<PlayingCard> deck = game.getNewDeck();

    // Insert four Aces and one filler card.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));

    game.startGame(deck, false, 5);
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    assertEquals(50, game.getScore());
  }

  /**
   * Tests that a full house is correctly scored as 25 points.
   */
  @Test
  public void testScoreForFullHouse() {
    PokerRectangles game = new PokerRectangles(5, 5);
    List<PlayingCard> deck = game.getNewDeck();

    // Insert three Aces and two Jacks.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.CLUBS));

    game.startGame(deck, false, 5);
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    assertEquals(25, game.getScore());
  }

  /**
   * Tests that a flush (all cards of the same suit, non-straight) is correctly scored as 20 points.
   */
  @Test
  public void testScoreForFlush() {
    PokerRectangles game = new PokerRectangles(5, 5);
    List<PlayingCard> deck = game.getNewDeck();

    // Insert five cards of HEARTS that do not form a straight.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.HEARTS));

    game.startGame(deck, false, 5);
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    assertEquals(20, game.getScore());
  }

  /**
   * Tests that a straight (cards in consecutive order, but not flush) is correctly
   * scored as 15 points.
   */
  @Test
  public void testScoreForStraight() {
    PokerRectangles game = new PokerRectangles(5, 5);
    List<PlayingCard> deck = game.getNewDeck();

    // Insert five cards that form a straight (5-6-7-8-9) of mixed suits.
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.HEARTS));

    game.startGame(deck, false, 5);
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    assertEquals(15, game.getScore());
  }

  /**
   * Tests that three of a kind is correctly scored as 10 points.
   */
  @Test
  public void testScoreForThreeOfAKind() {
    PokerRectangles game = new PokerRectangles(5, 5);
    List<PlayingCard> deck = game.getNewDeck();

    // Insert three Aces and two filler cards.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    assertEquals(10, game.getScore());
  }

  /**
   * Tests that two pair is correctly scored as 5 points.
   */
  @Test
  public void testScoreForTwoPair() {
    PokerRectangles game = new PokerRectangles(5, 5);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    assertEquals(5, game.getScore());
  }

  /**
   * Tests that a single pair is correctly scored as 2 points.
   */
  @Test
  public void testScoreForPair() {
    PokerRectangles game = new PokerRectangles(5, 5);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    assertEquals(2, game.getScore());
  }

  /**
   * Tests that a High Card hand is correctly scored as 0 points.
   */
  @Test
  public void testScoreForHighCard() {
    // Create a 5x5 PokerRectangles game.
    PokerRectangles game = new PokerRectangles(5, 5);
    // Get and shuffle the deck.
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    // Insert five cards that do not form any scoring combination:
    deck.add(0, new StandardPlayingCard(Ranks.ACE,   Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR,  Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE,  Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.KING,  Suits.HEARTS));

    // Start the game with shuffle disabled (so our custom cards are first).
    game.startGame(deck, false, 5);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }

    assertEquals("A High Card hand should score 0.", 0, game.getScore());
  }

  /**
   * Row 0: Flush (20 pts), Row 1: Pair = total 22.
   */
  @Test
  public void testCompoundScoreFlushAndPair() {
    PokerRectangles game = new PokerRectangles(5, 5);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    // Row 0: 5 hearts = flush
    deck.add(0, new StandardPlayingCard(Ranks.ACE,   Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.KING,  Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK,  Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE,  Suits.HEARTS));

    // Row 1: 2 pairs = actually we only need a single pair for 2 pts, so let's do Pair + 3 distinct
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.CLUBS));

    // Start game (no shuffle to preserve our order).
    game.startGame(deck, false, 5);

    // Place flush in row 0.
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }

    // Place pair in row 1.
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 1, col);
    }

    // Flush (20) + Pair (2) = 22 total.
    assertEquals(22, game.getScore());
  }

  /**
   * Row 0: Flush (20 pts), Row 1: Flush (20 pts) = total 40.
   */
  @Test
  public void testCompoundScoreFlushAndFlush() {
    PokerRectangles game = new PokerRectangles(5, 5);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    // Row 0: 5 clubs = flush
    deck.add(0, new StandardPlayingCard(Ranks.ACE,   Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR,  Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE,  Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX,   Suits.CLUBS));

    // Row 1: 5 spades = another flush
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN,   Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.JACK,  Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.KING,  Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE,   Suits.SPADES));

    game.startGame(deck, false, 5);

    // Place first flush in row 0.
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    // Place second flush in row 1.
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 1, col);
    }

    // 20 + 20 = 40 total.
    assertEquals(40, game.getScore());
  }

  /**
   * Row 0: Flush (20 pts), Row 1: Straight (15 pts) = total 35.
   */
  @Test
  public void testCompoundScoreFlushAndStraight() {
    PokerRectangles game = new PokerRectangles(5, 5);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    // Row 0: 5 diamonds = flush
    deck.add(0, new StandardPlayingCard(Ranks.TWO,   Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR,  Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE,  Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE,  Suits.DIAMONDS));

    // Row 1: 5-6-7-8-9 of mixed suits = straight
    deck.add(0, new StandardPlayingCard(Ranks.FIVE,  Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX,   Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE,  Suits.HEARTS));

    game.startGame(deck, false, 5);

    // Place flush in row 0.
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    // Place straight in row 1.
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 1, col);
    }

    // 20 + 15 = 35 total.
    assertEquals(35, game.getScore());
  }

  /**
   * Row 0: Flush (20 pts), Row 1: Full House (25 pts) = total 45.
   */
  @Test
  public void testCompoundScoreFlushAndFullHouse() {
    PokerRectangles game = new PokerRectangles(5, 5);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    // Row 0: 5 hearts = flush
    deck.add(0, new StandardPlayingCard(Ranks.TWO,   Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR,  Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE,  Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN,   Suits.HEARTS));

    // Row 1: Full house = three Aces + two Jacks
    deck.add(0, new StandardPlayingCard(Ranks.ACE,   Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE,   Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE,   Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK,  Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK,  Suits.CLUBS));

    game.startGame(deck, false, 5);

    // Place flush in row 0.
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    // Place full house in row 1.
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 1, col);
    }

    // 20 + 25 = 45 total.
    assertEquals(45, game.getScore());
  }

  /**
   * Row 0: Flush (20 pts), Row 1: Straight Flush (75 pts) = total 95.
   */
  @Test
  public void testCompoundScoreFlushAndStraightFlush() {
    PokerRectangles game = new PokerRectangles(5, 5);
    List<PlayingCard> deck = game.getNewDeck();
    Collections.shuffle(deck);

    // Row 0: 5 clubs = flush
    deck.add(0, new StandardPlayingCard(Ranks.TWO,   Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR,  Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE,  Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX,   Suits.CLUBS));

    // Row 1: 5-6-7-8-9 of spades = straight flush
    deck.add(0, new StandardPlayingCard(Ranks.FIVE,  Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SIX,   Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE,  Suits.SPADES));

    game.startGame(deck, false, 5);

    // Place flush in row 0.
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    // Place straight flush in row 1.
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 1, col);
    }

    // 20 + 75 = 95 total.
    assertEquals(95, game.getScore());
  }
}