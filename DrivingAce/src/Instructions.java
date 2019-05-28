import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.util.Duration;

public class Instructions extends SubScene{
  private final String IMAGE_PATH = "/resources/yellow_panel.png";

  public Instructions() {
    super(new AnchorPane(), 700, 500);
    prefWidth(400);
    prefHeight(100);
    
    BackgroundImage background = new BackgroundImage(
        new Image("/resources/yellow_panel.png", 300, 230, false, true), BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
    
    AnchorPane r = (AnchorPane)this.getRoot();
    r.setBackground(new Background(background));
    
    setLayoutX(0);
  //  setLayoutY(0);
  }
  
  public void moveIn() {
    TranslateTransition trans = new TranslateTransition();
    trans.setDuration(Duration.seconds(0.3));
    trans.setNode(this);
    trans.setToX(-676);
    trans.play();
  }
  
  
}
