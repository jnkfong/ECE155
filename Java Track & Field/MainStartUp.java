import javax.swing.*;

/**
 * MainStartup Class
 * The startup class of the entire program, that creates the frame containing the main Menu. This is the initial
 * class that is run when the program is opened.
 * 
 * @author James Fong, Prajvin Jalan & Simon Zhang
 * @since January 15, 2014
 */
public class MainStartUp
{
  /**Main method to start up program **/
     public static void main(String[] args)
     {
          JFrame mainFrame = new JFrame ("X-Country Tracker v1.0"); //Frame for the main menu 
          MainModel mainModel = new MainModel(); //Main model class containing methods for main menu
          MainView mainView = new MainView(mainModel); //Main view class that is the GUI for the main menu
          
          mainFrame.setContentPane(mainView);
          mainFrame.pack();
          mainFrame.setVisible(true);
          mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     }
}