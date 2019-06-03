import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;


/**
 * @author Jerry Zhu
 * @version 3
 */
public class SubScenes extends SubScene{
  
  /**
   * isVisible is a boolean that stores whether the subscene is visible or not.
   */
  private boolean isVisible;
  
  
  /**
   * SubsScenes constructor.
   * @param i an int that stores whether the subscene is the Instructions screen or High Scores Screen.
   */
  public SubScenes(int i) {
    super(new AnchorPane(), 400, 330);
    prefWidth(375);
    prefHeight(265);

    isVisible = false;
    
    BackgroundImage background = new BackgroundImage(
        new Image("/resources/yellow_panel.png", 400, 330, false, true), BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, null, null);
    
    AnchorPane r = (AnchorPane)this.getRoot();
    r.setBackground(new Background(background));

    setLayoutX(1040);
    setLayoutY(160);    
    
    Label l;
    if(i == 1) {
      String h = "The purpose of this game is to teach one to\n"
          + "adhere to the rules of safe driving, especially\n"
          + "those with ODD.\n\n"
          + "Level 1 models a real-life crash because of\n"
          + "reckless driving. \n"
          + "Level 2 is a driving course that teaches the\n"
          + "rules of proper driving.\n"
          + "Level 3 tests your ability to drive safely.\n\n"
          + "Use 'WASD' or Arrow Keys to control the car.";
      l = new Label(h);
      
      l.setFont(Font.font("calibri", 20));
      //setting the position of the text
      l.setLayoutX(20);
      l.setLayoutY(15);
    }else {
      l = new Label("High Scores");
      
      l.setFont(Font.font("calibri", 25));
      //setting the position of the text
      l.setLayoutX(135);
      l.setLayoutY(15);
    }
    r.getChildren().add(l);
  }
  
  
  /**
   * Moves the subscene.
   */
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
