package cs3500.pokerpolygons.model.hw04;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.Ranks;
import cs3500.pokerpolygons.model.hw02.Suits;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * An abstract class to handle the scoring of {@link PokerBasicPolygons} game, where we
 * get valid hands, score according to poker hands in a traditional Texas Hold 'Em game minus
 * the idea of a Royal flush being different from a Straight flush, both hands are considered a
 * "straight flush" and there is no distinguishing between the two.
 */
public abstract class PokerBasicScoring {

  /**
   * Calculates the total score of the given PokerPolygons game.
   * Subclasses must implement this method to extract valid hands appropriately.
   *
   * @param model the PokerPolygons game model from which to compute the score
   * @return the total score for the game
   */
  public abstract int calculateScore(PokerPolygons<PlayingCard> model);



  /**
   * Extracts valid hands from the given model.
   *
   * @param model the PokerPolygons game model
   * @return a list of valid hands extracted from the model
   */
  protected abstract List<List<PlayingCard>> extractHands(PokerPolygons<PlayingCard> model);

  /**
   * Finds the best score among all 5-card subsets of the given hand.
   *
   * @param hand the hand of cards to evaluate
   * @return the best score achievable from any 5-card subset of the hand
   */
  protected int findBest5CardSubset(List<PlayingCard> hand) {
    if (hand.size() < 5) {
      return 0;
    }
    int bestScore = 0;
    List<List<PlayingCard>> subsets = new ArrayList<>();
    generateCombinations(hand, 0, new ArrayList<>(), subsets);
    for (List<PlayingCard> subset : subsets) {
      bestScore = Math.max(bestScore, evaluateHand(subset));
    }
    return bestScore;
  }

  /**
   * Recursively generates all unique 5-card combinations from the given hand.
   *
   * @param hand the hand of cards from which to generate combinations
   * @param start the starting index for generating combinations
   * @param current the current combination being built
   * @param result the list that accumulates all valid 5-card combinations
   */
  protected void generateCombinations(List<PlayingCard> hand, int start,
                                      List<PlayingCard> current, List<List<PlayingCard>> result) {
    if (current.size() == 5) {
      result.add(new ArrayList<>(current));
      return;
    }
    for (int i = start; i < hand.size(); i++) {
      current.add(hand.get(i));
      generateCombinations(hand, i + 1, current, result);
      current.remove(current.size() - 1);
    }
  }

  /**
   * Evaluates the score of a 5-card hand based on poker rules.
   * The evaluation considers hand types such as straight flush, flush, four of a kind,
   * full house, straight, three of a kind, two pair, and a single pair.
   *
   * @param hand the hand of 5 cards to evaluate
   * @return the score of the hand according to poker rules
   */
  protected int evaluateHand(List<PlayingCard> hand) {
    if (hand.size() < 5) {
      return 0;
    }
    List<PlayingCard> sortedHand = new ArrayList<>(hand);
    sortHandByRank(sortedHand);
    int bestScore = 0;
    // Group cards by suit.
    Map<Suits, List<PlayingCard>> suitGroups = new HashMap<>();
    for (PlayingCard card : sortedHand) {
      suitGroups.putIfAbsent(card.getSuit(), new ArrayList<>());
      suitGroups.get(card.getSuit()).add(card);
    }
    // Check for flushes and straight flushes.
    for (List<PlayingCard> suitHand : suitGroups.values()) {
      if (suitHand.size() >= 5) {
        sortHandByRank(suitHand);
        for (int i = 0; i <= suitHand.size() - 5; i++) {
          List<PlayingCard> flushSubset = suitHand.subList(i, i + 5);
          if (isStraight(flushSubset)) {
            bestScore = Math.max(bestScore, 75); // Straight flush
          } else {
            bestScore = Math.max(bestScore, 20); // Regular flush
          }
        }
      }
    }
    bestScore = Math.max(bestScore, isFourOfAKind(sortedHand) ? 50 : 0);
    bestScore = Math.max(bestScore, isFullHouse(sortedHand) ? 25 : 0);
    for (int i = 0; i <= sortedHand.size() - 5; i++) {
      if (isStraight(sortedHand.subList(i, i + 5))) {
        bestScore = Math.max(bestScore, 15); // Regular straight
      }
    }
    bestScore = Math.max(bestScore, isThreeOfAKind(sortedHand) ? 10 : 0);
    bestScore = Math.max(bestScore, isTwoPair(sortedHand) ? 5 : 0);
    bestScore = Math.max(bestScore, isPair(sortedHand) ? 2 : 0);
    return bestScore;
  }

  /**
   * Sorts the given hand in ascending order based on the numerical value of each card's rank.
   *
   * @param hand the hand of cards to sort
   */
  protected void sortHandByRank(List<PlayingCard> hand) {
    hand.sort((card1, card2) ->
            Integer.compare(card1.getRank().getValue(), card2.getRank().getValue()));
  }

  /**
   * Determines whether the given hand contains Four of a Kind.
   *
   * @param hand the hand of cards to evaluate
   * @return true if the hand contains four cards of the same rank; false otherwise
   */
  protected boolean isFourOfAKind(List<PlayingCard> hand) {
    Map<Ranks, Integer> rankCounts = getRankCounts(hand);
    return rankCounts.containsValue(4);
  }

  /**
   * Determines whether the given hand is a Full House (three of a kind and a pair).
   *
   * @param hand the hand of cards to evaluate
   * @return true if the hand is a full house; false otherwise
   */
  protected boolean isFullHouse(List<PlayingCard> hand) {
    Map<Ranks, Integer> rankCounts = getRankCounts(hand);
    return rankCounts.containsValue(3) && rankCounts.containsValue(2);
  }

  /**
   * Determines whether the given hand is a straight (cards in consecutive order).
   * Ace is treated as both low (1) and high (14).
   *
   * @param hand the hand of cards to evaluate
   * @return true if the hand forms a straight; false otherwise
   */
  protected boolean isStraight(List<PlayingCard> hand) {
    List<Integer> values = new ArrayList<>();
    for (PlayingCard card : hand) {
      int rankValue = card.getRank().getValue();
      values.add(rankValue);
      if (rankValue == 1) { // Ace can be high
        values.add(card.getRank().getFourteenValue());
      }
    }
    Set<Integer> uniqueValues = new TreeSet<>(values);
    List<Integer> sortedValues = new ArrayList<>(uniqueValues);
    if (sortedValues.size() < 5) {
      return false;
    }
    for (int i = 0; i <= sortedValues.size() - 5; i++) {
      boolean sequential = true;
      for (int j = 0; j < 4; j++) {
        if (sortedValues.get(i + j) + 1 != sortedValues.get(i + j + 1)) {
          sequential = false;
          break;
        }
      }
      if (sequential) {
        return true;
      }
    }
    return false;
  }

  /**
   * Determines whether the given hand contains Three of a Kind.
   *
   * @param hand the hand of cards to evaluate
   * @return true if the hand contains three cards of the same rank; false otherwise
   */
  protected boolean isThreeOfAKind(List<PlayingCard> hand) {
    Map<Ranks, Integer> rankCounts = getRankCounts(hand);
    return rankCounts.containsValue(3);
  }

  /**
   * Determines whether the given hand contains Two Pair.
   *
   * @param hand the hand of cards to evaluate
   * @return true if the hand contains two distinct pairs; false otherwise
   */
  protected boolean isTwoPair(List<PlayingCard> hand) {
    Map<Ranks, Integer> rankCounts = getRankCounts(hand);
    int pairCount = 0;
    for (int count : rankCounts.values()) {
      if (count == 2) {
        pairCount++;
      }
    }
    return pairCount == 2;
  }

  /**
   * Determines whether the given hand contains a single Pair.
   *
   * @param hand the hand of cards to evaluate
   * @return true if the hand contains a pair; false otherwise
   */
  protected boolean isPair(List<PlayingCard> hand) {
    Map<Ranks, Integer> rankCounts = getRankCounts(hand);
    return rankCounts.containsValue(2);
  }

  /**
   * Counts the occurrences of each rank in the given hand.
   *
   * @param hand the hand of cards to evaluate
   * @return a map where the keys are ranks and the values are the number of occurrences of that
   *         rank
   */
  protected Map<Ranks, Integer> getRankCounts(List<PlayingCard> hand) {
    Map<Ranks, Integer> rankCounts = new HashMap<>();
    for (PlayingCard card : hand) {
      rankCounts.put(card.getRank(), rankCounts.getOrDefault(card.getRank(), 0) + 1);
    }
    return rankCounts;
  }
}