import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

/**
 * The LevelOne class.
 * 
 * @author Nicholas Glenn
 */
public class LevelOne {

  private long startNanoTime;

  /**
   * Ignore the other constructors for now ples.
   * 
   * @param root
   * @param width
   * @param height
   */
  public LevelOne(AnchorPane r) {
    Car damn = new Car(200, 200);
    // add car to thing
    startNanoTime = System.nanoTime();

    ArrayList<String> input = new ArrayList<String>();

    setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        String code = e.getCode().toString();

        // only add once... prevent duplicates
        if (!input.contains(code))
          input.add(code);
      }
    });

    new AnimationTimer() {
      @Override
      public void handle(long currentNanoTime) {
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        damn.setX(damn.getX() + (damn.getVelocity() * t) * ((damn.getDirection() + 90) / 90));
        damn.setY(damn.getY() + (damn.getVelocity() * t) * (damn.getDirection() / 90));
        startNanoTime = currentNanoTime;
        if (input.contains("W")) {
          damn.accelerate();
        }
        if (input.contains("A")) {
          damn.steerLeft();
        }
        if (input.contains("S")) {
          damn.brake();
        }
        if (input.contains("D")) {
          damn.steerRight();
        }
        if (input.contains("ESCAPE")) {
          System.exit(0);;
        }
      }
    }.start();
  }
}
