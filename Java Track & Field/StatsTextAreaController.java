import javax.swing.*;
import javax.swing.event.*;

/**
 * StatsTextAreaController Class
 * This class is used as a controller for the text areas within the Runner Statistics program.
 * 
 * @author Prajvin Jalan
 * @since January 15, 2014
 */
public class StatsTextAreaController extends Object implements DocumentListener
{
     StatsModel model; //model linked to the component
     JTextArea textArea; //current text area linked to the controller
     String type; //the specific type of button being clicked
     
     /**
     * Main constructor for the controller - links the component to the model.
     * @param StatsModel model      the model for the Stats View
     * @param JTextArea textArea      the text area being linked to the controller
     */
     public StatsTextAreaController (StatsModel model, JTextArea textArea)
     {
          super();
          this.model = model;
          this.textArea = textArea;
     }
     
     /**
     * Updates the stats view when the document is changed (changes comment)
     * @param DocumentEvent e      the change event of a document (text area)
     */
     public void changedUpdate(DocumentEvent e) 
     {
          this.model.runner.runnerStats.setComment(this.textArea.getText());
     }
     
     /**
     * Updates the stats view when text is added to the document (changes comment)
     * @param DocumentEvent e      the insert event of a document (text area)
     */
     public void insertUpdate(DocumentEvent e) 
     {
          this.model.runner.runnerStats.setComment(this.textArea.getText());
     }
     
     /**
     * Updates the stats view when text is removed from the document (changes comment)
     * @param DocumentEvent e      the remove event of a document (text area)
     */
     public void removeUpdate(DocumentEvent e) 
     {
          this.model.runner.runnerStats.setComment(this.textArea.getText());
     }
}