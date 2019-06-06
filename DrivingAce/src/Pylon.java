import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


/**
 * @author Nicholas Glenn
 * @version 3
 */
public class Pylon extends Rectangle implements Obstacle {

  /**
   * @param x x-coordinate
   * @param y y-coordinate
   */
  public Pylon(double x, double y) {
    super(x, y, 20, 20);
    setFill(new ImagePattern(new Image("/resources/cone_straight.png")));
  }

}
