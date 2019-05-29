import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.util.Duration;

public class Instructions extends SubScene{
  private boolean isVisible = false;
  
  public Instructions() {
    super(new AnchorPane(), 375, 265);
    prefWidth(375);
    prefHeight(265);
    
    BackgroundImage background = new BackgroundImage(
        new Image("/resources/yellow_panel.png", 375, 265, false, true), BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, null, null);
    
    AnchorPane r = (AnchorPane)this.getRoot();
    r.setBackground(new Background(background));

    Text t = new Text("Lol");
    
    //setting the position of the text
    t.setX(50);
    t.setY(160);
    
    setLayoutX(1040);
    setLayoutY(160);            
  }
  
  public void moveIn() {
    TranslateTransition trans = new TranslateTransition();
    trans.setDuration(Duration.seconds(0.3));
    trans.setNode(this);
    trans.setToX(-676);
    if(isVisible) {
      trans.setDuration(Duration.seconds(0.3));
      trans.setToX(1024);
    }
    trans.play();
    isVisible = !isVisible;
  }
}
