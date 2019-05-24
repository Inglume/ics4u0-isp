import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class Intro {
  
  public Intro(AnchorPane r) 
  {
    BackgroundImage background = new BackgroundImage(new Image("/resources/logo.png",200,515,false,true),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
    r.setBackground(new Background(background)); 
  } 
  
}
