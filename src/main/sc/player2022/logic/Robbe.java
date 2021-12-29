package sc.player2022.logic;

import sc.player2022.logic.Enums.Color;
import sc.player2022.logic.Enums.PieceType;

public class Robbe extends Piece{

  public Robbe(Color color, boolean isAmber) {
    super(color, isAmber);
  }

  
  @Override
  public PieceType getType() {
    return PieceType.robbe;
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

    if (Math.abs(newX - oldX) == 2 && Math.abs(newY - oldY) == 1 && (newX >= 0 && newX <= 7) && (newY >= 0 && newY <= 7)) {
      return true;
    } else if (Math.abs(newX - oldX) == 1 && Math.abs(newY - oldY) == 2 && (newX >= 0 && newX <= 7) && (newY >= 0 && newY <= 7)) {
      return true;
    } else {
      return false;
    }
  }
  
}
