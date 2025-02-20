package cs3500.pokerpolygons.model.hw04;

import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;

/**
 * A builder class following the builder pattern to construct instances of PokerPolygons
 * games with customizable types and dimensions.
 */
public class PokerPolygonsBuilder<X extends PlayingCard> {

  /**
   * An enumeration to define which types of games are available to play.
   */
  public enum GameType {
    TRI, RECT, LOOSE
  }

  private GameType type;
  private int sideLength;
  private int width;
  private int height;

  /**
   * Constructs a PokerPolygonsBuilder with the given default values:
   * - Game type set to TRI (PokerTriangles)
   * - Side length set to 5 (minimum for PokerTriangles)
   * - Width and height set to 5 (default for PokerRectangles)
   */
  public PokerPolygonsBuilder() {
    this.type = GameType.TRI;
    this.sideLength = 5;
    this.width = 5;
    this.height = 5;
  }

  /**
   * Sets the type of the game to the given type.
   *
   * @param type is the GameType to set for this game
   * @return this PokerPolygonsBuilder instance (for method chaining)
   * @throws IllegalArgumentException if type is null
   */
  public PokerPolygonsBuilder<X> setType(GameType type) {
    if (type == null) {
      throw new IllegalArgumentException("Game type cannot be null.");
    }
    this.type = type;
    return this;
  }

  /**
   * Sets the side length of a PokerTriangles/LooserPokerTriangles game to the given length.
   *
   * @param sideLength is the length of the sides of the right triangle (must be at least 5)
   * @return this PokerPolygonsBuilder instance (for method chaining)
   * @throws IllegalArgumentException if sideLength is less than 5
   */
  public PokerPolygonsBuilder<X> setSideLength(int sideLength) {
    if (sideLength < 5) {
      throw new IllegalArgumentException("Side length must be 5 or greater: " + sideLength);
    }
    this.sideLength = sideLength;
    return this;
  }

  /**
   * Sets the width of the PokerRectangles game to be built to the given width.
   *
   * @param width is the width of the rectangular board (must be at least 5)
   * @return this PokerPolygonsBuilder instance (for method chaining)
   * @throws IllegalArgumentException if width is less than 5
   */
  public PokerPolygonsBuilder<X> setWidth(int width) {
    if (width < 5) {
      throw new IllegalArgumentException("Width must be 5 or greater: " + width);
    }
    this.width = width;
    return this;
  }

  /**
   * Sets the height of the PokerRectangles game to be built to the given height.
   *
   * @param height is the height of the rectangular board (must be at least 5)
   * @return this PokerPolygonsBuilder (for method chaining)
   * @throws IllegalArgumentException if height is less than 5
   */
  public PokerPolygonsBuilder<X> setHeight(int height) {
    if (height < 5) {
      throw new IllegalArgumentException("Height must be 5 or greater: " + height);
    }
    this.height = height;
    return this;
  }

  /**
   * Builds and returns an instance of an appropriate PokerPolygons subclass based on the
   * desired customizations.
   *
   * @return an instance of PokerPolygons (PokerTriangles, PokerRectangles, or LoosePokerTriangles)
   * @throws IllegalStateException if the game type is invalid or dimensions are inconsistent
   */
  public PokerPolygons<X> build() {
    switch (this.type) {
      case TRI:
        return (PokerPolygons<X>) new PokerTriangles(sideLength);
      case RECT:
        return (PokerPolygons<X>) new PokerRectangles(width, height);
      case LOOSE:
        return (PokerPolygons<X>) new LoosePokerTriangles(sideLength);
      default:
        throw new IllegalStateException("Unknown game type: " + type);
    }
  }
}