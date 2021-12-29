package sc.player2022.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sc.api.plugins.IGameState;
import sc.player.IGameHandler;
import sc.player2022.logic.Enums.Color;
import sc.player2022.logic.Enums.PieceType;
import sc.plugin2022.GameState;
import sc.shared.GameResult;


/**
 * Das Herz des Clients:
 * Eine sehr simple Logik, die ihre Zuege zufaellig waehlt,
 * aber gueltige Zuege macht.
 * <p>
 * Ausserdem werden zum Spielverlauf Konsolenausgaben gemacht.
 */
public class Logic implements IGameHandler {
  private static final Logger log = LoggerFactory.getLogger(Logic.class);

  /** Aktueller Spielstatus. */
  private GameState gameState;
  public Board board = new Board();
  
  public void onGameOver(GameResult data) {
    log.info("Das Spiel ist beendet, Ergebnis: {}", data);
  }

  @Override
  public sc.plugin2022.Move calculateMove() {
    long startTime = System.currentTimeMillis();
    log.info("Es wurde ein Zug von {} angefordert.", gameState.getCurrentTeam());;
    if (gameState.getTurn() == 0) {
      for (int x = 0; x < 8; x += 7) {
        Color color = (x == 0 ? Color.white : Color.black);
        for (int y = 0; y < 8; y++) {
          sc.plugin2022.Piece piece = gameState.getBoard().get(x, y);
          if (piece.getType() == sc.plugin2022.PieceType.Robbe) {
            board.setSquare(x, y, Piece.create(color, false, PieceType.robbe));
          } else if (piece.getType() == sc.plugin2022.PieceType.Seestern) {
            board.setSquare(x, y, Piece.create(color, false, PieceType.seestern));
          } else if (piece.getType() == sc.plugin2022.PieceType.Moewe) {
            board.setSquare(x, y, Piece.create(color, false, PieceType.moewe));
          } else {
            board.setSquare(x, y, Piece.create(color, false, PieceType.herzmuschel));
          }
          System.out.println(board.getSquare(x, y).toString());
        }
      }
    }

    sc.plugin2022.Move move = gameState.getPossibleMoves().get(1);
    return move;
  }

  @Override
  public void onUpdate(IGameState gameState) {
    this.gameState = (GameState) gameState;
    log.info("Zug: {} Dran: {}", gameState.getTurn(), gameState.getCurrentTeam());
    sc.plugin2022.Move lastMove = this.gameState.getLastMove();
    
    if (lastMove != null) {
      board.performMove(Move.create(lastMove.getFrom().getX(), lastMove.getFrom().getY(), lastMove.getTo().getX(), lastMove.getTo().getY()));
    }
    board.getPiecesOfColor(Color.white).forEach((key, value) -> {
      System.out.println(key / 8 + ", " + (int)((key / 8.0 - key / 8) * 8) + " - " + key + " : " + value.getType() + " / " + value.isAmber());
    });
    System.out.println();
    board.getPiecesOfColor(Color.black).forEach((key, value) -> {
      System.out.println(key / 8 + ", " + (int)((key / 8.0 - key / 8) * 8) + " - " + key + " : " + value.getType() + " / " + value.isAmber());
    });
    System.out.println("White: " + board.getAmbersOfColor(Color.white));
    System.out.println("Black: " + board.getAmbersOfColor(Color.black));
    board.showBoard();
  }

  @Override
  public void onError(String error) {
    log.warn("Fehler: {}", error);
  }
}
