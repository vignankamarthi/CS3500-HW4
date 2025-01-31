package cs3500.pokerpolygons.view;

import cs3500.pokerpolygons.model.hw02.*;
import org.junit.Before;
import org.junit.Test;
//TODO: Create a full testing environment.
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class PokerTrianglesTextualViewTest {
  private PokerTrianglesTextualView<PlayingCard> emptyView;
  private PokerTrianglesTextualView<PlayingCard> populatedView;
  private PokerTriangles emptyGame;
  private PokerTriangles populatedGame;

  @Before
  public void setUp() {
    Random seededRandom = new Random(8); // Ensure deterministic deck order

    // Create an empty game with side length 5
    emptyGame = new PokerTriangles(5, seededRandom);
    emptyGame.startGame(getSampleDeck(), true, 4); // Ensuring deck & hand exist
    emptyView = new PokerTrianglesTextualView<>(emptyGame);

    // Create a populated game with side length 7
    populatedGame = new PokerTriangles(7, seededRandom);
    populatedGame.startGame(getSampleDeck(), false, 4);
    populatedView = new PokerTrianglesTextualView<>(populatedGame);
  }

  /**
   * To test initializaiton of an empty game with a randomly seeded deck.
   */
  @Test
  public void testEmptyBoardToString() {
    String expected =
            " __\n" +
                    " __ __\n" +
                    " __ __ __\n" +
                    " __ __ __ __\n" +
                    " __ __ __ __ __\n" +
                    "Deck: 52\n" +
                    "Hand: J♢, 8♢, 3♠, 2♣";

    assertEquals(expected, emptyView.toString());
  }

  @Test
  public void testPopulatedBoardToString() {
    String expected =
            " 2♢\n" +
                    " 3♣  8♢\n" +
                    " 4♡  3♡  5♢\n" +
                    " 5♣  4♠ 10♠  3♣\n" +
                    " 6♠  8♠  5♡  9♡  7♡\n" +
                    " K♣  9♦  J♡  6♣  K♣  J♢\n" +
                    " Q♠  J♢  A♡  2♣  8♠  9♠ 10♠\n" +
                    "Deck: 52\n" +
                    "Hand: J♠, A♠, J♢, 2♠";

    assertEquals(expected, populatedView.toString());
  }

  /**
   * Generates a sample deck for testing.
   */
  private List<PlayingCard> getSampleDeck() {
    List<PlayingCard> deck = new ArrayList<>();
    for (Ranks rank : Ranks.values()) {
      for (Suits suit : Suits.values()) {
        if (suit != Suits.EMPTY) {
          deck.add(new StandardPlayingCard(rank, suit));
        }
      }
    }
    return deck;
  }
}