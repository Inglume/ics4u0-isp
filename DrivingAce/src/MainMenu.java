import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

/**
 * The MainMenu class.
 * 
 * @author Jerry Zhu
 * 
 */
public class MainMenu {

  private long startNanoTime;
  
  public MainMenu(AnchorPane r) {
    MenuButton playBtn = new MenuButton("New Game");
    playBtn.setLayoutX(100);
    playBtn.setLayoutY(100);
    r.getChildren().add(playBtn);
    
    playBtn.setOnAction(e -> new LevelOne(r));
    
    MenuButton helpBtn = new MenuButton("Help");
    helpBtn.setLayoutX(100);
    helpBtn.setLayoutY(200);
    r.getChildren().add(helpBtn);
    
    helpBtn.setOnAction(e -> new Instructions(r));
    
    MenuButton exitBtn = new MenuButton("Quit");
    exitBtn.setLayoutX(100);
    exitBtn.setLayoutY(300);
    r.getChildren().add(exitBtn);
    
    exitBtn.setOnAction(e -> Platform.exit());
    
    BackgroundImage background = new BackgroundImage(new Image("/resources/menubackground.jpg",200,515,false,true),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
    r.setBackground(new Background(background));
  }
}
