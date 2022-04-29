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
 * This class represents the implementation of MarbleView.
 */
public class MarbleViewImpl extends JFrame implements MarbleView {


  private final JLabel scoreLabel = new JLabel();
  private final JLabel headLabel = new JLabel();
  private final JPanel scoreBoard = new JPanel();
  private final JPanel gameBoard = new JPanel();
  private final JPanel headBoard = new JPanel();
  private JButton[] buttons;
  private MarbleController controller = null;
  private int numOfButtons;
  private int boardSize;
  private final int FRAME_SIZE = 500;
  private Color BACKGROUND_COLOR = new Color(121, 166, 217);


  /**
   * This is the constructor of the MarbleViewImpl.
   */
  public MarbleViewImpl() {

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(FRAME_SIZE, FRAME_SIZE);
    this.setBackground(BACKGROUND_COLOR);

  }

  /**
   * Assign the MarbleController object to the controller field in this object.
   * @param controller a MarbleController object.
   */
  @Override
  public void initializeGame(MarbleController controller) {

    this.controller = controller;
    int armSize = controller.getArmSize();
    boardSize = armSize * 2 + 1;
    numOfButtons = boardSize * boardSize;
    buttons = new JButton[numOfButtons];
    setHeadBoard();
    setScoreBoard();
    setGameBoard();
    this.setLayout(new BorderLayout());
    this.add(headBoard, BorderLayout.NORTH);
    this.add(gameBoard, BorderLayout.CENTER);
    this.add(scoreBoard, BorderLayout.SOUTH);
    setVisible(true);

  }

  /**
   * Create the headboard of this game.
   * @return a JPanel object that is the headboard of the game.
   */
  @Override
  public void setHeadBoard() {

    headLabel.setBackground(BACKGROUND_COLOR);
    headLabel.setForeground(Color.BLACK);
    headLabel.setFont(new Font("Arial", Font.BOLD,40));
    headLabel.setHorizontalAlignment(JLabel.CENTER);
    headLabel.setOpaque(true);
    headLabel.setText("Marble Solitaire");

    headBoard.setBackground(BACKGROUND_COLOR);
    headBoard.add(headLabel);

  }

  /**
   * Create the headboard of this game.
   * @return a JPanel object that is the headboard of the game.
   */
  @Override
  public void setScoreBoard() {

    scoreLabel.setBackground(BACKGROUND_COLOR);
    scoreLabel.setForeground(Color.BLACK);
    scoreLabel.setFont(new Font("Arial", Font.BOLD,25));
    scoreLabel.setHorizontalAlignment(JLabel.CENTER);
    scoreLabel.setOpaque(true);
    scoreLabel.setText(controller.passMessage());

    scoreBoard.setBackground(BACKGROUND_COLOR);
    scoreBoard.add(scoreLabel);
  }


  /**
   * Create the board of this game.
   * @return a JPanel object that is the board of this game.
   */
  @Override
  public void setGameBoard() {

    int armSize = controller.getArmSize();
    int boardSize = armSize * 2 + 1;
    int numOfButtons = boardSize * boardSize;
    int fontSize = FRAME_SIZE / boardSize;

    gameBoard.setLayout(new GridLayout(boardSize, boardSize));

    for (int i = 0; i < numOfButtons; i++) {
      buttons[i] = new JButton();
      gameBoard.add(buttons[i]);
      buttons[i].setFont(new Font("Arial", Font.BOLD, fontSize));
      buttons[i].setFocusable(false);
      updateCell(i);
      int row = controller.convertToRow(i);
      int col = controller.convertToCol(i);
      buttons[i].addActionListener(e -> {controller.move(row, col);});
    }
  }

  public void updateCell(int buttonIndex) {

    int row = controller.convertToRow(buttonIndex);
    int col = controller.convertToCol(buttonIndex);

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

  public void updateBoard() {
    for (int i = 0; i < numOfButtons; i++) {
      updateCell(i);
    }

    scoreLabel.setText(controller.passMessage());
  }




}
