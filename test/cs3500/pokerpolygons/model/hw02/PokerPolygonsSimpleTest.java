package cs3500.pokerpolygons.model.hw02;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the PokerPolygonsSimple mock implementation.
 */
public class PokerPolygonsSimpleTest {

  /**
   * Tests that the constructor throws an IllegalArgumentException when the log is null.
   */
  @Test
  public void testConstructorNullLog() {
    try {
      new PokerPolygonsSimple<Card>(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Log cannot be null.", e.getMessage());
    }
  }

  /**
   * Tests that placeCardInPosition appends the correct message to the log.
   */
  @Test
  public void testPlaceCardInPosition() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    mock.placeCardInPosition(0, 1, 2);
    assertTrue(log.toString().contains("placeCardInPosition(0, 1, 2) called."));
  }

  /**
   * Tests that discardCard appends the correct message to the log.
   */
  @Test
  public void testDiscardCard() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    mock.discardCard(3);
    assertTrue(log.toString().contains("discardCard(3) called"));
  }

  /**
   * Tests that startGame appends the correct message to the log and sets state appropriately.
   */
  @Test
  public void testStartGame() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    List<Card> deck = new ArrayList<>();
    deck.add(new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(new StandardPlayingCard(Ranks.TWO, Suits.CLUBS));
    deck.add(new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    deck.add(new StandardPlayingCard(Ranks.FOUR, Suits.CLUBS));
    deck.add(new StandardPlayingCard(Ranks.FIVE, Suits.CLUBS));
    mock.startGame(deck, false, 3);
    assertTrue(log.toString().contains("startGame(deckSize = 5, shuffle = false, " +
            "handSize = 3) called."));
    // Verify that the hand now contains the first 3 cards.
    List<Card> hand = mock.getHand();
    assertEquals(3, hand.size());
    // Verify remaining deck size = 5 - 3 = 2.
    assertEquals(2, mock.getRemainingDeckSize());
  }

  /**
   * Tests that getWidth returns the default width and logs the call.
   */
  @Test
  public void testGetWidth() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    int width = mock.getWidth();
    assertEquals(5, width);
    assertTrue(log.toString().contains("getWidth() called."));
  }

  /**
   * Tests that getHeight returns the default height and logs the call.
   */
  @Test
  public void testGetHeight() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    int height = mock.getHeight();
    assertEquals(5, height);
    assertTrue(log.toString().contains("getHeight() called."));
  }

  /**
   * Tests that getNewDeck returns a copy of the new deck and that modifications to
   * the returned list do not affect the internal state.
   */
  @Test
  public void testGetNewDeck() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    List<Card> deck = new ArrayList<>();
    deck.add(new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(new StandardPlayingCard(Ranks.KING, Suits.HEARTS));
    mock.setNewDeck(deck);
    List<Card> returnedDeck = mock.getNewDeck();
    assertEquals(deck, returnedDeck);
    returnedDeck.add(new StandardPlayingCard(Ranks.TWO, Suits.DIAMONDS));
    // The internal newDeck should remain unchanged.
    assertEquals(2, mock.getNewDeck().size());
  }

  /**
   * Tests that getCardAt always returns null and logs the call.
   */
  @Test
  public void testGetCardAt() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    Card card = mock.getCardAt(1, 1);
    assertNull(card);
    assertTrue(log.toString().contains("getCardAt(1, 1) called:"));
  }

  /**
   * Tests that getHand returns a copy of the hand and that modifications to the returned list
   * do not affect the internal hand.
   */
  @Test
  public void testGetHand() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    List<Card> deck = new ArrayList<>();
    deck.add(new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(new StandardPlayingCard(Ranks.TWO, Suits.CLUBS));
    deck.add(new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    mock.startGame(deck, false, 2);
    List<Card> hand1 = mock.getHand();
    assertEquals(2, hand1.size());
    hand1.clear();
    List<Card> hand2 = mock.getHand();
    assertEquals(2, hand2.size());
  }

  /**
   * Tests that getScore returns the preset score and logs the call.
   */
  @Test
  public void testGetScore() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    mock.setScore(42);
    int score = mock.getScore();
    assertEquals(42, score);
    assertTrue(log.toString().contains("getScore() called."));
  }

  /**
   * Tests that getRemainingDeckSize returns the preset remaining deck size and logs the call.
   */
  @Test
  public void testGetRemainingDeckSize() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    mock.setRemainingDeckSize(10);
    int remaining = mock.getRemainingDeckSize();
    assertEquals(10, remaining);
    assertTrue(log.toString().contains("getRemainingDeckSize() called."));
  }

  /**
   * Tests that isGameOver returns the preset gameOver flag and logs the call.
   */
  @Test
  public void testIsGameOver() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    mock.setGameOver(true);
    boolean over = mock.isGameOver();
    assertTrue(over);
    assertTrue(log.toString().contains("isGameOver() called."));
  }

  /**
   * Tests that setWidth correctly updates the width.
   */
  @Test
  public void testSetWidth() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    mock.setWidth(7);
    assertEquals(7, mock.getWidth());
  }

  /**
   * Tests that setHeight correctly updates the height.
   */
  @Test
  public void testSetHeight() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    mock.setHeight(8);
    assertEquals(8, mock.getHeight());
  }

  /**
   * Tests that setScore correctly updates the score.
   */
  @Test
  public void testSetScore() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    mock.setScore(100);
    assertEquals(100, mock.getScore());
  }

  /**
   * Tests that setRemainingDeckSize correctly updates the remaining deck size.
   */
  @Test
  public void testSetRemainingDeckSize() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    mock.setRemainingDeckSize(15);
    assertEquals(15, mock.getRemainingDeckSize());
  }

  /**
   * Tests that setGameOver correctly updates the game over flag.
   */
  @Test
  public void testSetGameOver() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    mock.setGameOver(true);
    assertTrue(mock.isGameOver());
  }

  /**
   * Tests that setNewDeck correctly updates the new deck.
   */
  @Test
  public void testSetNewDeck() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    List<Card> deck = new ArrayList<>();
    deck.add(new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    mock.setNewDeck(deck);
    List<Card> newDeck = mock.getNewDeck();
    assertEquals(deck, newDeck);
  }

  /**
   * Tests that getLog returns the correct log of method calls.
   */
  @Test
  public void testGetLog() {
    StringBuilder log = new StringBuilder();
    PokerPolygonsSimple<Card> mock = new PokerPolygonsSimple<>(log);
    mock.getWidth();
    mock.getHeight();
    String currentLog = mock.getLog();
    assertTrue(currentLog.contains("getWidth() called."));
    assertTrue(currentLog.contains("getHeight() called."));
  }
}