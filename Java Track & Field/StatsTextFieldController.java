import javax.swing.*;
import java.awt.event.*;

/**
 * StatsTextFieldController Class
 * This class is used as a controller for the text fields within the Runner Statistics program.
 * 
 * @author Prajvin Jalan
 * @since January 15, 2014
 */
public class StatsTextFieldController extends Object implements ActionListener
{
     StatsModel model; //model linked to the component
     JTextField textField; //current text field linked to the controller
     String type; //the specific type of button being clicked
     
     /**
     * Main constructor for the controller - links the component to the model.
     * @param StatsModel model      the model for the Stats View
     * @param JTextField textField      the text field being linked to the controller
     * @param String type      the type of text field being changed
     */
     public StatsTextFieldController (StatsModel model, JTextField textField, String type)
     {
          super();
          this.model = model;
          this.textField = textField;
          this.type = type;
     }
     
     /**
     * Updates the stats view accordingly when a text field is changed (based on the type of text field being changed)
     * @param ActionEvent e      the change event of the text field (an action is performed)
     */
     public void actionPerformed(ActionEvent e)
     {
          if (this.type.equals("practiceTime")) { //validates the input and, if valid, saves the practice time for future use (adding practice time)
              this.model.prompt.getString(this.textField.getText(), this.textField, this.type);
          }
          else if (this.type.equals("raceTime")) { //validates the input and, if valid, saves the race time for future use (adding race time)
               this.model.prompt.getString(this.textField.getText(), this.textField, this.type);
          }
          else if (this.type.equals("indexPrac")) { //validates the input and, if valid, saves the practice time log for future use (removing practice time)
               this.model.prompt.getInt(this.textField.getText(), this.textField, this.type);
          }     
          else { //validates the input and, if valid, saves the race time log for future use (removing race time)
               this.model.prompt.getInt(this.textField.getText(), this.textField, this.type);
          }
     }  
}