package cs3500.pokerpolygons.view;

import javax.smartcardio.Card;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;

/**
 * To view the game of PokerTriangles.
 */
public class PokerTrianglesTextualView implements PokerPolygonsTextualView{
  private final PokerPolygons<PlayingCard> model;
  // ... any other fields you need

  public PokerTrianglesTextualView(PokerPolygons<PlayingCard> model) {
    this.model = model;
  }

  // your implementation goes here
}