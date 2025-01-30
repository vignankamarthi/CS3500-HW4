package cs3500.pokerpolygons.view;

import cs3500.pokerpolygons.model.hw02.EmptyCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.PlayingCard;
import java.util.List;

/**
 * A String type view of a PokerTriangles game.
 */
public class PokerTrianglesTextualView implements PokerPolygonsTextualView {
  private final PokerPolygons<PlayingCard> model;

  /**
   * Constructs a PokerTrianglesTextualView with the given instance.
   *
   * @param model the model to be displayed
   * @throws IllegalArgumentException if the model is null
   */
  public PokerTrianglesTextualView(PokerPolygons<PlayingCard> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null.");
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

    int width = model.getWidth();
    int height = model.getHeight();

    // Render the board (left-aligned)
    for (int row = 0; row < height; row++) {
      for (int col = 0; col <= row; col++) {
        PlayingCard card = model.getCardAt(row, col);
        sb.append(formatCard(card)).append(" ");
      }
      sb.append("\n");
    }

    // Display remaining deck size
    sb.append("Deck: ").append(model.getRemainingDeckSize()).append("\n");

    // Display the player's hand
    List<PlayingCard> hand = model.getHand();
    sb.append("Hand: ");
    for (int i = 0; i < hand.size(); i++) {
      sb.append(formatCard(hand.get(i)));
      if (i < hand.size() - 1) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }

  /**
   * Formats a card for display, ensuring proper spacing.
   *
   * @param card the card to format
   * @return the formatted card string
   */
  private String formatCard(PlayingCard card) {
    if (card == null) {
      return ""; // Out-of-bounds should return an empty string
    }
    if (card.equals(EmptyCard.getEmptyCard())) {
      return " __"; // Empty slots on the board
    }

    String cardStr = card.toString();
    if (cardStr.length() == 2) {
      return " " + cardStr; // Ensuring proper spacing for two-character cards
    }
    return cardStr; // Single-character ranks don't need extra spacing
  }
}