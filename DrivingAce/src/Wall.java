import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * The Wall class represents a wall that the car can collide with.
 * 
 * @author Nicholas Glenn
 * @version 4
 */
public class Wall extends Rectangle implements Obstacle {

  /**
   * Constructor of the Wall class.
   * 
   * @param x x-coordinate
   * @param y y-coordinate
   * @param width width of the Wall
   * @param height height of the Wall
   */
  public Wall(double x, double y, double width, double height) {
    super(x, y, width, height);
    setFill(Color.TRANSPARENT);
  }
}
