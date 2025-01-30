package cs3500.pokerpolygons.model.hw02;

import java.security.PublicKey;

/**
 * To represent the rank of a card in Poker, where aces are both high and low.
 */
public enum Ranks {
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  NINE(9),
  TEN(10),
  JACK(11),
  QUEEN(12),
  KING(13),
  ACE(1, 14);

  private final int oneValue;
  private final int fourteenValue;

  /**
   * To construct card ranks 2 - 13
   */
  Ranks(int value) {
    this.oneValue = value;
    this.fourteenValue = value;
  }

  /**
   * To construct the ace card.
   */
  Ranks(int oneValue, int fourteenValue) {
    this.oneValue = oneValue;
    this.fourteenValue = fourteenValue;
  }

  /**
   * To get the value of each rank. Here,
   * Ace defaults to 1.
   * @return this ranks value
   */
  public int getValue() {
    return this.oneValue;
  }

  /**
   * To get the value of the ace as a value of 14.
   * @return this Ace's 14 value.
   */
  public int getFourteenValue() {
    return this.fourteenValue;
  }


  /**
   * To return the String value of the rank.
   */
  @Override
  public String toString() {
    if (this == ACE) {
      return "A";
    }
    else {
      return String.valueOf(this.oneValue);
    }
  }

}