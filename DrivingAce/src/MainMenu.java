import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;

/**
 * The MainMenu class.
 * 
 * @author Jerry Zhu
 * 
 */
public class MainMenu extends Scene {

  private long startNanoTime;
  
  public MainMenu(Parent root, double width, double height) {
    super(root, width, height);
  }

  public MainMenu(Parent arg0) {
    super(arg0);
    // TODO Auto-generated constructor stub
  }

  public MainMenu(Parent root, Paint fill) {
    super(root, fill);
    // TODO Auto-generated constructor stub
  }

  public MainMenu(Parent root, double width, double height, Paint fill) {
    super(root, width, height, fill);
    // TODO Auto-generated constructor stub
  }

  public MainMenu(Parent root, double width, double height, boolean depthBuffer) {
    super(root, width, height, depthBuffer);
    // TODO Auto-generated constructor stub
  }

  public MainMenu(Parent root, double width, double height, boolean depthBuffer,
      SceneAntialiasing antiAliasing) {
    super(root, width, height, depthBuffer, antiAliasing);
    // TODO Auto-generated constructor stub
  }

}
