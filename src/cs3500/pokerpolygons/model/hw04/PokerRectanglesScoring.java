package cs3500.pokerpolygons.model.hw04;

import java.util.ArrayList;
import java.util.List;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;

/**
 * Scoring logic for a PokerRectangles game.
 * Valid hands include valid rows and valid columns.
 */
public class PokerRectanglesScoring extends PokerBasicScoring {

  /**
   * Calculates the total score for a PokerRectangles game.
   * It extracts valid rows and valid columns, then evaluates each hand.
   *
   * @param model the PokerPolygons game model
   * @return the total score for the game
   */
  @Override
  public int calculateScore(PokerPolygons<PlayingCard> model) {
    int totalScore = 0;
    List<List<PlayingCard>> hands = extractHands(model);
    for (List<PlayingCard> hand : hands) {
      if (hand.size() == 5) {
        totalScore += evaluateHand(hand);
      } else if (hand.size() > 5) {
        totalScore += findBest5CardSubset(hand);
      }
    }
    return totalScore;
  }

  /**
   * Extracts valid hands from the given model.
   * For rectangles, valid hands include valid rows and valid columns.
   * @param model the PokerPolygons game model
   * @return a list of valid hands extracted from the model
   */
  protected List<List<PlayingCard>> extractHands(PokerPolygons<PlayingCard> model) {
    List<List<PlayingCard>> hands = new ArrayList<>();
    hands.addAll(getValidRows(model));
    hands.addAll(getValidColumns(model));
    return hands;
  }

  /**
   * Extracts valid rows from the model.
   * A row is valid if it contains at least 5 non-null cards.
   *
   * @param model the PokerPolygons game model
   * @return a list of valid rows
   */
  private List<List<PlayingCard>> getValidRows(PokerPolygons<PlayingCard> model) {
    List<List<PlayingCard>> validRows = new ArrayList<>();
    int height = model.getHeight();
    int width = model.getWidth();
    for (int row = 0; row < height; row++) {
      List<PlayingCard> currentRow = new ArrayList<>();
      for (int col = 0; col < width; col++) {
        try {
          PlayingCard card = model.getCardAt(row, col);
          if (card != null) {
            currentRow.add(card);
          }
        } catch (IllegalArgumentException e) {
          break;
        }
      }
      if (currentRow.size() >= 5) {
        validRows.add(currentRow);
      }
    }
    return validRows;
  }

  /**
   * Extracts valid columns from the model.
   * A column is valid if it contains at least 5 non-null cards.
   *
   * @param model the PokerPolygons game model
   * @return a list of valid columns
   */
  private List<List<PlayingCard>> getValidColumns(PokerPolygons<PlayingCard> model) {
    List<List<PlayingCard>> validColumns = new ArrayList<>();
    int height = model.getHeight();
    int width = model.getWidth();
    for (int col = 0; col < width; col++) {
      List<PlayingCard> columnHand = new ArrayList<>();
      for (int row = 0; row < height; row++) {
        try {
          PlayingCard card = model.getCardAt(row, col);
          if (card != null) {
            columnHand.add(card);
          }
        } catch (IllegalArgumentException e) {
          // Ignore invalid positions.
        }
      }
      if (columnHand.size() >= 5) {
        validColumns.add(columnHand);
      }
    }
    return validColumns;
  }
}