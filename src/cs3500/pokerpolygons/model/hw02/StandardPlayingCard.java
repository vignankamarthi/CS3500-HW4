package cs3500.pokerpolygons.model.hw02;

/**
 * To represent the idea of a standard 52 card deck of playing cards.
 */
public class StandardPlayingCard implements PlayingCard {
  private final Ranks rank;
  private final Suits suit;

  /**
   * To construct a standard playing card.
   *
   */
  public StandardPlayingCard(Ranks rank, Suits suit) {
    this.rank = rank;
    this.suit = suit;
  }

  /**
   * To get the rank of this Standard Playing Card.
   * @return this Playing Card's rank.
   */
  public Ranks getRank() {
    return rank;
  }

  /**
   * To get the suit of this Playing Card.
   *
   * @return this Playing Card's suit.
   */
  @Override
  public Suits getSuit() {
    return suit;
  }

  /**
   * To return the desired format of a StandardPlayingCard as a String.
   * @return this StandardPlayingCard's formatted String.
   */
  @Override
  public String toString() {
    return this.rank.toString() + this.suit.toString();
  }
}
