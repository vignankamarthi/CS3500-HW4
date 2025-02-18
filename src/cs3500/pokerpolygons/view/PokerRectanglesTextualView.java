package cs3500.pokerpolygons.view;

import cs3500.pokerpolygons.model.hw02.EmptyCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.Card;

import java.io.IOException;
import java.util.List;

/**
 * A String type view of a PokerRectangles game to view the implementation details in a String
 * format.
 */
public class PokerRectanglesTextualView<C extends Card> implements PokerPolygonsTextualView {
  private final PokerPolygons<C> model;

  /**
   * Constructs a PokerRectanglesTextualView with the given game model.
   *
   * @param model the PokerPolygons game model
   * @throws IllegalArgumentException if the model is null
   */
  public PokerRectanglesTextualView(PokerPolygons<C> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
  }

  /**
   * Generates the string representation of the rectangular board, along with deck size and hand.
   *
   * @return a formatted string representing the game state
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int height = model.getHeight();
    int width = model.getWidth();

    // Construct board representation
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        C card = model.getCardAt(row, col);
        String cardStr = (card == null || card.equals(EmptyCard.getEmptyCard()))
                ? "__" : formatCard(card);
        sb.append(cardStr);
        if (col < width - 1) {
          sb.append(" ");
        }
      }
      sb.append("\n");
    }

    // Append deck size and hand info.
    sb.append("Deck: ").append(model.getRemainingDeckSize()).append("\n");
    sb.append("Hand: ").append(formatHand(model.getHand()));

    return sb.toString();
  }

  /**
   * Formats the player's hand as a comma-separated list.
   *
   * @param hand the list of cards in hand
   * @return the formatted hand string
   */
  private String formatHand(List<C> hand) {
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (C card : hand) {
      if (!first) {
        sb.append(", ");
      }
      sb.append(formatCard(card));
      first = false;
    }
    return sb.toString();
  }

  /**
   * Formats a card for display.
   *
   * @param card the card to format
   * @return the string representation of the card
   */
  private String formatCard(C card) {
    return card.toString();
  }

  /**
   * Appends the current textual view to the given appendable.
   *
   * @param append the Appendable to render the game state into
   * @throws IllegalArgumentException if append is null
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void render(Appendable append) throws IOException {
    if (append == null) {
      throw new IllegalArgumentException("Append cannot be null.");
    }
    append.append(this.toString());
  }
}