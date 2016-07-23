import javax.swing.*;
import java.awt.event.*;

/**
 * StatsCheckBoxClicked Class
 * This class is used as a controller for the check boxes within the Runner Statistics program.
 * 
 * @author James Fong & Prajvin Jalan
 * @since January 15, 2014
 */
public class StatsCheckBoxClicked extends Object implements ItemListener
{
     StatsModel model; //model linked to the component
     JCheckBox checkbox; //current check box linked to the controller
     
     /**
     * Main constructor for the controller - links the component to the model.
     * @param StatsModel model      the model for the Stats View
     * @param JCheckBox checkbox      the check box being linked to the controller
     */
     public StatsCheckBoxClicked (StatsModel model, JCheckBox checkbox)
     {
          this.model = model;
          this.checkbox = checkbox;
     }
     
     /**
     * Updates the stats view accordingly when a check box is clicked
     * @param ItemEvent e      the change event of a check box (checked or unchecked)
     */
     public void itemStateChanged (ItemEvent e)
     {
          if (e.getStateChange() == ItemEvent.SELECTED) {
               this.model.setRaceGraph(true);
          }
          else {
               this.model.setRaceGraph(false);
          }
     }
}