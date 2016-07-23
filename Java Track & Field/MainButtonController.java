import javax.swing.*;
import java.awt.event.*;

/**
 * MainButtonController Class
 * This class is used as a controller for the buttons within the main Menu.
 * 
 * @author Prajvin Jalan
 * @since January 15, 2014
 */
public class MainButtonController extends Object implements ActionListener
{
     MainModel model; //the model linked to the component
     JButton button; //current button linked to the controller
     String type; //the specific type of button being clicked
     
     /**
     * Main constructor for the controller - links the component to the model.
     * @param MainModel model      the model for the Menu
     * @param String button      the button being linked to the controller
     * @param String type      the type of button being clicked
     */
     public MainButtonController (MainModel model, JButton button, String type)
     {
          super();
          this.model = model;
          this.button = button;
          this.type = type;
     }
     
     /**
     * Updates the program accordingly when a button is clicked (based on the type of button being clicked)
     * @param ActionEvent e      the change event of a button (an action is performed)
     */
     public void actionPerformed(ActionEvent e)
     {
          if (this.type.equals("database")) { //opens the main Database
               this.model.openDatabase();
          }
          if (this.type.equals("quit")) { //quits the program
               this.model.quit();
          }
     }  
}
