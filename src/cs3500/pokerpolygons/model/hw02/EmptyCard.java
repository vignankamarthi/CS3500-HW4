package cs3500.pokerpolygons.model.hw02;

/**
 * Singleton representation of an empty card for an unoccupied board position.
 */
public class EmptyCard extends AbstractPlayingCard {
  private static final EmptyCard EMPTY = new EmptyCard();

  /**
   * Private constructor to enforce the singleton pattern.
   */
  private EmptyCard() {
    super(null, null); // Empty cards have no rank or suit
  }

  /**
   * Provides access to the singleton instance of an empty card.
   *
   * @return the singleton instance of EmptyCard
   */
  public static EmptyCard getEmptyCard() {
    return EMPTY;
  }

  /**
   * Represents an empty board slot as a string.
   *
   * @return the string representation of an empty slot
   */
  @Override
  public String toString() {
    return "__";
  }

  /**
   * Defines equality for empty cards.
   *
   * @return true for all comparisons with another empty card
   */
  @Override
  public boolean equals(Object obj) {
    return obj instanceof EmptyCard;
  }

  /**
   * Provides a constant hash code for all empty cards.
   *
   * @return 0 as the hashcode value
   */
  @Override
  public int hashCode() {
    return 0;
  }
}