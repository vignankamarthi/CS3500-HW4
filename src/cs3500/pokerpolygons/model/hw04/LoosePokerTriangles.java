package cs3500.pokerpolygons.model.hw04;

import java.util.Random;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;

/**
 * To represent a Looser PokerTriangles type game in the shape of an equilateral, right triangle.
 * The valid scoring hands are the outer diagonal, every row 5 cards or larger, and every column 5
 * cards or larger, with relaxed rules for flushes, straights, and straight flushes:
 * - Flush: 5 cards of the same color (red for diamonds & hearts, black for clubs & spades).
 * - Straight: 5 cards forming a skipped run (ranks can increase by 1 or 2).
 * - Straight Flush: 5 cards of the same color forming a skipped run.
 */
public class LoosePokerTriangles extends PokerTriangles {

  /**
   * Constructs a Looser PokerTriangles game with the specified side length.
   *
   * @param sideLength the length of the sides of the right triangle (must be at least 5)
   * @throws IllegalArgumentException if sideLength is less than 5
   */
  public LoosePokerTriangles(int sideLength) {
    super(sideLength);
  }

  /**
   * Constructs a Looser PokerTriangles game with the specified side length and Random object.
   *
   * @param sideLength the length of the sides of the right triangle (must be at least 5)
   * @param randomizer the Random object to seed random operations (must not be null)
   * @throws IllegalArgumentException if sideLength is less than 5 or randomizer is null
   */
  public LoosePokerTriangles(int sideLength, Random randomizer) {
    super(sideLength, randomizer);
  }

  /**
   * Returns the current score of the game using Looser PokerTriangles rules.
   *
   * @return the current score of the game
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int getScore() {
    gameHasNotStartedException();

    return new LoosePokerTrianglesScoring().calculateScore(this);
  }
}