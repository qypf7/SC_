package sc.player2022.logic;

import sc.player2022.logic.Enums.Color;
import sc.player2022.logic.Enums.PieceType;

public abstract class Piece {
  private Color color;
  public boolean isAmber;

  public Piece(Color color, boolean isAmber) {
    this.color = color;
    this.isAmber = isAmber;
  }

  public Color getColor() {
    return color;
  }

  public boolean isAmber() {
    return isAmber;
  }

  public void setIsAmber(boolean isAmber) {
    this.isAmber = isAmber;
  }
  
  public boolean canCapture(Move move, Board board) {
    if (checkLegalMove(move, board) && board.getSquare(move.getNewX(), move.getNewY()) != null) {
      return true;
    } else {
      return false;
    }
  }

  public Piece clone() {
    Piece piece = null;
    if (getType() == PieceType.robbe) piece = new Robbe(color, isAmber);
    else if (getType() == PieceType.seestern) piece = new Seestern(color, isAmber);
    else if (getType() == PieceType.moewe) piece = new Moewe(color, isAmber);
    else if (getType() == PieceType.herzmuschel) piece = new Herzmuschel(color, isAmber);
    return piece;
  }

  public abstract PieceType getType();

  public abstract boolean checkLegalMove(Move move, Board board);
  
  public static Piece create(Color color, boolean isAmber, PieceType pieceType) {
    Piece pieceData;
    if (pieceType == PieceType.robbe) pieceData = new Robbe(color, isAmber);
    else if (pieceType == PieceType.seestern) pieceData = new Seestern(color, isAmber);
    else if (pieceType == PieceType.moewe) pieceData = new Moewe(color, isAmber);
    else pieceData = new Herzmuschel(color, isAmber);
    return pieceData;
  }
}

