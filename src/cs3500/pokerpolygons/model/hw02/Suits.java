package cs3500.pokerpolygons.model.hw02;

/**
 * Represents the 4 suits in a standard deck of playing cards and
 * distinguishes between red and black suits. as well as an empty suit
 */
public enum Suits {
  CLUBS('♣', "Black"),
  DIAMONDS('♢', "Red"),
  HEARTS('♡', "Red"),
  SPADES('♠', "Black");

  private final char symbol;
  private final String color;

  /**
   * To construct a suit of a card.
   * @param symbol is the symbol of the suit.
   * @param color is the color fo the suit.
   */
  Suits(char symbol, String color) {
    this.symbol = symbol;
    this.color = color;
  }

  /**
   * To get this suit's symbol.
   * @return the symbol of this suit.
   */
  @Override
  public String toString() {
    return String.valueOf(symbol);
  }

  /**
   * To get this suit's color.
   * @return the color of this suit.
   */
  public String getColor() {
    return this.color;
  }


}