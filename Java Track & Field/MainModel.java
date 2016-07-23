import javax.swing.*;

/**
 * MainModel Class
 * The model for the main menu that opens the main Database program. Has methods to open the main program, and quit the
 * program (used mainly for aesthetics since it displays the STORM logo for the cross country team).
 * 
 * @author James Fong, Prajvin Jalan & Simon Zhang
 * @since January 15, 2014
 */
public class MainModel extends Object
{
     MainView mainView; //Main menu view connected to this main menu model
     DataModel dataModel; //Instance of the model class for the database
     DataView dataView; //Instance of the GUI for the database
     JFrame dbFrame; //Frame for the database menu
     
     /** Constructor for the MainModel class **/
     public MainModel()
     {
          super();
     }
     
     /**Assigns the GUI to this class**/
     public void setGUI (MainView mainView)
     {
          this.mainView = mainView;
     }
     
     /**Creates all database classes (model, view)**/
     public void openDatabase()
     {
          dbFrame = new JFrame ("Database");
          
          dataModel = new DataModel();
          dataView = new DataView(dataModel);
          
          dbFrame.setContentPane(dataView);
          dbFrame.pack();
          dbFrame.setResizable(false);
          dbFrame.setVisible(true);
          dbFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     }
     
     /**Quits the main menu of the program**/
     public void quit()
     {
          System.exit(0);
     }
}