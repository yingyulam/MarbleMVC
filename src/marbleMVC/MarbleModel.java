package marbleMVC;

/**
 * This interface represents the operations offered by the marble solitaire
 * model. One object of the model represents one game of marble solitaire.
 */
public interface MarbleModel {
  /**
   * Move a single marble from a given position to another given position.
   * A move is valid only if the from and to positions are valid. Specific
   * implementations may place additional constraints on the validity of a move.
   * @param fromRow the row number of the position to be moved from
   *                (starts at 0)
   * @param fromCol the column number of the position to be moved from
   *                (starts at 0)
   * @param toRow the row number of the position to be moved to
   *              (starts at 0)
   * @param toCol the column number of the position to be moved to
   *              (starts at 0)
   * @throws IllegalArgumentException if the move is not possible
   */
  void move(int fromRow,int fromCol,int toRow,int toCol) throws
          IllegalArgumentException;

  /**
   * Determine and return if the game is over or not. A game is over if no
   * more moves can be made.
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Return a string that represents the current state of the board. The
   * string should have one line per row of the game board. Each slot on the
   * game board is a single character (O, X or space for a marble, empty and
   * invalid position respectively). Slots in a row should be separated by a
   * space. Each row has no space before the first slot and after the last slot.
   * @return the game state as a string
   */
  String getGameState();

  /**
   * Return the number of marbles currently on the board.
   * @return the number of marbles currently on the board
   */
  int getScore();

  /**
   * Return the armSize of the game.
   * @return the armSize of the game
   */
  int getArmSize();

  /**
   * Return the CellStatus (enum type) of the specific cell.
   * @param row the row of the cell.
   * @param col the column of the cell.
   * @return the CellStatus of the specific cell
   */
  CellStatus getCellStatus(int row, int col) throws IllegalArgumentException;

}
