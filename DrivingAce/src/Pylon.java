import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Pylon extends Rectangle implements Obstacle {

  public Pylon(double x, double y) {
    super(x, y, 20, 20);
    setFill(new ImagePattern(new Image("/resources/cone_straight.png")));
  }

}
