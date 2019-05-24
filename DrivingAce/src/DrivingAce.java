import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * @author Jerry Zhu
 *
 */
public class DrivingAce extends Application {
  private AnchorPane root; 
  
  @Override
  public void start(Stage primaryStage) {
    root = new AnchorPane();
    root.setPrefSize(700, 500);

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
          
        }
    };
    timer.start();
    
    primaryStage.setTitle("Driving Ace");
    primaryStage.setScene(new Scene(root));
    
    MenuButton btn = new MenuButton("LOL");
    root.getChildren().add(btn);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
