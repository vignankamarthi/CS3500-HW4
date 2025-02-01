package cs3500.pokerpolygons.model.hw02;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Test suite for EmptyCard. Ensures correct behavior of an empty card,
 * covering:
 * - Rank and suit retrieval for empty cards
 * - Proper handling of equality and hash code
 * - String representation of empty cards
 * - Ensuring only one instance of EmptyCard exists
 */
public class EmptyCardTest {

  /**
   * Tests that an empty card returns null or a special value when retrieving rank.
   */
  @Test
  public void getRank_EmptyCard() {
    EmptyCard empty = EmptyCard.getEmptyCard();
    assertNull(empty.getRank());
  }

  /**
   * Tests that an empty card returns null or a special value when retrieving suit.
   */
  @Test
  public void getSuit_EmptyCard() {
    EmptyCard empty = EmptyCard.getEmptyCard();
    assertNull(empty.getSuit());
  }

  /**
   * Tests that two empty cards are considered equal.
   */
  @Test
  public void testEquals_SameEmptyCardInstance() {
    EmptyCard empty1 = EmptyCard.getEmptyCard();
    EmptyCard empty2 = EmptyCard.getEmptyCard();
    assertEquals(empty1, empty2);
  }

  /**
   * Tests that an empty card is not equal to a non-empty card.
   */
  @Test
  public void testEquals_EmptyCardVsRealCard() {
    EmptyCard empty = EmptyCard.getEmptyCard();
    StandardPlayingCard realCard = new StandardPlayingCard(Ranks.ACE, Suits.SPADES);
    assertNotEquals(empty, realCard);
  }

  /**
   * Tests that all instances of EmptyCard return the same hash code.
   */
  @Test
  public void testHashCode_ConsistentForEmptyCards() {
    EmptyCard empty1 = EmptyCard.getEmptyCard();
    EmptyCard empty2 = EmptyCard.getEmptyCard();
    assertEquals(empty1.hashCode(), empty2.hashCode());
  }

  /**
   * Tests that an empty card's string representation is correctly formatted.
   */
  @Test
  public void testToString_EmptyCard() {
    EmptyCard empty = EmptyCard.getEmptyCard();
    assertEquals("__", empty.toString());
  }

  /**
   * Tests that getEmptyCard() always returns the same instance.
   */
  @Test
  public void getEmptyCard_SingletonInstance() {
    EmptyCard instance1 = EmptyCard.getEmptyCard();
    EmptyCard instance2 = EmptyCard.getEmptyCard();
    assertSame(instance1, instance2);
  }
}