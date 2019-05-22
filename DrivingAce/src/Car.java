import javafx.scene.shape.Rectangle;

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
   * Constructor.
   * 
   * @param x x coordinate
   * @param y y coordinate
   */
  public Car(double x, double y) {
    super(x, y, 100, 200);
    // TODO Auto-generated constructor stub
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
    direction -= steeringRate;
  }

  /**
   * Steers car to right.
   */
  public void steerRight() {
    direction += steeringRate;
  }

}
