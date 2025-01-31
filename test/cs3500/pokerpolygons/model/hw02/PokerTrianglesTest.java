package cs3500.pokerpolygons.model.hw02;
//TODO: Refactor all tests
import org.junit.Test;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.*;

/**
 * Tests for the PokerTriangles class.
 */
public class PokerTrianglesTest {

  /**
   * Tests if placeCardInPosition() correctly places a card in a valid position.
   * Ensures the game state updates correctly and the hand and deck are updated.
   */
  @Test
  public void testPlaceCardInPositionValid() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    PlayingCard cardBefore = game.getHand().get(0);
    game.placeCardInPosition(0, 0, 0);

    assertEquals(cardBefore, game.getCardAt(0, 0));
    assertEquals(4, game.getHand().size()); // One card removed from hand
    assertEquals(51, game.getRemainingDeckSize()); // One card drawn from deck
  }

  /**
   * Tests if placeCardInPosition() throws an exception when the game is not started.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlaceCardInPositionBeforeStart() {
    PokerTriangles game = new PokerTriangles(5);
    game.placeCardInPosition(0, 0, 0);
  }

  /**
   * Tests if placeCardInPosition() throws an exception when placing in an occupied position.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlaceCardInOccupiedPosition() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    game.placeCardInPosition(0, 0, 0);
    game.placeCardInPosition(0, 0, 0); // Should fail
  }

  /**
   * Tests if discardCard() correctly removes a card from the hand and replaces it with one from the deck.
   */
  @Test
  public void testDiscardCardValid() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    PlayingCard discardedCard = game.getHand().get(0);
    game.discardCard(0);

    assertFalse(game.getHand().contains(discardedCard));
    assertEquals(5, game.getHand().size()); // Hand size should remain the same
    assertEquals(51, game.getRemainingDeckSize()); // One card removed from deck
  }

  /**
   * Tests if discardCard() throws an exception when the game is not started.
   */
  @Test(expected = IllegalStateException.class)
  public void testDiscardCardBeforeStart() {
    PokerTriangles game = new PokerTriangles(5);
    game.discardCard(0);
  }

  /**
   * Tests if startGame() properly initializes the game state.
   */
  @Test
  public void testStartGame() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, true, 5);

    assertEquals(5, game.getHand().size());
    assertEquals(52, game.getRemainingDeckSize()); // Deck remains full in this implementation
    assertFalse(game.isGameOver());
  }

  /**
   * Tests if startGame() throws an exception when given a null deck.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithNullDeck() {
    PokerTriangles game = new PokerTriangles(5);
    game.startGame(null, false, 5);
  }

  /**
   * Tests if getWidth() correctly returns the triangle side length.
   */
  @Test
  public void testGetWidth() {
    PokerTriangles game = new PokerTriangles(6);
    assertEquals(6, game.getWidth());
  }

  /**
   * Tests if getHeight() correctly returns the triangle side length.
   */
  @Test
  public void testGetHeight() {
    PokerTriangles game = new PokerTriangles(6);
    assertEquals(6, game.getHeight());
  }

  /**
   * Tests if getNewDeck() correctly returns a new deck with 52 cards.
   */
  @Test
  public void testGetNewDeck() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();

    assertEquals(52, deck.size());
  }

  /**
   * Tests if getCardAt() correctly retrieves a placed card.
   */
  @Test
  public void testGetCardAt() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    PlayingCard placedCard = game.getHand().get(0);
    game.placeCardInPosition(0, 0, 0);

    assertEquals(placedCard, game.getCardAt(0, 0));
  }

  /**
   * Tests if getCardAt() throws an exception when accessing an out-of-bounds position.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtOutOfBounds() {
    PokerTriangles game = new PokerTriangles(5);
    game.getCardAt(10, 10);
  }

  /**
   * Tests if getHand() correctly returns the current hand of the player.
   */
  @Test
  public void testGetHand() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    List<PlayingCard> hand = game.getHand();
    assertEquals(5, hand.size());
  }

  /**
   * Tests if getScore() correctly calculates the score.
   */
  @Test
  public void testGetScore() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    assertEquals(0, game.getScore()); // Assuming no hand is formed at the start
  }

  /**
   * Tests if getRemainingDeckSize() correctly returns the deck size.
   */
  @Test
  public void testGetRemainingDeckSize() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    assertEquals(52, game.getRemainingDeckSize()); // Full deck maintained
  }

  /**
   * Tests if isGameOver() returns false when the board is not yet filled.
   */
  @Test
  public void testIsGameOverFalse() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    assertFalse(game.isGameOver());
  }

  /**
   * Tests if isGameOver() returns true when the board is completely filled.
   */
  @Test
  public void testIsGameOverTrue() {
    PokerTriangles game = new PokerTriangles(5);
    List<PlayingCard> deck = game.getNewDeck();
    game.startGame(deck, false, 5);

    // Fill all board positions
    for (int row = 0; row < game.getHeight(); row++) {
      for (int col = 0; col <= row; col++) {
        if (game.getHand().size() > 0) {
          game.placeCardInPosition(0, row, col);
        }
      }
    }

    assertTrue(game.isGameOver());
  }
}