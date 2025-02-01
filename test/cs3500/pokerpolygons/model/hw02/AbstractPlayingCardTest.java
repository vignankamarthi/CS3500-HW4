package cs3500.pokerpolygons.model.hw02;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Testing class for AbstractPlayingCard. Ensures that card operations
 * function correctly under various conditions.
 */
public class AbstractPlayingCardTest {

  /**
   * Tests retrieval of rank when the rank consists of a single character.
   */
  @Test
  public void getRank_SingleCharacter() {
    AbstractPlayingCard card = new StandardPlayingCard(Ranks.ACE, Suits.HEARTS);
    assertEquals(Ranks.ACE, card.getRank());
  }

  /**
   * Tests retrieval of rank when the rank consists of two characters.
   */
  @Test
  public void getRank_TwoCharacters() {
    AbstractPlayingCard card = new StandardPlayingCard(Ranks.TEN, Suits.SPADES);
    assertEquals(Ranks.TEN, card.getRank());
  }

  /**
   * Tests retrieval of suit when the suit is a red-colored suit.
   */
  @Test
  public void getSuit_RedSuit() {
    AbstractPlayingCard card = new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS);
    assertEquals(Suits.HEARTS, card.getSuit());
  }

  /**
   * Tests retrieval of suit when the suit is a black-colored suit.
   */
  @Test
  public void getSuit_BlackSuit() {
    AbstractPlayingCard card = new StandardPlayingCard(Ranks.TWO, Suits.SPADES);
    assertEquals(Suits.SPADES, card.getSuit());
  }

  /**
   * Tests that two cards with the same rank and suit are considered equal.
   */
  @Test
  public void testEquals_SameRankAndSuit() {
    AbstractPlayingCard card1 = new StandardPlayingCard(Ranks.FIVE, Suits.SPADES);
    AbstractPlayingCard card2 = new StandardPlayingCard(Ranks.FIVE, Suits.SPADES);
    assertEquals(card1, card2);
  }

  /**
   * Tests that two cards with different ranks are not considered equal.
   */
  @Test
  public void testEquals_DifferentRank() {
    AbstractPlayingCard card1 = new StandardPlayingCard(Ranks.FIVE, Suits.SPADES);
    AbstractPlayingCard card2 = new StandardPlayingCard(Ranks.SIX, Suits.SPADES);
    assertNotEquals(card1, card2);
  }

  /**
   * Tests that two cards with different suits are not considered equal.
   */
  @Test
  public void testEquals_DifferentSuit() {
    AbstractPlayingCard card1 = new StandardPlayingCard(Ranks.FIVE, Suits.SPADES);
    AbstractPlayingCard card2 = new StandardPlayingCard(Ranks.FIVE, Suits.CLUBS);
    assertNotEquals(card1, card2);
  }

  /**
   * Tests that two identical cards generate the same hash code.
   */
  @Test
  public void testHashCode_IdenticalCards() {
    AbstractPlayingCard card1 = new StandardPlayingCard(Ranks.JACK, Suits.HEARTS);
    AbstractPlayingCard card2 = new StandardPlayingCard(Ranks.JACK, Suits.HEARTS);
    assertEquals(card1.hashCode(), card2.hashCode());
  }

  /**
   * Tests that two different cards generate different hash codes.
   */
  @Test
  public void testHashCode_DifferentCards() {
    AbstractPlayingCard card1 = new StandardPlayingCard(Ranks.JACK, Suits.HEARTS);
    AbstractPlayingCard card2 = new StandardPlayingCard(Ranks.TEN, Suits.HEARTS);
    assertNotEquals(card1.hashCode(), card2.hashCode());
  }

  /**
   * Tests string representation when the rank consists of a single character.
   */
  @Test
  public void testToString_SingleCharacterRank() {
    AbstractPlayingCard card = new StandardPlayingCard(Ranks.ACE, Suits.SPADES);
    assertEquals("A♠", card.toString());
  }

  /**
   * Tests string representation when the rank consists of two characters.
   */
  @Test
  public void testToString_TwoCharacterRank() {
    AbstractPlayingCard card = new StandardPlayingCard(Ranks.TEN, Suits.DIAMONDS);
    assertEquals("10♢", card.toString());
  }

  /**
   * Tests string representation when using a face card.
   */
  @Test
  public void testToString_FaceCard() {
    AbstractPlayingCard card = new StandardPlayingCard(Ranks.QUEEN, Suits.HEARTS);
    assertEquals("Q♡", card.toString());
  }
}