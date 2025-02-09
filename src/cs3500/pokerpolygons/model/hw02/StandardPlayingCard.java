package cs3500.pokerpolygons.model.hw02;

/**
 * Represents a standard playing card in a 52-card deck that is french-suited and A-K ranked.
 * No jokers are included, and this deck can be used to play and standard type of card game
 * like blackjack, poker, go-fish etc.
 */
public class StandardPlayingCard extends AbstractPlayingCard {

  /**
   * Constructs a standard playing card with the given rank and suit.
   *
   * @param rank the rank of the card
   * @param suit the suit of the card
   * @throws IllegalArgumentException if rank or suit is null
   */
  public StandardPlayingCard(Ranks rank, Suits suit) {
    super(rank, suit);
    if (rank == null || suit == null) {
      throw new IllegalArgumentException("Rank and suit cannot be null.");
    }
  }

  /**
   * Formats a standard playing card as a string.
   *
   * @return the formatted card string
   */
  @Override
  public String toString() {
    return rank.toString() + suit.toString();
  }


}