package cs3500.pokerpolygons;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw04.PokerPolygonsBuilder;
import cs3500.pokerpolygons.view.PokerTrianglesTextualView;
import cs3500.pokerpolygons.view.PokerRectanglesTextualView;

import java.io.IOException;
import java.io.PrintStream;

/**
 * The entry point for the PokerPolygons game, accepting command-line arguments to configure
 * and launch the game with appropriate model and view.
 */
public final class PokerPolygonsGame {

  /**
   * The main method declaration to take in command line inputs.
   * @param args is an array of strings to parse and process the command line inputs
   */
  public static void main(String[] args) {
    // Parsing game variant and validate if inputs are valid
    PokerPolygonsBuilder.GameType gameType = parseGameVariant(args);

    // Parse optional arguments
    int[] config = parseConfigArgs(args, gameType);

    // Configure and build the game
    PokerPolygons<PlayingCard> game = configureAndBuildGame(gameType, config[0],
            config[1], config[2]);

    // Start the game
    startGame(game, config[0]);

    // Render the game state
    renderGame(game, gameType, System.out);
  }

  /**
   * Parses the game variant from the first command-line argument.
   *
   * @param args Command-line arguments
   * @return The corresponding GameType
   * @throws IllegalArgumentException if the variant is missing or invalid
   */
  private static PokerPolygonsBuilder.GameType parseGameVariant(String[] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException("Game variant must be one of 'tri', 'loose', " +
              "or 'rectangle'.");
    }

    String gameVariant = args[0].toLowerCase();
    switch (gameVariant) {
      case "tri":
        return PokerPolygonsBuilder.GameType.TRI;
      case "loose":
        return PokerPolygonsBuilder.GameType.LOOSE;
      case "rectangle":
        return PokerPolygonsBuilder.GameType.RECT;
      default:
        throw new IllegalArgumentException("Invalid game variant: " + gameVariant +
                ". Game variant must be 'tri', 'loose', or 'rectangle'.");
    }
  }

  /**
   * Parses the optional configuration arguments (hand size, side length, width).
   *
   * @param args Command-line arguments
   * @param gameType The game variant being configured
   * @return An array of [handSize, sideLength, width] with defaults applied
   */
  private static int[] parseConfigArgs(String[] args, PokerPolygonsBuilder.GameType gameType) {
    int handSize = 1; // Given default hand size
    int sideLength = 5; // Given default side length (or length for PokerRectangles)
    int width = 5; // GIven default width for rectangle

    try {
      if (args.length > 1) {
        handSize = Integer.parseInt(args[1]);
        if (handSize < 1) {
          handSize = 1;
        }
      }
      if (args.length > 2) {
        sideLength = Integer.parseInt(args[2]);
        if (sideLength < 5) {
          sideLength = -1; // Create IndexOutOfBounds exception as instructed to in the assignment
        }
      }
      if (gameType == PokerPolygonsBuilder.GameType.RECT && args.length > 3) {
        width = Integer.parseInt(args[3]);
        if (width < 5) {
          width = -1; // Create IndexOutOfBounds exception as instructed to in the assignment
        }
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid config arguments.", e);
    }

    return new int[]{handSize, sideLength, width};
  }

  /**
   * Configures and builds the PokerPolygons game model using the builder.
   *
   * @param gameType The type of PokerPolygons game to build
   * @param handSize Number of cards in the starting hand
   * @param sideLength Side length (or width for rectangle)
   * @param width Width (height for rectangle, ignored for triangle/loose)
   * @return The configured PokerPolygons game
   */
  private static PokerPolygons<PlayingCard> configureAndBuildGame(
          PokerPolygonsBuilder.GameType gameType, int handSize, int sideLength, int width) {
    PokerPolygonsBuilder<PlayingCard> builder = new PokerPolygonsBuilder<>();
    builder.setType(gameType);

    if (gameType == PokerPolygonsBuilder.GameType.RECT) {
      builder.setWidth(sideLength); // For rectangle, sideLength is width (columns)
      builder.setHeight(width);     // Width argument is height (rows)
    } else {
      builder.setSideLength(sideLength); // For tri and loose, use side length
    }

    return builder.build();
  }

  /**
   * Starts the game with a standard deck and the specified hand size.
   *
   * @param game The PokerPolygons game to start
   * @param handSize Number of cards in the starting hand
   */
  private static void startGame(PokerPolygons<PlayingCard> game, int handSize) {
    game.startGame(game.getNewDeck(), true, handSize);
  }

  /**
   * Renders the game state to the specified output stream using the appropriate view.
   *
   * @param game The PokerPolygons game to render
   * @param gameType The type of game determining the view
   * @param out The output stream to render to
   */
  private static void renderGame(PokerPolygons<PlayingCard> game,
                                 PokerPolygonsBuilder.GameType gameType, PrintStream out) {
    try {
      if (gameType == PokerPolygonsBuilder.GameType.RECT) {
        PokerRectanglesTextualView<PlayingCard> view = new PokerRectanglesTextualView<>(game);
        view.render(out);
      } else {
        PokerTrianglesTextualView<PlayingCard> view = new PokerTrianglesTextualView<>(game);
        view.render(out);
      }
      out.println(); // Ensure a newline after rendering
    } catch (IOException e) {
      System.err.println("Error rendering game state: " + e.getMessage());
    }
  }
}