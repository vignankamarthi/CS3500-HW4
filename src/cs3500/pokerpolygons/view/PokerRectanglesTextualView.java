package cs3500.pokerpolygons.view;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.EmptyCard;

import java.util.List;

/**
 * A String type view of a PokerRectangles game to view the implementation details in a String
 * format.
 */
public class PokerRectanglesTextualView<C extends Card> extends PokerBasicPolygonsTextualView<C> {

  /**
   * Constructs a PokerRectanglesTextualView with the given model.
   *
   * @throws IllegalArgumentException if the model is null
   */
  public PokerRectanglesTextualView(PokerPolygons<C> model) {
    super(model);
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
      for (int col = 0; col < model.getWidth(); col++) {
        C card = getCardAtPosition(row, col);
        String cardStr = (card == null) ? "__" : formatCard(card);
        appendFormattedCell(sb, col, cardStr);
      }
      sb.append("\n");
    }

    // Append deck size
    sb.append("Deck: ").append(model.getRemainingDeckSize()).append("\n");

    // Append formatted hand
    sb.append("Hand: ").append(formatHand(model.getHand()));

    return sb.toString();
  }

}