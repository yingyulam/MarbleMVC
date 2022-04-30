package marbleMVC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class represents MarbleView Implementation. It is a child class of the JFrame class, which
 * creates the user interface for the player.
 */
public class MarbleViewImpl extends JFrame implements MarbleView {


  private final JPanel mainWindow = new JPanel();
  private final JLabel scoreLabel = new JLabel();
  private final JLabel headLabel = new JLabel();
  private final JPanel scoreBoard = new JPanel();
  private final JPanel gameBoard = new JPanel();
  private final JPanel headBoard = new JPanel();
  private final JLabel instructionLabel = new JLabel();

  private JButton[] buttons;
  private MarbleController controller = null;
  private int boardSize;
  private final int FRAME_SIZE = 500;
  private final Color BACKGROUND_COLOR = new Color(121, 166, 217);


  /**
   * This is the constructor of the MarbleViewImpl. It sets up basic settings for the frame, and
   * add different panels to the frame.
   */
  public MarbleViewImpl() {

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(FRAME_SIZE, FRAME_SIZE);
    this.setBackground(BACKGROUND_COLOR);
    this.setLayout(new BorderLayout());
    this.add(headBoard, BorderLayout.NORTH);
    this.add(gameBoard, BorderLayout.CENTER);
    this.add(scoreBoard, BorderLayout.SOUTH);
  }

  /**
   * This method assigns a MarbleController to the view and set up panels accordingly.
   *
   * @param controller a MarbleController object.
   */
  @Override
  public void initializeGame(MarbleController controller) {

    this.controller = controller;
    int armSize = controller.getArmSize();
    boardSize = armSize * 2 + 1;
    int numOfButtons = boardSize * boardSize;
    buttons = new JButton[numOfButtons];
    setHeadBoard();
    setScoreBoard();
    setGameBoard();
    setVisible(true);

  }

  /**
   * This method sets up the headboard of this game. It shows the name of the game.
   *
   */
  private void setHeadBoard() {

    headLabel.setBackground(BACKGROUND_COLOR);
    headLabel.setForeground(Color.BLACK);
    headLabel.setFont(new Font("Arial", Font.BOLD,50));
    headLabel.setHorizontalAlignment(JLabel.CENTER);
    headLabel.setOpaque(true);
    headLabel.setText("Marble Solitaire");

    instructionLabel.setBackground(BACKGROUND_COLOR);
    instructionLabel.setForeground(Color.BLACK);
    instructionLabel.setFont(new Font("Arial", Font.BOLD,25));
    instructionLabel.setOpaque(true);
    instructionLabel.setHorizontalAlignment(JLabel.CENTER);
    instructionLabel.setText("Move a marble by jumping over another marble vertically or "
        + "horizontally to an empty slot.");

    headBoard.setLayout(new BorderLayout());
    headBoard.setBackground(BACKGROUND_COLOR);
    headBoard.add(headLabel, BorderLayout.NORTH);
    headBoard.add(instructionLabel, BorderLayout.SOUTH);

  }

  /**
   * This method sets up the score board of the game. The score board shows the current score of the
   * game and if the game is over.
   */
  private void setScoreBoard() {

    scoreLabel.setBackground(BACKGROUND_COLOR);
    scoreLabel.setForeground(Color.BLACK);
    scoreLabel.setFont(new Font("Arial", Font.BOLD,25));
    scoreLabel.setHorizontalAlignment(JLabel.CENTER);
    scoreLabel.setOpaque(true);
    scoreLabel.setText(controller.passToScoreBoard());

    scoreBoard.setBackground(BACKGROUND_COLOR);
    scoreBoard.add(scoreLabel);
  }


  /**
   * This method sets up the game board of the game. Its pattern depends on the game board model
   * passed to the controller. It also adds ActionListeners to each button. The tryToMove method in
   * the controller will be called if a button is clicked.
   */
  private void setGameBoard() {

    int numOfButtons = boardSize * boardSize;
    int fontSize = FRAME_SIZE / boardSize;

    gameBoard.setLayout(new GridLayout(boardSize, boardSize));
    gameBoard.setBackground(Color.WHITE);

    for (int i = 0; i < numOfButtons; i++) {
      buttons[i] = new JButton();
      gameBoard.add(buttons[i]);
      buttons[i].setBackground(Color.WHITE);
      buttons[i].setFont(new Font("Arial", Font.BOLD, fontSize));
      buttons[i].setFocusable(false);
      updateCell(i);
      int row = i / boardSize;
      int col = i % boardSize;
      buttons[i].addActionListener(e -> {controller.recordPosition(row, col);});
    }
  }

  /**
   * This method updates the cells in the view according to the CellStatus provided by the controller.
   * The forbidden cells are set to invisible in the frame.
   *
   * @param buttonIndex the index number of the button that needs to be updated.
   */
  public void updateCell(int buttonIndex) {

    int row = buttonIndex / boardSize;
    int col = buttonIndex % boardSize;

    if (controller.getCellStatus(row, col) == CellStatus.FORBIDDEN) {
      buttons[buttonIndex].setVisible(false);
    }

    else if (controller.getCellStatus(row, col) == CellStatus.OCCUPIED) {
        buttons[buttonIndex].setText("O");
    }

    else {
        buttons[buttonIndex].setText(" ");
    }
  }

  /**
   * This method updates the text provided by the controller in the scoreboard
   */
  public void updateInfo() {
    scoreLabel.setText(controller.passToScoreBoard());
  }

  /**
   * Change the color of the button.
   * @param buttonIndex the index of the button
   */
  public void setButtonColor(int buttonIndex) {
    buttons[buttonIndex].setBackground(Color.PINK);
  }

  /**
   * Reset the color of the button to default.
   * @param buttonIndex the index of the button.
   */
  public void clearButtonColor(int buttonIndex) {
    buttons[buttonIndex].setBackground(Color.WHITE);
  }


}
