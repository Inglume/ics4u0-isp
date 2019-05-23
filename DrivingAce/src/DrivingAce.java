import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * @author Jerry Zhu
 *
 */
public class DrivingAce extends Application {

  @Override
  public void start(Stage primaryStage) {
    StackPane root = new StackPane();

    primaryStage.setTitle("Driving Ace");
    primaryStage.setScene(new MainMenu(root, 600, 400));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
