package cs3500.pokerpolygons.model.hw02;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for the StandardPlayingCard class.
 */
public class StandardPlayingCardTest {

  /**
   * Tests if getRank() correctly returns the rank of the card.
   */
  @Test
  public void testGetRank() {
    StandardPlayingCard card = new StandardPlayingCard(Ranks.ACE, Suits.HEARTS);
    assertEquals(Ranks.ACE, card.getRank());
  }

  /**
   * Tests if getSuit() correctly returns the suit of the card.
   */
  @Test
  public void testGetSuit() {
    StandardPlayingCard card = new StandardPlayingCard(Ranks.TWO, Suits.CLUBS);
    assertEquals(Suits.CLUBS, card.getSuit());
  }

  /**
   * Tests if toString() correctly formats the card.
   */
  @Test
  public void testToString() {
    StandardPlayingCard card = new StandardPlayingCard(Ranks.KING, Suits.SPADES);
    assertEquals("K♠", card.toString());
  }

  /**
   * Tests if toString() correctly formats the card with a 2 character rank.
   */
  @Test
  public void testToStringTwoCharacter() {
    StandardPlayingCard card = new StandardPlayingCard(Ranks.TEN, Suits.SPADES);
    assertEquals("10♠", card.toString());
  }

  /**
   * Tests if equals() correctly identifies two identical cards as equal.
   */
  @Test
  public void testEquals() {
    StandardPlayingCard card1 = new StandardPlayingCard(Ranks.QUEEN, Suits.DIAMONDS);
    StandardPlayingCard card2 = new StandardPlayingCard(Ranks.QUEEN, Suits.DIAMONDS);
    assertEquals(card1, card2);
  }

  /**
   * Tests if equals() correctly identifies two different cards as not equal.
   */
  @Test
  public void testNotEquals() {
    StandardPlayingCard card1 = new StandardPlayingCard(Ranks.TEN, Suits.HEARTS);
    StandardPlayingCard card2 = new StandardPlayingCard(Ranks.NINE, Suits.HEARTS);
    assertNotEquals(card1, card2);
  }

  /**
   * Tests if the constructor throws IllegalArgumentException when rank is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullRank() {
    new StandardPlayingCard(null, Suits.HEARTS);
  }

  /**
   * Tests if the constructor throws IllegalArgumentException when suit is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullSuit() {
    new StandardPlayingCard(Ranks.FIVE, null);
  }

  /**
   * Tests if the constructor throws IllegalArgumentException when both rank and suit are null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullRankAndSuit() {
    new StandardPlayingCard(null, null);
  }
}