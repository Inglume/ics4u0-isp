import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
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
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * The DrivingAce class.
 * 
 * @author Nicholas Glenn and Jerry Zhu
 * @version 1
 */
public class DrivingAce extends Application {

  /**
   * The Pane of the Game.
   */
  private AnchorPane root;


  /**
   * The start time as a part of the game loop.
   */
  private long startNanoTime;

  /**
   * The scene for the output. It will be put into the the Pane.
   */
  private Scene scene;


  /**
   * An instance of the Instructions class.
   */
  private Instructions ins;
  private Obstacle[] obstacles;
  private Car[] cars;
  private AnimationTimer animationTimer;

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
    //levelOne();
     levelTwo();
    // mainMenu();
    primaryStage.show();
  }


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

    ins = new Instructions();
    root.getChildren().add(ins);

    BackgroundImage background = new BackgroundImage(
        new Image("/resources/menubackground.jpg", 200, 615, false, true), BackgroundRepeat.REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));
  }


  public void addButtons() {
    MenuButton playBtn = new MenuButton("New Game", 190, 49, 23);
    playBtn.setLayoutX(90);
    playBtn.setLayoutY(160);
    root.getChildren().add(playBtn);
    playBtn.setOnAction(e -> levelTwo());

    MenuButton helpBtn = new MenuButton("Help", 190, 49, 23);
    helpBtn.setLayoutX(90);
    helpBtn.setLayoutY(260);
    root.getChildren().add(helpBtn);
    helpBtn.setOnAction(e -> ins.moveIn());

    MenuButton exitBtn = new MenuButton("Quit", 190, 49, 23);
    exitBtn.setLayoutX(90);
    exitBtn.setLayoutY(360);
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

  public void instructions() {
    root.getChildren().add(ins);
  }



  public void levelSelect() {
    // TODO menu with buttons for selecting three different levels or going back to main menu
  }



  public void levelOne() {
    root.getChildren().clear();
    Rectangle rect = new Rectangle(-100, -100, 1030, 930);
    rect.setFill(Color.WHITE);
    root.getChildren().add(rect);

    FadeTransition ft = new FadeTransition(Duration.millis(4000), rect);
    ft.setFromValue(2);
    ft.setToValue(0);
    ft.setAutoReverse(true);
    ft.setCycleCount(1);
    ft.play();

    Car car1 = new Car(600, 120, new Image("/resources/car_red_small_5.png"));
    car1.setVelocity(100);
    cars = new Car[] {car1};
    Wall leftWall = new Wall(-1, 0, 1, 600);
    Wall rightWall = new Wall(801, 0, 1, 600);
    Wall upWall = new Wall(0, -1, 800, 1);
    Wall downWall = new Wall(0, 601, 800, 1);
    Pylon pylon1 = new Pylon(10, 10);
    Pylon pylon2 = new Pylon(100, 200);
    Pylon pylon3 = new Pylon(200, 300);
    obstacles = new Obstacle[] {leftWall, rightWall, upWall, downWall, pylon1, pylon2, pylon3};

    for (Obstacle o : obstacles) {
      root.getChildren().add((Shape) o);
    }
    for (Car c : cars) {
      root.getChildren().add(c);
    }

    Image image = new Image("/resources/level1.jpg", 800, 615, false, true);
    BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));

    startNanoTime = System.nanoTime();
    addCar(new Car(488, 535, new Image("/resources/car_red_small_5.png")), image);
  }

  public void levelTwo() {
    root.getChildren().clear();
    Rectangle rect = new Rectangle(-100, -100, 1030, 930);
    rect.setFill(Color.WHITE);
    root.getChildren().add(rect);

    FadeTransition ft = new FadeTransition(Duration.millis(4000), rect);
    ft.setFromValue(2);
    ft.setToValue(0);
    ft.setAutoReverse(true);
    ft.setCycleCount(1);
    ft.play();

//    Wall leftWall = new Wall(-1, 0, 1, 600);
//    Wall rightWall = new Wall(801, 0, 1, 600);
//    Wall upWall = new Wall(0, -1, 800, 1);
//    Wall downWall = new Wall(0, 601, 800, 1);
//    Pylon pylon1 = new Pylon(10, 10);
//    Pylon pylon2 = new Pylon(100, 200);
//    Pylon pylon3 = new Pylon(200, 300);
//    obstacles = new Obstacle[] {leftWall, rightWall, upWall, downWall, pylon1, pylon2, pylon3};
//    root.getChildren().add(leftWall);
//    root.getChildren().add(rightWall);
//    root.getChildren().add(upWall);
//    root.getChildren().add(downWall);
//    root.getChildren().add(pylon1);
//    root.getChildren().add(pylon2);
//    root.getChildren().add(pylon3);

    Image image = new Image("/resources/2nd.jpg", 800, 615, false, true);
    BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));

    addCar(new Car(488, 522, new Image("/resources/car_red_small_5.png"), -90), image);
    
    Wall o1 = new Wall(473, 582, 124, 1, "l"); //lower
    Wall o2 = new Wall(473, 510, 118, 1, "l"); //upper
    
    //diagonal
    Wall o3 = new Wall(390, 538, 94, 1, "l"); //lower
    Wall o4 = new Wall(390, 460, 94, 1, "l"); //upper
    o3.getTransforms().add(new Rotate(28, o3.getX(), o3.getY()));
    o4.getTransforms().add(new Rotate(32, o4.getX(), o4.getY()));
    
    Wall o5 = new Wall(148, 559, 242, 1, "l"); //lower
    Wall o6 = new Wall(146, 480, 244, 1, "l"); //upper
    o5.getTransforms().add(new Rotate(355, o5.getX(), o5.getY()));
    o6.getTransforms().add(new Rotate(355, o6.getX(), o6.getY()));
    
    //straight
    Wall o7 = new Wall(139, 252, 1, 223, "l"); //right
    Wall o8 = new Wall(51, 252, 1, 223, "l"); //leftmost
    
    //bottom left corner
    Wall o9 = new Wall(51, 475, 1, 60, "l"); //upper
    Wall o10 = new Wall(66, 519, 35, 1, "l"); //middle
    Wall o11 = new Wall(90, 540, 60, 1, "l"); //lower
    o9.getTransforms().add(new Rotate(344, o9.getX(), o9.getY()));
    o10.getTransforms().add(new Rotate(45, o10.getX(), o10.getY()));
    o11.getTransforms().add(new Rotate(20, o11.getX(), o11.getY()));
    
    //top left corner
    Wall o12 = new Wall(68, 196, 1, 60, "l"); //lower
    Wall o13 = new Wall(70, 199, 52, 1, "l"); //upper
    o12.getTransforms().add(new Rotate(20, o12.getX(), o12.getY()));
    o13.getTransforms().add(new Rotate(332, o13.getX(), o13.getY()));
    
    //top horizontal
    Wall o14 = new Wall(116, 172, 280, 1, "l"); //upper
    Wall o15 = new Wall(142, 244, 273, 1, "l"); //lower
    
    //top turns
    Wall o16 = new Wall(398, 73, 1, 100, "l"); //upper
    Wall o17 = new Wall(399, 73, 1, 57, "l"); //upper
    o17.getTransforms().add(new Rotate(225, o17.getX(), o17.getY()));
    Wall o18 = new Wall(439, 33, 170, 1, "l"); //upper
    Wall o19 = new Wall(609, 33, 65, 1, "l"); //upper
    o19.getTransforms().add(new Rotate(22, o19.getX(), o19.getY()));
    Wall o20 = new Wall(666, 54, 45, 1, "l"); //upper
    o20.getTransforms().add(new Rotate(60, o20.getX(), o20.getY()));
    
    Wall o21 = new Wall(412, 244, 40, 1, "l"); //lower
    o21.getTransforms().add(new Rotate(325, o21.getX(), o21.getY()));
    Wall o22 = new Wall(445, 222, 50, 1, "l"); //lower
    o22.getTransforms().add(new Rotate(290, o22.getX(), o22.getY()));
    Wall o23 = new Wall(462, 97, 1, 80, "l"); //lower
    Wall o24 = new Wall(463, 97, 125, 1, "l"); //lower
    
    //finishes inner
    Wall o25 = new Wall(589, 97, 15, 1, "l");
    o25.getTransforms().add(new Rotate(58, o25.getX(), o25.getY()));
    Wall o26 = new Wall(595, 110, 1, 392, "l");
    
    //finishes outer
    Wall o27 = new Wall(690, 96, 1, 423, "l");
    Wall o28 = new Wall(690, 517, 1, 53, "l");
    o28.getTransforms().add(new Rotate(31, o28.getX(), o28.getY()));
    Wall o29 = new Wall(662, 560, 1, 68, "l");
    o29.getTransforms().add(new Rotate(71, o29.getX(), o29.getY()));
    
    obstacles = new Obstacle[] {o1, o2, o3, o4, o5, o6, o7, o8, o9, o10, o11, o12, o13, o14, o15, 
        o16, o17, o18, o19, o20, o21, o22, o23, o24, o25, o26, o27, o28, o29};
    for (Obstacle o : obstacles) {
      root.getChildren().add((Shape) o);
    }
    MenuButton menuBtn = new MenuButton("Main Menu", 125, 30, 15);
    menuBtn.setLayoutX(674);
    menuBtn.setLayoutY(579);
    root.getChildren().add(menuBtn);
    menuBtn.setOnAction(e -> mainMenu());
  }

  public void levelThree() {

  }

  public void addCar(Car car, Image image) {
    // TODO camera that moves with scene (read this:
    // https://stackoverflow.com/questions/47879463/2d-camera-in-javafx)
    root.getChildren().add(car);
    ArrayList<String> input = new ArrayList<String>();
    animationTimer = new AnimationTimer() {
      @Override
      public void handle(long currentNanoTime) {
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        car.move(t);
        updateBackground(car, image);
        startNanoTime = currentNanoTime;
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
            car.setVelocity(-car.getVelocity());
          }
        }
//        for (Car c : cars) {
//          if (((Path) Shape.intersect(car, (Shape) c)).getElements().size() > 0) { // if crashed
//            car.setVelocity(-car.getVelocity());
//          }
//          c.move(t, 0);
////          if (c.getX() < -c.getWidth() - 20) {
////            c.setX(root.getWidth());
////          }
////          if (c.getX() > root.getWidth() + c.getWidth() + 20) {
////            c.setX(-c.getWidth());
////          }
//          if (c.getY() < -c.getHeight() - 20) {
//            c.setY(root.getHeight());
//          }
//          if (c.getY() > root.getHeight() + c.getHeight() + 20) {
//            c.setY(-c.getHeight());
//          }
//        }
      }
    };
    animationTimer.start();
    
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        String code = e.getCode().toString();
        if (!input.contains(code)) {
          input.add(code);
        }
      }
    });

    scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        String code = e.getCode().toString();
        input.remove(code);
      }
    });
  }

    public void updateBackground(Car car, Image image) {
        double x = 0;
        double y = 0;
//        BackgroundPosition bp = new BackgroundPosition(Side.LEFT, t, false, Side.TOP, t, false);
//    BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
//        BackgroundRepeat.NO_REPEAT, /* do this */, BackgroundSize.DEFAULT);
//    root.setBackground(new Background(background));
      
    }

  public static void main(String[] args) {
    launch(args);
  }
}
