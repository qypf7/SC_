package sc.player2022.logic;

import sc.player2022.logic.Enums.Color;
import sc.player2022.logic.Enums.PieceType;

public class Moewe extends Piece {

  public Moewe(Color color, boolean isAmber) {
    super(color, isAmber);
  }

  @Override
  public PieceType getType() {
    return PieceType.moewe;
  }

  @Override
  public boolean checkLegalMove(Move move, Board board) {
    int oldX = move.getOldX();
    int oldY = move.getOldY();
    int newX = move.getNewX(); 
    int newY = move.getNewY();

    if (board.getSquare(newX, newY) != null) {
      if (board.getSquare(newX, newY).getColor() == super.getColor()) {
        return false;
      }
    }

    if (Math.abs(newX - oldX) == 1 && newY == oldY) {
      return true;
    } else if (newX == oldX && Math.abs(newY - oldY) == 1) {
      return true;
    } else {
      return false;
    }
  } 
}
