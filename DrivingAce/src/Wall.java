import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * The Wall class.
 * 
 * @author Nicholas Glenn
 * @version 3
 */
public class Wall extends Rectangle implements Obstacle {

  /**
   * Constructor.
   * 
   * @param x x-coordinate
   * @param y y-coordinate
   * @param width width of the Wall
   * @param height height of the Wall
   */
  public Wall(double x, double y, double width, double height) {
    super(x, y, width, height);
    setFill(Color.TRANSPARENT);
    setFill(Color.BLACK);
  }

}
