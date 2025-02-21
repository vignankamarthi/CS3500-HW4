package cs3500.pokerpolygons.model.hw04;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.Ranks;
import cs3500.pokerpolygons.model.hw02.StandardPlayingCard;
import cs3500.pokerpolygons.model.hw02.Suits;

/**
 * An abstract class to represent a Basic Polygon (triangles, squares, rectangles, circles, etc.)
 * Poker game where scoring includes valid rows, columns, and/or diagonals and scoring is determined
 * by the corresponding {@link PokerBasicScoring} class and implement the corresponding
 * {@link PokerPolygons} methods.
 */
public abstract class PokerBasicPolygons implements PokerPolygons<PlayingCard> {

  // Common fields for all BasicPolygon models.
  protected Deque<PlayingCard> deck;
  protected ArrayList<PlayingCard> hand;
  protected boolean isGameStarted;
  protected int handSize;
  protected Random randomizer;
  protected PlayingCard[][] gameBoard;

  /**
   * Creates and returns a new standard deck of 52 playing cards.
   *
   * @return a new list containing all 52 unique playing cards
   */
  @Override
  public List<PlayingCard> getNewDeck() {
    List<PlayingCard> defaultDeck = new ArrayList<>();
    for (Ranks rank : Ranks.values()) {
      for (Suits suit : Suits.values()) {
        defaultDeck.add(new StandardPlayingCard(rank, suit));
      }
    }
    return defaultDeck;
  }

  /**
   * Returns a copy of the player's current hand.
   *
   * @return a new list containing the cards in hand
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public List<PlayingCard> getHand() {
    gameHasNotStartedException();

    return new ArrayList<>(hand);
  }

  /**
   * Returns the number of cards left in the deck.
   *
   * @return the remaining deck size
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int getRemainingDeckSize() {
    gameHasNotStartedException();

    return deck.size();
  }

  /**
   * Discards the specified card from the hand and draws a new card from the deck.
   *
   * @param cardIdx the index of the card in hand to discard (0-indexed)
   * @throws IllegalStateException if the game has not started or if there are not enough cards left
   * @throws IllegalArgumentException if cardIdx is out of bounds for the hand
   */
  @Override
  public void discardCard(int cardIdx) {
    gameHasNotStartedException();

    if (deck.size() + hand.size() - 1 < getPlayableSpots()) {
      throw new IllegalStateException("Not enough cards left to discard and continue playing.");
    }
    if (hand.isEmpty()) {
      throw new IllegalStateException("Hand is empty.");
    }
    if (cardIdx < 0 || cardIdx >= hand.size()) {
      throw new IllegalArgumentException("Card index out of bounds of the hand: " + (cardIdx + 1));
    }
    hand.remove(cardIdx);
    hand.add(deck.removeFirst());
  }

  /**
   * Starts the game with the given deck, shuffle flag, and hand size.
   * Initializes the internal deck, hand, and game board.
   *
   * @param deck     the list of cards to play with
   * @param shuffle  whether the deck should be shuffled before starting
   * @param handSize the number of cards in the starting hand
   * @throws IllegalStateException    if the game has already started
   * @throws IllegalArgumentException if the deck is null, contains null, handSize is not positive,
   *                                  or the deck does not have enough cards for the hand and board
   */
  @Override
  public void startGame(List<PlayingCard> deck, boolean shuffle, int handSize) {
    if (deck == null) {
      throw new IllegalArgumentException("Deck is null.");
    }
    if (deck.contains(null)) {
      throw new IllegalArgumentException("Deck contains a null card.");
    }
    if (handSize <= 0) {
      throw new IllegalArgumentException("Hand size must be positive.");
    }
    if (deck.size() < handSize + getTotalBoardSize()) {
      throw new IllegalArgumentException("Deck size must be at least large enough to cover the " +
              "hand and board fully: "
              + (handSize + getTotalBoardSize()));
    }
    if (isGameStarted) {
      throw new IllegalStateException("Game has already started.");
    }
    this.handSize = handSize;
    List<PlayingCard> deckCopy = new ArrayList<>(deck);
    if (shuffle) {
      Collections.shuffle(deckCopy, randomizer);
    }
    this.hand = new ArrayList<>(deckCopy.subList(0, handSize));
    deckCopy.subList(0, handSize).clear();
    this.deck = new ArrayDeque<>(deckCopy);
    this.gameBoard = getInitializedBoard();
    isGameStarted = true;
  }

  /**
   * Returns the current score of the game.
   *
   * @return the current score of the game
   * @throws IllegalStateException if the game has not started
   */
  public abstract int getScore();

  /**
   * Returns the total number of playable cells on the board.
   *
   * @return the total board size
   */
  protected abstract int getTotalBoardSize();

  /**
   * Returns the number of playable spots left on the board.
   *
   * @return the count of empty cells
   */
  protected abstract int getPlayableSpots();

  /**
   * Initializes and returns the game board.
   *
   * @return the initialized game board with empty cards
   */
  protected abstract PlayingCard[][] getInitializedBoard();


  /**
   * Places a card from the hand to a given position on the polygonal board and then
   * draws a card from the deck if able.
   * @param cardIdx index of the card in hand to place (0-index based)
   * @param row row to place the card in (0-index based)
   * @param col column to place the card in (0-index based)
   * @throws IllegalStateException if the game has not started or there is a card at the given
   *     position
   * @throws IllegalArgumentException if cardIdx is out of bounds of the hand or
   *     row and col do not indicate a position on the polygon
   */
  @Override
  public abstract void placeCardInPosition(int cardIdx, int row, int col);

  /**
   * Returns the card in the indicated position on the board. If there is no card on the board
   * and the position is valid, the method will return null.
   * @param row the row to access
   * @param col the column to access
   * @return the card in the valid position or null if the position has no card
   * @throws IllegalArgumentException if the row and column are not a valid location
   *         for a card in the polygonal board
   */
  @Override
  public abstract PlayingCard getCardAt(int row, int col);

  /**
   * Returns true if the game is over. The implementation must
   * describe what it means for the game to be over.
   * @return true if the game is over, false otherwise
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public abstract boolean isGameOver();

  /**
   * Retrieve the number of cards that make up the width of the rectangle
   * that contains the polygon. (e.g. the number of columns in the widest row)
   * @return the width of the board
   */
  @Override
  public abstract int getWidth();


  /**
   * Retrieve the number of cards that make up the height of the rectangle
   * that contains the polygon. (e.g. the number of rows in the highest column)
   * @return the height of the board
   */
  @Override
  public abstract int getHeight();

  /**
   * To handle the exception if the game has started.
   *
   * @throws IllegalStateException if the game has not started.
   */
  protected IllegalStateException gameHasNotStartedException() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    return null;
  }
}