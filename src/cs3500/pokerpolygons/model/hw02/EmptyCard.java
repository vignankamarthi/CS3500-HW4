package cs3500.pokerpolygons.model.hw02;

/**
 * Utilizing a sentinel type pattern to represent an empty card for better scalability.
 */
public class EmptyCard implements PlayingCard {
  // Creating the empty object as a global variable of the class.
  private static final EmptyCard EMPTY = new EmptyCard();

  /**
   * Private constructor to enforce the singleton pattern,
   * where every instance provides access to a global constant.
   *
   */
  private EmptyCard() {}


  public static EmptyCard getEmptyCard() {
    return EMPTY;
  }

  /**
   * To represent an empty board.
   * @return the string representation of an empty slot.
   */
  @Override
  public String toString() {
    return " __";
  }

  /**
   * To get the rank of this Playing Card.
   *
   * @return this Playing Card's rank.
   */
  @Override
  public Ranks getRank() {
    return null;
  }

  /**
   * To get the suit of this Playing Card.
   *
   * @return this Playing Card's suit.
   */
  @Override
  public Suits getSuit() {
    return null;
  }

  /**
   * All Empty slots are equal to one-another.
   * @return true for all comparisons of an empty card.
   */
  @Override
  public boolean equals(Object obj) {
    return obj instanceof EmptyCard;
  }

  /**
   * All empty cards have the same hashcode conversion.
   * @return the hashcode conversion of a card.
   */
  @Override
  public int hashCode() {
    return 0;
  }
}