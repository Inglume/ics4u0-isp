import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends Rectangle implements Obstacle {
  public Wall(double x, double y, double width, double height) {
    super(x, y, width, height);
    setFill(Color.BLACK);
  }
  public Wall(double x, double y, double width, double height, String i) {
    super(x, y, width, height);
    setFill(Color.TRANSPARENT);
   // setFill(Color.BLACK);
  }
}