package marbleMVC;

/**
 * Run a Marble Solitaire game interactively on the console.
 */
public class Main {
  /**
   * Run a TicTacToe game interactively on the console.
   *
   * @param args not used
   */
  public static void main(String[] args) {
    MarbleModel model = new MarbleModelImpl();
    MarbleView view = new MarbleViewImpl();
    MarbleController c = new MarbleControllerImpl(model, view);
    c.playGame();
  }
}
