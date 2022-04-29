package marbleMVC;

import static java.lang.Math.*;


/**
 * This is a Controller for Marble Solitaire: handle user moves by executing them using the model;
 * convey move outcomes to the user in some form.
 */
public class MarbleControllerImpl implements MarbleController {

  private MarbleModel model;
  private MarbleView view;
  private int fromRow;
  private int fromCol;
  private int toRow;
  private int toCol;

  /**
   * This is the constructor of the MarbleControllerImpl class.
   *
   * @param model A MarbleModel object.
   * @param view A MarbleView object.
   * @throws IllegalArgumentException
   */
  public MarbleControllerImpl(MarbleModel model, MarbleView view) throws IllegalArgumentException {

    if (model == null) {
      throw new IllegalArgumentException("The model is invalid.");
    }

    if (view == null) {
      throw new IllegalArgumentException("The view is invalid.");
    }

    this.model = model;
    this.view = view;
    this.fromRow = -1;
    this.fromCol = -1;
    this.toRow = -1;
    this.toCol = -1;
  }

  /**
   * Execute a single game of Marble Solitaire.
   */
  @Override
  public void playGame() {

    view.initializeGame(this);

  }

  /**
   * Return the armSize of this game;
   *
   * @return the armSize of this game.
   */
  @Override
  public int getArmSize() {

    return model.getArmSize();

  }

  /**
   * Return the CellStatus (enum type) of the specific cell.
   *
   * @param row the row of the cell.
   * @param col the column of the cell.
   * @return the CellStatus of the specific cell.
   */
  @Override
  public CellStatus getCellStatus(int row, int col) {

    return model.getCellStatus(row, col);

  }

  /**
   * Try to make a move on the game board. It considers the clicks in pairs. If it is the first
   * click in a pair, the position will be stored as the "from position", otherwise the "to position".
   *
   * @param row the row index of the cell that the marble is moving from if it is the first click
   *            in a pair; the row index of the cell that the marble is moving to if it is the
   *            second click in a pair.
   * @param col the column index of the cell that the marble is moving from if it is the first click
   *            in a pair; the column index of the cell that the marble is moving to if it is the
   *            second click in a pair.
   */
  @Override
  public void tryToMove(int row, int col) {

    if (this.fromRow == -1 && this.fromCol == -1) {
      this.fromRow = row;
      this.fromCol = col;
      if (getCellStatus(this.fromRow,this.fromCol) == CellStatus.OCCUPIED) {
        view.setButtonColor(convertToButton(fromRow, fromCol));
      }
    }

    else {
      this.toRow = row;
      this.toCol = col;
      view.clearButtonColor(convertToButton(fromRow, fromCol));
    }

    if (this.fromRow != -1 && this.fromCol != -1 && this.toRow != -1 && this.toCol != -1) {
      try {
        model.move(fromRow, fromCol, toRow, toCol);
        updateBoard();

      }
      catch (IllegalArgumentException ignored) {

      }

      this.fromRow = -1;
      this.fromCol = -1;
      this.toRow = -1;
      this.toCol = -1;
    }
  }


  /**
   * Covert the row and column index of a cell to the corresponding button index for the view.
   *
   * @param row the row index of the cell.
   * @param col the column index of the cell.
   * @return the corresponding button index of the cell for the view.
   */
  private int convertToButton(int row, int col) {

    int armSize = model.getArmSize();
    int boardSize = armSize * 2 + 1;
    return row * boardSize + col;

  }

  /**
   * Return the initial score of this game.
   * @return the initial score of this game.
   */
  private int getInitialScore(){

    int armSize = model.getArmSize();
    int boardSize = model.getArmSize() * 2 + 1;
    int forbidSize = (boardSize - armSize) / 2;

    return boardSize * boardSize - 4 * forbidSize * forbidSize - 1;

  }


  /**
   * Pass the text message for the scoreboard in view depending on the game status.
   *
   * @return a string that represents the game status (game over or not and current score) for view.
   */
  @Override
  public String passToScoreBoard() {

    if (model.isGameOver()) {
      return "Game over. Your score is " + model.getScore() + " / " + getInitialScore();
    }

    else {
      return "Your score: " + model.getScore() + " / " + getInitialScore();
    }
  }

  /**
   * Update the game board for view after a successful move.
   *
   * After every move, it checks only the three cells that have been changed and update in the view
   * accordingly, which is more efficient than updating the entire board after each move.
   */
  @Override
  public void updateBoard() {

    if (fromRow == toRow) {
      int minCol = min(fromCol, toCol);
      int maxCol = max(fromCol, toCol);
      for (int col = minCol; col <= maxCol; col++) {
        view.updateCell(convertToButton(fromRow, col));
      }
    }

    else if (fromCol == toCol) {
      int minRow = min(fromRow, toRow);
      int maxRow = max(fromRow, toRow);
      for (int row = minRow; row <= maxRow; row++) {
        view.updateCell(convertToButton(row, fromCol));
      }
    }

    view.updateInfo();
  }

}
