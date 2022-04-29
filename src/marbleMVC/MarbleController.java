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
   * @param row the row of the cell.
   * @param col the column of the cell.
   * @return
   */
  CellStatus getCellStatus(int row, int col);

  void move(int row, int col);

  int convertToRow(int buttonIndex);

  int convertToCol(int buttonIndex);

  int convertToButton(int row, int col);

  String passMessage();

  //void update(int fromRow, int fromCol, int toRow, int toCol);

}
