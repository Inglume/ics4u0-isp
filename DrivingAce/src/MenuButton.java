import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.*;
import javafx.scene.text.Font;

/**
 * The MenuButton class.
 * 
 * @author Jerry Zhu
 * @version 1
 */
public class MenuButton extends Button {
  private final String BTN_PRESSED_STYLE =
      "-fx-background-color: transparent; -fx-background-image: url('/resources/yellow_button01.png'); -fx-background-repeat: no-repeat;"; 
  private final String BTN_FREE_STYLE =
      "-fx-background-color: transparent; -fx-background-image: url('/resources/yellow_button00.png'); -fx-background-repeat: no-repeat;"; 
  private int height;
  
  public MenuButton(String text, int w, int h, int f) {
    height = h;
    setText(text);
    setFont(Font.font("verdana", f));
    setMinWidth(w);
    setMinHeight(h);
    setMaxWidth(w);
    setMaxHeight(h);
    setStyle(BTN_FREE_STYLE);
    listeners();
  }
  

  private void setPressedStyle() {
    setStyle(BTN_PRESSED_STYLE);
    setPrefHeight(height - 4);
    setLayoutY(getLayoutY() + 4);
  }

  private void setFreeStyle() {
    setStyle(BTN_FREE_STYLE);
    setPrefHeight(height);
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
