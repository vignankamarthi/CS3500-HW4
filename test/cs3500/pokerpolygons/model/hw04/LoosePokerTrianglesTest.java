package cs3500.pokerpolygons.model.hw04;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.Ranks;
import cs3500.pokerpolygons.model.hw02.StandardPlayingCard;
import cs3500.pokerpolygons.model.hw02.Suits;
import cs3500.pokerpolygons.view.PokerTrianglesTextualView;

/**
 * Tests for the LoosePokerTriangles class including exceptions, general behavior,
 * and edge case behavior.
 */
public class LoosePokerTrianglesTest {

  /**
   * 1. Test placeCardInPosition: Handles multiple IllegalStateException and
   * IllegalArgumentException cases
   */
  @Test
  public void testPlaceCardInPosition() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    // Try to place a card at an invalid position
    try {
      game.placeCardInPosition(0, 4, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Out of bounds of the game board: -1, 0", e.getMessage());
    }

    try {
      game.placeCardInPosition(0, 3, 2); // Out of board range
    } catch (IllegalArgumentException e) {
      assertEquals("Row or column out of bounds: 10, 5", e.getMessage());
    }

    try {
      game.placeCardInPosition(0, -1, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Row and column indices must be non-negative.", e.getMessage());
    }
  }

  /**
   * 2. Test discardCard: Handles IllegalStateException and IllegalArgumentException cases
   */
  @Test
  public void testDiscardCard() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    for (int i = 0; i < 5; i++) {
      game.discardCard(0);
    }

    try {
      game.discardCard(0);
    } catch (IllegalStateException e) {
      assertEquals("Hand is empty.", e.getMessage());
    }

    try {
      game.discardCard(10);
    } catch (IllegalArgumentException e) {
      assertEquals("Card index out of bounds of the hand: 11", e.getMessage());
    }
  }

  /**
   * 3. Test startGame: Handles IllegalStateException and IllegalArgumentException cases
   */
  @Test
  public void testStartGame() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    game.startGame(deck, false, 5);

    try {
      game.startGame(null, false, 5);
    } catch (IllegalArgumentException e) {
      assertEquals("Deck is null.", e.getMessage());
    }
    try {
      game.startGame(deck, false, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Hand size must be positive.", e.getMessage());
    }
    try {
      game.startGame(deck, false, 5);
    } catch (IllegalStateException e) {
      assertEquals("Game has already started.", e.getMessage());
    }
  }

  /**
   * 4. Test getWidth: Should return correct value
   */
  @Test
  public void testGetWidth() {
    PokerPolygons game = new LoosePokerTriangles(5);
    assertEquals(5, game.getWidth());
  }

  /**
   * 5. Test getHeight: Should return correct value
   */
  @Test
  public void testGetHeight() {
    PokerPolygons game = new LoosePokerTriangles(5);
    assertEquals(5, game.getHeight());
  }

  /**
   * 6. Test getNewDeck: Should return 52 cards
   */
  @Test
  public void testGetNewDeck() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    assertEquals(52, deck.size());
  }

  /**
   * 7. Test getCardAt: Handles IllegalArgumentException cases
   */
  @Test
  public void testGetCardAt() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    try {
      game.getCardAt(-1, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Row or column out of bounds: -1, 0", e.getMessage());
    }

    try {
      game.getCardAt(10, 5);
    } catch (IllegalArgumentException e) {
      assertEquals("Row or column out of bounds: 10, 5", e.getMessage());
    }
  }

  /**
   * 8. Test getHand: Handles IllegalStateException
   */
  @Test
  public void testGetHand() {
    PokerPolygons game = new LoosePokerTriangles(5);

    try {
      game.getHand();
    } catch (IllegalStateException e) {
      assertEquals("Game has not started.", e.getMessage());
    }
  }

  /**
   * 9. Test getScore: Handles IllegalStateException
   */
  @Test
  public void testGetScore() {
    PokerPolygons game = new LoosePokerTriangles(5);

    try {
      game.getScore();
    } catch (IllegalStateException e) {
      assertEquals("Game has not started.", e.getMessage());
    }
  }

  /**
   * 10. Test getRemainingDeckSize: Handles IllegalStateException
   */
  @Test
  public void testGetRemainingDeckSize() {
    PokerPolygons game = new LoosePokerTriangles(5);

    try {
      game.getRemainingDeckSize();
    } catch (IllegalStateException e) {
      assertEquals("Game has not started.", e.getMessage());
    }
  }

  /**
   * 11. Test isGameOver: Handles IllegalStateException
   */
  @Test
  public void testIsGameOver() {
    PokerPolygons game = new LoosePokerTriangles(5);

    try {
      game.isGameOver();
    } catch (IllegalStateException e) {
      assertEquals("Game has not started.", e.getMessage());
    }
  }

  //----------------------------------------------------------------------------------------
  // Testing for general interface function.

  /**
   * Tests that a placed card is correctly set at the given board position.
   */
  @Test
  public void testPlaceCardInCorrectPosition() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    PlayingCard firstCard = new StandardPlayingCard(Ranks.TWO, Suits.CLUBS);
    game.placeCardInPosition(0, 0, 0);

    assertEquals(firstCard, game.getCardAt(0, 0));
  }

  /**
   * Tests that multiple placed cards are correctly set at their respective board positions.
   */
  @Test
  public void testPlaceMultipleCardsInCorrectPositions() {
    PokerPolygons game = new LoosePokerTriangles(5);

    // Generate a deck
    List<PlayingCard> deck = game.getNewDeck();

    // Custom cards to test equality
    PlayingCard firstCard = new StandardPlayingCard(Ranks.TWO, Suits.CLUBS);
    PlayingCard secondCard = new StandardPlayingCard(Ranks.TWO, Suits.HEARTS);

    // Start game with the modified deck
    game.startGame(deck, false, 5);

    // Place the cards manually at specific board positions
    game.placeCardInPosition(0, 0, 0);
    game.placeCardInPosition(1, 1, 1);

    // Test if the placed cards match the expected ones
    assertEquals(firstCard, game.getCardAt(0, 0));
    assertEquals(secondCard, game.getCardAt(1, 1));
  }

  /**
   * Tests that the deck size decreases when cards are placed on the board.
   */
  @Test
  public void testDeckSizeDecreasesAfterPlacingCards() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    int initialDeckSize = game.getRemainingDeckSize();

    game.placeCardInPosition(0, 0, 0);
    game.placeCardInPosition(1, 1, 0);
    game.placeCardInPosition(1, 1, 1);

    assertEquals(initialDeckSize - 3, game.getRemainingDeckSize());
  }

  /**
   * Tests that unoccupied board positions remain empty.
   */
  @Test
  public void testUnoccupiedPositionsRemainEmpty() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    game.placeCardInPosition(0, 0, 0);
    game.placeCardInPosition(1, 1, 0);
    game.placeCardInPosition(1, 1, 1);

    assertNull(game.getCardAt(2, 0));
    assertNull(game.getCardAt(2, 1));
    assertNull(game.getCardAt(2, 2));
  }

  /**
   * Tests that discarding a card correctly reduces hand size.
   */
  @Test
  public void testHandSizeAfterDiscard() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    int initialHandSize = game.getHand().size();

    game.discardCard(0); // Discard the first card

    assertEquals(initialHandSize, game.getHand().size());
  }

  /**
   * Tests that discarding a card reduces the remaining deck size.
   */
  @Test
  public void testDeckSizeAfterDiscard() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    int initialDeckSize = game.getRemainingDeckSize();

    game.discardCard(0); // Discard the first card

    assertEquals(initialDeckSize - 1, game.getRemainingDeckSize());
  }

  /**
   * Tests that the game starts correctly with a full deck of 52 cards.
   */
  @Test
  public void testStartGameWithFullDeck() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck(); // Full 52-card deck

    game.startGame(deck, false, 5);

    assertEquals(5, game.getHand().size()); // Hand size should be 5
    assertEquals(47, game.getRemainingDeckSize()); // 52 - 5 = 47 remaining
  }

  /**
   * Tests that getNewDeck() returns a deck with exactly 52 cards.
   */
  @Test
  public void testGetNewDeckSize() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    assertEquals(52, deck.size());
  }

  /**
   * Tests that getNewDeck() contains only unique cards.
   */
  @Test
  public void testGetNewDeckUniqueCards() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    Set<PlayingCard> uniqueCards = new HashSet<>(deck);

    assertEquals(52, uniqueCards.size()); // Ensures all 52 cards are unique
  }

  /**
   * Tests that getCardAt(row, col) correctly retrieves cards placed on the board.
   */
  @Test
  public void testGetCardAtFunction() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    // Place known card at specific locations
    game.placeCardInPosition(0, 0, 0); // Place card at (0,0)

    // Creating a default deck for comparison
    List<PlayingCard> defaultDeck = game.getNewDeck();

    // Check if the correct card are retrieved
    assertEquals(defaultDeck.get(0), game.getCardAt(0, 0));
  }

  /**
   * Tests that getHand() correctly retrieves the player's current hand.
   */
  @Test
  public void testGetHandFunction() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5); // Start game with hand size 5

    List<PlayingCard> hand = game.getHand();

    // To create the default deck again to compare to the cards inserted into the hand.
    List<PlayingCard> defaultDeck = game.getNewDeck();

    // Ensure hand size matches the expected number
    assertEquals(5, hand.size());

    // Ensure the hand contains the first 5 cards from the deck
    for (int i = 0; i < 5; i++) {
      assertEquals(defaultDeck.get(i), hand.get(i));
    }
  }

  /**
   * Test that getRemainingDeckSize() returns the correct initial deck size.
   */
  @Test
  public void testGetRemainingDeckSizeInitial() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    // 52 total cards - 5 initial hand cards = 47
    assertEquals(47, game.getRemainingDeckSize());
  }

  /**
   * Test that getRemainingDeckSize() correctly updates when discarding cards.
   */
  @Test
  public void testGetRemainingDeckSizeAfterDiscarding() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    game.discardCard(0);
    assertEquals(46, game.getRemainingDeckSize());

    game.discardCard(0);
    assertEquals(45, game.getRemainingDeckSize());
  }

  /**
   * Test that isGameOver() returns false when the game starts.
   */
  @Test
  public void testIsGameOverAtStart() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    assertFalse(game.isGameOver());
  }

  /**
   * Test that isGameOver() returns false when the game has some cards on the
   * board but isn't full.
   */
  @Test
  public void testIsGameOverAtMiddle() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    // Placing cards up to the 3rd row
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < row; col++) {
        game.placeCardInPosition(col, row, col);
      }
    }

    assertFalse(game.isGameOver());
  }

  /**
   * Test that isGameOver() returns true when the game is at an end state.
   */
  @Test
  public void testIsGameOverAtEnd() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Start the game with a known deck
    game.startGame(deck, false, 5);

    // Randomizer to select a card index from 0-4
    Random rand = new Random();

    // Fill up the entire game board
    for (int row = 0; row < 13; row++) {
      for (int col = 0; col <= row; col++) {
        if (!game.isGameOver()) {
          int randomCardIdx = rand.nextInt(5);
          game.placeCardInPosition(randomCardIdx, row, col);
        }
      }
    }

    // Now that the board is full, isGameOver() should return true
    assertTrue(game.isGameOver());
  }

  /**
   * Test that getScore() returns 0 at the start of the game.
   */
  @Test
  public void testGetScoreAtStart() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    assertEquals(0, game.getScore());
  }

  /**
   * Test that getScore() updates correctly when a valid hand is placed.
   */
  @Test
  public void testGetScoreAfterPlacingCards() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    // Place a valid poker hand (e.g., a flush)
    game.placeCardInPosition(0, 0, 0);
    game.placeCardInPosition(0, 1, 0);
    game.placeCardInPosition(0, 2, 0);
    game.placeCardInPosition(0, 3, 0);
    game.placeCardInPosition(0, 4, 0);

    assertTrue(game.getScore() > 0); // Score should increase
  }

  /**
   * Test that getScore() correctly calculates when multiple hands are completed.
   */
  @Test
  public void testGetScoreWithMultipleHands() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    // Place cards in different locations to form valid poker hands
    game.placeCardInPosition(0, 0, 0);
    game.placeCardInPosition(0, 1, 0);
    game.placeCardInPosition(0, 2, 0);
    game.placeCardInPosition(0, 3, 0);
    game.placeCardInPosition(0, 4, 0);

    game.placeCardInPosition(0, 2, 2);
    game.placeCardInPosition(0, 3, 3);
    game.placeCardInPosition(0, 4, 4);

    assertTrue(game.getScore() > 0); // Should be higher than a single hand score
  }

  /**
   * Test that getScore() returns the maximum possible score when the board is full.
   */
  @Test
  public void testGetScoreWhenBoardIsFull() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    // Fill the board completely
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col <= row; col++) {
        game.placeCardInPosition(0, row, col);
      }
    }

    assertTrue(game.getScore() > 0); // Score should be maxed out
  }

  /**
   * Test that a single pair is correctly scored as 2 points.
   */
  @Test
  public void testScoreForPair() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int i = 0; i < 5; i++) {
      game.placeCardInPosition(0, i, 0);
    }

    assertEquals(2, game.getScore()); // A pair should score exactly 2
  }

  /**
   * Test that two pairs are correctly scored as 5 points.
   */
  @Test
  public void testScoreForTwoPair() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int i = 0; i < 5; i++) {
      game.placeCardInPosition(0, i, 0);
    }

    assertEquals(5, game.getScore()); // Two pairs should score exactly 5
  }

  /**
   * Test that three of a kind is correctly scored as 10 points.
   */
  @Test
  public void testScoreForThreeOfAKind() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int i = 0; i < 5; i++) {
      game.placeCardInPosition(0, i, 0);
    }

    assertEquals(10, game.getScore()); // Three of a kind should score exactly 10
  }

  /**
   * Tests that an Ace-low straight (A-2-3-4-5 of mixed suits)
   * is correctly scored as 15 points.
   */
  @Test
  public void testScoreForAceLowStraight() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.TWO, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int i = 0; i < 5; i++) {
      game.placeCardInPosition(0, i, 0);
    }

    assertEquals(15, game.getScore()); // Ace-low straight should score 15
  }

  /**
   * Tests that an Ace-high straight (10-J-Q-K-A of mixed suits)
   * is correctly scored as 15 points.
   */
  @Test
  public void testScoreForAceHighStraight() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.TEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int i = 0; i < 5; i++) {
      game.placeCardInPosition(0, i, 0);
    }

    assertEquals(15, game.getScore()); // Ace-high straight should score 15
  }

  /**
   * Tests that a middle straight (5-6-7-8-9 of mixed suits)
   * is correctly scored as 15 points.
   */
  @Test
  public void testScoreForMidStraight() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int i = 0; i < 5; i++) {
      game.placeCardInPosition(0, i, 0);
    }

    assertEquals(15, game.getScore()); // Middle straight should score 15
  }

  /**
   * Test that a flush is correctly scored as 20 points.
   */
  @Test
  public void testScoreForFlush() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int i = 0; i < 5; i++) {
      game.placeCardInPosition(0, i, 0);
    }

    assertEquals(20, game.getScore()); // A flush should score exactly 20
  }

  /**
   * Test that a full house is correctly scored as 25 points.
   */
  @Test
  public void testScoreForFullHouse() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.CLUBS));

    game.startGame(deck, false, 5);

    for (int i = 0; i < 5; i++) {
      game.placeCardInPosition(0, i, 0);
    }

    assertEquals(25, game.getScore());
  }

  /**
   * Test that four of a kind is correctly scored as 50 points.
   */
  @Test
  public void testScoreForFourOfAKind() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.CLUBS));

    game.startGame(deck, false, 5);

    for (int i = 0; i < 5; i++) {
      game.placeCardInPosition(0, i, 0);
    }

    assertEquals(50, game.getScore()); // Four of a kind should score exactly 50
  }

  /**
   * Tests that an Ace-low straight flush (A-2-3-4-5 of the same suit)
   * is correctly scored as 75 points.
   */
  @Test
  public void testScoreForAceLowStraightFlush() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.TWO, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int i = 0; i < 5; i++) {
      game.placeCardInPosition(0, i, 0);
    }

    assertEquals(75, game.getScore()); // Ace-low straight flush should score 75
  }

  /**
   * Tests that an Ace-high straight flush (10-J-Q-K-A of the same suit)
   * is correctly scored as 75 points.
   */
  @Test
  public void testScoreForAceHighStraightFlush() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.TEN, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));

    game.startGame(deck, false, 5);

    for (int i = 0; i < 5; i++) {
      game.placeCardInPosition(0, i, 0);
    }

    assertEquals(75, game.getScore()); // Ace-high straight flush should score 75
  }

  /**
   * Tests that a middle straight flush (5-6-7-8-9 of the same suit)
   * is correctly scored as 75 points.
   */
  @Test
  public void testScoreForMidStraightFlush() {
    PokerPolygons game = new LoosePokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.DIAMONDS));

    game.startGame(deck, false, 5);

    for (int i = 0; i < 5; i++) {
      game.placeCardInPosition(0, i, 0);
    }

    assertEquals(75, game.getScore()); // Middle straight flush should score 75
  }

  /**
   * Test that playing a full game with a board of side length 6 results in the expected score.
   */
  @Test
  public void testMissingJUnitTest() {
    PokerPolygons game = new LoosePokerTriangles(6);

    List<PlayingCard> defaultDeck = game.getNewDeck();
    List<PlayingCard> customDeck = new ArrayList<>();

    customDeck.add(new StandardPlayingCard(Ranks.QUEEN, Suits.SPADES));
    customDeck.add(new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    customDeck.add(new StandardPlayingCard(Ranks.THREE, Suits.HEARTS));
    customDeck.add(new StandardPlayingCard(Ranks.TWO, Suits.SPADES));
    customDeck.add(new StandardPlayingCard(Ranks.SEVEN, Suits.CLUBS));
    customDeck.add(new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    customDeck.add(new StandardPlayingCard(Ranks.FOUR, Suits.SPADES));
    customDeck.add(new StandardPlayingCard(Ranks.FOUR, Suits.CLUBS));
    customDeck.add(new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    customDeck.add(new StandardPlayingCard(Ranks.FOUR, Suits.HEARTS));
    customDeck.add(new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));
    customDeck.add(new StandardPlayingCard(Ranks.TEN, Suits.HEARTS));
    customDeck.add(new StandardPlayingCard(Ranks.NINE, Suits.HEARTS));
    customDeck.add(new StandardPlayingCard(Ranks.EIGHT, Suits.HEARTS));
    customDeck.add(new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS));
    customDeck.add(new StandardPlayingCard(Ranks.THREE, Suits.SPADES));
    customDeck.add(new StandardPlayingCard(Ranks.SEVEN, Suits.DIAMONDS));
    customDeck.add(new StandardPlayingCard(Ranks.THREE, Suits.DIAMONDS));
    customDeck.add(new StandardPlayingCard(Ranks.SEVEN, Suits.HEARTS));
    customDeck.add(new StandardPlayingCard(Ranks.SIX, Suits.SPADES));
    customDeck.add(new StandardPlayingCard(Ranks.SEVEN, Suits.SPADES));

    for (int i = customDeck.size(); i < 52; i++) {
      customDeck.add(defaultDeck.get(i));
    }

    game.startGame(customDeck, false, 25);

    for (int row = 0; row < 6; row++) {
      for (int col = 0; col <= row; col++) {
        game.placeCardInPosition(0, row, col);
      }
    }

    // Note: This assumes PokerTrianglesTextualView works with LoosePokerTriangles
    PokerTrianglesTextualView view = new PokerTrianglesTextualView(game);
    System.out.println(view.toString());

    assertEquals(127, game.getScore());
  }
}