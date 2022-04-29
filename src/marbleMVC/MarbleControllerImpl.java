package marbleMVC;

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
   * @return
   */
  @Override
  public CellStatus getCellStatus(int row, int col) {
    return model.getCellStatus(row, col);
  }

  @Override
  public void move(int row, int col) {

    if (this.fromRow == -1 && this.fromCol == -1) {
      this.fromRow = row;
      this.fromCol = col;
    }

    else {
      this.toRow = row;
      this.toCol = col;
    }

    if (this.fromRow != -1 && this.fromCol != -1 && this.toRow != -1 && this.toCol != -1) {
      try {
        model.move(fromRow, fromCol, toRow, toCol);
        view.updateBoard();

      }
      catch (IllegalArgumentException e) {
      }

      this.fromRow = -1;
      this.fromCol = -1;
      this.toRow = -1;
      this.toCol = -1;
    }
  }

  @Override
  public int convertToRow(int buttonIndex) {

    int armSize = model.getArmSize();
    int boardSize = armSize * 2 + 1;
    return buttonIndex / boardSize;

  }

  @Override
  public int convertToCol(int buttonIndex) {
    int armSize = model.getArmSize();
    int boardSize = armSize * 2 + 1;

    return buttonIndex % boardSize;
  }

  @Override
  public int convertToButton(int row, int col) {

    int armSize = model.getArmSize();
    int boardSize = armSize * 2 + 1;
    return row * boardSize + col;
  }

  @Override
  public String passMessage() {
    if (model.isGameOver()) {
      return "Game over. Your score is " + model.getScore();
    }

    else {
      return "score: " + model.getScore();
    }
  }

/*  @Override
  public void update(fromRow, fromCol, toRow, toCol) {

    if (fromRow == toRow) {
      for (int col = fromCol; col <= toCol; col++) {
        System.out.println("here");
        view.updateCell(convertToButton(fromRow, col));
      }
    }

    else if (fromCol == toCol) {
      for (int row = fromRow; row <= toRow; row++) {
        view.updateCell(convertToButton(row, fromCol));
      }
    }
  }*/



}
