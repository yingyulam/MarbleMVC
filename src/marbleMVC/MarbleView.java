package marbleMVC;

/**
 * This is the interface of MarbleView.
 */
public interface MarbleView {

  /**
   * This method assigns a MarbleController to the view and set up panels accordingly.
   *
   * @param controller a MarbleController object.
   */
  void initializeGame(MarbleController controller);

  /**
   * This method updates the cells in the view according to the CellStatus provided by the controller.
   * The forbidden cells are set to invisible in the frame.
   *
   * @param buttonIndex the index number of the button that needs to be updated.
   */
  void updateCell(int buttonIndex);

  /**
   * This method updates the text provided by the controller in the scoreboard
   */
  void updateInfo();

  /**
   * Change the color of the button.
   * @param buttonIndex the index of the button
   */
  void setButtonColor(int buttonIndex);

  /**
   * Reset the color of the button to default.
   * @param buttonIndex the index of the button.
   */
  void clearButtonColor(int buttonIndex);
}
