package cs3500.pokerpolygons.model.hw04;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.Ranks;
import cs3500.pokerpolygons.model.hw02.StandardPlayingCard;
import cs3500.pokerpolygons.model.hw02.Suits;

/**
 * Tests for the PokerRectangles class including exceptions, general behavior,
 * and edge case behavior.
 */
public class PokerRectanglesTest {

  /**
   * 1. Test placeCardInPosition: Handles multiple IllegalStateException and
   * IllegalArgumentException cases.
   */
  @Test
  public void testPlaceCardInPosition() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    try {
      game.placeCardInPosition(0, 2, 7);
    } catch (IllegalArgumentException e) {
      assertEquals("Row or column out of bounds: 2, 7", e.getMessage());
    }

    try {
      game.placeCardInPosition(0, -1, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Row or column out of bounds: -1, 0", e.getMessage());
    }
  }

  /**
   * 2. Test discardCard: Handles IllegalStateException and IllegalArgumentException cases.
   */
  @Test
  public void testDiscardCard() {
    PokerRectangles game = new PokerRectangles(7, 5);
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
      assertTrue(e.getMessage().contains("Card index out of bounds"));
    }
  }

  /**
   * 3. Test startGame: Handles IllegalStateException and IllegalArgumentException cases.
   */
  @Test
  public void testStartGame() {
    PokerRectangles game = new PokerRectangles(7, 5);
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
   * 4. Test getWidth: Should return the correct width.
   */
  @Test
  public void testGetWidth() {
    PokerRectangles game = new PokerRectangles(7, 5);
    assertEquals(7, game.getWidth());
  }

  /**
   * 5. Test getHeight: Should return the correct height.
   */
  @Test
  public void testGetHeight() {
    PokerRectangles game = new PokerRectangles(7, 5);
    assertEquals(5, game.getHeight());
  }

  /**
   * 6. Test getNewDeck: Should return a 52-card deck with unique cards.
   */
  @Test
  public void testGetNewDeck() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    assertEquals(52, deck.size());
    Set<PlayingCard> uniqueCards = new HashSet<>(deck);
    assertEquals(52, uniqueCards.size());
  }

  /**
   * 7. Test getCardAt: Handles IllegalArgumentException for invalid positions.
   */
  @Test
  public void testGetCardAt() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    try {
      game.getCardAt(-1, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Row or column out of bounds: -1, 0", e.getMessage());
    }

    try {
      game.getCardAt(5, 3);
    } catch (IllegalArgumentException e) {
      assertEquals("Row or column out of bounds: 5, 3", e.getMessage());
    }

    try {
      game.getCardAt(2, 7);
    } catch (IllegalArgumentException e) {
      assertEquals("Row or column out of bounds: 2, 7", e.getMessage());
    }
  }

  /**
   * 8. Test getHand: Should throw IllegalStateException if game has not started.
   */
  @Test
  public void testGetHand() {
    PokerRectangles game = new PokerRectangles(7, 5);
    try {
      game.getHand();
    } catch (IllegalStateException e) {
      assertEquals("Game has not started.", e.getMessage());
    }
  }

  /**
   * 9. Test getScore: Should throw IllegalStateException if game has not started.
   */
  @Test
  public void testGetScore() {
    PokerRectangles game = new PokerRectangles(7, 5);
    try {
      game.getScore();
    } catch (IllegalStateException e) {
      assertEquals("Game has not started.", e.getMessage());
    }
  }

  /**
   * 10. Test getRemainingDeckSize: Should throw IllegalStateException if game has not started.
   */
  @Test
  public void testGetRemainingDeckSize() {
    PokerRectangles game = new PokerRectangles(7, 5);
    try {
      game.getRemainingDeckSize();
    } catch (IllegalStateException e) {
      assertEquals("Game has not started.", e.getMessage());
    }
  }

  /**
   * 11. Test isGameOver: Should throw IllegalStateException if game has not started.
   */
  @Test
  public void testIsGameOver() {
    PokerRectangles game = new PokerRectangles(7, 5);
    try {
      game.isGameOver();
    } catch (IllegalStateException e) {
      assertEquals("Game has not started.", e.getMessage());
    }
  }

  /**
   * Tests that a placed card is correctly set at the given board position.
   */
  @Test
  public void testPlaceCardInCorrectPosition() {
    PokerRectangles game = new PokerRectangles(7, 5);
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
    PokerRectangles game = new PokerRectangles(7, 5);

    List<PlayingCard> deck = game.getNewDeck();

    PlayingCard firstCard = new StandardPlayingCard(Ranks.TWO, Suits.CLUBS);
    PlayingCard secondCard = new StandardPlayingCard(Ranks.TWO, Suits.HEARTS);

    game.startGame(deck, false, 5);

    game.placeCardInPosition(0, 0, 0);
    game.placeCardInPosition(1, 1, 1);

    assertEquals(firstCard, game.getCardAt(0, 0));
    assertEquals(secondCard, game.getCardAt(1, 1));
  }

  /**
   * Tests that the deck size decreases when cards are placed on the board.
   */
  @Test
  public void testDeckSizeDecreasesAfterPlacingCards() {
    PokerRectangles game = new PokerRectangles(7, 5);
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
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    game.placeCardInPosition(0, 0, 0);
    game.placeCardInPosition(1, 1, 0);
    game.placeCardInPosition(1, 1, 1);

    // Check that some positions in row 2 remain empty.
    assertNull(game.getCardAt(2, 0));
    assertNull(game.getCardAt(2, 1));
    assertNull(game.getCardAt(2, 2));
  }

  /**
   * Tests that discarding a card correctly reduces hand size.
   */
  @Test
  public void testHandSizeAfterDiscard() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    int initialHandSize = game.getHand().size();

    game.discardCard(0); // Discard the first card

    assertEquals(initialHandSize, game.getHand().size());
  }

  /**
   * Tests that a new card is drawn into the hand after discarding.
   */
  @Test
  public void testNewCardIsDrawnAfterDiscard() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    PlayingCard oldFirstCard = game.getHand().get(0);
    game.discardCard(0); // Discard the first card

    assertNotEquals("First card should now be different after discarding.",
            oldFirstCard, game.getHand().get(0));
  }

  /**
   * Tests that discarding a card reduces the remaining deck size by one.
   */
  @Test
  public void testDeckSizeAfterDiscard() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    int initialDeckSize = game.getRemainingDeckSize();

    game.discardCard(0); // Discard the first card

    assertEquals("Remaining deck size should decrease by one after discarding.",
            initialDeckSize - 1, game.getRemainingDeckSize());
  }

  /**
   * Tests that the game starts correctly with a full deck of 52 cards.
   */
  @Test
  public void testStartGameWithFullDeck() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck(); // Full 52-card deck

    game.startGame(deck, false, 5);

    assertEquals("Hand size should be 5.", 5, game.getHand().size());
    // For a full deck: 52 - 5 = 47 remaining cards.
    assertEquals("Remaining deck size should be 47.", 47,
            game.getRemainingDeckSize());
  }

  /**
   * Tests that getNewDeck() returns a deck with exactly 52 cards.
   */
  @Test
  public void testGetNewDeckSize() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();

    assertEquals("A new deck should contain 52 cards.", 52, deck.size());
  }

  /**
   * Tests that getNewDeck() contains only unique cards.
   */
  @Test
  public void testGetNewDeckUniqueCards() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    Set<PlayingCard> uniqueCards = new HashSet<>(deck);

    assertEquals("All 52 cards in the deck should be unique.", 52,
            uniqueCards.size());
  }

  /**
   * Tests that getCardAt(row, col) correctly retrieves cards placed on the board.
   */
  @Test
  public void testGetCardAtFunction() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    game.placeCardInPosition(0, 0, 0); // Place card at (0,0)

    List<PlayingCard> defaultDeck = game.getNewDeck();

    assertEquals(defaultDeck.get(0), game.getCardAt(0, 0));
  }

  /**
   * Tests that getHand() correctly retrieves the player's current hand.
   */
  @Test
  public void testGetHandFunction() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5); // Start game with hand size 5

    List<PlayingCard> hand = game.getHand();

    // Create a default deck for comparison.
    List<PlayingCard> defaultDeck = game.getNewDeck();

    assertEquals("Hand size should be 5.", 5, hand.size());

    // Ensure the hand contains the first 5 cards from the deck.
    for (int i = 0; i < 5; i++) {
      assertEquals(defaultDeck.get(i), hand.get(i));
    }
  }

  /**
   * Tests that getRemainingDeckSize() returns the correct initial deck size.
   */
  @Test
  public void testGetRemainingDeckSizeInitial() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    // 52 total cards - 5 initial hand cards = 47 remaining.
    assertEquals("Remaining deck size should be 47.", 47,
            game.getRemainingDeckSize());
  }

  /**
   * Tests that getRemainingDeckSize() correctly updates when discarding cards.
   */
  @Test
  public void testGetRemainingDeckSizeAfterDiscarding() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    game.discardCard(0);
    assertEquals("Remaining deck size should update correctly after one discard.",
            46, game.getRemainingDeckSize());

    game.discardCard(0);
    assertEquals("Remaining deck size should update correctly after two discards.",
            45, game.getRemainingDeckSize());
  }

  /**
   * Tests that isGameOver() returns false when the game starts.
   */
  @Test
  public void testIsGameOverAtStart() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    assertFalse("Game should not be over at start.", game.isGameOver());
  }

  /**
   * Tests that isGameOver() returns false when the game has some cards on the board but isn't full.
   */
  @Test
  public void testIsGameOverAtMiddle() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    for (int row = 0; row < 3; row++) {
      // Place cards in the first 3 columns of each row.
      for (int col = 0; col < 3; col++) {
        game.placeCardInPosition(0, row, col);
      }
    }
    assertFalse("Game should not be over when board is partially filled.", game.isGameOver());
  }

  /**
   * Tests that isGameOver() returns true when the board is completely filled.
   */
  @Test
  public void testIsGameOverAtEnd() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    // Fill the entire board.
    for (int row = 0; row < game.getHeight(); row++) {
      for (int col = 0; col < game.getWidth(); col++) {
        if (!game.isGameOver()) {
          // Always use the first card from hand.
          game.placeCardInPosition(0, row, col);
        }
      }
    }
    assertTrue("Game should be over when board is full.", game.isGameOver());
  }

  /**
   * Tests that getScore() returns 0 at the start of the game.
   */
  @Test
  public void testGetScoreAtStart() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    assertEquals("Score should be 0 at the start.", 0, game.getScore());
  }

  /**
   * Tests that getScore() updates correctly when a valid hand is placed.
   * For rectangles, scoring considers rows and columns.
   */
  @Test
  public void testGetScoreAfterPlacingCards() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    // Place 5 cards in row 0 (forming a valid row)
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    assertTrue("Score should be greater than 0 after placing a valid hand.", game.getScore() > 0);
  }

  /**
   * Tests that getScore() correctly calculates when multiple hands are completed.
   */
  @Test
  public void testGetScoreWithMultipleHands() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 1, col);
    }
    assertTrue("Score should increase when multiple hands are completed.", game.getScore() > 0);
  }

  /**
   * Tests that getScore() returns a non-zero score when the board is completely filled.
   */
  @Test
  public void testGetScoreWhenBoardIsFull() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    // Fill the board completely.
    for (int row = 0; row < game.getHeight(); row++) {
      for (int col = 0; col < game.getWidth(); col++) {
        game.placeCardInPosition(0, row, col);
      }
    }
    assertTrue("Score should be greater than 0 when board is full.", game.getScore() > 0);
  }

  /**
   * Tests that a single pair is correctly scored as 2 points.
   */
  @Test
  public void testScoreForPair() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    // Start the game with shuffle disabled to preserve our custom order.
    game.startGame(deck, false, 5);

    // Place the five predetermined pair cards in row 0.
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }
    assertEquals("A single pair should score exactly 2 points.", 2, game.getScore());
  }

  /**
   * Tests that two pairs are correctly scored as 5 points.
   */
  @Test
  public void testScoreForTwoPair() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    // Start the game with shuffle disabled.
    game.startGame(deck, false, 5);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }

    // Two pairs should score exactly 5.
    assertEquals(5, game.getScore());
  }

  /**
   * Tests that three of a kind is correctly scored as 10 points.
   */
  @Test
  public void testScoreForThreeOfAKind() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }

    // Three-of-a-kind should score exactly 10.
    assertEquals(10, game.getScore());
  }

  /**
   * Tests that an Ace-low straight (A-2-3-4-5 of mixed suits)
   * is correctly scored as 15 points.
   */
  @Test
  public void testScoreForAceLowStraight() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    // Insert an Ace-low straight (A-2-3-4-5 of mixed suits) at the top of the deck.
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.TWO, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    // Place the five straight cards in row 0 (columns 0-4).
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }

    assertEquals(15, game.getScore());
  }

  /**
   * Tests that an Ace-high straight (10-J-Q-K-A of mixed suits)
   * is correctly scored as 15 points.
   */
  @Test
  public void testScoreForAceHighStraight() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    // Insert an Ace-high straight (10-J-Q-K-A of mixed suits) at the top of the deck.
    deck.add(0, new StandardPlayingCard(Ranks.TEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));

    game.startGame(deck, false, 5);

    // Place the five straight cards in row 0 (columns 0-4).
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }

    assertEquals(15, game.getScore());
  }

  /**
   * Tests that a middle straight flush (5-6-7-8-9 of DIAMONDS)
   * is correctly scored as 75 points.
   */
  @Test
  public void testScoreForMidStraightFlush() {
    PokerRectangles game = new PokerRectangles(7, 5);
    List<PlayingCard> deck = game.getNewDeck();

    Collections.shuffle(deck);

    // Insert a middle straight flush (5-6-7-8-9 of DIAMONDS) at the top of the deck.
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.DIAMONDS));

    // Start the game with shuffle disabled.
    game.startGame(deck, false, 5);

    // Place the five pre-determined straight flush cards in row 0 (columns 0 to 4).
    for (int col = 0; col < 5; col++) {
      game.placeCardInPosition(0, 0, col);
    }

    assertEquals(75, game.getScore()); // Middle straight flush should score 75.
  }

  /**
   * Tests that playing a full game with a board of size 6x6 results in the expected score.
   */
  @Test
  public void testMissingJUnitTest() {
    // Use a 6x6 board for this test.
    PokerRectangles game = new PokerRectangles(6, 6);

    // Generate a default deck and create a custom deck for controlled setup.
    List<PlayingCard> defaultDeck = game.getNewDeck();
    List<PlayingCard> customDeck = new ArrayList<>();

    customDeck.add(new StandardPlayingCard(Ranks.QUEEN, Suits.SPADES));  // (0,0)

    customDeck.add(new StandardPlayingCard(Ranks.ACE, Suits.SPADES));    // (1,0)
    customDeck.add(new StandardPlayingCard(Ranks.THREE, Suits.HEARTS));  // (1,1)

    customDeck.add(new StandardPlayingCard(Ranks.TWO, Suits.SPADES));    // (2,0)
    customDeck.add(new StandardPlayingCard(Ranks.SEVEN, Suits.CLUBS));   // (2,1)
    customDeck.add(new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));   // (2,2)

    customDeck.add(new StandardPlayingCard(Ranks.FOUR, Suits.SPADES));   // (3,0)
    customDeck.add(new StandardPlayingCard(Ranks.FOUR, Suits.CLUBS));    // (3,1)
    customDeck.add(new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS)); // (3,2)
    customDeck.add(new StandardPlayingCard(Ranks.FOUR, Suits.HEARTS));   // (3,3)

    customDeck.add(new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));   // (4,0)
    customDeck.add(new StandardPlayingCard(Ranks.TEN, Suits.HEARTS));    // (4,1)
    customDeck.add(new StandardPlayingCard(Ranks.NINE, Suits.HEARTS));   // (4,2)
    customDeck.add(new StandardPlayingCard(Ranks.EIGHT, Suits.HEARTS));  // (4,3)
    customDeck.add(new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS));  // (4,4)

    customDeck.add(new StandardPlayingCard(Ranks.THREE, Suits.SPADES));  // (5,0)
    customDeck.add(new StandardPlayingCard(Ranks.SEVEN, Suits.DIAMONDS)); // (5,1)
    customDeck.add(new StandardPlayingCard(Ranks.THREE, Suits.DIAMONDS)); // (5,2)
    customDeck.add(new StandardPlayingCard(Ranks.SEVEN, Suits.HEARTS));   // (5,3)
    customDeck.add(new StandardPlayingCard(Ranks.SIX, Suits.SPADES));     // (5,4)
    customDeck.add(new StandardPlayingCard(Ranks.SEVEN, Suits.SPADES));   // (5,5)

    // Fill the remaining deck with arbitrary cards from the default deck.
    for (int i = customDeck.size(); i < 52; i++) {
      customDeck.add(defaultDeck.get(i));
    }

    // Start the game with the custom deck (shuffle disabled) and a large hand size.
    game.startGame(customDeck, false, 15);

    // Place the cards in order from (0,0) top-left, row by row.
    // For a rectangle, we fill all 6 columns for each of the 6 rows.
    for (int row = 0; row < 6; row++) {
      for (int col = 0; col < game.getWidth(); col++) {
        game.placeCardInPosition(0, row, col);
      }
    }

    // Verify that the final score matches the expected value.
    assertEquals(223, game.getScore());
  }
}