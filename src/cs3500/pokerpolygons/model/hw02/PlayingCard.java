package cs3500.pokerpolygons.model.hw02;

/**
 * To represent the idea of a playing card in a game.
 */
public interface PlayingCard extends Card {

  @Override
  String toString();

  /**
   * To get the rank of this Playing Card.
   * @return this Playing Card's rank.
   */
  public Ranks getRank();

  /**
   * To get the suit of this Playing Card.
   * @return this Playing Card's suit.
   */
  public Suits getSuit();

}
