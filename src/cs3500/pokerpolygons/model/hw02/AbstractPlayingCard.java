package cs3500.pokerpolygons.model.hw02;
//TODO: Create Tests
/**
 * An abstract class representing a playing card, encapsulating common attributes
 * and behaviors shared by all playing cards.
 */
public abstract class AbstractPlayingCard implements PlayingCard {
  protected final Ranks rank;
  protected final Suits suit;

  /**
   * Constructs an AbstractPlayingCard.
   *
   * @param rank the rank of the card
   * @param suit the suit of the card
   */
  protected AbstractPlayingCard(Ranks rank, Suits suit) {
    this.rank = rank;
    this.suit = suit;
  }

  /**
   * Retrieves the rank of this card.
   *
   * @return the rank of the card
   */
  @Override
  public Ranks getRank() {
    return rank;
  }

  /**
   * Retrieves the suit of this card.
   *
   * @return the suit of the card
   */
  @Override
  public Suits getSuit() {
    return suit;
  }

  /**
   * Defines equality for playing cards based on rank and suit.
   *
   * @param obj the object to compare with
   * @return true if the cards have the same rank and suit, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof AbstractPlayingCard)) {
      return false;
    }
    AbstractPlayingCard that = (AbstractPlayingCard) obj;
    return this.rank == that.rank && this.suit == that.suit;
  }

  /**
   * Generates a hashcode for the card.
   *
   * @return the hashcode value
   */
  @Override
  public int hashCode() {
    return (rank == null ? 0 : rank.hashCode()) * 31 + (suit == null ? 0 : suit.hashCode());
  }

  /**
   * Abstract method for rendering a card as a string.
   * Subclasses must implement their own formatting.
   *
   * @return the string representation of the card
   */
  @Override
  public abstract String toString();
}