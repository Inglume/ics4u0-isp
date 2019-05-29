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
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * The DrivingAce class.
 * 
 * @author Nicholas Glenn and Jerry Zhu
 * @version 1
 */
public class DrivingAce extends Application {
  private AnchorPane root;
  private long startNanoTime;
  private Scene scene;
  private Instructions ins;
  private Obstacle[] obstacles;

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
    mainMenu();
    addCar(new Car(50, 10), scene);
    root.getChildren().add(new Pylon(10, 10));
    root.getChildren().add(new Pylon(10, 20));
    root.getChildren().add(new Pylon(10, 40));
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
    // PauseTransition pause = new PauseTransition(Duration.seconds(5));
    // pause.play();

    FadeTransition ft = new FadeTransition(Duration.millis(4000), imageView1);
    ft.setFromValue(2);
    ft.setToValue(0);
    ft.setAutoReverse(true);
    ft.play();
    ft.setCycleCount(1);
    ft.setOnFinished(e -> mainMenu());
  }


  public void mainMenu() {
    // Rectangle rect = new Rectangle (-100, -100, 830, 630);
    // rect.setFill(Color.WHITE);
    // root.getChildren().add(rect);
    //
    // FadeTransition ft = new FadeTransition(Duration.millis(4000), rect);
    // ft.setFromValue(2);
    // ft.setToValue(0);
    // ft.setAutoReverse(true);
    // ft.setCycleCount(1);
    // ft.play();
    // ft.setOnFinished(e -> addButtons());

    ins = new Instructions();
    root.getChildren().add(ins);
    addButtons();
    BackgroundImage background = new BackgroundImage(
        new Image("/resources/menubackground.jpg", 200, 615, false, true), BackgroundRepeat.REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));
  }

  public void addButtons() {
    MenuButton playBtn = new MenuButton("New Game");
    playBtn.setLayoutX(90);
    playBtn.setLayoutY(160);
    root.getChildren().add(playBtn);
    playBtn.setOnAction(e -> levelOne());

    MenuButton helpBtn = new MenuButton("Help");
    helpBtn.setLayoutX(90);
    helpBtn.setLayoutY(260);
    root.getChildren().add(helpBtn);
    helpBtn.setOnAction(e -> ins.moveIn());

    MenuButton exitBtn = new MenuButton("Quit");
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

  public void levelOne() {
    Obstacle[] obstacles = new Obstacle[] {new Pylon(100, 100)};
    new Car(100, 100);
  }

  public void levelTwo() {

  }

  public void levelThree() {

  }

  public void addCar(Car damn, Scene scene) {
    ArrayList<String> input = new ArrayList<String>();

    new AnimationTimer() {
      @Override
      public void handle(long currentNanoTime) {
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        damn.move(t);
        startNanoTime = currentNanoTime;
        if (input.contains("W") || input.contains("UP")) {
          damn.accelerate();
        }
        if (input.contains("A") || input.contains("LEFT")) {
          damn.steerLeft();
        }
        if (input.contains("S") || input.contains("DOWN")) {
          damn.reverse();
        }
        if (input.contains("D") || input.contains("RIGHT")) {
          damn.steerRight();
        }
        if (input.contains("ESCAPE")) {
          Platform.exit();
        }
        for (Obstacle o : obstacles) {
          // if ((damn.intersects(o)) {
          // System.out.println("CRASHED");
          // }
        }
      }
    }.start();

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

    root.getChildren().add(damn);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
