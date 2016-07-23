import javax.swing.*;

/**
 * StatsModel Class
 * The main model that controls all the functions of the primary program (Database). The user can add and remove runner
 * practice times and race times, which will be added to their list of times. The user can also set a comment about the 
 * runner that tells them about their performance. This model contains the methods that add and remove stats, as well as
 * convert them from the form (mm:ss) to a float value (used for calculations).
 * 
 * @author James Fong, Prajvin Jalan & Simon Zhang
 * @since January 15, 2014
 */
public class StatsModel extends Object
{
     Runner runner; //the runner object connected to this Stats model
     
     StatsView statsView; //this model's main Stats view
     AddStatsView addStatsView; //this model's Add Stats view
     RemoveStatsView removeStatsView; //this model's Remove Stats view
     
     JFrame addStats; //JFrame that holds the Add Stats view
     JFrame removeStats; //JFrame that holds the Remove Stats view
     
     //For the current stats that are being worked with
     float pracTime, raceTime;
     int logPracTime, logRaceTime;
     
     //Used to check input
     StatsPrompt prompt;
     
     //Connects this stats model to the main database (so it can be updated whenever stats is updated)
     DataModel dataModel;
     
     //Used to check whether to print race graph or not
     boolean selectRace = false;
     
     /** Constructor for StatsModel class
       * @param Runner runner     the runner from the main Database that this model is connected to
       * @param DataModel dataModel     the main Database that this model is connected to
       */ 
     public StatsModel (Runner runner, DataModel dataModel)
     {
          super();
          this.runner = runner;
          this.prompt = new StatsPrompt(this); //creates the StatsPrompt for inputs associated with this Stats Model/View
          this.dataModel = dataModel;
     }
     
     /** Sets this model's StatsView
       * @param StatsView statsView     the stats view which will be connected to this model
       */ 
     public void setGUI (StatsView statsView) 
     {
          this.statsView = statsView;
     }
     
     /** Sets this model's AddStatsView
       * @param AddStatsView addStatsView     the add stats view which will be connected to this model
       */
     public void setAddStatsGUI (AddStatsView addStatsView) 
     {
          this.addStatsView = addStatsView;
     }
     
     /** Sets this model's RemoveStatsView
       * @param RemoveStatsView removeStatsView     the remove stats view which will be connected to this model
       */
     public void setRemoveStatsGUI (RemoveStatsView removeStatsView) 
     {
          this.removeStatsView = removeStatsView;
     }
     
     /** Sets the student's current practice time being added and updates the add stats view
       * @param String pracTime     the practice time that must be added to the student's stats
       */
     public void setCurrentPractice (String pracTime)
     {
          this.pracTime = this.convertToFloat(pracTime); //converts the practice time (mm:ss) to a float value and saves
          this.addStatsView.update(true, this.addStatsView.practiceTime, this.addStatsView.raceTime, "Please enter a race time in the form (mm:ss). Leave empty if you would not like to add a race time.");
     }
     
     /** Sets the student's current race time being added and updates the add stats view
       * @param String raceTime     the race time that must be added to the student's stats
       */
     public void setCurrentRace (String raceTime)
     {
          this.raceTime = this.convertToFloat(raceTime); //converts the race time (mm:ss) to a float value and saves
          this.addStatsView.update(true, this.addStatsView.raceTime, this.addStatsView.add, "Please click 'Add' to continue.");
     }
     
     /** Sets the student's current practice time log to be removed and updates the remove stats view
       * @param int logPracTime     the practice time log that must be removed from the student's stats
       */
     public void setCurrentLogPractice (int logPracTime)
     {
          this.logPracTime = logPracTime;
          this.removeStatsView.update(true, this.removeStatsView.practiceTime, this.removeStatsView.raceTime, "Please enter the log of the race time you would like to remove. Leave empty if you would not like to remove a race time.");
     }
     
     /** Sets the student's current race time log to be removed and updates the remove stats view
       * @param int logRaceTime     the race time log that must be removed from the student's stats
       */
     public void setCurrentLogRace (int logRaceTime)
     {
          this.logRaceTime = logRaceTime;
          this.removeStatsView.update(true, this.removeStatsView.raceTime, this.removeStatsView.remove, "Please click 'Remove' to continue.");
     }
     
     /** Adds the student's stats based on whatever current stats were assigned (practice and race times) */
     public void addStats()
     {
          boolean notFound = true; //used to check if an unused value in the list was found (when adding new values)
                    
          //Runs through practice times list in runner stats, and looks for empty spots to replace with new stat (otherwise just adds to list)
          for (int x = 0; x < this.runner.runnerStats.practiceTimes.size(); x++) {
               //if the new practice time is 0 (user does not input value), then will break out of loop without adding an empty spot (gets added later)
               if (this.pracTime == 0.0) {
                    notFound = false;
                    break;
               }
               //if the practice time is not 0 (above loop not run), and the spot in the practice time list is 0 - will replace 0 value with stat
               //repeat ^ for other lists
               if (this.runner.runnerStats.practiceTimes.get(x) == 0.0) {
                    notFound = false;
                    this.runner.runnerStats.practiceTimes.set(x, this.pracTime);
                    this.runner.runnerStats.kmPaces.set(x, this.runner.runnerStats.calcKmPace(this.pracTime));
                    this.runner.runnerStats.raceDistances.set(x, this.runner.runnerStats.raceD);
                    break;
               }
               //could not find an empty spot for stat
               else {
                    notFound = true;
               }
          }
          //adds the stats to the end of the lists
          if (notFound == true) {
               this.runner.runnerStats.practiceTimes.add(this.pracTime);
               this.runner.runnerStats.kmPaces.add(this.runner.runnerStats.calcKmPace(this.pracTime));
               this.runner.runnerStats.raceDistances.add(this.runner.runnerStats.raceD);
          }
          
          //Runs through race times list in runner stats, and looks for empty spots to replace with new stat (otherwise just adds to list)
          notFound = true; //resets variable (in case size of the list is 0 and loop doesn't run)
          for (int x = 0; x < this.runner.runnerStats.raceTimes.size(); x++) {
               //if the new race time is 0 (user does not input value), then will break out of loop without adding an empty spot (gets added later)
               if (this.raceTime == 0.0) {
                    notFound = false;
                    break;
               }
               //replaces empty spot for stat with race time
               if (this.runner.runnerStats.raceTimes.get(x) == 0.0) {
                    notFound = false;
                    this.runner.runnerStats.raceTimes.set(x, this.raceTime);
                    break;
               }
               //could not find empty spot for race time
               else {
                    notFound = true;
               }
          }
          //adds race time to the end of the list if empty spot was not found
          if (notFound == true) {
               this.runner.runnerStats.raceTimes.add(this.raceTime);
          }
          
          //updates lists in table
          this.updateTables();
          
          this.addStats.setVisible(false); //hides add frame after student is added
          this.statsView.updateStats(); //updates stats view
     }
     
     /** Removes the student stats based on values that were input for practice and race time logs */
     public void removeStats()
     {
          //removes practice time as long as the log isn't 0 (which means nothing to remove)
          //repeats for other lists connected to practice time
          if (this.logPracTime != 0) {
               this.runner.runnerStats.practiceTimes.remove(this.logPracTime - 1);
               this.runner.runnerStats.kmPaces.remove(this.logPracTime - 1);
               this.runner.runnerStats.raceDistances.remove(this.logPracTime - 1);
          }
          
          //removes race time as long as the log isn't 0 (which means nothing to remove)
          if (this.logRaceTime != 0) {
               this.runner.runnerStats.raceTimes.remove(this.logRaceTime - 1);
          }
          
          //updates lists in table
          this.updateTables();
          
          this.removeStats.setVisible(false); //hides add frame after student is added
          this.statsView.updateStats(); //updates stats view
     }
     
     /**Finds the largest list, and adds empty values to the other lists to equate all their sizes (so the table can add rows properly)*/
     private void updateTables()
     {
          int maxSize = 0; //used for adding empty items to lists until all are the same size
          int practiceSize = 0; //size of practice times list excluding zeros
          int raceSize = 0; //size of race times list excluding zeros
          
          //Determines the size of the practice times list (excluding zeros)
          for (int x = 0; x < this.runner.runnerStats.practiceTimes.size(); x++) {
               if (this.runner.runnerStats.practiceTimes.get(x) != 0.0f) {
                    practiceSize++;
               }
          }
          
          //Determines the size of the race times list (excluding zeros)
          for (int x = 0; x < this.runner.runnerStats.raceTimes.size(); x++) {
               if (this.runner.runnerStats.raceTimes.get(x) != 0.0f) {
                    raceSize++;
               }
          }
          
          //Determines the larger of the two lists
          maxSize = practiceSize;
          if (raceSize > maxSize) {
               maxSize = raceSize;
          }
          
          //Adds empty values to equate all list sizes
          while (this.runner.runnerStats.practiceTimes.size() < maxSize) {
               this.runner.runnerStats.practiceTimes.add(0.0f);
               this.runner.runnerStats.kmPaces.add(0.0f);
               this.runner.runnerStats.raceDistances.add(0.0f);
          }
          while (this.runner.runnerStats.raceTimes.size() < maxSize) {
               this.runner.runnerStats.raceTimes.add(0.0f);
          }
     }
     
     /**Opens Add Stats frame (to add student stats)*/
     public void openAddStats () 
     {
          //creates frame and view
          this.addStats = new JFrame("Add Stats");
          this.addStatsView = new AddStatsView(this);
          
          this.addStats.setContentPane(this.addStatsView);
          this.addStats.pack();
          this.addStats.setVisible(true);
          this.addStats.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     }
     
     /**Opens Remove Stats frame (to remove student stats)*/
     public void openRemoveStats () 
     {
          //creates frame and view
          this.removeStats = new JFrame("Remove Stats");
          this.removeStatsView = new RemoveStatsView(this);
          
          this.removeStats.setContentPane(this.removeStatsView);
          this.removeStats.pack();
          this.removeStats.setVisible(true);
          this.removeStats.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     }
     
     /**Converts a string value (mm:ss) to float value (so it can be used for calculations)
       * @param String value     the string value to be converted
       * @returns     the float value converted from the string value
       */ 
     public float convertToFloat (String value)
     {
          //takes string values before and after colon (:)
          String sDigit = value.substring(0,value.indexOf(":"));
          String sDecimal = value.substring(value.indexOf(":") + 1);
          //changes string value (number) to float value
          float fDigit = Float.parseFloat(sDigit);
          float fDecimal = Float.parseFloat(sDecimal)/60;
          
          //returns the new float value
          return (fDigit + fDecimal);
     }
     
     /**Sets if the race times graph is to be shown
       * @param boolean check     true or false based on whether to show the race times graph or not
       */ 
     public void setRaceGraph (boolean check)
     {
          this.selectRace = check;
          this.statsView.updateStats(); //updates stats view
          
     }
}