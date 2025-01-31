package cs3500.pokerpolygons.view;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import java.util.List;

/**
 * A String type view of a PokerTriangles game.
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

    // Render the board with a leading space for correct alignment
    for (int row = 0; row < height; row++) {
      sb.append(" "); // Add leading space

      for (int col = 0; col <= row; col++) {
        C card = model.getCardAt(row, col);
        if (card == null) {
          sb.append("__"); // Empty slot
        } else {
          sb.append(formatCard(card));
        }
        if (col < row) { // Space only between elements, not at the end
          sb.append(" ");
        }
      }
      sb.append("\n"); // Move to the next line
    }

    // Display remaining deck size
    sb.append("Deck: ").append(model.getRemainingDeckSize()).append("\n");

    // Display the player's hand
    List<C> hand = model.getHand();
    sb.append("Hand:");
    for (int i = 0; i < hand.size(); i++) {
      sb.append(formatCard(hand.get(i)));
      if (i < hand.size() - 1) {
        sb.append(",");
      }
    }
    return sb.toString();
  }

  /**
   * Formats a card for display.
   *
   * @param card the card to format
   * @return the formatted card string
   */
  private String formatCard(C card) {
    String cardStr = card.toString();

    if (cardStr.length() == 2) {
      return " " + cardStr; // Ensuring spacing for two-character cards
    } else {
      return cardStr;
    }
  }
}