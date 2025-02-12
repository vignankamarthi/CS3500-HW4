package cs3500.pokerpolygons.view;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

/**
 * Tests for the simple textual view implementation.
 */
public class PokerTrianglesTextualViewSimpleTest {
  private PokerTrianglesTextualViewSimple<PlayingCard> emptyView;
  private PokerTrianglesTextualViewSimple<PlayingCard> populatedView;

  @Before
  public void setUp() {
    Random seededRandom = new Random(8); // Ensure deterministic deck order

    // Creating an empty game with side length 5 with a seeded deck and shuffle enabled
    PokerTriangles emptyGame = new PokerTriangles(5, seededRandom);
    emptyGame.startGame(emptyGame.getNewDeck(), true, 4);
    emptyView = new PokerTrianglesTextualViewSimple<>(emptyGame);

    // Creating a populated game with side length 7 with a seeded deck and shuffle disabled
    PokerTriangles populatedGame = new PokerTriangles(7, seededRandom);
    populatedGame.startGame(emptyGame.getNewDeck(), false, 4);
    populatedView = new PokerTrianglesTextualViewSimple<>(populatedGame);
  }

  /**
   * Tests that toString() returns the fixed mock view state string.
   */
  @Test
  public void testToStringReturnsFixedString() {
    assertEquals("Mock view state", emptyView.toString());
  }

  /**
   * Tests that render(Appendable) appends the view's string representation to an empty Appendable.
   */
  @Test
  public void testRenderAppendsToEmptyAppendable() {
    StringBuilder output = new StringBuilder();
    try {
      emptyView.render(output);
    } catch (IOException e) {
      fail("IOException should not be thrown.");
    }
    assertEquals(emptyView.toString(), output.toString());
  }

  /**
   * Tests that render(Appendable) appends the
   * view's string representation to a non-empty Appendable.
   */
  @Test
  public void testRenderAppendsToNonEmptyAppendable() {
    StringBuilder output = new StringBuilder("Initial");
    try {
      populatedView.render(output);
    } catch (IOException e) {
      fail("IOException should not be thrown.");
    }
    assertEquals("Initial" + populatedView.toString(), output.toString());
  }

  /**
   * Tests that render(Appendable) throws an IllegalArgumentException when given a null Appendable.
   */
  @Test
  public void testRenderThrowsExceptionForNullAppendable() {
    assertThrows(IllegalArgumentException.class, () -> populatedView.render(null));
  }

  /**
   * Tests that constructing a PokerTrianglesTextualViewSimple with a null model
   * throws an IllegalArgumentException.
   */
  @Test
  public void testConstructorThrowsExceptionForNullModel() {
    assertThrows(IllegalArgumentException.class, () ->
            new PokerTrianglesTextualViewSimple<PlayingCard>(null));
  }
}