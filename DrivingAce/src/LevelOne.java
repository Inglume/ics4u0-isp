import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Translate;

/**
 * The LevelOne class.
 * 
 * @author Nicholas Glenn
 */
public class LevelOne {

  /**
   * Starting time in nanoseconds.
   */
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
    startNanoTime = System.nanoTime();

    ArrayList<String> input = new ArrayList<String>();

    setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        String code = e.getCode().toString();
        if (!input.contains(code)) {
          input.add(code);
        }
      }
    });

    setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        String code = e.getCode().toString();
        input.remove(code);
      }
    });

    new AnimationTimer() {
      @Override
      public void handle(long currentNanoTime) {
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        double x = (damn.getVelocity() * t) * ((damn.getDirection() + 90) / 90);
        System.out.println(x);
        double y = (damn.getVelocity() * t) * (damn.getDirection() / 90);
        System.out.println(y);
        Translate translate = new Translate(x, y);
        damn.getTransforms().addAll(translate);
        startNanoTime = currentNanoTime;
        if (input.contains("W")) {
          damn.accelerate();
          System.out.println("Forwards");
        }
        if (input.contains("A")) {
          damn.steerLeft();
          System.out.println("Left");
        }
        if (input.contains("S")) {
          damn.brake();
          System.out.println("Backwards");
        }
        if (input.contains("D")) {
          damn.steerRight();
          System.out.println("Right");
        }
        if (input.contains("ESCAPE")) {
          System.exit(0);
        }
      }
    }.start();
  }
}
