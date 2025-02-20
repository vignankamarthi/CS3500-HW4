package cs3500.pokerpolygons.model.hw02;

import java.util.Random;
import cs3500.pokerpolygons.model.hw04.PokerBasicPolygons;
import cs3500.pokerpolygons.model.hw04.PokerBasicScoring;
import cs3500.pokerpolygons.model.hw04.PokerTrianglesScoring;

/**
 * To represent a PokerSquares type game in the shape of an equilateral, right triangle. The valid
 * scoring hands are the outer diagonal, every row 5 cards or larger, and every column 5 cards
 * or larger.
 */
public class PokerTriangles extends PokerBasicPolygons {

  // The side length of the triangle.
  private final int sideLength;

  /**
   * Constructs a PokerTriangles game with the specified side length.
   *
   * @param sideLength the length of the sides of the right triangle (must be at least 5)
   * @throws IllegalArgumentException if sideLength is less than 5
   */
  public PokerTriangles(int sideLength) {
    if (sideLength < 5) {
      throw new IllegalArgumentException("Side length must be at least 5: " + sideLength);
    }
    this.sideLength = sideLength;
    this.randomizer = new Random();
    this.isGameStarted = false;
  }

  /**
   * Constructs a PokerTriangles game with the specified side length and Random object.
   *
   * @param sideLength the length of the sides of the right triangle (must be at least 5)
   * @param randomizer the Random object to seed random operations (must not be null)
   * @throws IllegalArgumentException if sideLength is less than 5 or randomizer is null
   */
  public PokerTriangles(int sideLength, Random randomizer) {
    if (sideLength < 5) {
      throw new IllegalArgumentException("Side length must be at least 5: " + sideLength);
    }
    if (randomizer == null) {
      throw new IllegalArgumentException("Randomizer cannot be null.");
    }
    this.sideLength = sideLength;
    this.randomizer = randomizer;
    this.isGameStarted = false;
  }

  /**
   * Initializes and returns the triangular game board.
   * The board is in the shape of a jagged array where row i has (i+1) cells,
   * each initialized with an empty card.
   *
   * @return the initialized triangular game board
   */
  @Override
  protected PlayingCard[][] getInitializedBoard() {
    PlayingCard[][] board = new PlayingCard[sideLength][];
    for (int row = 0; row < sideLength; row++) {
      board[row] = new PlayingCard[row + 1];
      for (int col = 0; col <= row; col++) {
        board[row][col] = EmptyCard.getEmptyCard();
      }
    }
    return board;
  }

  /**
   * Returns the current score of the game.
   *
   * @return the current score of the game
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int getScore()  {
    gameHasNotStartedException();

    return new PokerTrianglesScoring().calculateScore(this);
  }

  /**
   * Returns the total number of playable cells in the triangle. For a equilateral right triangle,
   * the calculation = (sideLength * (sideLength + 1)) / 2
   *
   * @return total board size
   */
  @Override
  protected int getTotalBoardSize() {
    return (sideLength * (sideLength + 1)) / 2;
  }

  /**
   * Returns the number of playable spots left on the board.
   * Counts cells that still contain an empty card.
   *
   * @return the number of empty cells in the triangle
   */
  @Override
  protected int getPlayableSpots() {
    int count = 0;
    for (int row = 0; row < sideLength; row++) {
      for (int col = 0; col <= row; col++) {
        if (gameBoard[row][col] != null && gameBoard[row][col].equals(EmptyCard.getEmptyCard())) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Places a card from the hand to the specified position on the triangular board,
   * then draws a new card from the deck if available.
   *
   * @param cardIdx the index of the card in hand to place (0-indexed)
   * @param row the row to place the card in (0-indexed; must be less than sideLength)
   * @param col the column to place the card in (0-indexed; must be ≤ row)
   * @throws IllegalStateException if the game has not started
   * @throws IllegalArgumentException if indices are invalid or the target cell is already occupied
   */
  @Override
  public void placeCardInPosition(int cardIdx, int row, int col) {
    gameHasNotStartedException();

    if (cardIdx < 0 || cardIdx >= hand.size()) {
      throw new IllegalArgumentException("Card index in the hand is out of bounds: "
              + (cardIdx + 1));
    }
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Row and column indices must be non-negative.");
    }
    if (row >= sideLength || col > row) {
      throw new IllegalArgumentException("Row or column out of bounds: " + row + ", " + col);
    }
    if (!gameBoard[row][col].equals(EmptyCard.getEmptyCard())) {
      throw new IllegalArgumentException("Given position already has a card.");
    }
    gameBoard[row][col] = hand.remove(cardIdx);
    if (!deck.isEmpty()) {
      hand.add(deck.removeFirst());
    }
  }

  /**
   * Returns the card in the specified position on the triangular board.
   * If the cell is empty, returns null.
   *
   * @param row the row to access (0-indexed)
   * @param col the column to access (0-indexed)
   * @return the card at (row, col) or null if unoccupied
   * @throws IllegalArgumentException if the indices do not represent a valid cell in the triangle
   */
  @Override
  public PlayingCard getCardAt(int row, int col) {
    gameHasNotStartedException();

    if (row < 0 || row >= sideLength || col < 0 || col > row) {
      throw new IllegalArgumentException("Row or column out of bounds: " + row + ", " + col);
    }
    PlayingCard card = gameBoard[row][col];
    if (card != null && card.equals(EmptyCard.getEmptyCard())) {
      return null;
    }
    return card;
  }

  /**
   * Returns true if the game is over. THe game is over if every cell on the
   * triangular board is occupied.
   *
   * @return true if the board is full; false otherwise
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public boolean isGameOver() {
    gameHasNotStartedException();

    for (int row = 0; row < sideLength; row++) {
      for (int col = 0; col <= row; col++) {
        if (gameBoard[row][col].equals(EmptyCard.getEmptyCard())) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Returns the width of the triangular board.
   * For a triangle, the width is defined as the side length.
   *
   * @return the side length
   */
  @Override
  public int getWidth() {
    return sideLength;
  }

  /**
   * Returns the height of the triangular board.
   * For a triangle, the height is defined as the side length.
   *
   * @return the side length
   */
  @Override
  public int getHeight() {
    return sideLength;
  }
}
