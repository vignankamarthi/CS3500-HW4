package cs3500.pokerpolygons.view;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import java.io.IOException;

/**
 * A simple mock implementation of the PokerPolygonsTextualView interface.
 * This view returns a fixed string to represent the model's state.
 */
public class PokerTrianglesTextualViewSimple<C extends Card>  implements PokerPolygonsTextualView {

  /**
   * Constructs a simple textual view for the given model.
   *
   * @param model the game model
   * @throws IllegalArgumentException if model is null
   */
  public PokerTrianglesTextualViewSimple(PokerPolygons<C> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
  }

  /**
   * Returns a fixed string representing the view.
   *
   * @return a mock view state string value
   */
  @Override
  public String toString() {
    return "Mock view state";
  }

  /**
   * Appends the view's string representation to the provided Appendable.
   *
   * @param append the Appendable to which the view is appended to.
   * @throws IllegalArgumentException if append is null
   * @throws IOException              if the append operation fails
   */
  @Override
  public void render(Appendable append) throws IOException {
    if (append == null) {
      throw new IllegalArgumentException("Append cannot be null.");
    }
    append.append(this.toString());
  }
}