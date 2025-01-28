package cs3500.pokerpolygons.model.hw02;

import java.util.List;

/**
 * To represent a PokerSquares type game in the shape of an equilateral, right triangle.
 */
class PokerTriangles implements PokerPolygons<PlayingCard> {

  // TODO: Implement this method and JavaDoc
  /**
   * Places a card from the hand to a given position on the polygonal board and then
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

  }

  // TODO: Implement this method and JavaDoc
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

  // TODO: Implement this method and JavaDoc
  /**
   * Retrieve the number of cards that make up the width of the rectangle
   * that contains the polygon. (e.g. the number of columns in the widest row)
   *
   * @return the width of the board
   */
  @Override
  public int getWidth() {
    return 0;
  }

  // TODO: Implement this method and JavaDoc
  /**
   * Retrieve the number of cards that make up the height of the rectangle
   * that contains the polygon. (e.g. the number of rows in the highest column)
   *
   * @return the height of the board
   */
  @Override
  public int getHeight() {
    return 0;
  }

  // TODO: Implement this method and JavaDoc
  /**
   * Creates a brand new deck of all 52 possible cards.
   *
   * @return a new deck of all possible cards that can be used to play the game
   */
  @Override
  public List<PlayingCard> getNewDeck() {
    return List.of();
  }

  // TODO: Implement this method and JavaDoc
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
    return null;
  }

  // TODO: Implement this method and JavaDoc
  /**
   * Returns a copy of the player's current hand. If their hand is empty, then an empty
   * list is returned.
   *
   * @return a copy of the player's current hand
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public List<PlayingCard> getHand() {
    return List.of();
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

  // TODO: Implement this method and JavaDoc
  /**
   * Returns the number of cards left in the deck being used during the game.
   *
   * @return the number of cards left in the deck used in game
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int getRemainingDeckSize() {
    return 0;
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
