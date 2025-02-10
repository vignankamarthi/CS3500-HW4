package cs3500.pokerpolygons.model.hw02;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import cs3500.pokerpolygons.model.hw02.Ranks;
import cs3500.pokerpolygons.model.hw02.Suits;
import cs3500.pokerpolygons.view.PokerTrianglesTextualView;


/**
 * Comprehensive test suite that ensures every IllegalArgumentException
 * is properly thrown in PokerTriangles.
 */
public class PokerTrianglesTest {

  /**
   * 1. Test placeCardInPosition: Handles multiple IllegalStateException and
   * IllegalArgumentException cases
   */
  @Test
  public void testPlaceCardInPosition() {
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);
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
      assertEquals("Card index out of bounds of the hand: 10", e.getMessage());
    }




  }

  /**
   * 3. Test startGame: Handles IllegalStateException and IllegalArgumentException cases
   */
  @Test
  public void testStartGame() {
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);
    assertEquals(5, game.getWidth());
  }

  /**
   * 5. Test getHeight: Should return correct value
   */
  @Test
  public void testGetHeight() {
    PokerTriangles game = new PokerTriangles(5);
    assertEquals(5, game.getHeight());
  }

  /**
   * 6. Test getNewDeck: Should return 52 cards
   */
  @Test
  public void testGetNewDeck() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    assertEquals(52, deck.size());
  }

  /**
   * 7. Test getCardAt: Handles IllegalArgumentException cases
   */
  @Test
  public void testGetCardAt() {
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);

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
    PokerTriangles game = new PokerTriangles(5);

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
    PokerTriangles game = new PokerTriangles(5);

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
    PokerTriangles game = new PokerTriangles(5);

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
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);

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
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    PlayingCard oldFirstCard = game.getHand().get(0);
    game.discardCard(0); // Discard the first card

    assertNotEquals(oldFirstCard, game.getHand().get(0)); // First card should now be different
  }


  /**
   * Tests that discarding a card reduces the remaining deck size.
   */
  @Test
  public void testDeckSizeAfterDiscard() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    int initialDeckSize = game.getRemainingDeckSize();

    game.discardCard(0); // Discard the first card

    assertEquals(initialDeckSize - 1, game.getRemainingDeckSize());
  }

  /**
   * Tests that discarding the last card in hand correctly removes it.
   */
  @Test
  public void testDiscardLastCard() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    int lastCardIndex = game.getHand().size() - 1;
    PlayingCard lastCard = game.getHand().get(lastCardIndex);

    game.discardCard(lastCardIndex);

    assertFalse(game.getHand().contains(lastCard)); // Ensure last card is no longer in hand
  }


  /**
   * Tests discarding a card from the middle of the hand.
   */
  @Test
  public void testDiscardMiddleCard() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    int middleIndex = game.getHand().size() / 2;
    PlayingCard middleCard = game.getHand().get(middleIndex);

    game.discardCard(middleIndex);

    assertFalse(game.getHand().contains(middleCard)); // Ensure middle card was removed
  }

  /**
   * Tests that the game starts with the correct number of cards in hand.
   */
  @Test
  public void testStartGameInitialHandSize() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    int handSize = 5;

    game.startGame(deck, false, handSize);

    assertEquals(handSize, game.getHand().size());
  }

  /**
   * Tests that starting the game correctly reduces the deck size.
   */
  @Test
  public void testStartGameDeckSizeReduction() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    int handSize = 5;
    int initialDeckSize = deck.size();

    game.startGame(deck, false, handSize);

    assertEquals(initialDeckSize - handSize, game.getRemainingDeckSize());
  }

  /**
   * Tests that the board is empty at the start of the game.
   */
  @Test
  public void testStartGameBoardEmpty() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    for (int row = 0; row < game.getHeight(); row++) {
      for (int col = 0; col <= row; col++) {
        assertNull(game.getCardAt(row, col)); // Ensure no cards are placed initially
      }
    }
  }


  /**
   * Tests that starting the game with shuffle enabled changes the deck order.
   */
  @Test
  public void testStartGameShuffleChangesOrder() {
    PokerTriangles game1 = new PokerTriangles(5);
    PokerTriangles game2 = new PokerTriangles(5);

    List<PlayingCard> deck1 = game1.getNewDeck();
    List<PlayingCard> deck2 = game2.getNewDeck();

    game1.startGame(deck1, false, 5); // No shuffle
    game2.startGame(deck2, true, 5);  // With shuffle

    assertNotEquals(game1.getHand(), game2.getHand()); // Hands should be different// Hands should be different
  }


  /**
   * Tests that the game can start with a different hand size.
   */
  @Test
  public void testStartGameWithDifferentHandSize() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    int customHandSize = 7; // Change default

    game.startGame(deck, false, customHandSize);

    assertEquals(customHandSize, game.getHand().size());
  }

  /**
   * Tests that the game can start when the deck has just enough cards.
   */
  @Test
  public void testStartGameWithMinimalDeck() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = new ArrayList<>();

    // Add only enough cards for the hand and board
    for (int i = 0; i <= 20; i++) {
      deck.add(new StandardPlayingCard(Ranks.ACE, Suits.SPADES)); // Sample card
    }

    game.startGame(deck, false, 5);

    assertEquals(5, game.getHand().size()); // Hand size still correct
    assertEquals(16, game.getRemainingDeckSize()); // Deck has only enough for board
  }


  /**
   * Tests that the game starts correctly with a full deck of 52 cards.
   */
  @Test
  public void testStartGameWithFullDeck() {
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    assertEquals(52, deck.size());
  }


  /**
   * Tests that getNewDeck() contains only unique cards.
   */
  @Test
  public void testGetNewDeckUniqueCards() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    Set<PlayingCard> uniqueCards = new HashSet<>(deck);

    assertEquals(52, uniqueCards.size()); // Ensures all 52 cards are unique
  }

  /**
   * Tests that getCardAt(row, col) correctly retrieves cards placed on the board.
   */
  @Test
  public void testGetCardAtFunction() {
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5); // Start game with hand size 5

    List<PlayingCard> hand = game.getHand();

    // TO create the default deck again to compare to the cards inserted into the hand.
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
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Start the game with a known deck
    game.startGame(deck, false, 5);

    // Randomizer to select a card index from 0-4
    // I wanted to randomize the cards for no reason lol.
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    assertEquals(0, game.getScore());
  }

  /**
   * Test that getScore() updates correctly when a valid hand is placed.
   */
  @Test
  public void testGetScoreAfterPlacingCards() {
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);
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
    PokerTriangles game = new PokerTriangles(5);
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
    // Initialize game
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Shuffle deck before adding custom pair cards
    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    // Start the game with shuffle disabled (so our straight is preserved)
    game.startGame(deck, false, 5);

    // Place the five pre-determined pair cards on the board
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
    // Initialize game
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Shuffle deck before adding custom two-pair cards
    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    // Start the game with shuffle disabled (so our straight is preserved)
    game.startGame(deck, false, 5);

    // Place the five pre-determined two-pair cards on the board
    for (int i = 0; i < 5; i++) {
      game.placeCardInPosition(0, i, 0);
    }

    // Verify the score is exactly 5 (Two Pair)
    assertEquals(5, game.getScore()); // Two pairs should score exactly 5
  }

  /**
   * Test that three of a kind is correctly scored as 10 points.
   */
  @Test
  public void testScoreForThreeOfAKind() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Shuffle deck before adding custom three-of-a-kind cards
    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    // Start the game with shuffle disabled (so our straight is preserved)
    game.startGame(deck, false, 5);

    // Place the five pre-determined three-of-a-kind cards on the board
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Shuffle deck before adding custom straight cards
    Collections.shuffle(deck);

    // Insert an Ace-low straight (A-2-3-4-5 of mixed suits) at the top of the deck
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.TWO, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    // Start the game with shuffle disabled (so our straight is preserved)
    game.startGame(deck, false, 5);

    // Place the five pre-determined straight cards on the board
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Shuffle deck before adding custom straight cards
    Collections.shuffle(deck);

    // Insert an Ace-high straight (10-J-Q-K-A of mixed suits) at the top of the deck
    deck.add(0, new StandardPlayingCard(Ranks.TEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));

    // Start the game with shuffle disabled (so our straight is preserved)
    game.startGame(deck, false, 5);

    // Place the five pre-determined straight cards on the board
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Shuffle deck before adding custom straight cards
    Collections.shuffle(deck);

    // Insert a middle straight (5-6-7-8-9 of mixed suits) at the top of the deck
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.HEARTS));

    // Start the game with shuffle disabled (so our straight is preserved)
    game.startGame(deck, false, 5);

    // Place the five pre-determined straight cards on the board
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Manually shuffle the deck BEFORE adding flush cards
    Collections.shuffle(deck);

    // Insert a known flush (five cards of the same suit) at the top of the deck
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.HEARTS));

    // Start the game with shuffle disabled (so our manually set flush is preserved)
    game.startGame(deck, false, 5);

    // Place the five pre-determined flush cards on the board
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Manually shuffle the deck BEFORE adding full house cards
    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.CLUBS));

    // Start the game with shuffle disabled
    game.startGame(deck, false, 5);

    // Place the five pre-determined full house cards on the board
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Manually shuffle the deck BEFORE adding four of a kind cards
    Collections.shuffle(deck);

    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.CLUBS));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.CLUBS));

    // Start the game with shuffle disabled
    game.startGame(deck, false, 5);

    // Place the five pre-determined four of a kind cards on the board
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Shuffle deck before adding custom straight flush cards
    Collections.shuffle(deck);

    // Insert Ace-low straight flush (A-2-3-4-5 of HEARTS) at the top of the deck
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.TWO, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.THREE, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FOUR, Suits.HEARTS));
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.HEARTS));

    // Start the game with shuffle disabled (so our straight flush is preserved)
    game.startGame(deck, false, 5);

    // Place the five pre-determined straight flush cards on the board
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Shuffle deck before adding custom straight flush cards
    Collections.shuffle(deck);

    // Insert Ace-high straight flush (10-J-Q-K-A of SPADES) at the top of the deck
    deck.add(0, new StandardPlayingCard(Ranks.TEN, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.JACK, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.QUEEN, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.KING, Suits.SPADES));
    deck.add(0, new StandardPlayingCard(Ranks.ACE, Suits.SPADES));

    // Start the game with shuffle disabled (so our straight flush is preserved)
    game.startGame(deck, false, 5);

    // Place the five pre-determined straight flush cards on the board
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
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    // Shuffle deck before adding custom straight flush cards
    Collections.shuffle(deck);

    // Insert a middle straight flush (5-6-7-8-9 of DIAMONDS) at the top of the deck
    deck.add(0, new StandardPlayingCard(Ranks.FIVE, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SIX, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.SEVEN, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.EIGHT, Suits.DIAMONDS));
    deck.add(0, new StandardPlayingCard(Ranks.NINE, Suits.DIAMONDS));

    // Start the game with shuffle disabled (so our straight flush is preserved)
    game.startGame(deck, false, 5);

    // Place the five pre-determined straight flush cards on the board
    for (int i = 0; i < 5; i++) {
      game.placeCardInPosition(0, i, 0);
    }

    assertEquals(75, game.getScore()); // Middle straight flush should score 75
  }

  /**
   * Test that playing a full game with a board of side length 6 works correctly.
   */
  /**
   * Test that playing a full game with a board of side length 6 results in the expected board state.
   */
  /**
   * Test that playing a full game with a board of side length 6 results in the expected score.
   */
  @Test
  public void testMissingJUnitTest() {
    PokerTriangles game = new PokerTriangles(6);

    // Generate a default deck and create a new list for controlled setup
    List<PlayingCard> defaultDeck = game.getNewDeck();
    List<PlayingCard> customDeck = new ArrayList<>();

    // Add cards in the EXACT order as required by the JUnit test case
    // I couldn't pass for homework 2
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

    // Fill the remaining deck with arbitrary cards that won't contribute to the score
    for (int i = customDeck.size(); i < 52; i++) {
      customDeck.add(defaultDeck.get(i));
    }

    // Start game with the custom deck (shuffle disabled)
    game.startGame(customDeck, false, 25);

    // Place the cards in order from (0,0) top-left, row by row
    for (int row = 0; row < 6; row++) {
      for (int col = 0; col <= row; col++) {
        game.placeCardInPosition(0, row, col);
      }
    }

    // Print the board state using the view’s toString() method
    PokerTrianglesTextualView view = new PokerTrianglesTextualView(game);
    System.out.println(view.toString());

    // Verify that the final score matches the expected 127 from the grading system
    assertEquals(127, game.getScore());
  }
}