package cs3500.pokerpolygons.model.hw02;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import static cs3500.pokerpolygons.model.hw02.EmptyCard.getEmptyCard;

/**
 * To represent a PokerSquares type game in the shape of an equilateral, right triangle.
 */
class PokerTriangles implements PokerPolygons<PlayingCard> {

  private Deque<PlayingCard> deck;
  private boolean shuffle;
  private int handSize;
  private ArrayList<PlayingCard> hand;
  private boolean isGameStarted;
  private final int sideLength;
  private Random randomizer;
  private PlayingCard[][] gameBoard;

  /**
   * A constructor to create an instance of a PokerTriangle game
   * with only the side.
   * @param sideLength is the length of the sides of the right equilateral triangle.
   * @throws IllegalArgumentException if the side length is less than 5.
   *
   */
  public PokerTriangles(int sideLength) {
    if (sideLength < 5) {
      throw new IllegalArgumentException("Side length must be at least 5: " + sideLength);
    }

    this.shuffle = false;
    this.isGameStarted = false;
    this.sideLength = sideLength;
    this.randomizer = new Random();
  }


  /**
   * A constructor to create an instance of a PokerTriangle game with
   * a random object to seed random operations.
   * with only the side.
   * @param sideLength is the length of the sides of the right equilateral triangle.
   * @throws IllegalArgumentException if the side length is less than 5.
   * @throws IllegalArgumentException if the Randomizer is null.
   *
   */
  public PokerTriangles(int sideLength, Random randomizer) {
    if (sideLength < 5) {
      throw new IllegalArgumentException("Side length must be at least 5: " + sideLength);
    }
    if (randomizer == null) {
      throw new IllegalArgumentException("Randomizer cannot be null.");
    }

    this.shuffle = false;
    this.isGameStarted = false;
    this.sideLength = sideLength;
    this.randomizer = randomizer;

  }

  //TODO: Decide whether to make this static and edit a current board or return a new one.
  /**
   * To implement the game board given a side length.
   * @param sideLength is the desired side length of the board.
   * @return an initialized game board given the side length with
   *         all empty cards.
   */
  private PlayingCard[][] initializeGameBoard(int sideLength) {
    PlayingCard[][] gameBoard = new PlayingCard[sideLength][];
    for (int row = 0; row < sideLength; row++) {
      gameBoard[row] = new PlayingCard[row + 1];
      for (int col = 0; col <= row; col++) {
        gameBoard[row][col] = getEmptyCard();
      }
    }
    return gameBoard;
  }


  /**
   * Returns the total number of cells playable in this PokerTriangles.
   * @return the total number of playable spaces on the board.
   */
  private int getTotalBoardSize() {
    return (this.getSizeLength() + (this.getSizeLength() + 1)) / 2;
  }

  /**
   * A getter for the private final field sideLength, representing the game of the size.
   * @return this PokerTriangle's game size.
   */
  private int getSizeLength() {
    return this.sideLength;
  }

  /**
   * Places a card from the hand to a given position on the equilateral right triangle board and then
   * draws a card from the deck if able.
   *
   * @param cardIdx index of the card in hand to place (0-index based)
   * @param row     row to place the card in (0-index based)
   * @param col     column to place the card in (0-index based)
   * @throws IllegalStateException    if the game has not started or there is a card at the given
   *                                  position
   * @throws IllegalArgumentException if cardIdx is out of bounds of the hand or
   *                                  row and col do not indicate a position on the polygon
   */
  @Override
  public void placeCardInPosition(int cardIdx, int row, int col) {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    if (outOfBounds(row, col, this.gameBoard) == true) {
      throw new IllegalStateException("Out of bounds of the game board: " + row + ", " + col);
    }
    if (hasCardInPosition(row, col, this.gameBoard) == true) {
      throw new IllegalStateException("Given position already has a card.");
    }
    if (cardIdx >= this.handSize || cardIdx < 0) {
      throw new IllegalArgumentException("Card index out of bounds of the hand: " + cardIdx);
    }

    // Place desired card in deck to desired position.
    this.gameBoard[row][col] = this.hand.remove(cardIdx);

    // Draw from the deck into the hand.
    if (!this.deck.isEmpty()) {
      this.hand.add(cardIdx, this.deck.removeFirst());
    }

  }

  /**
   * To determine if the given position is out of bounds.
   * @return whether the given position is out of the given game board.
   */
  private static boolean outOfBounds(int row, int col, PlayingCard[][] gameBoard) {
    return gameBoard[row][col] == null;
  }

  /**
   * To determine if there is a card already places at this position.
   * @return whether there is a card in this position or not.
   */
  private static boolean hasCardInPosition(int row, int col, PlayingCard[][] gameBoard) {
    return gameBoard[row][col].equals(getEmptyCard());
  }



  /**
   * Discards the specified card from the hand, but only if the player can also draw a new card
   * and there are enough cards between the remaining deck and hand to fill the remaining empty
   * positions on the board.
   *
   * @param cardIdx index of the card in hand to discard (0-index based)
   * @throws IllegalStateException    if the game has not started or there are no cards left to draw
   * @throws IllegalArgumentException if cardIdx is out of bounds for the hand
   */
  @Override
  public void discardCard(int cardIdx) {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    if (this.hand.size() < 0) {
      throw new IllegalStateException("Hand is empty.");
    }
    if (cardIdx >= this.handSize || cardIdx < 0) {
      throw new IllegalArgumentException("Card index out of bounds of the hand: " + cardIdx);
    }

    ArrayList<PlayingCard> temp = new ArrayList<>();
    temp = dequeToArrayList(this.deck);
    temp.remove(cardIdx);
    this.deck = arrayListToDeque(temp);

  }

  /**
   * A helper method to convert the given deck from a Deque to an ArrayList
   */
  private ArrayList<PlayingCard> dequeToArrayList(Deque<PlayingCard> deck) {
    ArrayList<PlayingCard> convertedDeck = new ArrayList<>();
    for (PlayingCard card : deck) {
      convertedDeck.add(card);
    }
    return convertedDeck;
  }

  /**
   * A helper method to convert the given deck from an ArrayList to a Deque
   */
  private Deque<PlayingCard> arrayListToDeque(ArrayList<PlayingCard> deck) {
    Deque<PlayingCard> convertedDeck = new ArrayDeque<>();
    for (PlayingCard card : deck) {
      convertedDeck.add(card);
    }
    return convertedDeck;
  }

  // TODO: Implement this method and JavaDoc
  /**
   * Starts the game with the given deck and hand size. If shuffle is set to true,
   * then the deck is shuffled prior to dealing the hand.
   *
   * <p>Note that modifying the deck given here outside this method should have no effect
   * on the game itself.
   *
   * @param deck     list of cards to play the game with
   * @param shuffle  whether the deck should be shuffled
   * @param handSize maximum hand size for the game
   * @throws IllegalStateException    if the game has already been started
   * @throws IllegalArgumentException if the deck is null or contains a null object,
   *                                  if handSize is not positive (i.e. 0 or less),
   *                                  or if the deck does not contain enough cards to fill the board
   *                                  AND fill a starting hand
   */
  @Override
  public void startGame(List<PlayingCard> deck, boolean shuffle, int handSize) {

  }

  /**
   * Retrieve the number of cards that make up the width of the triangle.
   * that contains the polygon. (e.g. the number of columns in the widest row)
   *
   * @return the width of the game board.
   */
  @Override
  public int getWidth() {
    return sideLength;
  }

  /**
   * Retrieve the number of cards that make up the height of the triangle.
   * that contains the triangle. (e.g. the number of rows in the highest column)
   *
   * @return the height of the board.
   */
  @Override
  public int getHeight() {
    return sideLength;
  }

  /**
   * Creates a brand new deck of all 52 possible cards.
   *
   * @return a new deck of all possible cards that can be used to play the game
   */
  @Override
  public List<PlayingCard> getNewDeck() {
    List<PlayingCard> defaultDeck = new ArrayList<PlayingCard>();
    for (Ranks rank : Ranks.values()) {
      for (Suits suit : Suits.values()) {
        defaultDeck.add(new StandardPlayingCard(rank, suit));
      }
    }
    return defaultDeck;
  }

  /**
   * Returns the card in the indicated position on the board. If there is no card on the board
   * and the position is valid, the method will return null.
   *
   * @param row the row to access
   * @param col the column to access
   * @return the card in the valid position or null if the position has no card
   * @throws IllegalArgumentException if the row and column are not a valid location
   *                                  for a card in the polygonal board
   */
  @Override
  public PlayingCard getCardAt(int row, int col) {
    if (row < 0 || row >= this.getSizeLength() || col < 0 || col >= this.getSizeLength()) {
      throw new IllegalArgumentException("Row or column out of bounds: " + row + ", " + col);
    }
    return this.gameBoard[row][col];
  }

  /**
   * Returns a copy of the player's current hand. If their hand is empty, then an empty
   * list is returned.
   *
   * @return a copy of the player's current hand
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public List<PlayingCard> getHand() {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    return new ArrayList<>(this.hand);
  }

  // TODO: Implement this method and JavaDoc
  /**
   * Returns the current score of the game. The rules of scoring are determined
   * by the implementation.
   *
   * @return the current score of the game
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int getScore() {
    return 0;
  }

  /**
   * To extract the hands valid for scoring from the game board. That includes
   * all 3 sides, and any hands that has 5 or more cards.
   * @return a list of hands that are valid for scoring from the game board.
   */
  private List<List<PlayingCard>> extractHands() {
    List<List<PlayingCard>> hands = new ArrayList<>();

    // Adding all valid rows (including the last row automatically)
    hands.addAll(getValidRows());

    // Adding all valid columns (including the first column automatically)
    hands.addAll(getValidColumns());

    // Adding the main diagonal (always valid)
    hands.add(getMainDiagonal());

    return hands;
  }



  /**
   * Returns the number of cards left in the deck being used during the game.
   *
   * @return the number of cards left in the deck used in game
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int getRemainingDeckSize() {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    return this.deck.size();
  }

  // TODO: Implement this method and JavaDoc
  /**
   * Returns true if the game is over. The implementation must
   * describe what it means for the game to be over.
   *
   * @return true if the game is over, false otherwise
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public boolean isGameOver() {
    return false;
  }
}
