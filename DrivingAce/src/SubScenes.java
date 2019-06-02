import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class SubScenes extends SubScene{
  private boolean isVisible;
  
  public SubScenes(int i) {
    super(new AnchorPane(), 375, 330);
    prefWidth(375);
    prefHeight(265);

    isVisible = false;
    
    BackgroundImage background = new BackgroundImage(
        new Image("/resources/yellow_panel.png", 375, 330, false, true), BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, null, null);
    
    AnchorPane r = (AnchorPane)this.getRoot();
    r.setBackground(new Background(background));

    setLayoutX(1040);
    setLayoutY(160);    
    
    if(i == 1) {
      Label l = new Label("Instructions");
      //setting the position of the text
   //  l.setLayoutX(1045);
     // l.setLayoutY(165);
      r.getChildren().add(l);
    }else {
      Label l = new Label("High Scores");
      //setting the position of the text
   //  l.setLayoutX(1045);
     // l.setLayoutY(165);
      r.getChildren().add(l);
    }
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
  
  public boolean getVisible() {
    return isVisible;
  }
}
