import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;

/**
 * The LevelOne class.
 * 
 * @author Nicholas Glenn
 */
public class LevelOne {

  private long startNanoTime;

  public LevelOne(Parent arg0) {
    super(arg0);
    // TODO Auto-generated constructor stub
  }

  public LevelOne(Parent root, Paint fill) {
    super(root, fill);
    // TODO Auto-generated constructor stub
  }

  /**
   * Ignore the other constructors for now ples.
   * 
   * @param root
   * @param width
   * @param height
   */
  public LevelOne(Parent root, double width, double height) {
    super(root, width, height);
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

  public LevelOne(Parent root, double width, double height, Paint fill) {
    super(root, width, height, fill);
    // TODO Auto-generated constructor stub
  }

  public LevelOne(Parent root, double width, double height, boolean depthBuffer) {
    super(root, width, height, depthBuffer);
    // TODO Auto-generated constructor stub
  }

  public LevelOne(Parent root, double width, double height, boolean depthBuffer,
      SceneAntialiasing antiAliasing) {
    super(root, width, height, depthBuffer, antiAliasing);
    // TODO Auto-generated constructor stub
  }

}
