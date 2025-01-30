package cs3500.pokerpolygons.model.hw02;

/**
 * To represent the idea of a playing card in a game.
 */
public interface PlayingCard extends Card {

  /**
   * Renders a card with its value followed by its suit as one of
   * the following symbols (♣, ♠, ♡, ♢).
   * For example, the 3 of Hearts is rendered as {@code "3♡"}.
   *
   * @return the formatted card
   */
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
