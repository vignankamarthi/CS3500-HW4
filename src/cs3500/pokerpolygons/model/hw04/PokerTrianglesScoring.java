package cs3500.pokerpolygons.model.hw04;



import java.util.ArrayList;
import java.util.List;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;

/**
 * Scoring logic for a PokerTriangles game.
 * Valid hands include valid rows, valid columns, and the main diagonal.
 */
public class PokerTrianglesScoring extends PokerBasicScoring {

  /**
   * Calculates the total score for a PokerTriangles game.
   * It extracts valid rows, valid columns, and the main diagonal, and then evaluates each hand.
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
   * For triangles, valid hands include valid rows, valid columns, and the main diagonal.
   *
   * @param model the PokerPolygons game model
   * @return a list of valid hands extracted from the model
   */
  protected List<List<PlayingCard>> extractHands(PokerPolygons<PlayingCard> model) {
    List<List<PlayingCard>> hands = new ArrayList<>();
    hands.addAll(getValidRows(model));
    hands.addAll(getValidColumns(model));
    hands.add(getMainDiagonal(model));
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
          // For boards (e.g., triangular) where not all columns are valid, break out.
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
          // Skip invalid positions.
        }
      }
      if (columnHand.size() >= 5) {
        validColumns.add(columnHand);
      }
    }
    return validColumns;
  }

  /**
   * Extracts the main diagonal from the model.
   * The main diagonal consists of cards at positions (i, i) for each row of
   * side length i.
   *
   * @param model the PokerPolygons game model
   * @return a list representing the main diagonal
   */
  private List<PlayingCard> getMainDiagonal(PokerPolygons<PlayingCard> model) {
    List<PlayingCard> mainDiagonal = new ArrayList<>();
    int minDim = Math.min(model.getWidth(), model.getHeight());
    for (int i = 0; i < minDim; i++) {
      try {
        PlayingCard card = model.getCardAt(i, i);
        if (card != null) {
          mainDiagonal.add(card);
        }
      } catch (IllegalArgumentException e) {
        // Skip invalid positions.
      }
    }
    return mainDiagonal;
  }
}