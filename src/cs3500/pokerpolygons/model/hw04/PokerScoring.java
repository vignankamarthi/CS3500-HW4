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

//TODO: Make sure to not test this yet, you will need to create a different type of scoring then abstract, so test then
//TODO: Make sure the scope is correct.
//TODO: Thoroughly test this class.
/**
 * A class to handle the scoring of a standard PokerPolygons game, where we
 * get valid hands, score according to poker hands in a traditional Texas Hold 'Em game minus
 * the idea of a Royal flush being different from a Straight flush, both hands are considered a
 * "straight flush" and there is no distinguishing between the two.
 */
public class PokerScoring {

  /**
   * Calculates the total score of the given PokerPolygons game.
   * It extracts all valid hands (rows, columns, and main diagonal) and then, for each hand,
   * either evaluates its score directly (if exactly 5 cards) or finds the best score among
   * all its 5-card subsets (if more than 5 cards).
   *
   * @param model the PokerPolygons game model from which to compute the score
   * @return the total score for the game
   */
  public static int calculateScore(PokerPolygons<PlayingCard> model) {
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
   * Extracts all valid hands from the given game model.
   * Valid hands include valid rows, valid columns, and the main diagonal.
   *
   * @param model the PokerPolygons game model
   * @return a list of valid hands extracted from the game
   */
  private static List<List<PlayingCard>> extractHands(PokerPolygons<PlayingCard> model) {
    List<List<PlayingCard>> hands = new ArrayList<>();
    hands.addAll(getValidRows(model));
    hands.addAll(getValidColumns(model));
    hands.add(getMainDiagonal(model));
    return hands;
  }

  /**
   * Extracts the valid rows from the given game model.
   * A row is considered valid if it contains at least 5 non-null cards.
   * For each row, the method iterates over column indices from 0 to model.getWidth()-1;
   * if an invalid index is encountered, it stops processing that row.
   *
   * @param model the PokerPolygons game model
   * @return a list of valid rows, each represented as a list of cards
   */
  private static List<List<PlayingCard>> getValidRows(PokerPolygons<PlayingCard> model) {
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
          // For boards (e.g. triangular) where not all columns are valid, break out.
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
   * Extracts the valid columns from the given game model.
   * A column is considered valid if it contains at least 5 non-null cards.
   * For each column index, the method iterates over all row indices, ignoring invalid positions.
   *
   * @param model the PokerPolygons game model
   * @return a list of valid columns, each represented as a list of cards
   */
  private static List<List<PlayingCard>> getValidColumns(PokerPolygons<PlayingCard> model) {
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
          // Skip invalid indices.
        }
      }
      if (columnHand.size() >= 5) {
        validColumns.add(columnHand);
      }
    }
    return validColumns;
  }

  /**
   * Extracts the main diagonal from the given game model.
   * The main diagonal consists of cards at positions (i, i) for all valid indices.
   *
   * @param model the PokerPolygons game model
   * @return a list representing the main diagonal of the board
   */
  private static List<PlayingCard> getMainDiagonal(PokerPolygons<PlayingCard> model) {
    List<PlayingCard> mainDiagonal = new ArrayList<>();
    int minDimension = Math.min(model.getWidth(), model.getHeight());
    for (int i = 0; i < minDimension; i++) {
      try {
        PlayingCard card = model.getCardAt(i, i);
        if (card != null) {
          mainDiagonal.add(card);
        }
      } catch (IllegalArgumentException e) {
        // Skip if the index is not valid.
      }
    }
    return mainDiagonal;
  }

  /**
   * Finds the best score among all 5-card subsets of the given hand.
   * This method generates all unique combinations of 5 cards from the hand and
   * returns the maximum score.
   *
   * @param hand the hand of cards to evaluate
   * @return the best score achievable from any 5-card subset of the hand
   */
  private static int findBest5CardSubset(List<PlayingCard> hand) {
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
   * @param hand    the hand of cards from which to generate combinations
   * @param start   the starting index for generating combinations
   * @param current the current combination being built
   * @param result  the list that accumulates all valid 5-card combinations
   */
  private static void generateCombinations(List<PlayingCard> hand,
                                           int start,
                                           List<PlayingCard> current,
                                           List<List<PlayingCard>> result) {
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
   * The evaluation considers various hand types such as straight flush, flush,
   * four of a kind, full house, straight, three of a kind, two pair, and a single pair.
   *
   * @param hand the hand of 5 cards to evaluate
   * @return the score of the hand according to poker rules
   */
  private static int evaluateHand(List<PlayingCard> hand) {
    if (hand.size() < 5) {
      return 0;
    }
    // Create a sorted copy of the hand.
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
  private static void sortHandByRank(List<PlayingCard> hand) {
    hand.sort((card1, card2) ->
            Integer.compare(card1.getRank().getValue(), card2.getRank().getValue()));
  }

  /**
   * Determines whether the given hand contains Four of a Kind.
   *
   * @param hand the hand of cards to evaluate
   * @return true if the hand contains four cards of the same rank; false otherwise
   */
  private static boolean isFourOfAKind(List<PlayingCard> hand) {
    Map<Ranks, Integer> rankCounts = getRankCounts(hand);
    return rankCounts.containsValue(4);
  }

  /**
   * Determines whether the given hand is a Full House (three of a kind and a pair).
   *
   * @param hand the hand of cards to evaluate
   * @return true if the hand is a full house; false otherwise
   */
  private static boolean isFullHouse(List<PlayingCard> hand) {
    Map<Ranks, Integer> rankCounts = getRankCounts(hand);
    return rankCounts.containsValue(3) && rankCounts.containsValue(2);
  }

  /**
   * Determines whether the given hand is a flush (all cards have the same suit).
   *
   * @param hand the hand of cards to evaluate
   * @return true if the hand is a flush; false otherwise
   */
  private static boolean isFlush(List<PlayingCard> hand) {
    Suits firstSuit = hand.get(0).getSuit();
    for (PlayingCard card : hand) {
      if (!card.getSuit().equals(firstSuit)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether the given hand is a straight (cards in consecutive order).
   * Ace is treated as both low (1) and high (14).
   *
   * @param hand the hand of cards to evaluate
   * @return true if the hand forms a straight; false otherwise
   */
  private static boolean isStraight(List<PlayingCard> hand) {
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
      boolean isSequential = true;
      for (int j = 0; j < 4; j++) {
        if (sortedValues.get(i + j) + 1 != sortedValues.get(i + j + 1)) {
          isSequential = false;
          break;
        }
      }
      if (isSequential) {
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
  private static boolean isThreeOfAKind(List<PlayingCard> hand) {
    Map<Ranks, Integer> rankCounts = getRankCounts(hand);
    return rankCounts.containsValue(3);
  }

  /**
   * Determines whether the given hand contains Two Pair.
   *
   * @param hand the hand of cards to evaluate
   * @return true if the hand contains two distinct pairs; false otherwise
   */
  private static boolean isTwoPair(List<PlayingCard> hand) {
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
  private static boolean isPair(List<PlayingCard> hand) {
    Map<Ranks, Integer> rankCounts = getRankCounts(hand);
    return rankCounts.containsValue(2);
  }

  /**
   * Counts the occurrences of each rank in the given hand.
   *
   * @param hand the hand of cards to evaluate
   * @return a map where the keys are ranks and the values are the number of
   *         occurrences of that rank
   */
  private static Map<Ranks, Integer> getRankCounts(List<PlayingCard> hand) {
    Map<Ranks, Integer> rankCounts = new HashMap<>();
    for (PlayingCard card : hand) {
      rankCounts.put(card.getRank(), rankCounts.getOrDefault(card.getRank(), 0) + 1);
    }
    return rankCounts;
  }
}