package cs3500.pokerpolygons.model.hw04;

import org.junit.Test;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;

import static org.junit.Assert.*;
public class PokerPolygonsBuilderTest {

  /**
   * Test that setType throws IllegalArgumentException when given null.
   */
  @Test
  public void testSetTypeNull() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    try {
      builder.setType(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Game type cannot be null.", e.getMessage());
    }
  }

  /**
   * Test that setSideLength throws IllegalArgumentException when sideLength is less than 5.
   */
  @Test
  public void testSetSideLengthLessThanFive() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    try {
      builder.setSideLength(4);
    } catch (IllegalArgumentException e) {
      assertEquals("Side length must be 5 or greater: 4", e.getMessage());
    }

    try {
      builder.setSideLength(0);
    } catch (IllegalArgumentException e) {
      assertEquals("Side length must be 5 or greater: 0", e.getMessage());
    }

    try {
      builder.setSideLength(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("Side length must be 5 or greater: -1", e.getMessage());
    }
  }

  /**
   * Test that setWidth throws IllegalArgumentException when width is less than 5.
   */
  @Test
  public void testSetWidthLessThanFive() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    try {
      builder.setWidth(4);
    } catch (IllegalArgumentException e) {
      assertEquals("Width must be 5 or greater: 4", e.getMessage());
    }

    try {
      builder.setWidth(0);
    } catch (IllegalArgumentException e) {
      assertEquals("Width must be 5 or greater: 0", e.getMessage());
    }

    try {
      builder.setWidth(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("Width must be 5 or greater: -1", e.getMessage());
    }
  }

  /**
   * Test that setHeight throws IllegalArgumentException when height is less than 5.
   */
  @Test
  public void testSetHeightLessThanFive() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    try {
      builder.setHeight(4);
    } catch (IllegalArgumentException e) {
      assertEquals("Height must be 5 or greater: 4", e.getMessage());
    }

    try {
      builder.setHeight(0);
    } catch (IllegalArgumentException e) {
      assertEquals("Height must be 5 or greater: 0", e.getMessage());
    }

    try {
      builder.setHeight(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("Height must be 5 or greater: -1", e.getMessage());
    }
  }


  /**
   * Test that the default constructor sets TRI, sideLength=5, width=5, height=5,
   * and builds a PokerTriangles with side length 5.
   */
  @Test
  public void testDefaultConstructor() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    PokerPolygons<PlayingCard> game = builder.build();

    // Default should be a PokerTriangles with side length 5
    assertTrue(game instanceof PokerTriangles);
    assertEquals(5, game.getWidth());
    assertEquals(5, game.getHeight());
  }

  /**
   * Test that setType correctly sets TRI and build reflects it.
   */
  @Test
  public void testSetTypeTRI() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.TRI);
    PokerPolygons<PlayingCard> triGame = builder.build();
    assertTrue(triGame instanceof PokerTriangles);
    assertEquals(5, triGame.getWidth());
    assertEquals(5, triGame.getHeight());
  }

  /**
   * Test that setType correctly sets RECT and build reflects it.
   */
  @Test
  public void testSetTypeRECT() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.RECT);
    PokerPolygons<PlayingCard> rectGame = builder.build();
    assertTrue(rectGame instanceof PokerRectangles);
    assertEquals(5, rectGame.getWidth());
    assertEquals(5, rectGame.getHeight());
  }

  /**
   * Test that setType correctly sets LOOSE and build reflects it.
   */
  @Test
  public void testSetTypeLOOSE() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.LOOSE);
    PokerPolygons<PlayingCard> looseGame = builder.build();
    assertTrue(looseGame instanceof LoosePokerTriangles);
    assertEquals(5, looseGame.getWidth());
    assertEquals(5, looseGame.getHeight());
  }

  /**
   * Test that setSideLength correctly sets the side length for TRI games.
   */
  @Test
  public void testSetSideLengthTRI() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.TRI);
    builder.setSideLength(7);
    PokerPolygons<PlayingCard> triGame = builder.build();
    assertTrue(triGame instanceof PokerTriangles);
    assertEquals(7, triGame.getWidth());
    assertEquals(7, triGame.getHeight());
  }

  /**
   * Test that setSideLength is ignored by RECT games (uses default width/height).
   */
  @Test
  public void testSetSideLengthRECT() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.RECT);
    builder.setSideLength(9);
    PokerPolygons<PlayingCard> rectGame = builder.build();
    assertTrue(rectGame instanceof PokerRectangles);
    assertEquals(5, rectGame.getWidth()); // Should still be 5, not 9
    assertEquals(5, rectGame.getHeight());
  }

  /**
   * Test that setSideLength correctly sets the side length for LOOSE games.
   */
  @Test
  public void testSetSideLengthLOOSE() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.LOOSE);
    builder.setSideLength(8);
    PokerPolygons<PlayingCard> looseGame = builder.build();
    assertTrue(looseGame instanceof LoosePokerTriangles);
    assertEquals(8, looseGame.getWidth());
    assertEquals(8, looseGame.getHeight());
  }

  /**
   * Test that setWidth is ignored by TRI games (uses sideLength).
   */
  @Test
  public void testSetWidthTRI() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.TRI);
    builder.setSideLength(6);
    builder.setWidth(8);
    PokerPolygons<PlayingCard> triGame = builder.build();
    assertTrue(triGame instanceof PokerTriangles);
    assertEquals(6, triGame.getWidth()); // Should be 6, not 8
    assertEquals(6, triGame.getHeight());
  }

  /**
   * Test that setWidth correctly sets the width for RECT games.
   */
  @Test
  public void testSetWidthRECT() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.RECT);
    builder.setWidth(7);
    PokerPolygons<PlayingCard> rectGame = builder.build();
    assertTrue(rectGame instanceof PokerRectangles);
    assertEquals(7, rectGame.getWidth());
    assertEquals(5, rectGame.getHeight()); // Height unchanged
  }

  /**
   * Test that setWidth is ignored by LOOSE games (uses sideLength).
   */
  @Test
  public void testSetWidthLOOSE() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.LOOSE);
    builder.setSideLength(7);
    builder.setWidth(9);
    PokerPolygons<PlayingCard> looseGame = builder.build();
    assertTrue(looseGame instanceof LoosePokerTriangles);
    assertEquals(7, looseGame.getWidth()); // Should be 7, not 9
    assertEquals(7, looseGame.getHeight());
  }

  /**
   * Test that setHeight is ignored by TRI games (uses sideLength).
   */
  @Test
  public void testSetHeightTRI() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.TRI);
    builder.setSideLength(6);
    builder.setHeight(8);
    PokerPolygons<PlayingCard> triGame = builder.build();
    assertTrue(triGame instanceof PokerTriangles);
    assertEquals(6, triGame.getWidth());
    assertEquals(6, triGame.getHeight()); // Should be 6, not 8
  }

  /**
   * Test that setHeight correctly sets the height for RECT games.
   */
  @Test
  public void testSetHeightRECT() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.RECT);
    builder.setHeight(7);
    PokerPolygons<PlayingCard> rectGame = builder.build();
    assertTrue(rectGame instanceof PokerRectangles);
    assertEquals(5, rectGame.getWidth()); // Width unchanged
    assertEquals(7, rectGame.getHeight());
  }

  /**
   * Test that setHeight is ignored by LOOSE games (uses sideLength).
   */
  @Test
  public void testSetHeightLOOSE() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.LOOSE);
    builder.setSideLength(7);
    builder.setHeight(9);
    PokerPolygons<PlayingCard> looseGame = builder.build();
    assertTrue(looseGame instanceof LoosePokerTriangles);
    assertEquals(7, looseGame.getWidth());
    assertEquals(7, looseGame.getHeight()); // Should be 7, not 9
  }

  /**
   * Test that build constructs a PokerTriangles with default TRI settings.
   */
  @Test
  public void testBuildTRI() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.TRI);
    PokerPolygons<PlayingCard> triGame = builder.build();
    assertTrue(triGame instanceof PokerTriangles);
    assertEquals(5, triGame.getWidth());
    assertEquals(5, triGame.getHeight());
  }

  /**
   * Test that build constructs a PokerRectangles with custom RECT settings.
   */
  @Test
  public void testBuildRECT() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.RECT);
    builder.setWidth(6);
    builder.setHeight(7);
    PokerPolygons<PlayingCard> rectGame = builder.build();
    assertTrue(rectGame instanceof PokerRectangles);
    assertEquals(6, rectGame.getWidth());
    assertEquals(7, rectGame.getHeight());
  }

  /**
   * Test that build constructs a LoosePokerTriangles with custom LOOSE settings.
   */
  @Test
  public void testBuildLOOSE() {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(PokerPolygonsBuilder.GameType.LOOSE);
    builder.setSideLength(8);
    PokerPolygons<PlayingCard> looseGame = builder.build();
    assertTrue(looseGame instanceof LoosePokerTriangles);
    assertEquals(8, looseGame.getWidth());
    assertEquals(8, looseGame.getHeight());
  }

}