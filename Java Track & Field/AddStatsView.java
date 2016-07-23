import javax.swing.*;
import java.awt.*;

/**
 * AddStatsView Class
 * The view that allows the user to add statistics information for the runner; including practice times and race times.
 * 
 * @author James Fong & Prajvin Jalan
 * @since January 15, 2014
 */
public class AddStatsView extends JPanel
{
     StatsModel model; //Main Stats model connected to this view of the student Stats
     JButton add = new JButton("Add"); //JButton that adds the stats
     
     JLabel askPracticeTime = new JLabel("Practice Time: ");  //JLabel that describes the practice time text field
     JLabel askRaceTime = new JLabel("Race Time: "); //JLabel that describes the race time text field
     JLabel systemMessage = new JLabel(); //JLabel that displays the system message
     
     JTextField practiceTime = new JTextField(10); //JTextField for user to input a practice time
     JTextField raceTime = new JTextField(10); //JTextField for user to input a race time
     
     JPanel north = new JPanel(); //JPanel that holds most components (inputs, button)
     JPanel south = new JPanel(); //JPanel that holds the system message
     
     BorderLayout mainLayout = new BorderLayout(); //Layout for this main AddStatsView
     
     /** Constructor for the AddStatsView class
       * @param StatsModel model     Links the main Stats model to this view
       */
     public AddStatsView (StatsModel model)
     {
          this.model = model; //sets this view's model
          this.model.setAddStatsGUI(this); //connects this view to the stats model
          this.layoutView(); //sets this view's looks
          this.disableComponents(); //disables all components
          this.practiceTime.setEnabled(true); //enables first component
          this.registeredControllers(); //creates component controllers
     }
     
     /**Sets up this AddStatsView's layout (looks) */
     public void layoutView()
     {
          //sets panel size and layout
          this.setPreferredSize(new Dimension(800,120));                   
          this.setLayout(mainLayout);
          
          north.setBorder(BorderFactory.createTitledBorder("Input"));
          south.setBorder(BorderFactory.createTitledBorder("System Message"));
          
          //initial message for inputting a practice time
          systemMessage.setText("Please enter a practice time in the form (mm:ss). Leave empty if you would not like to add a practice time.");
          
          //adds components to north panel
          north.add(askPracticeTime);
          north.add(practiceTime);
          
          north.add(askRaceTime);
          north.add(raceTime);
                              
          north.add(add);
          
          //adds system message label to south panel
          south.add(systemMessage);
          
          //adds all panels to this main view's panel
          this.add(north, BorderLayout.NORTH);
          this.add(south, BorderLayout.CENTER);
     }
     
     /**Creates controllers for the required components in the Add Stats View class*/
     public void registeredControllers()
     {
          //add stats button controller
          StatsButtonController addStatClicked = new StatsButtonController (this.model, this.add, "addStat");
          this.add.addActionListener(addStatClicked);
          
          //practice time text field controller
          StatsTextFieldController practiceTimeChanged = new StatsTextFieldController (this.model, this.practiceTime, "practiceTime");
          this.practiceTime.addActionListener(practiceTimeChanged);
          
          //race time text field controller
          StatsTextFieldController raceTimeChanged = new StatsTextFieldController (this.model, this.raceTime, "raceTime");
          this.raceTime.addActionListener(raceTimeChanged);
     }
     
     /** Updates this Add Stats View based on values passed in: which components to enable/disable (based on user input), and whether to add a message
       * @param boolean correctInput     whether or not the user entered the correct input
       * @param JTextField currentTextField     the current text field being worked with
       * @param JComponent nextComponent     the next component to enable (either a text field or a button)
       * @param String message     displays a message if the user input was incorrect, or none if it was correct
       */
     public void update (boolean correctInput, JTextField currentTextField, JComponent nextComponent, String message)
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
     
     /** Disables all this view's components*/
     private void disableComponents ()
     {
          this.practiceTime.setEnabled(false);
          this.raceTime.setEnabled(false);
          this.add.setEnabled(false);
     }
}