import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DrivingAce extends Application {

  @Override
  public void start(Stage primaryStage) {
    StackPane root = new StackPane(new Car(200, 200));

    primaryStage.setTitle("Driving Ace");
    primaryStage.setScene(new LevelOne(root, 640, 480));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
