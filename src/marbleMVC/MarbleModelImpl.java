package marbleMVC;

import static java.lang.Math.abs;

/**
 * This class represents the implementation of the MarbleSolitaireModel Interface. It can be
 * initialized to 4 different constructors.
 */
public class MarbleModelImpl implements MarbleModel {

  private static int STANDARD_ARM_SIZE = 3;
  private final int armSize;
  private final int boardSize;
  private CellStatus[][] board;
  private int score;

  /**
   * This is the first constructor of the MarbleSolitaireModelImpl. It takes no parameters and
   * initializes the game board as standard arm thickness of size 3 and the empty slot at the center
   * of the game board.
   */
  public MarbleModelImpl() {

    this.armSize = STANDARD_ARM_SIZE;
    this.boardSize = this.armSize * 2 + 1;
    this.board = createBoard(this.armSize, armSize, armSize);
    this.score = setInitialScore();

  }

  /**
   * This is the second constructor of the MarbleSolitaireModelImpl. It crates the game board as
   * the standard arm thickness of size 3 and the empty slot at the position (sRow, sCol).
   *
   * @param sRow the row index of the initial empty slot.
   * @param sCol the column index of the initial empty slot.
   */
  public MarbleModelImpl(int sRow, int sCol) {

    this.armSize = STANDARD_ARM_SIZE;
    this.boardSize = this.armSize * 2 + 1;
    this.board = createBoard(this.armSize, sRow, sCol);
    this.score = setInitialScore();

  }

  /**
   * This is the third constructor of the MarbleSolitaireModelImpl. It initializes the game board
   * with customized arm thickness and the empty slot at the center of the game board.
   * @param armSize the customized arm thickness.
   */
  public MarbleModelImpl(int armSize) throws IllegalArgumentException{

    this.armSize = armSize;
    this.boardSize = this.armSize * 2 + 1;
    this.board = createBoard(this.armSize, armSize, armSize);
    this.score = setInitialScore();
  }

  /**
   * This is the fourth constructor of the MarbleSolitaireModelImpl. It crates the game board with
   * customized arm thickness and the empty slot at the position (sRow, sCol).
   *
   * @param armSize the customized armSize of the game board.
   * @param sRow the row index of the initial empty slot.
   * @param sCol the column index of the initial empty slot.
   */
  public MarbleModelImpl(int armSize, int sRow, int sCol) {

    this.armSize = armSize;
    this.boardSize = this.armSize * 2 + 1;
    this.board = createBoard(this.armSize, sRow, sCol);
    this.score = setInitialScore();
  }

  /**
   * Return a two-dimensional array that represents the board of the game.
   * @param armSize the arm thickness of the board.
   * @param sRow the index of the row of the empty slot.
   * @param sCol the index of the column of the empty slot.
   * @return a two-dimensional array that represents the board of the game.
   */
  private CellStatus[][] createBoard(int armSize, int sRow, int sCol) throws IllegalArgumentException {

    if (armSize < STANDARD_ARM_SIZE || armSize % 2 == 0) {
      throw new IllegalArgumentException("The arm thickness must be a positive odd integer.");
    }

    int boardSize = armSize * 2 + 1;

    if (sRow < 0 || sRow >= boardSize || sCol < 0 || sCol >= boardSize || isForbidden(sRow, sCol)) {
      throw new IllegalArgumentException("Invalid position for the empty cell.");
    }

    CellStatus[][] board = new CellStatus[boardSize][boardSize];

    for (int i = 0; i < boardSize; i++) {

      for (int j = 0; j < boardSize; j++) {

        if (i == sRow && j == sCol) {
          board[i][j] = CellStatus.EMPTY;
        }

        else if (isForbidden(i, j)) {
          board[i][j] = CellStatus.FORBIDDEN;
        }

        else {
          board[i][j] = CellStatus.OCCUPIED;
        }
      }
    }

    return board;
  }

  /**
   * Determine if the cell on the board is forbidden (i.e. not allowed for a marble).
   *
   * @param row the row of the cell to check.
   * @param col the column of the cell to check
   * @return a boolean that indicates if the specific cell is forbidden for a marble.
   */
  private boolean isForbidden(int row, int col) {

    if (row < 0 || row >= this.boardSize || col < 0 || col >= boardSize) {
      throw new IllegalArgumentException("Invalid cell position of the game board.");
    }

    int forbidSize = (this.boardSize - this.armSize) / 2;

    return row < forbidSize && col < forbidSize ||
        row < forbidSize && col >= (forbidSize + this.armSize) ||
        row >= (forbidSize + this.armSize) && col < forbidSize ||
        row >= (forbidSize + this.armSize) && col >= (forbidSize + this.armSize);
  }

  /**
   * Return the initial score of the board.
   * @return the initial score of the board.
   */
  private int setInitialScore() {

    int forbidSize = (this.boardSize - this.armSize) / 2;

    return boardSize * boardSize - 4 * forbidSize * forbidSize - 1;

  }

  /**
   * Return the CellStatus (enum type) of the specific cell.
   * @param row the row of the cell.
   * @param col the column of the cell.
   * @return
   */
  public CellStatus getCellStatus(int row, int col) throws IllegalArgumentException{

    if (row < 0 || row >= this.boardSize || col < 0 || col >= this.boardSize) {
      throw new IllegalArgumentException("Invalid cell position of the game board.");
    }

    return board[row][col];

  }

  /**
   * Move a single marble from a given position to another given position. A move is valid only if
   * the from and to positions are valid. Specific implementations may place additional constraints
   * on the validity of a move.
   *
   * @param fromRow the row number of the position to be moved from (starts at 0)
   * @param fromCol the column number of the position to be moved from (starts at 0)
   * @param toRow   the row number of the position to be moved to (starts at 0)
   * @param toCol   the column number of the position to be moved to (starts at 0)
   * @throws IllegalArgumentException if the move is not possible
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {

    if (getCellStatus(fromRow, fromCol) == CellStatus.FORBIDDEN ||
        getCellStatus(fromRow, fromCol) == CellStatus.EMPTY) {
      throw new IllegalArgumentException("The from position of the move is invalid.");
    }

    if (getCellStatus(toRow, toCol) == CellStatus.FORBIDDEN ||
        getCellStatus(toRow, toCol) == CellStatus.OCCUPIED) {
      throw new IllegalArgumentException("The to position of the move is invalid.");
    }

    int midRow, midCol;
    int rowDiff = toRow - fromRow;
    int colDiff = toCol - fromCol;

    if (rowDiff == 0) {
      midRow = fromRow;
      if (colDiff == 2 && checkEast(fromRow, fromCol)) {
        midCol = fromCol + 1;
      }
      else if (colDiff == -2 && checkWest(fromRow, fromCol)) {
        midCol = fromCol - 1;
      }
      else {
        throw new IllegalArgumentException("Invalid horizontal move: marble must jump over exactly"
            + " one marble and land in an empty slot exactly two positions away");
      }
    }
    else if (colDiff == 0) {
      midCol = fromCol;
      if (rowDiff == 2 && checkSouth(fromRow, fromCol)) {
        midRow = fromRow + 1;
      }
      else if (rowDiff == -2 && checkNorth(fromRow, fromCol)){
        midRow = fromRow - 1;
      }
      else {
        throw new IllegalArgumentException("Invalid vertical move: marble must jump over exactly one "
            + "marble and land in an empty slot exactly two positions away");
      }
    }
    else {
      throw new IllegalArgumentException("Invalid move: only horizontal and vertical moves are allowed");
    }

    this.board[fromRow][fromCol] = CellStatus.EMPTY;
    this.board[midRow][midCol] = CellStatus.EMPTY;
    this.board[toRow][toCol] = CellStatus.OCCUPIED;
    this.score -= 1;

  }

  /**
   * Determine if the marble can move to north.
   * @param row the row index of the marble to be moved.
   * @param col the column index of the marble to be moved.
   * @return a boolean indicates if the marble can be moved to the north.
   */
  private boolean checkNorth(int row, int col) {
    int midRow = row - 1;
    int toRow = row - 2;
    return (getCellStatus(midRow, col) == CellStatus.OCCUPIED) &&
        (getCellStatus(toRow, col) == CellStatus.EMPTY);
  }

  /**
   * Determine if the marble can move to south.
   * @param row the row index of the marble to be moved.
   * @param col the column index of the marble to be moved.
   * @return a boolean indicates if the marble can be moved to the south.
   */
  private boolean checkSouth(int row, int col) {
    int midRow = row + 1;
    int toRow = row + 2;
    return (getCellStatus(midRow, col) == CellStatus.OCCUPIED) &&
        (getCellStatus(toRow, col) == CellStatus.EMPTY);
  }

  /**
   * Determine if the marble can move to west.
   * @param row the row index of the marble to be moved.
   * @param col the column index of the marble to be moved.
   * @return a boolean indicates if the marble can be moved to the west.
   */
  private boolean checkWest(int row, int col) {
    int midCol = col - 1;
    int toCol = col - 2;
    return (getCellStatus(row, midCol) == CellStatus.OCCUPIED) &&
        (getCellStatus(row, toCol) == CellStatus.EMPTY);
  }


  /**
   * Determine if the marble can move to east.
   * @param row the row index of the marble to be moved.
   * @param col the column index of the marble to be moved.
   * @return a boolean indicates if the marble can be moved to the east.
   */
  private boolean checkEast(int row, int col) {
    int midCol = col + 1;
    int toCol = col + 2;
    return (getCellStatus(row, midCol) == CellStatus.OCCUPIED) &&
        (getCellStatus(row, toCol) == CellStatus.EMPTY);
  }

  /**
   * Determine if the specific position has legal move
   * @param row the row index of cell
   * @param col the column index of the cell
   * @return a boolean that indicates if there is at least a legal move for the position,
   */
  private boolean hasLegalMove(int row, int col) throws IllegalArgumentException{

    if (getCellStatus(row, col) != CellStatus.OCCUPIED) {
      throw new IllegalArgumentException("No marble on this cell.");
    }

    if (row < 2) {
      return checkSouth(row, col) || checkWest(row, col) || checkEast(row, col);
    }

    if (row > this.boardSize - 3) {
      return checkNorth(row, col) || checkWest(row, col) || checkEast(row, col);
    }

    if (col < 2) {
      return checkNorth(row, col) || checkSouth(row, col) || checkEast(row, col);
    }

    if (col > this.boardSize - 3) {
      return checkNorth(row, col) || checkSouth(row, col) || checkWest(row, col);
    }

    return checkNorth(row, col) || checkSouth(row, col) || checkWest(row, col) || checkEast(row, col);

  }

  /**
   * Determine and return if the game is over or not. A game is over if no more moves can be made.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    for (int i = 0; i < this.boardSize; i++) {
      for (int j = 0; j < this.boardSize; j++) {
        if (getCellStatus(i, j) == CellStatus.OCCUPIED && hasLegalMove(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Return a string that represents the current state of the board. The string should have one line
   * per row of the game board. Each slot on the game board is a single character (O, X or space for
   * a marble, empty and invalid position respectively). Slots in a row should be separated by a
   * space. Each row has no space before the first slot and after the last slot.
   *
   * @return the game state as a string
   */
  @Override
  public String getGameState() {

    String gameState = "";
    for (int i = 0; i < this.boardSize; i++) {
      for (int j = 0; j < this.boardSize; j++) {
        if (getCellStatus(i, j) == CellStatus.FORBIDDEN) {
          gameState += " ";
        }
        else if (getCellStatus(i, j) == CellStatus.OCCUPIED) {
          gameState += "O";
        }
        else if (getCellStatus(i, j) == CellStatus.EMPTY){
          gameState += "_";
        }
        if (j != this.boardSize - 1) {
          gameState += " ";
        }
        else if (i != this.boardSize - 1){
          gameState += "\n";
        }
      }
    }
    return gameState;
  }

  /**
   * Return the number of marbles currently on the board.
   *
   * @return the number of marbles currently on the board
   */
  @Override
  public int getScore() {
    return this.score;
  }

  /**
   * Return the armSize of the game.
   * @return the armSize of the game
   */
  @Override
  public int getArmSize() {
    return this.armSize;
  }

}
