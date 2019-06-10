import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
 * The DrivingAce class is the main class that has all of the output and screens of Driving Ace, a
 * game that teaches users how to drive safer on the road. It has a menu and three levels. In level
 * one, the driver lands in an inevitable crash that teaches a lesson about driving. In level two,
 * the driver completes an obstacle course to learn how to drive better. In level three, the driver
 * drives in a neighbourhood to learn to drive safely in a real-world situation.
 * 
 * @author Nicholas Glenn and Jerry Zhu
 * @version 4
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
  private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

  /**
   * Array of cars in a level.
   */
  private ArrayList<Car> cars = new ArrayList<Car>();

  /**
   * AnimationTimer in a level.
   */
  private AnimationTimer animationTimer;

  /**
   * Stores the number of times the car collides with the walls.
   */
  private int collisionCount;

  /**
   * Stores the intro text for level 1.
   */
  private String intro1;

  /**
   * Stores the intro text for level 2.
   */
  private String intro2;

  /**
   * Stores the intro text for level 3.
   */
  private String intro3;

  /**
   * Stores the name of the user.
   */
  private String name;

  /**
   * Starts the program.
   */
  @Override
  public void start(Stage primaryStage) {
    intro1 =
        "Objective: Navigate the highway and drive to the end\nof the road.\nYou fail if you crash into another car.\n\nPress a Key to Continue...";
    intro2 =
        "Objective: Complete the Obstacle Course.\nYou Fail After 10 Collisions.\n\nPress a Key to Continue...";
    intro3 =
        "Objective: Drive through a neighbourhood using the \nnew driving skills you've learned throughout the game.\n\nPress a Key to Continue...";

    primaryStage.setResizable(false);
    root = new AnchorPane();
    root.setMinSize(800, 600);
    root.setMaxSize(800, 600);
    scene = new Scene(root);

    primaryStage.setTitle("Driving Ace");
    primaryStage.setScene(scene);
    intro();
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
   * The main menu screen of the game.
   */
  public void mainMenu() {
    collisionCount = 0;
    root.getChildren().clear();
    if (animationTimer != null) {
      animationTimer.stop();
    }
    Rectangle rect = new Rectangle(-100, -100, 1030, 930);
    rect.setFill(Color.WHITE);
    root.getChildren().add(rect);

    FadeTransition ft = new FadeTransition(Duration.millis(2000), rect);
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
    exitBtn.setCancelButton(true);
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
    logo.setOnMouseEntered(e -> logo.setEffect(new DropShadow()));

    logo.setOnMouseExited(e -> logo.setEffect(null));
    root.getChildren().add(logo);
  }


  /**
   * Adds the menu button into the screens of the levels.
   */
  public void addMenuButton() {
    MenuButton menuBtn = new MenuButton("Main Menu", 125, 30, 15);
    menuBtn.setDefaultButton(false);
    menuBtn.setCancelButton(true);
    menuBtn.setLayoutX(674);
    menuBtn.setLayoutY(579);
    menuBtn.addEventFilter(KeyEvent.KEY_PRESSED, k -> {
      if (k.getCode().toString().equals("SPACE")) {
        k.consume();
      }
    });
    root.getChildren().add(menuBtn);
    menuBtn.setOnAction(e -> mainMenu());
  }

  /**
   * Level selection menu.
   */
  public void levelSelect() {
    Image image = new Image("/resources/menubackground.jpg", 200, 615, false, true);
    resetLevel(image, true);

    MenuButton oneBtn = new MenuButton("Level One", 190, 49, 23);
    oneBtn.setLayoutX(300);
    oneBtn.setLayoutY(120);
    root.getChildren().add(oneBtn);
    oneBtn.setOnAction(e -> intros(1, intro1));

    MenuButton twoBtn = new MenuButton("Level Two", 190, 49, 23);
    twoBtn.setLayoutX(300);
    twoBtn.setLayoutY(260);
    root.getChildren().add(twoBtn);
    twoBtn.setOnAction(e -> intros(2, intro2));

    MenuButton threeBtn = new MenuButton("Level Three", 190, 49, 23);
    threeBtn.setLayoutX(300);
    threeBtn.setLayoutY(400);
    root.getChildren().add(threeBtn);
    threeBtn.setOnAction(e -> intros(3, intro3));
  }

  /**
   * The introduction screen for each level that contains the instructions.
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
      Image image = new Image("/resources/1.png", 0, 0, false, true);
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
      Image image = new Image("/resources/3.png", 0, 800, false, true);
      BackgroundPosition bp = new BackgroundPosition(Side.LEFT, -400, false, Side.TOP, -200, false);
      BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
          BackgroundRepeat.NO_REPEAT, bp, BackgroundSize.DEFAULT);
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
    KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.05), event -> {
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
   * Resets the program after a level is finished.
   * 
   * @param image stores the new background image.
   */
  public void resetLevel(Image image) {
    root.getChildren().clear();
    obstacles = new ArrayList<Obstacle>();
    cars = new ArrayList<Car>();
    collisionCount = 0;

    BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));
  }

  /**
   * Resets the program after a level is finished.
   * 
   * @param image stores the new background image.
   * @param repeat stores whether the user repeats the level.
   */
  public void resetLevel(Image image, boolean repeat) {
    root.getChildren().clear();
    obstacles = new ArrayList<Obstacle>();
    cars = new ArrayList<Car>();
    collisionCount = 0;

    if (repeat) {
      BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.REPEAT,
          BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
      root.setBackground(new Background(background));
    } else {
      BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
          BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
      root.setBackground(new Background(background));
    }
  }

  /**
   * Level one of the game.
   */
  public void levelOne() {
    Image image = new Image("/resources/1.png", 0, 0, false, true);
    resetLevel(image);

    cars.add(new Car(190, 420, new Image("/resources/car_yellow_small_3.png"), 180));
    cars.add(new Car(190, 120, new Image("/resources/car_black_small_1.png"), 180));
    cars.add(new Car(250, 10, new Image("/resources/car_green_small_2.png"), 180));
    cars.add(new Car(250, 500, new Image("/resources/car_black_small_1.png"), 180));
    cars.add(new Car(310, 320, new Image("/resources/car_yellow_small_3.png"), 180));
    cars.add(new Car(310, 720, new Image("/resources/car_black_small_1.png"), 180));
    cars.add(new Car(370, 100, new Image("/resources/car_green_small_2.png"), 180));
    cars.add(new Car(370, 600, new Image("/resources/car_blue_small_4.png"), 180));

    cars.add(new Car(440, 220, new Image("/resources/car_black_small_1.png")));
    cars.add(new Car(510, 20, new Image("/resources/car_blue_small_4.png")));
    cars.add(new Car(570, 380, new Image("/resources/car_green_small_2.png")));
    cars.add(new Car(630, 270, new Image("/resources/car_yellow_small_3.png")));


    Wall leftWall = new Wall(0, 0, 137, 3900);
    obstacles.add(leftWall);
    Wall rightWall = new Wall(700, 0, 100, 3900);
    obstacles.add(rightWall);

    for (Obstacle o : obstacles) {
      root.getChildren().add((Shape) o);
    }
    for (Car c : cars) {
      c.setVelocity(150);
      root.getChildren().add(c);
    }

    addCar(new Car(150, 100, new Image("/resources/car_red_small_5.png"), 180), image, 1);
    addMenuButton();
  }


  /**
   * Level two of the game.
   */
  public void levelTwo() {
    Image image = new Image("/resources/2.png", 800, 615, false, true);
    resetLevel(image);
    addCar(new Car(488, 522, new Image("/resources/car_red_small_5.png"), -90), image, 2);

    Wall o1 = new Wall(423, 582, 184, 20); // lower
    obstacles.add(o1);
    Wall o2 = new Wall(473, 510, 118, 1); // upper
    obstacles.add(o2);

    // diagonal
    Wall o3 = new Wall(386, 535, 154, 50); // lower
    o3.getTransforms().add(new Rotate(28, o3.getX(), o3.getY()));
    obstacles.add(o3);
    Wall o4 = new Wall(382, 454, 110, 1); // upper
    o4.getTransforms().add(new Rotate(32, o4.getX(), o4.getY()));
    obstacles.add(o4);

    Wall o5 = new Wall(134, 559, 260, 51); // lower
    o5.getTransforms().add(new Rotate(355, o5.getX(), o5.getY()));
    obstacles.add(o5);
    Wall o6 = new Wall(146, 480, 254, 1); // upper
    o6.getTransforms().add(new Rotate(355, o6.getX(), o6.getY()));
    obstacles.add(o6);

    // straight
    Wall o7 = new Wall(139, 252, 1, 223); // right
    obstacles.add(o7);
    Wall o8 = new Wall(1, 202, 51, 398); // leftmost
    obstacles.add(o8);

    // bottom left corner
    Wall o9 = new Wall(16, 460, 31, 160); // upper
    o9.getTransforms().add(new Rotate(344, o9.getX(), o9.getY()));
    obstacles.add(o9);
    Wall o10 = new Wall(52, 505, 80, 21); // middle
    o10.getTransforms().add(new Rotate(45, o10.getX(), o10.getY()));
    obstacles.add(o10);
    Wall o11 = new Wall(90, 540, 60, 21); // lower
    o11.getTransforms().add(new Rotate(20, o11.getX(), o11.getY()));
    obstacles.add(o11);

    // top left corner
    Wall o12 = new Wall(8, 196, 61, 60); // lower
    o12.getTransforms().add(new Rotate(20, o12.getX(), o12.getY()));
    obstacles.add(o12);
    Wall o13 = new Wall(50, 210, 102, 1); // upper
    o13.getTransforms().add(new Rotate(332, o13.getX(), o13.getY()));
    obstacles.add(o13);

    // top horizontal
    Wall o14 = new Wall(112, 132, 284, 41); // upper
    obstacles.add(o14);
    Wall o15 = new Wall(142, 244, 273, 214); // lower
    obstacles.add(o15);

    // top turns
    Wall o16 = new Wall(10, 70, 388, 100); // upper
    obstacles.add(o16);
    Wall o17 = new Wall(399, 73, 1, 57); // upper
    o17.getTransforms().add(new Rotate(225, o17.getX(), o17.getY()));
    obstacles.add(o17);
    Wall o18 = new Wall(339, 3, 400, 31); // upper
    obstacles.add(o18);
    Wall o19 = new Wall(609, 33, 65, 1); // upper
    o19.getTransforms().add(new Rotate(22, o19.getX(), o19.getY()));
    obstacles.add(o19);
    Wall o20 = new Wall(666, 54, 45, 1); // upper
    o20.getTransforms().add(new Rotate(60, o20.getX(), o20.getY()));
    obstacles.add(o20);

    Wall o21 = new Wall(402, 244, 60, 100); // lower
    o21.getTransforms().add(new Rotate(325, o21.getX(), o21.getY()));
    obstacles.add(o21);
    Wall o22 = new Wall(445, 222, 50, 100); // lower
    o22.getTransforms().add(new Rotate(290, o22.getX(), o22.getY()));
    obstacles.add(o22);
    Wall o24 = new Wall(463, 97, 125, 410); // lower
    obstacles.add(o24);

    // finishes inner
    Wall o25 = new Wall(569, 97, 15, 30);
    o25.getTransforms().add(new Rotate(58, o25.getX(), o25.getY()));
    obstacles.add(o25);
    Wall o26 = new Wall(495, 110, 101, 392);
    obstacles.add(o26);
    Wall o27 = new Wall(690, 0, 100, 600);
    obstacles.add(o27);
    Wall o28 = new Wall(690, 517, 30, 53);
    o28.getTransforms().add(new Rotate(31, o28.getX(), o28.getY()));
    obstacles.add(o28);
    Wall o29 = new Wall(662, 560, 30, 68);
    o29.getTransforms().add(new Rotate(71, o29.getX(), o29.getY()));
    obstacles.add(o29);

    // start line
    Wall o30 = new Wall(525, 510, 1, 80);
    obstacles.add(o30);
    Wall o31 = new Wall(542, 510, 1, 80);
    obstacles.add(o31);

    for (Obstacle o : obstacles) {
      root.getChildren().add((Shape) o);
    }
    addMenuButton();
    Label l = new Label("Collisions Left");

    l.setFont(Font.font("Open Sans", 24));
    // setting the position of the text
    l.setLayoutX(13);
    l.setLayoutY(6);
    root.getChildren().add(l);
  }

  /**
   * Level three of the game.
   */
  public void levelThree() {
    Image image = new Image("/resources/3.png", 0, 800, true, false);
    resetLevel(image);

    BackgroundPosition bp = new BackgroundPosition(Side.LEFT, -400, false, Side.TOP, -200, false);
    BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, bp, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));

    cars.add(new Car(-40, 100, new Image("/resources/car_black_small_1.png"), 180));
    cars.add(new Car(-40, 340, new Image("/resources/car_blue_small_4.png"), 180));
    cars.add(new Car(40, 220, new Image("/resources/car_green_small_2.png"), 180));
    cars.add(new Car(40, 380, new Image("/resources/car_yellow_small_3.png"), 180));
    cars.add(new Car(120, 120, new Image("/resources/car_yellow_small_3.png")));
    cars.add(new Car(120, 200, new Image("/resources/car_green_small_2.png")));
    cars.add(new Car(120, 360, new Image("/resources/car_blue_small_4.png")));

    cars.add(new Car(1160, 100, new Image("/resources/car_black_small_1.png"), 180));
    cars.add(new Car(1160, 340, new Image("/resources/car_blue_small_4.png"), 180));
    cars.add(new Car(1240, 220, new Image("/resources/car_green_small_2.png"), 180));
    cars.add(new Car(1240, 380, new Image("/resources/car_yellow_small_3.png"), 180));
    cars.add(new Car(1320, 120, new Image("/resources/car_green_small_2.png")));
    cars.add(new Car(1320, 200, new Image("/resources/car_blue_small_4.png")));
    cars.add(new Car(1320, 360, new Image("/resources/car_yellow_small_3.png")));

    cars.add(new Car(70, 50, new Image("/resources/car_yellow_small_3.png"), -90));
    cars.add(new Car(180, 140, new Image("/resources/car_blue_small_4.png"), -90));
    cars.add(new Car(500, 296, new Image("/resources/car_black_small_1.png"), 90));
    cars.add(new Car(340, 380, new Image("/resources/car_yellow_small_3.png"), 90));

    Wall topL = new Wall(-400, -200, 326, 240);
    obstacles.add(topL);
    Wall botL = new Wall(-400, 436, 326, 240);
    obstacles.add(botL);

    Wall topM = new Wall(248, -200, 890, 240);
    obstacles.add(topM);
    Wall botM = new Wall(248, 436, 890, 240);
    obstacles.add(botM);

    Wall topR = new Wall(1456, -200, 450, 240);
    obstacles.add(topR);
    Wall botR = new Wall(1456, 436, 450, 240);
    obstacles.add(botR);

    Wall left = new Wall(-399, 40, 485, 500);
    obstacles.add(left);

    Wall right = new Wall(1460, 35, 2, 510); // rightmost
    obstacles.add(right);

    Wall leftBot = new Wall(100, 600, 380, 2);
    obstacles.add(leftBot);
    Wall bot = new Wall(-75, 465, 160, 240);
    obstacles.add(bot);
    Wall rightBot = new Wall(1100, 465, 380, 240);
    obstacles.add(rightBot);

    Wall top = new Wall(-75, -200, 325, 240);
    obstacles.add(top);

    Wall mid = new Wall(85, 40, 500, 240);
    obstacles.add(mid);
    Wall mid2 = new Wall(586, 0, 180, 240);
    obstacles.add(mid2);
    Wall mid3 = new Wall(767, -40, 350, 240);
    obstacles.add(mid3);

    Wall beside = new Wall(1100, -200, 198, 216);
    obstacles.add(beside);

    Wall goal = new Wall(1300, -200, 500, 100);
    obstacles.add(goal);


    for (Obstacle o : obstacles) {
      ((Rectangle) o).setArcWidth(60);
      ((Rectangle) o).setArcHeight(60);
      root.getChildren().add((Shape) o);
    }
    for (Car c : cars) {
      c.setVelocity(100);
      root.getChildren().add(c);
    }

    addCar(new Car(200, 550, new Image("/resources/car_red_small_5.png")), image, 3);
    addMenuButton();
  }

  /**
   * Adds a user-controlled car to the screen.
   * 
   * @param car the car to add
   * @param image the background image
   * @param level the level the car will be added to
   */
  public void addCar(Car car, Image image, int level) {
    root.getChildren().add(car);
    ArrayList<String> input = new ArrayList<String>();
    lastNanoTime = System.nanoTime();
    animationTimer = new AnimationTimer() {
      boolean passed = false;

      @Override
      public void handle(long currentNanoTime) {
        double t = (currentNanoTime - lastNanoTime) / 1_000_000_000.0;
        lastNanoTime = currentNanoTime;
        car.move(t);
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
        if (input.contains("SPACE")) {
          car.brake();
        }
        if (level == 1
            && root.getBackground().getImages().get(0).getPosition().getVerticalPosition() < -3000
            && !passed) { // whoa very long if statement
          passed = true;
          int oldSize = cars.size();
          cars.add(new Car(170, 600, new Image("/resources/car_black_small_1.png")));
          cars.add(new Car(230, 600, new Image("/resources/car_black_small_1.png")));
          cars.add(new Car(290, 600, new Image("/resources/car_black_small_1.png")));
          cars.add(new Car(350, 600, new Image("/resources/car_black_small_1.png")));
          cars.add(new Car(420, 600, new Image("/resources/car_black_small_1.png")));
          cars.add(new Car(490, 600, new Image("/resources/car_black_small_1.png")));
          cars.add(new Car(550, 600, new Image("/resources/car_black_small_1.png")));
          cars.add(new Car(610, 600, new Image("/resources/car_black_small_1.png")));
          for (int i = oldSize; i < cars.size(); i++) {
            cars.get(i).setVelocity(200);
            root.getChildren().add(cars.get(i));
          }
        }
        if (collisionCount == 0 && level == 2) {
          Label f = new Label("10");
          f.setLayoutX(64);
          f.setLayoutY(45);
          f.setFont(Font.font("Open Sans", 32));
          root.getChildren().add(f);
        }
        if (level == 2 && collisionCount < 10
            && ((Path) Shape.intersect(car, (Shape) obstacles.get(obstacles.size() - 1)))
                .getElements().size() > 0) {
          passed = true;
          levelEnd(true, 2);
          this.stop();
        }
        if (level == 3 && ((Path) Shape.intersect(car, (Shape) obstacles.get(obstacles.size() - 1)))
            .getElements().size() > 0) {
          levelEnd(true, 3);
          this.stop();
        }
        for (Obstacle o : obstacles) {
          if (((Path) Shape.intersect(car, (Shape) o)).getElements().size() > 0) { // if
                                                                                   // crashed
            car.move(-t);
            car.setVelocity(-car.getVelocity());

            if (level == 2 && !passed) {
              Rectangle rect = new Rectangle(45, 50, 60, 40);
              rect.setFill(Color.WHITE);
              root.getChildren().add(rect);
              Label l = new Label(String.valueOf(10 - (++collisionCount)));
              l.setLayoutX(74);
              l.setLayoutY(43);
              l.setFont(Font.font("Open Sans", 35));
              root.getChildren().add(l);
              if (collisionCount >= 10) {
                levelEnd(false, 2);
                collisionCount = 0;
                this.stop();
              }
            }
          }
        }
        for (Car c : cars) {
          if (((Path) Shape.intersect(car, (Shape) c)).getElements().size() > 0) { // if crashed
            if (level == 1) {
              levelEnd(passed, 1);
              this.stop();
            } else {
              levelEnd(false, 3);
              this.stop();
            }
          }
          c.move(t, 0);
          Bounds bounds = c.localToScene(c.getBoundsInLocal());
          if ((c.getDirection() == 90 && bounds.getMinX() > root.getWidth() + 20
              || c.getDirection() == -90 && bounds.getMaxX() < -20)) {
            c.setY(c.getY() + root.getWidth() + c.getWidth() + 20);
            c.center.setY(c.center.getY() + root.getWidth() + c.getWidth() + 20);
          }
          if ((c.getDirection() == 0 && bounds.getMaxY() < -20
              || c.getDirection() == 180 && bounds.getMinY() > root.getHeight() + 20)) {
            c.setY(c.getY() + root.getHeight() + c.getHeight() + 20);
            c.center.setY(c.center.getY() + root.getHeight() + c.getHeight() + 20);
          }
        }
        if (level != 2) {
          updateBackground(car, t);
        }
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

  /**
   * Moves background depending on where the car is in location to the screen.
   * 
   * @param car the car the player controls
   * @param t time elapsed
   */
  public void updateBackground(Car car, double t) {
    BackgroundPosition oldBackgroundPosition =
        root.getBackground().getImages().get(0).getPosition();
    Image image = root.getBackground().getImages().get(0).getImage();

    Bounds bounds = car.center.localToScene(car.center.getBoundsInLocal()); // Taken from
                                                                            // https://stackoverflow.com/questions/31148690/get-real-position-of-a-node-in-javafx
    double x = oldBackgroundPosition.getHorizontalPosition() - car.predictMoveX(t);
    double y = oldBackgroundPosition.getVerticalPosition() - car.predictMoveY(t);
    double offsetX = 0;
    double offsetY = 0;
    double rightEdgeLimit = -image.getWidth() + root.getWidth();
    double bottomEdgeLimit = -image.getHeight() + root.getHeight();
    double margin = 100;

    if (x > 0 || (x < -root.getWidth() / 2 && bounds.getMaxX() < margin)) { // past left edge of
                                                                            // background
      offsetX = -x;
      x = 0;
    } else if (x < rightEdgeLimit || (x > rightEdgeLimit + root.getWidth() / 2
        && bounds.getMaxX() > root.getWidth() - margin)) { // past right edge
      offsetX = -x + rightEdgeLimit;
      x = rightEdgeLimit;
    }
    if (y > 0 || (y > -root.getHeight() / 2 && bounds.getMaxY() < margin)) { // past top edge of
                                                                             // background
      offsetY = -y;
      y = 0;
    } else if (y < bottomEdgeLimit || (y < bottomEdgeLimit + root.getHeight() / 2
        && bounds.getMaxY() > root.getHeight() - margin)) { // past bottom
      // edge of
      // background
      offsetY = -y + bottomEdgeLimit;
      y = bottomEdgeLimit;
    }
    car.translate(offsetX, offsetY);
    car.move(-t, 0);
    for (Car c : cars) {
      c.translate(-car.predictMoveX(t) + offsetX, -car.predictMoveY(t) + offsetY);
    }
    for (Obstacle o : obstacles) {
      ((Shape) o).setLayoutX(((Shape) o).getLayoutX() - car.predictMoveX(t) + offsetX);
      ((Shape) o).setLayoutY(((Shape) o).getLayoutY() - car.predictMoveY(t) + offsetY);
    }
    BackgroundPosition bp = new BackgroundPosition(Side.LEFT, x, false, Side.TOP, y, false);
    BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, bp, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));
  }


  /**
   * Outputs the screen corresponding to whether the user has passed the level or not.
   * 
   * @param hasPassed stores whether the user passed the level.
   * @param level stores the level at which the user passed or failed.
   */
  public void levelEnd(boolean hasPassed, int level) {
    MenuButton menuBtn = new MenuButton("Main Menu", 190, 49, 23);
    menuBtn.setLayoutX(310);
    menuBtn.setLayoutY(500);
    menuBtn.setOnAction(e -> mainMenu());

    root.getChildren().clear();
    if (!hasPassed) {
      Rectangle rect = new Rectangle(-100, -100, 1030, 930);
      rect.setFill(Color.RED);
      root.getChildren().add(rect);
      // Setting the image view 1
      ImageView i = new ImageView(new Image("/resources/YOU LOSE.png", 350, 500, false, true));

      // Setting the position of the image
      i.setX(180);
      i.setY(200);

      // setting the fit height and width of the image view
      i.setFitHeight(100);
      i.setFitWidth(480);

      i.setOnMouseEntered(e -> i.setEffect(new DropShadow()));
      i.setOnMouseExited(e -> i.setEffect(null));

      root.getChildren().add(i);

      MenuButton tryBtn = new MenuButton("Try Again", 190, 49, 23);
      tryBtn.setLayoutX(310);
      tryBtn.setLayoutY(420);
      root.getChildren().add(tryBtn);
      tryBtn.setOnAction(e -> {
        if (level == 1)
          intros(level, intro1);
        else if (level == 2)
          intros(level, intro2);
        else
          intros(level, intro3);
      });
      root.getChildren().add(menuBtn);
    } else {
      Rectangle rect = new Rectangle(-100, -100, 1030, 930);
      rect.setFill(Color.BLUE);
      root.getChildren().add(rect);

      // Setting the image view 1
      ImageView i = new ImageView(new Image("/resources/YOU WIN.png", 350, 500, false, true));

      // Setting the position of the image
      i.setX(190);
      i.setY(200);

      // setting the fit height and width of the image view
      i.setFitHeight(100);
      i.setFitWidth(450);

      i.setOnMouseEntered(e -> i.setEffect(new DropShadow()));
      i.setOnMouseExited(e -> i.setEffect(null));

      root.getChildren().add(i);

      if (level == 1) {
        i.setY(50);
        Label a = new Label("What happened at the end was that numerous cars crashed into you\n"
            + "and both of you died. Incidentally, those drivers were young drivers\n"
            + "just like you who suffer from road rage and ODD. You did a great job\n"
            + "following the rules but they didn't. They saw fit to disobey the rules. It\n"
            + "demonstrates that unsound driving is extremely dangerous for both you\n"
            + "and those around you, making it especially important for you to be a\n"
            + "cautious driver and to follow the rules.");
        a.setLayoutX(120);
        a.setLayoutY(180);
        a.setFont(Font.font("open sans", 18));
        a.setTextFill(Color.web("#FFFFFF"));
        root.getChildren().add(a);
        MenuButton nextBtn = new MenuButton("Next Level", 190, 49, 23);
        nextBtn.setLayoutX(310);
        nextBtn.setLayoutY(420);
        root.getChildren().add(nextBtn);
        nextBtn.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            if (level + 1 == 2)
              intros(level + 1, intro2);
            else
              intros(level + 1, intro3);
          }
        });
      } else if (level == 2) {
        Label a = new Label("Username: ");
        a.setLayoutX(130);
        a.setLayoutY(475);
        a.setFont(Font.font("open sans", 25));
        a.setTextFill(Color.web("#FFFFFF"));
        root.getChildren().add(a);

        Label b = new Label("Your Score is : " + (10 - collisionCount) * 100);
        b.setLayoutX(130);
        b.setLayoutY(420);
        b.setFont(Font.font("open sans", 25));
        b.setTextFill(Color.web("#FFFFFF"));
        root.getChildren().add(b);

        TextField t = new TextField();
        t.setText(null);
        t.setLayoutX(260);
        t.setLayoutY(470);
        t.setMaxHeight(50);
        t.setMinHeight(50);
        t.setMaxWidth(250);
        t.setMinWidth(250);
        t.setFont(Font.font("open sans", 20));
        root.getChildren().add(t);

        t.textProperty().addListener((observable, oldValue, newValue) -> {
          if (newValue.length() > 0) {
            char c = newValue.charAt(newValue.length() - 1);
            if (!(c >= 65 && c <= 90) && !(c >= 97 && c <= 122)) {
              ((StringProperty) observable).setValue(oldValue);
            } else if (newValue.length() > 8) {
              ((StringProperty) observable).setValue(oldValue);
            } else {
              ((StringProperty) observable).setValue(newValue);
            }
          }
        });

        MenuButton m = new MenuButton("Submit", 190, 49, 23);
        m.setLayoutX(550);
        m.setLayoutY(470);
        m.setOnAction(e -> {
          name = t.getText();

          try {
            new File(System.getProperty("user.home") + "/DrivingAce/").mkdirs();
            File file = new File(System.getProperty("user.home") + "/DrivingAce/highscores.txt");
//            file.createNewFile();
            PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            output.println(name);
            output.println((10 - collisionCount) * 100);
            output.close();
          } catch (IOException n) {
            n.printStackTrace();
          }

          root.getChildren().remove(m);
          root.getChildren().remove(a);
          root.getChildren().remove(b);
          root.getChildren().remove(t);
          root.getChildren().add(menuBtn);
          Label c = new Label("Your Score has been Recorded.");
          c.setLayoutX(250);
          c.setLayoutY(350);
          c.setFont(Font.font("open sans", 25));
          c.setTextFill(Color.web("#FFFFFF"));
          root.getChildren().add(c);

          MenuButton nextBtn = new MenuButton("Next Level", 190, 49, 23);
          nextBtn.setLayoutX(310);
          nextBtn.setLayoutY(420);
          root.getChildren().add(nextBtn);

          nextBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              if (level + 1 == 2)
                intros(level + 1, intro2);
              else
                intros(level + 1, intro3);
            }
          });
        });
        root.getChildren().add(m);
      }
      if (level != 2) {
        root.getChildren().add(menuBtn);
      }
    }
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
