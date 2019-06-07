import java.io.*;
import java.lang.reflect.Array;
import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
      l.setFont(Font.font("helvetica", 18));
      //setting the position of the text
      l.setLayoutX(20);
      l.setLayoutY(15);
    }else {
      File file = new File(System.getProperty("user.home")+ "/DrivingAce/highscores.txt");
    //  file.mkdirs();
      
      l = new Label("High Scores");

      l.setFont(Font.font("helvetica", 23));
      //setting the position of the text
      l.setLayoutX(135);
      l.setLayoutY(15);

      BufferedReader input;
      String line = "";
      int numberOfLine = 0;

      //counts number of scores
      while (true)
      {
        try
        {
          file.createNewFile();

          input = new BufferedReader (new FileReader (file));

          //loop for as long as there is data in the file
          while (line != null)
          {
            line = input.readLine (); //reads each line in the file
            numberOfLine++;
          }
          input.close (); //closes the stream
          break;
        }
        catch (IOException e){ System.out.println("f");e.printStackTrace(); }
      }

      String[] linesFile = new String [numberOfLine - 1]; //create array with size to match number of lines in file
      String[] names = new String [(numberOfLine - 1) / 2];
      int[] scores = new int [(numberOfLine - 1) / 2]; //creates an array to store the converted string array

      try
      {
        //open the same file again
        BufferedReader a = new BufferedReader (new FileReader (file)); // reset the buffer
        int x = 0;

        while (x < linesFile.length) //loop until end of file is reached
        {
          linesFile [x] = a.readLine (); //feed each data line into an array
          x++;
        }

        //stores the names into the string array
        for (int p = 0 ; p < linesFile.length ; p+=2)
        {
          names [p / 2] = linesFile [p];
          scores [p / 2] = Integer.parseInt (linesFile [p + 1]);
        }
        a.close (); //close data file
      }
      catch (IOException e) { e.printStackTrace(); }

      
      //insertion sort
      for(int x = 0; x < scores.length; x++) {
        int temp = scores[x], pos = x;
        String temp1 = names[x];
        
        while(pos > 0 && temp > scores[pos-1]) {
          scores[pos] = scores[pos-1];
          names[pos] = names[pos-1];
          pos--;
        }
        scores[pos] = temp;
        names[pos] = temp1;
      }
      
      int t = 0; 
      if(linesFile.length <= 10)
        t = linesFile.length;
      else
        t = 10;
      for (int x = 0 ; x < t/2; x++)
      {
        Label first = new Label(names[x]);
        first.setLayoutX(100);
        first.setLayoutY(80 + x*30);
        first.setFont(Font.font("Helvetica", 25));
        r.getChildren().add(first);

        Label second = new Label(String.valueOf(scores[x]));
        second.setLayoutX(300);
        second.setLayoutY(80 + x*30);
        second.setFont(Font.font("Calibri", 25));
        r.getChildren().add(second);
      }
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
