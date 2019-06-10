import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;


/**
 * The Car class represents a car that can drive.
 * 
 * @author Nicholas Glenn
 * @version 4
 */
public class Car extends Rectangle {

  /**   
   * Center of car.
   */
  public Rectangle center;

  /**
   * Velocity of car.
   */
  private double velocity;

  /**
   * Velocity of car.
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
   * Max velocity of car.
   */
  private double maxVelocity;

  /**
   * Length of car.
   */
  private static final double length = 43;

  /**
   * Width of car.
   */
  private static final double width = 24;

  /**
   * Constructor of the car class.
   * 
   * @param x x-coordinate
   * @param y y-coordinate
   * @param i image fill of car
   */
  public Car(double x, double y, Image i) {
    super(x, y, width, length);
    setFill(new ImagePattern(i));
    setArcHeight(20);
    setArcWidth(20);
    center = new Rectangle(x + width / 2, y + length / 2, 0, 0);
    accelerationRate = 3;
    steeringRate = 3;
    maxVelocity = 400;
  }

  /**
   * Constructor with direction.
   * 
   * @param x x-coordinate
   * @param y y-coordinate
   * @param i image fill of car
   * @param direction direction of car
   */
  public Car(double x, double y, Image i, double direction) {
    super(x, y, width, length);
    setFill(new ImagePattern(i));
    setArcHeight(20);
    setArcWidth(20);
    center = new Rectangle(x + width / 2, y + length / 2, 0, 0);
    this.direction = direction;
    accelerationRate = 3;
    steeringRate = 3;
    maxVelocity = 400;
    getTransforms().add(new Rotate(direction, center.getX(), center.getY()));
    center.getTransforms().add(new Rotate(direction, center.getX(), center.getY()));
  }
  
  /**
   * Predicts the x-coordinate of the car after time.
   * 
   * @param t time elapsed
   * @return change in x-coordinate
   */
  public double predictMoveX(double t) {
    Bounds oldBounds = center.localToScene(center.getBoundsInLocal());
    move(t, 0);
    Bounds newBounds = center.localToScene(center.getBoundsInLocal());
    move(-t, 0);
    return newBounds.getMaxX() - oldBounds.getMaxX();
  }

  /**
   * Predicts the y-coordinate of the car after time.
   * 
   * @param t time elapsed
   * @return change in y-coordinate
   */
  public double predictMoveY(double t) {
    Bounds oldBounds = center.localToScene(center.getBoundsInLocal());
    move(t, 0);
    Bounds newBounds = center.localToScene(center.getBoundsInLocal());
    move(-t, 0);
    return newBounds.getMaxY() - oldBounds.getMaxY();
  }

  /**
   * Move coordinates of car.
   */
  public void move(double t) {
    double cool = velocity * t;
    friction(1);
    setY(getY() - cool);
    center.setY(center.getY() - cool);
  }

  /**
   * Move coordinates of car with specified amount of friction.
   */
  public void move(double t, double friction) {
    double cool = velocity * t;
    friction(friction);
    setY(getY() - cool);
    center.setY(center.getY() - cool);
  }
  
  /**
   * Translates car by specified amount.
   * 
   * @param x change in x
   * @param y change in y
   */
  public void translate(double x, double y) {
   setLayoutX(getLayoutX() + x);
   center.setLayoutX(center.getLayoutX() + x);
   setLayoutY(getLayoutY() + y);
   center.setLayoutY(center.getLayoutY() + y);
  }

  /**
   * Repositions car to a point.
   * 
   * @param x x-coordinate to move to
   * @param y y-coordinate to move to
   */
  public void reposition(double x, double y) {
   Bounds bounds = center.localToScene(center.getBoundsInLocal());
   setLayoutX(getLayoutX() + x - bounds.getMaxX());
   center.setLayoutX(center.getLayoutX() + x - bounds.getMaxX());
   setLayoutY(getLayoutY() + y - bounds.getMaxY());
   center.setLayoutY(center.getLayoutY() + y - bounds.getMaxY());
  }

  /**
   * Accelerates car.
   */
  public void accelerate() {
    velocity += accelerationRate;
    if (velocity > maxVelocity) {
      velocity = maxVelocity;
    }
  }

  /**
   * Reverses car.
   */
  public void reverse() {
    velocity -= accelerationRate;
    if (velocity < -maxVelocity) {
      velocity = -maxVelocity;
    }
  }

  /**
   * Brakes car.
   */
  public void brake() {
    if (velocity < 0) {
    velocity += accelerationRate;
    }
    else if (velocity > 0) {
    velocity -= accelerationRate;
    }
  }

  /**
   * Adds friction to car.
   */
  private void friction(double f) {
    if (velocity > 0) {
      velocity -= f;
    } else if (velocity < 0) {
      velocity += f;
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

  /**
   * Gets velocity.
   * 
   * @return velocity
   */
  public double getVelocity() {
    return velocity;
  }

  /**
   * Sets velocity.
   * 
   * @param velocity velocity
   */
  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }

  /**
   * Gets direction.
   * 
   * @return direction
   */
  public double getDirection() {
    return direction;
  }

  /**
   * Sets direction
   * 
   * @param direction direction
   */
  public void setDirection(double direction) {
    this.direction = direction;
  }
}
