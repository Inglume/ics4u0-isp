import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

/**
 * The Car class.
 * 
 * @author Nicholas Glenn
 * @version 1
 */
public class Car extends Rectangle {

  /**
   * Center of car.
   */
  private Rectangle center;

  /**
   * Velocity of car.
   */
  private double velocity;

  /**
   * Rate of acceleration.
   */
  private double accelerationRate;

  /**
   * Rate of steering.
   */
  private double steeringRate;

  /**
   * 
   */
  private static final double length = 64;

  /**
   * 
   */
  private static final double width = 36;

  /**
   * Constructor.
   * 
   * @param x x coordinate
   * @param y y coordinate
   */
  public Car(double x, double y, Image i) {
    super(x, y, width, length);
    setFill(new ImagePattern(i));
    center = new Rectangle(x + width / 2, y + length / 2, 0, 0);
    velocity = 0;
    accelerationRate = 5;
    steeringRate = 4;
  }

  public boolean intersects(Car c) {
    return false;
  }

  public boolean intersects(Obstacle o) {
    return false;
  }

  /**
   * Move coordinates of car.
   */
  public void move(double t) {
    double cool = velocity * t;
    friction();
    setY(getY() - cool);
    center.setY(center.getY() - cool);
  }

  /**
   * Accelerates car.
   */
  public void accelerate() {
    velocity += accelerationRate;
  }

  /**
   * Reverses car.
   */
  public void reverse() {
    velocity -= accelerationRate;
  }

  /**
   * Brakes car.
   */
  public void brake() {
    velocity -= accelerationRate;
    if (velocity < 0) {
      velocity = 0;
    }
  }

  /**
   * Adds friction to car.
   */
  private void friction() {
    if (velocity > 0) {
      velocity--;
    } else if (velocity < 0) {
      velocity++;
    }
  }

  /**
   * Steers car to left.
   */
  public void steerLeft() {
    double rotate = steeringRate * velocity / -300;
    getTransforms().add(new Rotate(rotate, center.getX(), center.getY()));
    center.getTransforms().add(new Rotate(rotate, center.getX(), center.getY()));
  }

  /**
   * Steers car to right.
   */
  public void steerRight() {
    double rotate = steeringRate * velocity / 300;
    getTransforms().add(new Rotate(rotate, center.getX(), center.getY()));
    center.getTransforms().add(new Rotate(rotate, center.getX(), center.getY()));
  }

  public double getVelocity() {
    return velocity;
  }

  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }

}
