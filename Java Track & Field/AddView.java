import javax.swing.*;
import java.awt.*;

/**
 * AddView Class
 * The view that allows the user to add general information for the runner; including student number, last name, 
 * first name, age, division, and race distance.
 * 
 * @author James Fong & Prajvin Jalan
 * @since January 15, 2014
 */
public class AddView extends JPanel
{
     DataModel model; //Main database model connected to this view of the database
     
     JButton enter = new JButton("Enter"); //JButton used to add the student using the values input by the user
     
     JLabel askStud = new JLabel("Student #: "); //JLabel describing the studNum text field
     JLabel askLast = new JLabel("Last Name: "); //JLabel describing the last text field
     JLabel askFirst = new JLabel("First Name: "); //JLabel describing  the first text field
     JLabel askAge = new JLabel("Age: "); //JLabel describing  the age text field
     JLabel askDivision = new JLabel("Division: "); //JLabel describing  the division text field
     JLabel askRaceD = new JLabel("Race Distance: "); //JLabel describing  the studNum text field
     JLabel systemMessage = new JLabel(); //JLabel to show the system message (used when input is being validated)
     
     JTextField studNum = new JTextField(6); //JTextField for user to input student number
     JTextField last = new JTextField(10); //JTextField for user to input student last name
     JTextField first = new JTextField(10); //JTextField for user to input student first name
     JTextField age = new JTextField(2); //JTextField for user to input student age
     JTextField division = new JTextField(10); //JTextField for user to input student division
     JTextField raceD = new JTextField(5); //JTextField for user to input student race distance
     
     JPanel north = new JPanel(); //JPanel that holds all buttons, textfields, and most labels
     JPanel south = new JPanel(); //JPanel that holds the system message label
     
     BorderLayout mainLayout = new BorderLayout(); //Layout for the panel (to order all components properly)
     
     /** Constructor for the AddView class
       * @param DataModel model     Links the main Database model to this view
       */
     public AddView (DataModel model)
     {
          this.model = model; //sets model
          this.model.setAddGUI(this); //connects this view to the model
          this.layoutView(); //sets up view of this panel
          this.disableComponents(); //disables all panel components
          this.studNum.setEnabled(true); //enables first panel component
          this.registeredControllers(); //registers all component controllers
     }
     
     /**Sets up this Add View's layout (looks) */
     public void layoutView()
     {
          //sets this main panels size and layout
          this.setPreferredSize(new Dimension(1200,120));
          this.setLayout(mainLayout);
          
          north.setBorder(BorderFactory.createTitledBorder("Input"));
          south.setBorder(BorderFactory.createTitledBorder("System Message"));
          
          //Inputs and action components put to the upper part of the panel
          north.add(askStud);
          north.add(studNum);
          
          north.add(askLast);
          north.add(last);
          
          north.add(askFirst);
          north.add(first);
          
          north.add(askAge);
          north.add(age);
          
          north.add(askDivision);
          north.add(division);
          
          north.add(askRaceD);
          north.add(raceD);
          
          north.add(enter);
          
          //System message put to the lower part of the panel
          south.add(systemMessage);
          
          //adds all panels to the main Add View panel
          this.add(north, BorderLayout.NORTH);
          this.add(south, BorderLayout.CENTER);
          
     }
     
     /**Creates controllers for the required components in the Add View class*/
     public void registeredControllers()
     {
          //enter button controller
          DataButtonController addStudentClicked = new DataButtonController (this.model, this.enter, "addStudent");
          this.enter.addActionListener(addStudentClicked);
          
          //student number text field controller
          DataTextFieldController studNumChanged = new DataTextFieldController (this.model, this.studNum, "studNum", "addView");
          this.studNum.addActionListener(studNumChanged);
          
          //student last name text field controller
          DataTextFieldController lastChanged = new DataTextFieldController (this.model, this.last, "last", "addView");
          this.last.addActionListener(lastChanged);
          
          //student first name text field controller
          DataTextFieldController firstChanged = new DataTextFieldController (this.model, this.first, "first", "addView");
          this.first.addActionListener(firstChanged);
          
          //student age text field controller
          DataTextFieldController ageChanged = new DataTextFieldController (this.model, this.age, "age", "addView");
          this.age.addActionListener(ageChanged);
          
          //student division text field controller
          DataTextFieldController divisionChanged = new DataTextFieldController (this.model, this.division, "division", "addView");
          this.division.addActionListener(divisionChanged);
          
          //student race distance text field controller
          DataTextFieldController raceDChanged = new DataTextFieldController (this.model, this.raceD, "raceD", "addView");
          this.raceD.addActionListener(raceDChanged);
     }
     
     /** Updates this Add View based on values passed in: which components to enable/disable (based on user input), and whether to add a message
       * @param boolean correctInput     whether or not the user entered the correct input
       * @param JTextField currentTextField     the current text field being worked with
       * @param JComponent nextComponent     the next component to enable (either a text field or a button)
       * @param String message     displays a message if the user input was incorrect, or none if it was correct
       */
     public void updateAdd (boolean correctInput, JTextField currentTextField, JComponent nextComponent, String message)
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
     
     /**Disables all components in this Add View**/
     private void disableComponents()
     {
          this.studNum.setEnabled(false);
          this.last.setEnabled(false);
          this.first.setEnabled(false);
          this.age.setEnabled(false);
          this.division.setEnabled(false);
          this.raceD.setEnabled(false);
          this.enter.setEnabled(false);
     }
}