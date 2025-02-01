package cs3500.pokerpolygons.view;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.EmptyCard;

import java.util.List;

/**
 * A String type view of a PokerTriangles game, ensuring empty spaces use EmptyCard.
 */
public class PokerTrianglesTextualView<C extends Card> implements PokerPolygonsTextualView {
  private final PokerPolygons<C> model;

  /**
   * Constructs a PokerTrianglesTextualView with the given instance.
   *
   * @throws IllegalArgumentException if the model is null
   */
  public PokerTrianglesTextualView(PokerPolygons<C> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
  }

  /**
   * Generates the string representation of the game.
   *
   * @return the formatted board, deck size, and hand
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int height = model.getHeight();

    // Construct board representation
    for (int row = 0; row < height; row++) {
      for (int col = 0; col <= row; col++) {
        C card = model.getCardAt(row, col);
        String cardStr = (card == null
                || card.equals(EmptyCard.getEmptyCard())) ? "__" : formatCard(card);

        // Ensure first column always has a leading space
        if (col == 0) {
          if (cardStr.length() == 3) {
            sb.append(cardStr);
          }
          else if (cardStr.length() == 2) {
            sb.append(" ").append(cardStr);
          }
          else {
            sb.append(" ").append(cardStr);
          }
        } else if (cardStr.length() == 3) {  // If it's a "10" card, only one space before
          sb.append(" ").append(cardStr);
        } else {
          sb.append("  ").append(cardStr); // Default: two spaces before every other column
        }
      }
      sb.append("\n");
    }

    // Append deck size
    sb.append("Deck: ").append(model.getRemainingDeckSize()).append("\n");

    // Append formatted hand
    sb.append("Hand: ").append(formatHand(model.getHand()));

    return sb.toString();
  }

  /**
   * Checks if the given (row, col) is on the diagonal.
   */
  private boolean isDiagonal(int row, int col) {
    return row == col;
  }

  /**
   * Formats the player's hand with proper spacing.
   */
  private String formatHand(List<C> hand) {
    StringBuilder sb = new StringBuilder();
    boolean first = true;

    for (C card : hand) {
      if (!first) {
        sb.append(", ");
      }

      String formattedCard = formatCard(card);

      // Ensure 10-based cards align properly
      if (formattedCard.length() == 3) {
        sb.append(formattedCard);
      } else {
        sb.append(formattedCard);
      }

      first = false;
    }
    return sb.toString();
  }

  /**
   * Formats a card for display, ensuring proper alignment for "10" and single-character cards.
   */
  private String formatCard(C card) {
    String cardStr = card.toString();
    return cardStr;
  }
}