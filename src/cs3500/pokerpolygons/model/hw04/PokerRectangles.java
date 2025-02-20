package cs3500.pokerpolygons.model.hw04;

import java.util.Random;

import cs3500.pokerpolygons.model.hw02.EmptyCard;
import cs3500.pokerpolygons.model.hw02.PlayingCard;

/**
 * To represent a PokerSquares type game in the shape of a rectangle. The valid
 * scoring hands are every row 5 cards or larger, and every column 5 cards
 * or larger.
 */
public class PokerRectangles extends PokerBasicPolygons {

  // Rectangle-specific fields (distinct height and width)
  private final int width;
  private final int height;

  /**
   * Constructs a PokerRectangles game with the specified width and height.
   *
   * @param width  the number of columns of the board (must be at least 5)
   * @param height the number of rows of the board (must be at least 5)
   * @throws IllegalArgumentException if width or height is less than 5
   */
  public PokerRectangles(int width, int height) {
    if (width < 5 || height < 5) {
      throw new IllegalArgumentException("Both width and height must be at least 5. " +
              "Given: width = " + width + ", height = " + height);
    }
    this.width = width;
    this.height = height;
    this.isGameStarted = false;
    this.randomizer = new Random();
  }

  /**
   * Constructs a PokerRectangles game with the specified width, height, and Random object.
   *
   * @param width the number of columns of the board (must be at least 5)
   * @param height the number of rows of the board (must be at least 5)
   * @param randomizer the Random object to seed random operations (must not be null)
   * @throws IllegalArgumentException if width or height is less than 5, or randomizer is null
   */
  public PokerRectangles(int width, int height, Random randomizer) {
    if (width < 5 || height < 5) {
      throw new IllegalArgumentException("Both width and height must be at least 5. " +
              "Received: width=" + width + ", height=" + height);
    }
    if (randomizer == null) {
      throw new IllegalArgumentException("Randomizer cannot be null.");
    }
    this.width = width;
    this.height = height;
    this.randomizer = randomizer;
    this.isGameStarted = false;
  }

  /**
   * Initializes and returns the rectangular game board.
   * The board is a 2D array with dimensions with the given
   * height and width parameters.
   *
   * @return the initialized game board
   */
  @Override
  protected PlayingCard[][] getInitializedBoard() {
    PlayingCard[][] board = new PlayingCard[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
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

    return new PokerRectanglesScoring().calculateScore(this);
  }

  /**
   * Returns the total number of playable cells on the board.
   *
   * @return total board size
   */
  @Override
  protected int getTotalBoardSize() {
    return width * height;
  }

  /**
   * Returns the number of playable spots left on the board.
   * Counts cells that still contain an empty card.
   *
   * @return the count of empty cells
   */
  @Override
  protected int getPlayableSpots() {
    int count = 0;
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (gameBoard[row][col] != null && gameBoard[row][col].equals(EmptyCard.getEmptyCard())) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Places a card from the hand to the specified position on the rectangular board,
   * then draws a new card from the deck if available.
   *
   * @param cardIdx the index of the card in hand to place (0-indexed)
   * @param row     the row index to place the card (0-indexed)
   * @param col     the column index to place the card (0-indexed)
   * @throws IllegalStateException if the game has not started
   * @throws IllegalArgumentException if indices are out of bounds or the target cell is occupied
   */
  @Override
  public void placeCardInPosition(int cardIdx, int row, int col) {
    gameHasNotStartedException();

    if (cardIdx < 0 || cardIdx >= hand.size()) {
      throw new IllegalArgumentException("Card index in the hand is out of bounds: " + (cardIdx + 1));
    }
    if (row < 0 || col < 0 || row >= height || col >= width) {
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
   * Returns the card in the specified position on the rectangular board.
   * If the cell is empty, returns null.
   *
   * @param row the row index (0-indexed)
   * @param col the column index (0-indexed)
   * @return the card at (row, col) or null if unoccupied
   * @throws IllegalArgumentException if the row or column is invalid
   */
  @Override
  public PlayingCard getCardAt(int row, int col) {
    gameHasNotStartedException();

    if (row < 0 || row >= height || col < 0 || col >= width) {
      throw new IllegalArgumentException("Row or column out of bounds: " + row + ", " + col);
    }
    PlayingCard card = gameBoard[row][col];
    if (card != null && card.equals(EmptyCard.getEmptyCard())) {
      return null;
    }
    return card;
  }

  /**
   * Returns true if the game is over, meaning every cell on the rectangular board is occupied.
   *
   * @return true if the board is full; false otherwise
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public boolean isGameOver() {
    gameHasNotStartedException();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (gameBoard[row][col].equals(EmptyCard.getEmptyCard())) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Returns the width (number of columns) of the rectangular board.
   *
   * @return the board's width
   */
  @Override
  public int getWidth() {
    return width;
  }

  /**
   * Returns the height (number of rows) of the rectangular board.
   *
   * @return the board's height
   */
  @Override
  public int getHeight() {
    return height;
  }
}
