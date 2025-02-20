package cs3500.pokerpolygons.model.hw04;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Scoring logic for a LooserPokerTriangles game.
 * Valid hands include valid rows, valid columns, and the main diagonal, with relaxed rules for
 * flushes, straights, and straight flushes.
 */
public class LoosePokerTrianglesScoring extends PokerTrianglesScoring {

  /**
   * Evaluates the score of a 5-card hand based on Looser PokerTriangles rules.
   * The evaluation considers hand types such as straight flush, flush, four of a kind,
   * full house, straight, three of a kind, two pair, and a single pair.
   * Additional relaxed definitions include:
   * - Flush: 5 cards of the same color (red for diamonds & hearts, black for clubs & spades).
   * - Straight: 5 cards forming a skipped run (ranks can increase by 1 or 2 now).
   * - Straight Flush: 5 cards of the same color forming a skipped run.
   *
   * @param hand the hand of 5 cards to evaluate
   * @return the score of the hand according to Looser PokerTriangles rules
   */
  @Override
  protected int evaluateHand(List<PlayingCard> hand) {
    if (hand.size() < 5) {
      return 0;
    }
    List<PlayingCard> sortedHand = new ArrayList<>(hand);
    sortHandByRank(sortedHand);
    int bestScore = 0;

    // Group cards by color, not rank
    Map<String, List<PlayingCard>> colorGroups = groupCardsByColor(sortedHand);

    // Check for flushes and straight flushes (by color)
    for (List<PlayingCard> colorHand : colorGroups.values()) {
      if (colorHand.size() >= 5) {
        sortHandByRank(colorHand);
        for (int i = 0; i <= colorHand.size() - 5; i++) {
          List<PlayingCard> flushSubset = colorHand.subList(i, i + 5);
          if (isSkippedStraight(flushSubset)) {
            bestScore = Math.max(bestScore, 75); // Straight flush (same color, skipped run)
          } else if (isSameColor(flushSubset)) {
            bestScore = Math.max(bestScore, 20); // Regular flush (same color)
          }
        }
      }
    }

    // Check for regular straight (across all colors)
    if (isSkippedStraight(sortedHand)) {
      bestScore = Math.max(bestScore, 15); // Regular straight (skipped run, any color)
    }

    // Keep other hand types unchanged (four of a kind, full house, etc.)
    bestScore = Math.max(bestScore, isFourOfAKind(sortedHand) ? 50 : 0);
    bestScore = Math.max(bestScore, isFullHouse(sortedHand) ? 25 : 0);
    bestScore = Math.max(bestScore, isThreeOfAKind(sortedHand) ? 10 : 0);
    bestScore = Math.max(bestScore, isTwoPair(sortedHand) ? 5 : 0);
    bestScore = Math.max(bestScore, isPair(sortedHand) ? 2 : 0);

    return bestScore;
  }

  /**
   * Groups cards by their color (red for diamonds + hearts, black for clubs + spades).
   *
   * @param cards the list of cards to group
   * @return a map where keys are colors ("Red" or "Black") and values are lists of cards
   */
  private Map<String, List<PlayingCard>> groupCardsByColor(List<PlayingCard> cards) {
    Map<String, List<PlayingCard>> colorGroups = new HashMap<>();
    for (PlayingCard card : cards) {
      String color = card.getSuit().getColor();
      colorGroups.putIfAbsent(color, new ArrayList<>());
      colorGroups.get(color).add(card);
    }
    return colorGroups;
  }


  /**
   * Determines whether the given hand has cards of the same color.
   * Color is defined as red (diamonds + hearts) or black (clubs + spades).
   *
   * @param hand the hand of cards to evaluate
   * @return true if all cards have the same color and false otherwise
   */
  private boolean isSameColor(List<PlayingCard> hand) {
    if (hand.size() < 5) {
      return false;
    }
    String color = hand.get(0).getSuit().getColor();
    for (PlayingCard card : hand) {
      if (!color.equals(card.getSuit().getColor())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether the given hand forms a skipped straight (ranks increase by 1 or 2).
   *
   * @param hand the hand of cards to evaluate
   * @return true if the hand forms a skipped straight and false otherwise
   */
  private boolean isSkippedStraight(List<PlayingCard> hand) {
    if (hand.size() < 5) {
      return false;
    }
    List<Integer> values = new ArrayList<>();
    for (PlayingCard card : hand) {
      int rankValue = card.getRank().getValue();
      values.add(rankValue);
      if (rankValue == 1) { // Ace can be high as well
        values.add(card.getRank().getFourteenValue());
      }
    }
    Set<Integer> uniqueValues = new TreeSet<>(values);
    List<Integer> sortedValues = new ArrayList<>(uniqueValues);
    if (sortedValues.size() < 5) {
      return false;
    }
    for (int i = 0; i <= sortedValues.size() - 5; i++) {
      boolean skippedRun = true;
      for (int j = 0; j < 4; j++) {
        int difference = sortedValues.get(i + j + 1) - sortedValues.get(i + j);
        if (difference != 1 && difference != 2) {
          skippedRun = false;
          break;
        }
      }
      if (skippedRun) {
        return true;
      }
    }
    return false;
  }

}