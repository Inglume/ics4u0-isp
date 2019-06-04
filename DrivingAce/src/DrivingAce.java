import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * The DrivingAce class.
 * 
 * @author Nicholas Glenn and Jerry Zhu
 * @version 3
 */
public class DrivingAce extends Application {

  /**
   * The Pane of the Game.
   */
  private AnchorPane root;

  /**
   * The start time as a part of the game loop.
   */
  private long lastNanoTime;

  /**
   * The scene for the output. It will be put into the the Pane.
   */
  private Scene scene;

  /**
   * An instance of the SubScenes class for Instructions.
   */
  private SubScenes ins;

  /**
   * An instance of the SubScenes class for High Scores.
   */
  private SubScenes high;

  /**
   * Array of objects in a level.
   */
  private Obstacle[] obstacles;

  /**
   * Array of cars in a level.
   */
  private Car[] cars = new Car[0];

  /**
   * AnimationTimer in a level.
   */
  private AnimationTimer animationTimer;

  private int collisionCount;
  
  /**
   * Runs everything.
   */
  @Override
  public void start(Stage primaryStage) {
    primaryStage.setResizable(false);
    root = new AnchorPane();
    root.setMinSize(800, 600);
    root.setMaxSize(800, 600);
    scene = new Scene(root);

    primaryStage.setTitle("Driving Ace");
    primaryStage.setScene(scene);
    // intro();
    // intros(2, "Objective: Complete the Obstacle Course. \nYou Fail After 5 Collisions.\nPress a
    // Key to Continue.");
   // mainMenu();
    //levelEnd(false);
    // levelTwo();
    // levelOne();
    // levelThree();
    levelEnd(false, 1);
    primaryStage.show();
  }


  /**
   * Splash screen to program.
   */
  public void intro() {
    Image image = new Image("/resources/logo_transparent.png", 500, 815, false, true);

    // Setting the image view 1
    ImageView imageView1 = new ImageView(image);

    // Setting the position of the image
    imageView1.setX(270);
    imageView1.setY(170);

    // setting the fit height and width of the image view
    imageView1.setFitHeight(250);
    imageView1.setFitWidth(250);


    root.getChildren().add(imageView1);

    FadeTransition ft = new FadeTransition(Duration.millis(4000), imageView1);
    ft.setFromValue(2);
    ft.setToValue(0);
    ft.setAutoReverse(true);
    ft.play();
    ft.setCycleCount(1);
    ft.setOnFinished(e -> mainMenu());
  }

  /**
   * Main menu.
   */
  public void mainMenu() {
    root.getChildren().clear();
    obstacles = new Wall[0];
    if (animationTimer != null) {
      animationTimer.stop();
    }
    Rectangle rect = new Rectangle(-100, -100, 1030, 930);
    rect.setFill(Color.WHITE);
    root.getChildren().add(rect);

    FadeTransition ft = new FadeTransition(Duration.millis(4000), rect);
    ft.setFromValue(2);
    ft.setToValue(0);
    ft.setAutoReverse(true);
    ft.setCycleCount(1);
    ft.play();
    ft.setOnFinished(e -> addButtons());

    ins = new SubScenes(1);
    root.getChildren().add(ins);
    high = new SubScenes(2);
    root.getChildren().add(high);

    BackgroundImage background = new BackgroundImage(
        new Image("/resources/menu.png", 200, 615, false, true), BackgroundRepeat.REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));
  }

  /**
   * Adds buttons to main menu.
   */
  public void addButtons() {
    MenuButton playBtn = new MenuButton("New Game", 190, 49, 23);
    playBtn.setLayoutX(90);
    playBtn.setLayoutY(160);
    root.getChildren().add(playBtn);
    playBtn.setOnAction(e -> levelSelect());

    MenuButton scoresBtn = new MenuButton("High Scores", 190, 49, 23);
    scoresBtn.setLayoutX(90);
    scoresBtn.setLayoutY(250);
    root.getChildren().add(scoresBtn);
    scoresBtn.setOnAction(e -> {
      if (ins.getVisible())
        ins.moveIn();
      high.moveIn();
    });

    MenuButton helpBtn = new MenuButton("Help", 190, 49, 23);
    helpBtn.setLayoutX(90);
    helpBtn.setLayoutY(340);
    root.getChildren().add(helpBtn);
    helpBtn.setOnAction(e -> {
      if (high.getVisible())
        high.moveIn();
      ins.moveIn();
    });

    MenuButton exitBtn = new MenuButton("Quit", 190, 49, 23);
    exitBtn.setLayoutX(90);
    exitBtn.setLayoutY(430);
    root.getChildren().add(exitBtn);
    exitBtn.setOnAction(e -> Platform.exit());

    // Setting the image view 1
    ImageView logo = new ImageView(new Image("/resources/name1.png", 350, 500, false, true));

    // Setting the position of the image
    logo.setX(370);
    logo.setY(40);

    // setting the fit height and width of the image view
    logo.setFitHeight(100);
    logo.setFitWidth(350);
    logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        logo.setEffect(new DropShadow());
      }
    });

    logo.setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        logo.setEffect(null);
      }
    });
    root.getChildren().add(logo);
  }

  
  /**
   * Adds the menu button into the screens of the levels.
   */
  public void addMenuButton() {
    MenuButton menuBtn = new MenuButton("Main Menu", 125, 30, 15);
    menuBtn.setLayoutX(674);
    menuBtn.setLayoutY(579);
    root.getChildren().add(menuBtn);
    menuBtn.setOnAction(e -> mainMenu());
  }
  
  /**
   * Level selection menu.
   */
  public void levelSelect() {
    collisionCount = 0;
    
    root.getChildren().clear();

    BackgroundImage background = new BackgroundImage(
        new Image("/resources/menubackground.jpg", 200, 615, false, true), BackgroundRepeat.REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));


    MenuButton oneBtn = new MenuButton("Level One", 190, 49, 23);
    oneBtn.setLayoutX(300);
    oneBtn.setLayoutY(120);
    root.getChildren().add(oneBtn);
    oneBtn.setOnAction(e -> intros(1,
        "Objective: Complete the Obstacle Course. \nYou Fail After 5 Collisions.\nPress a Key to Continue."));

    MenuButton twoBtn = new MenuButton("Level Two", 190, 49, 23);
    twoBtn.setLayoutX(300);
    twoBtn.setLayoutY(260);
    root.getChildren().add(twoBtn);
    twoBtn.setOnAction(e -> intros(2,
        "Objective: Complete the Obstacle Course. \nYou Fail After 5 Collisions.\nPress a Key to Continue."));

    MenuButton threeBtn = new MenuButton("Level Three", 190, 49, 23);
    threeBtn.setLayoutX(300);
    threeBtn.setLayoutY(400);
    root.getChildren().add(threeBtn);
    threeBtn.setOnAction(e -> intros(3,
        "Objective: Complete the Obstacle Course. \nYou Fail After 5 Collisions.\nPress a Key to Continue."));
  }

  /**
   * Introduces level.
   * 
   * @param l the level to open
   * @param str message included on introduction to level
   */
  public void intros(int l, String str) {
    root.getChildren().clear();
    Rectangle rect = new Rectangle(-100, -100, 1030, 930);
    rect.setFill(Color.WHITE);
    rect.setOpacity(0.8);
    root.getChildren().add(rect);

    if (l == 1) {
      Image image = new Image("/resources/1.png", 800, 615, false, true);
      BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
          BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
      root.setBackground(new Background(background));
      root.getScene().setOnKeyPressed(e -> {
        levelOne();
      });
    } else if (l == 2) {
      Image image = new Image("/resources/2.png", 800, 615, false, true);
      BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
          BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
      root.setBackground(new Background(background));
      root.getScene().setOnKeyPressed(e -> {
        levelTwo();
      });
    } else {
      Image image = new Image("/resources/3.png", 800, 615, false, true);
      BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
          BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
      root.setBackground(new Background(background));
      root.getScene().setOnKeyPressed(e -> {
        levelThree();
      });
    }

    Timeline timeline = new Timeline();
    Text text = new Text();
    text.setLayoutX(200);
    text.setLayoutY(250);
    text.setFont(Font.font("verdana", 20));
    final IntegerProperty i = new SimpleIntegerProperty(0);
    KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.1), event -> {
      if (i.get() > str.length()) {
        timeline.stop();
      } else {
        text.setText(str.substring(0, i.get()));
        i.set(i.get() + 1);
      }
    });
    timeline.getKeyFrames().add(keyFrame);
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
    root.getChildren().add(text);
  }

  /**
   * Level one.
   */
  public void levelOne() {
    root.getChildren().clear();

    Car car1 = new Car(600, 120, new Image("/resources/car_red_small_5.png"));
    car1.setVelocity(100);

    Car car2 = new Car(220, 120, new Image("/resources/car_red_small_5.png"), 180);
    car2.setVelocity(100);

    cars = new Car[] {car1, car2};
    Wall leftWall = new Wall(0, 0, 415, 615);
    Wall rightWall = new Wall(688, 0, 100, 615);
    obstacles = new Obstacle[] {leftWall, rightWall};

    for (Obstacle o : obstacles) {
      root.getChildren().add((Shape) o);
    }

    for (Car c : cars) {
      root.getChildren().add(c);
    }

    Image image = new Image("/resources/1.png", 0, 1000, true, true);
    BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));

    lastNanoTime = System.nanoTime();
    addCar(new Car(500, 100, new Image("/resources/car_red_small_5.png"), 0), image, 1);
    
    addMenuButton();
  }


  /**
   * Level two.
   */
  public void levelTwo() {
    root.getChildren().clear();

    Image image = new Image("/resources/2.png", 0, 615, false, true);
    BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));

    addCar(new Car(488, 522, new Image("/resources/car_red_small_5.png"), -90), image, 2);

    Wall o1 = new Wall(423, 582, 184, 20, "l"); // lower
    Wall o2 = new Wall(473, 510, 118, 1, "l"); // upper

    // diagonal
    Wall o3 = new Wall(386, 535, 154, 50, "l"); // lower
    Wall o4 = new Wall(382, 454, 110, 1, "l"); // upper
    o3.getTransforms().add(new Rotate(28, o3.getX(), o3.getY()));
    o4.getTransforms().add(new Rotate(32, o4.getX(), o4.getY()));

    Wall o5 = new Wall(134, 559, 260, 51, "l"); // lower
    Wall o6 = new Wall(146, 480, 254, 1, "l"); // upper
    o5.getTransforms().add(new Rotate(355, o5.getX(), o5.getY()));
    o6.getTransforms().add(new Rotate(355, o6.getX(), o6.getY()));

    // straight
    Wall o7 = new Wall(139, 252, 1, 223, "l"); // right
    Wall o8 = new Wall(1, 202, 51, 398, "l"); // leftmost

    // bottom left corner
    Wall o9 = new Wall(16, 460, 31, 160, "l"); // upper
    Wall o10 = new Wall(52, 505, 80, 21, "l"); // middle
    Wall o11 = new Wall(90, 540, 60, 21, "l"); // lower
    o9.getTransforms().add(new Rotate(344, o9.getX(), o9.getY()));
    o10.getTransforms().add(new Rotate(45, o10.getX(), o10.getY()));
    o11.getTransforms().add(new Rotate(20, o11.getX(), o11.getY()));

    // top left corner
    Wall o12 = new Wall(8, 196, 61, 60, "l"); // lower
    Wall o13 = new Wall(50, 210, 102, 1, "l"); // upper
    o12.getTransforms().add(new Rotate(20, o12.getX(), o12.getY()));
    o13.getTransforms().add(new Rotate(332, o13.getX(), o13.getY()));

    // top horizontal
    Wall o14 = new Wall(112, 132, 284, 41, "l"); // upper
    Wall o15 = new Wall(142, 244, 273, 214, "l"); // lower

    // top turns
    Wall o16 = new Wall(10, 70, 388, 100, "l"); // upper
    Wall o17 = new Wall(399, 73, 1, 57, "l"); // upper
    o17.getTransforms().add(new Rotate(225, o17.getX(), o17.getY()));
    Wall o18 = new Wall(339, 3, 400, 31, "l"); // upper
    Wall o19 = new Wall(609, 33, 65, 1, "l"); // upper
    o19.getTransforms().add(new Rotate(22, o19.getX(), o19.getY()));
    Wall o20 = new Wall(666, 54, 45, 1, "l"); // upper
    o20.getTransforms().add(new Rotate(60, o20.getX(), o20.getY()));

    Wall o21 = new Wall(402, 244, 60, 100, "l"); // lower
    o21.getTransforms().add(new Rotate(325, o21.getX(), o21.getY()));
    Wall o22 = new Wall(445, 222, 50, 100, "l"); // lower
    o22.getTransforms().add(new Rotate(290, o22.getX(), o22.getY()));
    Wall o24 = new Wall(463, 97, 125, 410, "l"); // lower

    // finishes inner
    Wall o25 = new Wall(569, 97, 15, 30, "l");
    o25.getTransforms().add(new Rotate(58, o25.getX(), o25.getY()));
    Wall o26 = new Wall(495, 110, 101, 392, "l");
    Wall o27 = new Wall(690, 0, 100, 600, "l");
    Wall o28 = new Wall(690, 517, 30, 53, "l");
    o28.getTransforms().add(new Rotate(31, o28.getX(), o28.getY()));
    Wall o29 = new Wall(662, 560, 30, 68, "l");
    o29.getTransforms().add(new Rotate(71, o29.getX(), o29.getY()));

    obstacles = new Obstacle[] {o1, o2, o3, o4, o5, o6, o7, o8, o9, o10, o11, o12, o13, o14, o15,
        o16, o17, o18, o19, o20, o21, o22, o24, o25, o26, o27, o28, o29};
    for (Obstacle o : obstacles) {
      root.getChildren().add((Shape) o);
    }
    addMenuButton();
   
  }

  /**
   * Level three.
   */
  public void levelThree() {
    root.getChildren().clear();

    Car car1 = new Car(600, 120, new Image("/resources/car_red_small_5.png"));
    car1.setVelocity(100);
    cars = new Car[] {car1};
    Wall topL = new Wall(-1, 0, 240, 109);
    Wall topL2 = new Wall(239, 0, 20, 99);

    Wall topR = new Wall(578, 0, 233, 105);
    Wall topR2 = new Wall(558, 0, 20, 95);

    Wall botL = new Wall(0, 465, 252, 150);
    Wall botL2 = new Wall(252, 475, 15, 140);

    Wall botR = new Wall(578, 465, 233, 150);
    Wall botR2 = new Wall(553, 478, 25, 140);

    obstacles = new Obstacle[] {topL, topL2, topR, topR2, botL, botL2, botR, botR2};

    for (Obstacle o : obstacles) {
      root.getChildren().add((Shape) o);
    }
    for (Car c : cars) {
      root.getChildren().add(c);
    }

    Image image = new Image("/resources/3.png", 800, 615, false, true);
    BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));

    lastNanoTime = System.nanoTime();
    addCar(new Car(488, 535, new Image("/resources/car_red_small_5.png")), image, 3);
    
    addMenuButton();
  }

  /**
   * Adds car to stage.
   * 
   * @param car the car to add
   * @param image the background image
   */
  public void addCar(Car car, Image image, int level) {
    // TODO camera that moves with scene (read this:
    // https://stackoverflow.com/questions/47879463/2d-camera-in-javafx)
    root.getChildren().add(car);
    ArrayList<String> input = new ArrayList<String>();
    animationTimer = new AnimationTimer() {
      @Override
      public void handle(long currentNanoTime) {
        double t = (currentNanoTime - lastNanoTime) / 1_000_000_000.0;
        lastNanoTime = currentNanoTime;
        Bounds oldBounds = car.center.localToScene(car.center.getBoundsInLocal());
        car.move(t);
        Bounds centerBounds = car.center.localToScene(car.center.getBoundsInLocal());
        Background oldBackground = root.getBackground();
        if (input.contains("W") || input.contains("UP")) {
          car.accelerate();
        }
        if (input.contains("A") || input.contains("LEFT")) {
          car.steerLeft();
        }
        if (input.contains("S") || input.contains("DOWN")) {
          car.reverse();
        }
        if (input.contains("D") || input.contains("RIGHT")) {
          car.steerRight();
        }
        for (Obstacle o : obstacles) {
          if (((Path) Shape.intersect(car, (Shape) o)).getElements().size() > 0) { // if crashed
            car.move(-t);
            car.setVelocity(-car.getVelocity());

            System.out.println(++collisionCount);
            if(level == 2 && collisionCount >= 10) {
              levelEnd(false, 2);
            }
          }
        }
        for (Car c : cars) {
          if (((Path) Shape.intersect(car, (Shape) c)).getElements().size() > 0) { // if crashed
            car.move(-t);
            car.setVelocity(-car.getVelocity());
          }
          c.move(t, 0);
        }
        Bounds bounds = car.center.localToScene(car.center.getBoundsInLocal());
        // updateBackground(car, t, bounds, oldBounds, image,
        // oldBackground.getImages().get(0).getPosition());
      }
    };
    animationTimer.start();

    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        String key = e.getCode().toString();
        if (!input.contains(key)) {
          input.add(key);
        }
      }
    });

    scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        String key = e.getCode().toString();
        input.remove(key);
      }
    });
  }

  public double clamp(double n, double min, double max) {
    if (n < min) {
      return min;
    }
    if (n > max) {
      return max;
    }
    return n;
  }

  /**
   * This method is a work-in-progress, ignore this.
   */
  public void updateBackground(Car car, double t, Bounds bounds, Bounds oldBounds, Image image,
      BackgroundPosition old) {
    // root.getBackground().
    double x = 0;
    double y = 0;
    double offsetX = 0;
    double offsetY = 0;

    // maybe make it move based on car's direction and velocity using sin and cosine
    // disregard this ^^
    // I have to make the background move if the car crosses the middle and stop when it reaches
    // this vv

    car.relocate(root.getWidth() / 2, root.getHeight() / 2);

    if (x > 0) { // past left edge of background
      offsetX = -x;
      x = 0;
    }
    double rightEdgeLimit = -image.getWidth() + root.getWidth();
    if (x < rightEdgeLimit) { // past right edge of background
      offsetX = -x;
      x = rightEdgeLimit;
    }
    if (y > 0) { // past left edge of background
      offsetY = -y;
      y = 0;
    }
    double bottomEdgeLimit = -image.getHeight() + root.getHeight();
    if (y < bottomEdgeLimit) { // past right edge of background
      offsetY = -y;
      y = bottomEdgeLimit;
    }

    if (offsetX != 0) {
      System.out.println(x + " " + y);
      System.out.println(offsetX);
    }
    if (offsetY != 0) {
      System.out.println(x + " " + y);
      System.out.println(offsetY);
    }

    car.translate(offsetX, offsetY);

    // if (oldBounds.getMaxX() <= root.getWidth() / 2 && bounds.getMaxX() > root.getWidth() / 2 ||
    // oldBounds.getMaxX() >= root.getWidth() / 2 && bounds.getMaxX() < root.getWidth() / 2 ||
    // oldBounds.getMaxY() <= root.getHeight() / 2 && bounds.getMaxY() > root.getHeight() / 2 ||
    // oldBounds.getMaxY() >= root.getHeight() / 2 && bounds.getMaxY() < root.getHeight() / 2 ) {
    //// if (old.getHorizontalPosition() > -image.getWidth() + root.getWidth()
    //// && old.getHorizontalPosition() < 0) {
    // x = old.getHorizontalPosition() + oldBounds.getMaxX() - bounds.getMaxX();
    // car.translate(oldBounds.getMaxX() - bounds.getMaxX(), 0);
    //// }
    //// if (old.getVerticalPosition() > -image.getHeight() + root.getHeight()
    //// && old.getVerticalPosition() < 0) {
    // y = old.getVerticalPosition() + oldBounds.getMaxY() - bounds.getMaxY();
    // car.translate(0, oldBounds.getMaxY() - bounds.getMaxY());
    // x = clamp(x, 0, -image.getWidth() + root.getWidth());
    // y = clamp(y, 0, -image.getHeight() + root.getHeight());
    //// }
    // }
    // // if (old.getHorizontalPosition() >= image.getWidth() - root.getWidth()) {
    // //
    // // } else if (old.getHorizontalPosition() <= 0) {
    // //
    // // }
    // // if ((x = clampRange(bounds.getMaxX() - root.getWidth() / 2, 0, image.getWidth() -
    // // root.getWidth())) != 0
    // // || (y = clampRange(bounds.getMaxY() - root.getHeight() / 2, 0,
    // // image.getHeight() - root.getHeight())) != 0) {
    // // moved = false;
    // // System.out.println("moved");
    // // }
    // // if ((x = clampRange(bounds.getMaxX() - root.getWidth() / 2, 0, image.getWidth() -
    // // root.getWidth())) != 0
    // // || (y = clampRange(bounds.getMaxY() - root.getHeight() / 2, 0,
    // // image.getHeight() - root.getHeight())) != 0) {
    // // moved = false;
    // // }
    //
    // // System.out.println(x + " " + y);
    // // x -= root.getWidth();
    // // y -= root.getHeight() / 2;
    BackgroundPosition bp = new BackgroundPosition(Side.LEFT, x, false, Side.TOP, y, false);
    BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, bp, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));
  }

  
  /**
   * Outputs the screen corresponding to whether the user has passed the level or not.
   * @param hasWon stores whether the user passed the level.
   * @param level stores the level at which the user passed or failed.
   */
  public void levelEnd(boolean hasPassed, int level) {
    root.getChildren().clear();
    if(!hasPassed) {
      Rectangle rect = new Rectangle(-100, -100, 1030, 930);
      rect.setFill(Color.RED);
      root.getChildren().add(rect);
      
      MenuButton tryBtn = new MenuButton("Try Again", 190, 49, 23);
      tryBtn.setLayoutX(310);
      tryBtn.setLayoutY(420);
      root.getChildren().add(tryBtn);
      tryBtn.setOnAction(e -> intros(level, "s"));
    } else {
      Rectangle rect = new Rectangle(-100, -100, 1030, 930);
      rect.setFill(Color.BLUE);
      root.getChildren().add(rect);
    }
    MenuButton menuBtn = new MenuButton("Main Menu", 190, 49, 23);
    menuBtn.setLayoutX(310);
    menuBtn.setLayoutY(500);
    root.getChildren().add(menuBtn);
    menuBtn.setOnAction(e -> mainMenu());
  }
  
  /**
   * Driver method.
   * 
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
