import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.*;
import javafx.scene.text.Font;


/**
 * The MenuButton class.
 * 
 * @author Jerry Zhu
 * @version 3
 */
public class MenuButton extends Button {

  /**
   * Stores the style of the pressed button.
   */
  private final String BTN_PRESSED_STYLE =
      "-fx-background-color: transparent; -fx-background-image: url('/resources/yellow_button01.png'); -fx-background-repeat: no-repeat;";

  /**
   * Stores the style of the non-pressed button.
   */
  private final String BTN_FREE_STYLE =
      "-fx-background-color: transparent; -fx-background-image: url('/resources/yellow_button00.png'); -fx-background-repeat: no-repeat;";

  /**
   * Stores the height of the button.
   */
  private int height;

  /**
   * Constructor.
   * 
   * @param text the text in the button.
   * @param w width of the button.
   * @param h height of the button.
   * @param f font size.
   */
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

  /**
   * Sets the style of the pressed button.
   */
  private void setPressedStyle() {
    setStyle(BTN_PRESSED_STYLE);
    setPrefHeight(height - 4);
    setLayoutY(getLayoutY() + 4);
  }

  /**
   * Sets the style of the free button.
   */
  private void setFreeStyle() {
    setStyle(BTN_FREE_STYLE);
    setPrefHeight(height);
    setLayoutY(getLayoutY() - 4);
  }

  /**
   * Adds the listener objects to the button.
   */
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
