package cs3500.pokerpolygons.model.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple mock implementation of the PokerPolygons interface.
 * It records calls in a log (for test verification) and allows test code
 * to preset return values for methods.
 *
 * @param <C> the type of card (extending Card)
 */
public class PokerPolygonsSimple<C extends Card> implements PokerPolygons<C> {

  // A log to record method calls.
  private final StringBuilder log;

  // Dummy state variables that tests can preset.
  private boolean gameStarted;
  private int width;
  private int height;
  private List<C> hand;
  private List<C> newDeck;
  private int score;
  private int remainingDeckSize;
  private boolean gameOver;

  /**
   * Constructs a mock PokerPolygonsSimple with a given log.
   *
   * @param log the StringBuilder used to record method calls.
   * @throws IllegalArgumentException if log is null.
   */
  // TODO: Test the constructor throws the exception correctly
  public PokerPolygonsSimple(StringBuilder log) {
    if (log == null) {
      throw new IllegalArgumentException("Log cannot be null.");
    }
    this.log = log;

    // Setting default dummy values.
    this.gameStarted = false;
    this.width = 5;   // default width for testing
    this.height = 5;  // default height for testing
    this.hand = new ArrayList<>();
    this.newDeck = new ArrayList<>();
    this.score = 0;
    this.remainingDeckSize = 0;
    this.gameOver = false;
  }

  /**
   * A simple representation of placing a card in a position.
   * @param cardIdx index of the card in hand to place (0-index based)
   * @param row row to place the card in (0-index based)
   * @param col column to place the card in (0-index based)
   */
  @Override
  public void placeCardInPosition(int cardIdx, int row, int col) {
    log.append(String.format("placeCardInPosition(%d, %d, %d) called. ", cardIdx, row, col));
  }

  /**
   * A simple representation of discarding a card.
   * @param cardIdx index of the card in hand to discard (0-index based)
   */
  @Override
  public void discardCard(int cardIdx) {
    log.append(String.format("discardCard(%d) called", cardIdx));
  }

  /**
   * A simple representation of starting a game.
   * @param deck list of cards to play the game with
   * @param shuffle whether the deck should be shuffled
   * @param handSize maximum hand size for the game
   */
  @Override
  public void startGame(List<C> deck, boolean shuffle, int handSize) {
    log.append(String.format("startGame(deckSize = %d, shuffle = %b, handSize = %d) called. ",
            deck.size(), shuffle, handSize));
    gameStarted = true;
    // For testing, I simulate taking the first 'handSize' cards into hand.
    this.hand = new ArrayList<>();
    for (int i = 0; i < handSize && i < deck.size(); i++) {
      this.hand.add(deck.get(i));
    }
    // Store the remaining deck size.
    this.remainingDeckSize = deck.size() - handSize;
  }

  /**
   * A simple representation of getting the width.
   * @return the width.
   */
  @Override
  public int getWidth() {
    log.append("getWidth() called. ");
    return width;
  }

  /**
   * A simple representation of getting the height.
   * @return the height.
   */
  @Override
  public int getHeight() {
    log.append("getHeight() called. ");
    return height;
  }

  /**
   * A simple representation of getting a new deck.
   * @return a copy of the deck.
   */
  @Override
  public List<C> getNewDeck() {
    log.append("getNewDeck() called. ");
    // Return a copy of the newDeck list.
    return new ArrayList<>(newDeck);
  }

  /**
   * A simple representation of getting a card.
   * @param row the row to access
   * @param col the column to access
   * @return
   */
  @Override
  public C getCardAt(int row, int col) {
    log.append(String.format("getCardAt(%d, %d) called: ", row, col));
    // For simplicity in the mock, always return null.
    return null;
  }

  /**
   * A simple representation of getting the hand.
   * @return a copy of the hand.
   */
  @Override
  public List<C> getHand() {
    log.append("getHand() called. ");
    return new ArrayList<>(hand);
  }

  /**
   * A simple representation of getting the score.
   * @return the blank variable score.
   */
  @Override
  public int getScore() {
    log.append("getScore() called. ");
    return score;
  }

  /**
   * A simple representation of getting the remaining deck size.
   * @return the remaining deck size.
   */
  @Override
  public int getRemainingDeckSize() {
    log.append("getRemainingDeckSize() called. ");
    return remainingDeckSize;
  }

  /**
   * A simple representation of checking if the game is over.
   * @return the variable if the game is over.
   */
  @Override
  public boolean isGameOver() {
    log.append("isGameOver() called. ");
    return gameOver;
  }

  // Additional setters for tests

  /**
   * Sets the width value returned by getWidth().
   *
   * @param width the width to set.
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Sets the height value returned by getHeight().
   *
   * @param height the height to set.
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * Sets the score value returned by getScore().
   *
   * @param score the score to set.
   */
  public void setScore(int score) {
    this.score = score;
  }

  /**
   * Sets the remaining deck size value returned by getRemainingDeckSize().
   *
   * @param remainingDeckSize the remaining deck size to set.
   */
  public void setRemainingDeckSize(int remainingDeckSize) {
    this.remainingDeckSize = remainingDeckSize;
  }

  /**
   * Sets the game over flag returned by isGameOver().
   *
   * @param gameOver true if the game should be considered over.
   */
  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }

  /**
   * Sets the new deck to be returned by getNewDeck().
   *
   * @param newDeck the list of cards to set.
   */
  public void setNewDeck(List<C> newDeck) {
    this.newDeck = new ArrayList<>(newDeck);
  }

  /**
   * Returns the log of all method calls.
   *
   * @return the log as a String.
   */
  public String getLog() {
    return log.toString();
  }
}