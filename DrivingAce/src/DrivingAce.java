import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;


/**
 * @author Nicholas Glenn and Jerry Zhu
 *
 */
public class DrivingAce extends Application {

  private AnchorPane root;

  long startNanoTime;

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setResizable(false);
    root = new AnchorPane();
    root.setPrefSize(700, 500);
    Scene scene = new Scene(root);
    Car damn = new Car(200, 200);
    startNanoTime = System.nanoTime();

    ArrayList<String> input = new ArrayList<String>();

    new AnimationTimer() {
      @Override
      public void handle(long currentNanoTime) {
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        damn.move(t);
        startNanoTime = currentNanoTime;
        if (input.contains("W")) {
          damn.accelerate();
        }
        if (input.contains("A")) {
          damn.steerLeft();
        }
        if (input.contains("S")) {
          damn.reverse();
        }
        if (input.contains("D")) {
          damn.steerRight();
        }
        if (input.contains("ESCAPE")) {
          System.exit(0);
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

    primaryStage.setTitle("Driving Ace");
    primaryStage.setScene(scene);
    intro();
    // mainMenu();

    primaryStage.show();
  }

  public void intro() {
    BackgroundImage background = new BackgroundImage(
        new Image("/resources/gray.jpeg", 200, 515, false, true), BackgroundRepeat.REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));

    try {
      Image image = new Image(new FileInputStream("/resources/logo.png"));

      // Setting the image view 1
      ImageView imageView1 = new ImageView(image);

      // Setting the position of the image
      imageView1.setX(50);
      imageView1.setY(25);

      // setting the fit height and width of the image view
      imageView1.setFitHeight(300);
      imageView1.setFitWidth(250);

      // Setting the preserve ratio of the image view
      imageView1.setPreserveRatio(true);
    } catch (FileNotFoundException e) {
      System.out.println("YOU SUCK");
    }
  }


  public void mainMenu() {
    MenuButton playBtn = new MenuButton("New Game");
    playBtn.setLayoutX(100);
    playBtn.setLayoutY(100);
    root.getChildren().add(playBtn);

    playBtn.setOnAction(e -> levelOne());

    MenuButton helpBtn = new MenuButton("Help");
    helpBtn.setLayoutX(100);
    helpBtn.setLayoutY(200);
    root.getChildren().add(helpBtn);

    helpBtn.setOnAction(e -> instructions());

    MenuButton exitBtn = new MenuButton("Quit");
    exitBtn.setLayoutX(100);
    exitBtn.setLayoutY(300);
    root.getChildren().add(exitBtn);

    exitBtn.setOnAction(e -> Platform.exit());

    BackgroundImage background = new BackgroundImage(
        new Image("/resources/menubackground.jpg", 200, 515, false, true), BackgroundRepeat.REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    root.setBackground(new Background(background));
  }

  public void instructions() {

  }

  public void levelOne() {

  }

  public void levelTwo() {

  }

  public void levelThree() {

  }

  public static void main(String[] args) {
    launch(args);
  }
}
