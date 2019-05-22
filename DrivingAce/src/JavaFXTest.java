import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The A class is a test class for JavaFX
 * 
 * @author Nicholas Glenn
 * @date May 21, 2019
 */
public class JavaFXTest extends Application {

  @Override
  public void start(Stage stage) {
    Canvas canvas = new Canvas(640, 480);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.RED);
    gc.fillRect(0, 0, 100, 100);
    Button btn = new Button();
    btn.setText("Say 'Hello World'");
    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        System.out.println("Hello World!");
      }
    });

    Group root = new Group();
    root.getChildren().add(canvas);
    root.getChildren().add(btn);

    Scene scene = new Scene(root, 300, 250);

    stage.setTitle("Hello World!");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
