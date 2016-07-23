/**
 * Runner Class
 * The class that extends the Object class to create a Runner Object with various general attributes; including student
 * number, last name, first name, division, age, and race distance. This object is created when a new runner is added to
 * the main Database.
 * 
 * @author James Fong & Prajvin Jalan
 * @since January 15, 2014
 */
public class Runner extends Object
{
     //this runner's RunnerStats object (containing all their stats)
     RunnerStats runnerStats;
     
     //sets this runner's regular information (student number, last name, first name, division, age, race distance)
     int studNum;
     String last, first, division;
     byte age;
     float raceD;
     
     /**Constructor for the runner object
       * @param int studNum     the runner's student number
       * @param String last     the runner's last name
       * @param String first     the runner's first name
       * @param byte age     the runner's age
       * @param String division     the runner's division
       * @param float raceD     the runner's race distance
       */ 
     public Runner(int studNum, String last, String first, byte age, String division, float raceD)
     {
          super();
          this.studNum = studNum;
          this.last = last;
          this.first = first;
          this.age = age;
          this.division = division;
          this.raceD = raceD;
          this.runnerStats = new RunnerStats (this.raceD); //creates a runnerStats object connected to their race distance
     }
}