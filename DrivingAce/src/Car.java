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
   * Velocity of car.
   */
  private double velocity;

  /**
   * Direction of car (left is negative value, right is positive value, straight is 0).
   */
  private double direction;

  /**
   * Rate of acceleration.
   */
  private double accelerationRate;

  /**
   * Rate of steering.
   */
  private double steeringRate;

  /**
   * Rotates left.
   */
  private Rotate rotateLeft;

  /**
   * Rotates right.
   */
  private Rotate rotateRight;

  /**
   * Constructor.
   * 
   * @param x x coordinate
   * @param y y coordinate
   */
  public Car(double x, double y) {
    super(x, y, 100, 200);
    setFill(Color.RED);
    setStroke(Color.RED);
    velocity = 0;
    setDirection(0);
    accelerationRate = 10;
    steeringRate = 20;
    rotateLeft = new Rotate(-2);
    rotateRight = new Rotate(2);
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
   * Steers car to left.
   */
  public void steerLeft() {
    setDirection(getDirection() - steeringRate);
    getTransforms().add(rotateLeft);
  }

  /**
   * Steers car to right.
   */
  public void steerRight() {
    setDirection(getDirection() + steeringRate);
    getTransforms().add(rotateRight);
  }

  public double getVelocity() {
    return velocity;
  }

  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }

  public double getDirection() {
    return direction;
  }

  public void setDirection(double direction) {
    this.direction = direction;
  }

}
