import javax.swing.*;
import java.awt.*;

/**
 * StudentView Class
 * The view that allows the user to input the student number of a runner from the database; the appropriate view is
 * opened based on whichever button was clicked earlier (the view to edit a student, or the view to see their statistics)
 * 
 * @author James Fong & Prajvin Jalan
 * @since January 15, 2014
 */
public class StudentView extends JPanel
{
     DataModel model; //Main database model connected to this view of the database
     JButton studEntered = new JButton("Enter"); //JButton that saves the student who's student number was entered (used to open edit or stats view)
     
     JPanel north = new JPanel(); //JPanel that holds most components (label, input, button)
     JPanel south = new JPanel(); //JPanel that holds system message
     
     BorderLayout mainLayout = new BorderLayout(); //Layout for this main Student View
     
     JLabel askStud = new JLabel("Enter the Student Number: ");  //JLabel that describes the student number text field
     JTextField inputStud = new JTextField(6); //JTextField for user to input student number
     
     JLabel systemMessage = new JLabel(); //JLabel that displays system message
     
     String previousButton; //the previous button that was clicked to open this view (opens another view based on this variable)
     
     /** Constructor for the StudentView class
       * @param DataModel model     Links the main Database model to this view
       * @param String previousButton     the button that was clicked to open this view
       */
     public StudentView (DataModel model, String previousButton)
     {
          this.model = model; //sets this view's model
          this.previousButton = previousButton; //sets the previous button clicked
          this.model.setStudGUI(this); //connects this view to the model (database)
          this.layoutView(); //sets this view's looks
          this.disableComponents(); //disables all components
          this.inputStud.setEnabled(true); //enables the first component
          this.registeredControllers(); //creates this view's controllers
     }
     
     /**Sets up this Student View's layout (looks) */
     public void layoutView()
     {
          //sets this view's size and layout
          this.setPreferredSize(new Dimension(700,120));
          this.setLayout(mainLayout);
          
          north.setBorder(BorderFactory.createTitledBorder("Input"));
          south.setBorder(BorderFactory.createTitledBorder("System Message"));
          
          //adds components to the north panel
          north.add(askStud);
          north.add(inputStud);
          north.add(studEntered);
          
          //adds system message label to the south panel
          south.add(systemMessage);
          
          //adds all panels to this main Student View panel
          this.add(north, BorderLayout.NORTH);
          this.add(south, BorderLayout.CENTER);
     }
     
     /**Creates controllers for the required components in the Student View class*/
     public void registeredControllers()
     {
          //enter student button controller
          DataButtonController studEnteredClicked = new DataButtonController (this.model, this.studEntered, "enterStudent", this.previousButton);
          this.studEntered.addActionListener(studEnteredClicked);
          
          //student number text field controller
          DataTextFieldController studNumChanged = new DataTextFieldController (this.model, this.inputStud, "studNumEntered", "studentView");
          this.inputStud.addActionListener(studNumChanged);
     }
     
     /** Updates this Student View based on values passed in: which components to enable/disable (based on user input), and whether to add a message
       * @param boolean correctInput     whether or not the user entered the correct input
       * @param JTextField currentTextField     the current text field being worked with
       * @param JComponent nextComponent     the next component to enable (either a text field or a button)
       * @param String message     displays a message if the user input was incorrect, or none if it was correct
       */
     public void updateStudent (boolean correctInput, JTextField currentTextField, JComponent nextComponent, String message)
     {
          //if user input was not correct, will highlight current text field (for user to re-enter information)
          if (correctInput == false) {
               currentTextField.selectAll();
               systemMessage.setText(message);
          }
          //if user input was correct, will disable current component, enable next component, and display a message
          else {
               currentTextField.setEnabled(false);
               nextComponent.setEnabled(true);
               nextComponent.requestFocusInWindow();
               systemMessage.setText(message);
          }
     }
     
     /**Disables all this Student View's components*/
     private void disableComponents()
     {
          this.inputStud.setEnabled(false);
          this.studEntered.setEnabled(false);
     }
}