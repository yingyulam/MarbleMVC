package marbleMVC;

/**
 * This is the interface of MarbleView.
 */
public interface MarbleView {

  void setGameBoard();

  void setHeadBoard();

  void setScoreBoard();

  void initializeGame(MarbleController controller);

  //void updateBoard();

  void updateCell(int buttonIndex);

  void updateInfo();
}
