import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


/**
 * The Pylon class represents a pylon that the car can collide with.
 * 
 * @author Nicholas Glenn
 * @version 4
 */
public class Pylon extends Rectangle implements Obstacle {

  /**
   * Constructor.
   * https://kenney.nl/assets/ui-pack
   * 
   * @param x x-coordinate
   * @param y y-coordinate
   */
  public Pylon(double x, double y) {
    super(x, y, 20, 20);
    setFill(new ImagePattern(new Image("/resources/cone_straight.png")));
  }
}
