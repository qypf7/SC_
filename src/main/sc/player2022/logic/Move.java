package sc.player2022.logic;

public class Move {
  private int oldX, oldY;
  private int newX, newY;

  public Move(int oldX, int oldY, int newX, int newY) {
    this.oldX = oldX;
    this.oldY = oldY;
    this.newX = newX;
    this.newY = newY;
  }

  public int getOldX() {
    return oldX;
  }

  public int getOldY() {
    return oldY;
  }

  public int getNewX() {
    return newX;
  }

  public int getNewY() {
    return newY;
  }

  public String toString() {
    String text = " moved from (" + oldX + ", " + oldY + ") to (" + newX + ", " + newY + ")";
    return text;
  }

  public static Move create(int oldX, int oldY, int newX, int newY) {
    Move move = new Move(oldX, oldY, newX, newY);
    return move;
  }
}
