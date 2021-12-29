package sc.player2022.logic;

import java.util.HashMap;

import sc.player2022.logic.Enums.Champion;
import sc.player2022.logic.Enums.Color;
import sc.player2022.logic.Enums.PieceType;

/**
 * Speichert alle Daten eines Spiels.
 */
public class Board {
  //#region Variables 
  /**
   * Speichert die Positionen der Spielfiguren auf dem Spielbrett
   */
  private Piece[][] board;

  private int turn; // Speichert die Rundenzahl
  private Color currentColor; // Speichert welcher Spieler dran ist
  
  private int whiteAmbers, blackAmbers; // Speichert die Anzahl der Bernsteine jedes Spielers

  /**
   * Behält Überblick über die Spielfiguren des jeweiligen Spielers. <p>
   * <b> key:</b> hashCode der Position auf dem Spielbrett <p>
   * <b> value:</b> Spielfigur
   */
  private HashMap<Integer, Piece> whitePieces = new HashMap<Integer, Piece>(), blackPieces = new HashMap<Integer, Piece>();
  //#endregion

  /**
   * Default-Konstruktor
   */
  public Board() {
    board = new Piece[8][8];
    // Erstelle eine leeres Spielbrett
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        board[x][y] = null;
      }
    }
    currentColor = Color.white; // Setze den startenden Spieler zu Weiß
  }

  /**
   * Dieser Konstruktor erstell ein neues Spielbrett auf Grundlage des gegebenen Spielbretts.
   * @param board - Das Spielbrett welches kopiert werden soll
   */
  public Board(Board board) {
    this.board = new Piece[8][8];
    // Erstelle ein leeres Spielbrett
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        this.board[x][y] = null;
      }
    }
    /*
     * Kopiere die Daten des gegebenen Spielbretts und setze sie in das neue Spielbrett ein.
     */
    currentColor = board.getCurrentColor(); // Setze die Farbe des startenden Spielers
    
    // Speichere auf welcher Position die Spielfiguren des jeweiligen Spielers stehen
    whitePieces.putAll(board.getPiecesOfColor(Color.white)); 
    blackPieces.putAll(board.getPiecesOfColor(Color.black));
    
    // Setze die Bernsteine der jeweiligen Spieler
    whiteAmbers = board.getAmbersOfColor(Color.white);
    blackAmbers = board.getAmbersOfColor(Color.black);

    // Setze die Spielfiguren des jeweiligen Spielers auf ihre jeweilige Position auf dem Spielbrett 
    whitePieces.forEach((key, value) -> {
      this.board[key / 8][(int)((key / 8.0 - key / 8) * 8)] = value.clone();
    });
    blackPieces.forEach((key, value) -> {
      this.board[key / 8][(int)((key / 8.0 - key / 8) * 8)] = value.clone();
    });
  }

  public Board(HashMap<Integer, Piece> pieces, Color currentColor, int turn, int whiteAmbers, int blackAmbers) {
    this.board = new Piece[8][8];
    // Erstelle ein leeres Spielbrett
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        this.board[x][y] = null;
      }
    }
    /*
     * Kopiere die gegebenen Daten und setze sie in das neue Spielbrett ein.
     */
    this.currentColor = currentColor; // Setze die Farbe des startenden Spielers
    
    // Speichere auf welcher Position die Spielfiguren des jeweiligen Spielers stehen
    pieces.forEach((key, value) -> {
      if (value.getColor() == Color.white) whitePieces.put(key, value);
      else if (value.getColor() == Color.black) blackPieces.put(key, value);
    });
    
    // Setze die Bernsteine der jeweiligen Spieler
    this.whiteAmbers = whiteAmbers;
    this.blackAmbers = blackAmbers;

    // Setze die Spielfiguren des jeweiligen Spielers auf ihre jeweilige Position auf dem Spielbrett 
    whitePieces.forEach((key, value) -> {
      this.board[key / 8][(int)((key / 8.0 - key / 8) * 8)] = value.clone();
    });
    blackPieces.forEach((key, value) -> {
      this.board[key / 8][(int)((key / 8.0 - key / 8) * 8)] = value.clone();
    });
  }

  /**
   * Gibt die Spielfigur auf dem Feld der XY-Koordinate zurück. <p>
   * <b> Note: </b> Ist dieses Feld leer wird {@code null} zurückgegeben.
   * @param x - Die X-Koordinate auf dem Spielbrett
   * @param y - Die Y-Koordinate auf dem Spielbrett
   * @return Die Spielfigur auf diesem Feld
   */
  public Piece getSquare(int x, int y) {
    return board[x][y];
  }

  /**
   * Setzt die gegebene Spielfigur auf die XY-Koordinate.
   * Außerdem werden folgende HashMaps aktualisiert: <p>
   * {@code whitePieces, blackPieces}
   * @param x - Die X-Koordinate auf dem Spielbrett
   * @param y - Die Y-Koordinate auf dem Spielbrett
   * @param piece - Welche Spielfigur auf das Feld gesetzt wird
   */
  private void setSquare(int x, int y, Piece piece) {
    if (piece != null) { // Wenn gegebene Spielfigur nicht null ist
      // Aktualisiere HashMaps
      if (piece.getColor() == Color.white) whitePieces.put(hashCode(x, y), piece);
      else blackPieces.put(hashCode(x, y), piece);

      board[x][y] = piece; // Setze Spielfigur aufs Feld
    }
  }

  /**
   * Entfernt die Spielfigur auf dem Feld der XY-Koordinate.
   * Außerdem werden folgende HashMaps aktualisiert: <p>
   * {@code whitePieces, blackPieces}
   * @param x - Die X-Koordinate auf dem Spielbrett
   * @param y - Die Y-Koordinate auf dem Spielbrett
   */
  private void clearSquare(int x, int y) {
    if (getSquare(x, y) != null) { // Wenn eine Spielfigur auf dem Feld ist
      // Aktualisiere HashMaps
      if (getSquare(x, y).getColor() == Color.white) whitePieces.remove(hashCode(x, y));
      else blackPieces.remove(hashCode(x, y));

      board[x][y] = null; // Entferne Spielfigur vom Feld
    }
  }

  /**
   * Gibt die aktuelle Rundenzahl zurück.
   * @return Aktuelle Rundenzahl
   */
  public int getTurn() {
    return turn;
  }

  /**
   * Gibt die Farbe des Spielers zurück, welcher gerade am Zug ist.
   * @return Farbe des aktuellen Spielers
   */
  public Color getCurrentColor() {
    return currentColor;
  }

  /**
   * Gibt die Anzahl der aktuellen Bernsteine des Spielers der gegebenen Farbe zurück.
   * @param color - Die Farbe des Spielers
   * @return Die akutellen Bernsteine eines spezifischen Spielers
   */
  public int getAmbersOfColor(Color color) {
    return (color == Color.white ? whiteAmbers : blackAmbers);
  }
  
  /**
   * Gibt je nach gegebener Farbe eine der folgenden HashMaps zurück:
   * {@code whitePieces, blackPieces} 
   * @param color - Die Farbe des Spielers
   * @return Eine HashMap, welche die Position von den Spielfiguren eines Spielers speichert
   */
  public HashMap<Integer, Piece> getPiecesOfColor(Color color) {
    return (color == Color.white ? whitePieces : blackPieces);
  }
  
  public Champion performMove(Move move) {
    Piece piece = getSquare(move.getOldX(), move.getOldY());
    int newX = move.getNewX();
    int newY = move.getNewY();

    if (whitePieces.size() == 0) return Champion.black;
    else if (blackPieces.size() == 0) return Champion.white;
    
    if (getSquare(newX, newY) != null) {
      if (piece.isAmber() == true || getSquare(newX, newY).isAmber() == true) {
        clearSquare(newX, newY);
        
        if (piece.getColor() == Color.white)  {
          whiteAmbers++; 
        } else {
          blackAmbers++;
        }
        clearSquare(move.getOldX(), move.getOldY());
      } else {
        clearSquare(newX, newY);
        setSquare(newX, newY, piece);
        piece.setIsAmber(true);
        clearSquare(move.getOldX(), move.getOldY());
      }
    } else {
      setSquare(newX, newY, piece);
      clearSquare(move.getOldX(), move.getOldY());
    }
    
    if (piece.getType() != PieceType.robbe) {
      if (piece.getColor() == Color.white && newX == 7) {
        clearSquare(newX, newY);
        whiteAmbers++;
      } else if (piece.getColor() == Color.black && newX == 0) {
        clearSquare(newX, newY);
        blackAmbers++;
      }
    }

    if (whiteAmbers > 1) return Champion.white;
    if (blackAmbers > 1) return Champion.black;

    nextTurn();
    
    if (turn == 60) {
      if (whiteAmbers > blackAmbers) return Champion.white;
      else if (whiteAmbers < blackAmbers) return Champion.black;
      else return Champion.gray;
    } else {
      return Champion.none;
    }
  }

  /**
   * Beginne die nächste Runde und ändere die Farbe des Spielers, welcher gerade am Zug ist.
   */
  private void nextTurn() {
    currentColor = (currentColor == Color.white ? Color.black : Color.white);
    turn++;
  }

  public void showBoard() {
    System.out.println();
    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 8; x++) {
        if (getSquare(x, y) != null) {
          if (getSquare(x, y).getType() == PieceType.robbe) System.out.print("R");
          else if (getSquare(x, y).getType() == PieceType.seestern) System.out.print("S");
          else if (getSquare(x, y).getType() == PieceType.moewe) System.out.print("M");
          else System.out.print("H");
        } else {
          System.out.print("_");
        }
        System.out.print(" ");
      }
      System.out.println();
    }
  }

  public int hashCode(int x, int y) {
    int i = 0;
    for (int k1 = 0;; k1++) {
      for (int k2 = 0; k2 < 8; k2++) {
        if (k1 == x && k2 == y) return i;
        i++;
      }
    }
  }
}


