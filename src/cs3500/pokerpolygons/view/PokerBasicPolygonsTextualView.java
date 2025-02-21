package cs3500.pokerpolygons.view;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import java.io.IOException;
import java.util.List;

/**
 * An abstract String type view of a PokerPolygons game to view the implementation details in a
 * String format.
 */
public abstract class PokerBasicPolygonsTextualView<C extends Card> implements
        PokerPolygonsTextualView {
  protected final PokerPolygons<C> model;

  /**
   * Constructs a PokerBasicPolygonsTextualView with the given model.
   *
   * @throws IllegalArgumentException if the model is null
   */
  protected PokerBasicPolygonsTextualView(PokerPolygons<C> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
  }

  /**
   * Generates the string representation of the game.
   *
   * @return the correctly formatted board, hand, and score.
   */
  public abstract String toString();

  /**
   * Formats the player's hand with proper spacing.
   */
  protected String formatHand(List<C> hand) {
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
   * Formats a card for display.
   */
  protected String formatCard(C card) {
    String cardStr = card.toString();
    return cardStr;
  }

  /**
   * To append the current textual output to the given appendable.
   *
   * @param append is the argument that will get the textual view appended on to.
   * @throws IllegalArgumentException if append is null
   * @throws IOException              if the rendering fails for some reason
   */
  @Override
  public void render(Appendable append) throws IOException {
    if (append == null) {
      throw new IllegalArgumentException("Append cannot be null.");
    }

    append.append(this.toString());
  }

  /**
   * Appends a formatted cell to the string builder while handling spacing rules.
   *
   * @param sb the StringBuilder to append to
   * @param col the column index
   * @param cardStr the string representation of the card
   */
  protected void appendFormattedCell(StringBuilder sb, int col, String cardStr) {
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
      sb.append("  ").append(cardStr); // Default is two spaces before every other column
    }
  }

  /**
   * Returns the card at the specified position on the board.
   *
   * @param row the row index
   * @param col the column index
   * @return the card at the position, or null if empty
   */
  protected C getCardAtPosition(int row, int col) {
    return model.getCardAt(row, col);
  }
}