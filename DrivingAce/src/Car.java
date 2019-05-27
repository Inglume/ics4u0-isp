import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

/**
 * The Car class does thsinsgs.
 * 
 * @author Nicholas Glenn
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
   * Constructor.
   * 
   * @param x x coordinate
   * @param y y coordinate
   */
  public Car(double x, double y) {
    super(x, y, 50, 100);
    setFill(Color.RED);
    setStroke(Color.RED);
    center = new Rectangle(x + 25, y + 50, 0, 0);
    velocity = 0;
    accelerationRate = 10;
    steeringRate = 2;
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
