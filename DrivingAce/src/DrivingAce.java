import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
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
     levelTwo();
    //mainMenu();
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
    obstacles = new Obstacle[0];
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

    addCar(new Car(488, 535, new Image("/resources/car_red_small_5.png"), 0), scene);
    Obstacle leftWall = new Obstacle(-1, 0, 1, 600);
    Obstacle rightWall = new Obstacle(801, 0, 1, 600);
    Obstacle upWall = new Obstacle(0, -1, 800, 1);
    Obstacle downWall = new Obstacle(0, 601, 800, 1);
    Pylon pylon1 = new Pylon(10, 10);
    Pylon pylon2 = new Pylon(100, 200);
    Pylon pylon3 = new Pylon(200, 300);
    obstacles = new Obstacle[] {leftWall, rightWall, upWall, downWall, pylon1, pylon2, pylon3};
    root.getChildren().add(leftWall);
    root.getChildren().add(rightWall);
    root.getChildren().add(upWall);
    root.getChildren().add(downWall);
    root.getChildren().add(pylon1);
    root.getChildren().add(pylon2);
    root.getChildren().add(pylon3);

    BackgroundImage background = new BackgroundImage(
        new Image("/resources/level1.jpg", 800, 615, false, true), BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));
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

    Obstacle leftWall = new Obstacle(-1, 0, 1, 600);
    Obstacle rightWall = new Obstacle(801, 0, 1, 600);
    Obstacle upWall = new Obstacle(0, -1, 800, 1);
    Obstacle downWall = new Obstacle(0, 601, 800, 1);
    Pylon pylon1 = new Pylon(10, 10);
    Pylon pylon2 = new Pylon(100, 200);
    Pylon pylon3 = new Pylon(200, 300);
    obstacles = new Obstacle[] {leftWall, rightWall, upWall, downWall, pylon1, pylon2, pylon3};
    root.getChildren().add(leftWall);
    root.getChildren().add(rightWall);
    root.getChildren().add(upWall);
    root.getChildren().add(downWall);
    root.getChildren().add(pylon1);
    root.getChildren().add(pylon2);
    root.getChildren().add(pylon3);

    BackgroundImage background = new BackgroundImage(
        new Image("/resources/2nd.jpg", 800, 615, false, true), BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));

    addCar(new Car(488, 522, new Image("/resources/car_red_small_5.png"), -90), scene);
    Obstacle o1 = new Obstacle(473, 582, 124, 1, "l"); //lower
    Obstacle o2 = new Obstacle(473, 510, 118, 1, "l"); //upper
    
    //diagonal
    Obstacle o3 = new Obstacle(390, 538, 94, 1, "l"); //lower
    Obstacle o4 = new Obstacle(390, 460, 94, 1, "l"); //upper
    o3.getTransforms().add(new Rotate(28, o3.getX(), o3.getY()));
    o4.getTransforms().add(new Rotate(32, o4.getX(), o4.getY()));
    
    Obstacle o5 = new Obstacle(148, 559, 242, 1, "l"); //lower
    Obstacle o6 = new Obstacle(146, 480, 244, 1, "l"); //upper
    o5.getTransforms().add(new Rotate(355, o5.getX(), o5.getY()));
    o6.getTransforms().add(new Rotate(355, o6.getX(), o6.getY()));
    
    //straight
    Obstacle o7 = new Obstacle(139, 252, 1, 223, "l"); //right
    Obstacle o8 = new Obstacle(51, 252, 1, 223, "l"); //leftmost
    
    //bottom left corner
    Obstacle o9 = new Obstacle(51, 475, 1, 60, "l"); //upper
    Obstacle o10 = new Obstacle(66, 519, 35, 1, "l"); //middle
    Obstacle o11 = new Obstacle(90, 540, 60, 1, "l"); //lower
    o9.getTransforms().add(new Rotate(344, o9.getX(), o9.getY()));
    o10.getTransforms().add(new Rotate(45, o10.getX(), o10.getY()));
    o11.getTransforms().add(new Rotate(20, o11.getX(), o11.getY()));
    
    //top left corner
    Obstacle o12 = new Obstacle(68, 196, 1, 60, "l"); //lower
    Obstacle o13 = new Obstacle(70, 199, 52, 1, "l"); //upper
    o12.getTransforms().add(new Rotate(20, o12.getX(), o12.getY()));
    o13.getTransforms().add(new Rotate(332, o13.getX(), o13.getY()));
    
    //top horizontal
    Obstacle o14 = new Obstacle(116, 172, 273, 1, "l"); //lower
    
    obstacles = new Obstacle[] {o1, o2, o3, o4, o5, o6, o7, o8, o9, o10, o11, o12, o13, o14};
    for(int x = 0; x < obstacles.length; x++)
      root.getChildren().add(obstacles[x]);
      
    
    MenuButton menuBtn = new MenuButton("Main Menu", 125, 30, 15);
    menuBtn.setLayoutX(674);
    menuBtn.setLayoutY(579);
    root.getChildren().add(menuBtn);
    menuBtn.setOnAction(e -> mainMenu());
  }

  public void levelThree() {

  }

  public void addCar(Car car, Scene scene) {
    // TODO camera that moves with scene (read this:
    // https://stackoverflow.com/questions/47879463/2d-camera-in-javafx)
    root.getChildren().add(car);
    ArrayList<String> input = new ArrayList<String>();

    animationTimer = new AnimationTimer() {
      @Override
      public void handle(long currentNanoTime) {
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        car.move(t);
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
          if (o != null && ((Path) Shape.intersect(car, o)).getElements().size() > 0) {
            System.out.println("CRASHED");
            car.setVelocity(-car.getVelocity());
          }
        }
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

  public static void main(String[] args) {
    launch(args);
  }
}
