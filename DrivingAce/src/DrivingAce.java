import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * @author jerry
 *
 */
public class DrivingAce extends Application {

  @Override
  public void start(Stage primaryStage) {
    Car car = new Car(200, 200);
    Pane root = new Pane();

    primaryStage.setTitle("Driving Ace");
    primaryStage.setScene(new LevelOne(root, 640, 480));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
