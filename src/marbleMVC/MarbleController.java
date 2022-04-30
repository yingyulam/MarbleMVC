package marbleMVC;

/**
 * Represents a Controller for Marble Solitaire: handle user moves by executing them using the model;
 * convey move outcomes to the user in some form.
 */

public interface MarbleController {

  /**
   * Execute a single game of Marble Solitaire.
   */
  void playGame();

  /**
   * Return the armSize of this game;
   * @return the armSize of this game.
   */
  int getArmSize();

  /**
   * Return the CellStatus (enum type) of the specific cell.
   *
   * @param row the row of the cell.
   * @param col the column of the cell.
   * @return the CellStatus of the specific cell.
   */
  CellStatus getCellStatus(int row, int col);

  /**
   * Record the position being clicked.
   *
   * @param row the row index of the cell that is clicked.
   * @param col the column index of the cell that is clicked.
   */
  void recordPosition(int row, int col);

  /**
   * Pass the text message for the scoreboard in view depending on the game status.
   *
   * @return a string that represents the game status (game over or not and current score) for view.
   */
  String passToScoreBoard();

  /**
   * Update the game board for view after a successful move.
   *
   * After every move, it checks only the three cells that have been changed and update in the view
   * accordingly, which is more efficient than updating the entire board after each move.
   */
  void updateBoard();

}
