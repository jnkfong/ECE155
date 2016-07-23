import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

/**
 * StatsView Class
 * The view for the Runner Statistics program. Includes the buttons for all the program functions (add stats, remove stats), 
 * a table to display the various statistical information (race distances, km paces, practice times, race times), a panel
 * for a graph (visual representation) of the runner's practice and race times, and text areas for their common statistics
 * and the user's comment pertaining to their performance.
 * 
 * @author James Fong, Prajvin Jalan & Simon Zhang
 * @since January 15, 2014
 */
public class StatsView extends JPanel
{
     StatsModel model; //Stats model connected to this view of runner stats
     JButton add = new JButton ("Add"); //JButton that opens add stats view
     JButton remove = new JButton ("Remove"); //JButton that opens remove stats view
     
     JTextArea generalStats = new JTextArea(9,30); //text area for general stats (best/worst/average)
     JTextArea comment = new JTextArea(9,30); //text area for user to input comment about runner
     
     JPanel graph = new JPanel (); //JPanel that holds Graph
     JPanel statistics = new JPanel(); //JPanel that holds Stats Tables
     JPanel dispComment = new JPanel(); //JPanel that holds Comments
     JPanel genStats = new JPanel(); //JPanel that holds General Stats
     
     JPanel statsButtons = new JPanel(); //JPanel for add and remove stats buttons
     JPanel midStats = new JPanel(); //JPanel for stats tables and graph
     JPanel extraView = new JPanel(); //JPanel for comment and general stats
     
     //Sets up the default table model and JTable for all the runner stats
     Object [][] rowStats = null; //Initial row stats for table
     String [] columnStats = {"Log #", "Race Distance (km)", "Km Pace (min/km)", "Practice Times (min)", "Race Times (min)"}; //Used to display the headers on the JTable
     DefaultTableModel dtm = new DefaultTableModel(rowStats, columnStats); //Stores the statistics in view of a table format with a header
     JTable stats = new JTable(dtm); //Displays the information found in the DefaultTableModel in a table format
     JPanel statsTable = new JPanel(); //Used for stats JTable
     
     BorderLayout statsLayout = new BorderLayout(); //Used to organize the layout of the GUI
     BorderLayout genLayout = new BorderLayout(); //Used to organize the layout of the GUI
     
     JCheckBox raceChoice = new JCheckBox("Select to Display Race Times Graph"); //JCheckBox for displaying a certain graph
     
     /** Constructor for the StatsView class
       * @param StatsModel model Used to modify the information found in the StatsModel
       */     
     public StatsView (StatsModel model)
     {
          this.setPreferredSize(new Dimension (1000, 700)); //sets view size
          this.model = model; //sets this view's model
          this.model.setGUI(this); //connects the Stats model to this view
          this.layoutView(); //this view's looks
          this.registeredControllers(); //controllers for components
          this.updateStats(); //updates this view
     }
     
     /** Draws the initial layout of StatsView GUI**/
     public void layoutView()
     {
          //sets initial layouts
          this.setLayout(statsLayout);
          midStats.setLayout(genLayout);
          
          //Adds buttons section on the GUI
          statsButtons.setBorder(BorderFactory.createTitledBorder("Stats Options"));
          statsButtons.add(add);
          statsButtons.add(remove);
          
          //Resizes table columns
          stats.getColumnModel().getColumn(0).setPreferredWidth(45);
          stats.getColumnModel().getColumn(1).setPreferredWidth(140);
          stats.getColumnModel().getColumn(2).setPreferredWidth(140);
          stats.getColumnModel().getColumn(3).setPreferredWidth(140);
          stats.getColumnModel().getColumn(4).setPreferredWidth(140);
          
          //Creates Table/Panel Set for information
          stats.setEnabled(false);
          JTableHeader header = stats.getTableHeader();
          header.setBackground(Color.LIGHT_GRAY);
          JScrollPane pane = new JScrollPane(stats);
          stats.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
          pane.setPreferredSize(new Dimension(450,400));
          statsTable.add(pane);
          
          //Holds Stats Table
          statistics.setBorder(BorderFactory.createTitledBorder("Main Runner Statistics"));
          statistics.add(statsTable);
          midStats.add(statistics, BorderLayout.WEST);
          
          //Sets up graph panel
          graph.setBorder(BorderFactory.createTitledBorder("Graph"));
          graph.setPreferredSize(new Dimension(500,400));
          graph.add(raceChoice);
          midStats.add(graph, BorderLayout.EAST);
          
          //Sets up comment panel
          dispComment.setBorder(BorderFactory.createTitledBorder("Comments"));
          dispComment.add(comment);
          
          //Sets up general stats panel
          genStats.setBorder(BorderFactory.createTitledBorder("Common Statistics"));
          generalStats.setEditable(false);
          genStats.add(generalStats);
          
          //Creates entire bottom view panel(comment, general stats)
          extraView.add(dispComment);
          extraView.add(genStats);          
          
          //Adds all panels to this main Stats View panel
          this.add(statsButtons,BorderLayout.NORTH);
          this.add(midStats, BorderLayout.CENTER);
          this.add(extraView, BorderLayout.SOUTH);
     }
     
     /**Creates controllers for the required components in the Stats View class*/
     public void registeredControllers()
     {
          //add stat button controller
          StatsButtonController addClicked = new StatsButtonController (this.model, this.add, "add");
          this.add.addActionListener(addClicked);
          
          //remove stat button controller
          StatsButtonController removeClicked = new StatsButtonController (this.model, this.remove, "remove");
          this.remove.addActionListener(removeClicked);
          
          //comment text area controller
          StatsTextAreaController commentChanged = new StatsTextAreaController (this.model, this.comment);
          this.comment.getDocument().addDocumentListener(commentChanged);
          
          //graph choice check box controller
          StatsCheckBoxClicked drawRace = new StatsCheckBoxClicked(this.model, this.raceChoice);
          this.raceChoice.addItemListener(drawRace);
     }
     
     /**Redraws StatsView*/
     public void updateStats()
     {
          //deletes original statistics table
          if (dtm.getRowCount() > 0) {
               for (int i = dtm.getRowCount(); i > 0; i--) {
                    dtm.removeRow(i - 1);
               }
          }
          
          //prints table as long as there is one row of information (both practice times and race times have at least one piece of information)
          if (this.model.runner.runnerStats.practiceTimes.size() != 0 && this.model.runner.runnerStats.raceTimes.size() != 0)
          {
               //recreates statistics table
               for (int x = 0; x < this.model.runner.runnerStats.practiceTimes.size(); x++) {
                    dtm.addRow (new Object[] {x+1, this.model.runner.runnerStats.raceDistances.get(x),
                         this.model.dataModel.convertToString(this.model.runner.runnerStats.kmPaces.get(x)),
                         this.model.dataModel.convertToString(this.model.runner.runnerStats.practiceTimes.get(x)),
                         this.model.dataModel.convertToString(this.model.runner.runnerStats.raceTimes.get(x))});
               }
               
               //prints general statistics; takes stat from RunnerStats and uses dataModel to convert it to a string value (so it prints in the form mm:ss)
               this.generalStats.setText("Best Practice Time: " + this.model.dataModel.convertToString(this.model.runner.runnerStats.getBest("practice")) + "\n" + 
                                         "Worst Practice Time: " + this.model.dataModel.convertToString(this.model.runner.runnerStats.getWorst("practice")) + "\n" + 
                                         "Average Practice Time: " + this.model.dataModel.convertToString(this.model.runner.runnerStats.getAverage("practice")) + "\n" + 
                                         "Average of Recent 3 Practice Times: "+ this.model.dataModel.convertToString(this.model.runner.runnerStats.getAverageThree("practice"))+"\n\n"+
                                         "Best Race Time: " + this.model.dataModel.convertToString(this.model.runner.runnerStats.getBest("race")) + "\n" + 
                                         "Worst Race Time: " + this.model.dataModel.convertToString(this.model.runner.runnerStats.getWorst("race")) + "\n" + 
                                         "Average Race Time: " + this.model.dataModel.convertToString(this.model.runner.runnerStats.getAverage("race")) + "\n" +
                                         "Average of Recent 3 Race Times: "+ this.model.dataModel.convertToString(this.model.runner.runnerStats.getAverageThree("race")));
               
               //creates graph out of practice times (removes previous and creates a new one)
               this.graph.removeAll();
               graph.add(raceChoice);               
               if(this.model.selectRace == false)
               {
                    //Draws the graph by passing through the practice times statistics
                    Graph practiceGraph = new Graph (this.model.runner.runnerStats.practiceTimes, this.model.dataModel);
                    practiceGraph.setPreferredSize(new Dimension(450, 400)); 
                    this.graph.add(practiceGraph);
                    this.raceChoice.setText("Select to Display Race Times Graph");
               }
               else //selectRace is true
               {
                    //Draws the graph by passing through the race times statistics
                    Graph raceGraph = new Graph (this.model.runner.runnerStats.raceTimes, this.model.dataModel);
                    raceGraph.setPreferredSize(new Dimension(450, 400)); 
                    this.graph.add(raceGraph);
                    this.raceChoice.setText("Deselect to Display Practice Times Graph");
               }
          }
          
          //sets the comment text area's text to any previously written comment
          this.comment.setText(this.model.runner.runnerStats.comment);
          
          //updates dataModel (since there may be a new best km pace)
          this.model.dataModel.dataView.updateData();
     }
}