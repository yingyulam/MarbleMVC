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
   * Record the position being clicked if appropriate.
   *
   * @param row the row index of the cell that is clicked.
   * @param col the column index of the cell that is clicked.
   *
   */
  @Override
  public void recordPosition(int row, int col) {

    if (model.isGameOver()) {
      return;
    }

    if (this.fromRow == -1 && this.fromCol == -1 && getCellStatus(row, col) == CellStatus.OCCUPIED) {
      this.fromRow = row;
      this.fromCol = col;
      view.setButtonColor(convertToButton(fromRow, fromCol));
    }

    else if (this.toRow == -1 && this.toCol == -1 &&
        getCellStatus(fromRow, fromCol) == CellStatus.OCCUPIED){
      this.toRow = row;
      this.toCol = col;
    }

    tryToMove();

  }

  /**
   * Try to make a move on the board. If a move is successful, the "from" and "to" positions will be
   * reset to -1. If a move is impossible, the positions will be changed accordingly.
   */
  private void tryToMove() {

    if (this.fromRow == -1 && this.fromCol == -1 || this.toRow == -1 && this.toCol == -1) {

      return;
    }

    try {
      model.move(fromRow, fromCol, toRow, toCol);
      updateBoard();
      view.clearButtonColor(convertToButton(fromRow, fromCol));
      this.fromRow = -1;
      this.fromCol = -1;
      this.toRow = -1;
      this.toCol = -1;
    }

    catch (IllegalArgumentException e) {
      if (getCellStatus(toRow, toCol) == CellStatus.OCCUPIED) {
        view.clearButtonColor(convertToButton(fromRow, fromCol));
        this.fromRow = this.toRow;
        this.fromCol = this.toCol;
        this.toRow = -1;
        this.toCol = -1;
        view.setButtonColor(convertToButton(fromRow, fromCol));
      }

      else if (getCellStatus(toRow, toCol) == CellStatus.EMPTY) {
        this.toRow = -1;
        this.toCol = -1;
      }

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
