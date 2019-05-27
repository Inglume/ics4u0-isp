import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

<<<<<<< HEAD
public class MenuButton extends Button{
  private final String FONT_PATH = "/src/resources/kenvector_future.ttf";
  private final String BTN_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/src/resources/yellow_button01.png');";
  private final String BTN_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/src/resources/yellow_button00.png');";
=======
/**
 * The MenuButton class.
 * 
 * @author Jerry Zhu
 * @version 1
 */
public class MenuButton extends Button {
  private final String FONT_PATH = "src/resources/kenvector_future.ttf";
  private final String BTN_PRESSED_STYLE =
      "-fx-background-color: transparent; -fx-background-image: url('src/resources/yellow_button01.png');";
  private final String BTN_FREE_STYLE =
      "-fx-background-color: transparent; -fx-background-image: url('src/resources/yellow_button00.png');";
>>>>>>> refs/remotes/origin/master

  public MenuButton(String text) {
    setText(text);
    setButtonFont();
    setPrefWidth(190);
    setPrefHeight(49);
    setStyle(BTN_FREE_STYLE);
    listeners();
  }

  private void setButtonFont() {
    try {
      setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
    } catch (FileNotFoundException e) {
      setFont(Font.font("verdana", 23));
    }
  }

  private void setPressedStyle() {
    setStyle(BTN_PRESSED_STYLE);
    setPrefHeight(45);
    setLayoutY(getLayoutY() + 4);
  }

  private void setFreeStyle() {
    setStyle(BTN_FREE_STYLE);
    setPrefHeight(49);
    setLayoutY(getLayoutY() - 4);
  }

  private void listeners() {
    setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY))
          setPressedStyle();
      }
    });

    setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY))
          setFreeStyle();
      }
    });

    setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        setEffect(new DropShadow());
      }
    });

    setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        setEffect(null);
      }
    });
  }
}
