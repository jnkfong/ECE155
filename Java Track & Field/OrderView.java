import javax.swing.*;
import java.awt.*;

/**
 * OrderView Class
 * The view that allows the user to order runners by their general information; including options for student number, 
 * last name, first name, age, division, race distance, and best km pace within their division.
 * 
 * @author Prajvin Jalan
 * @since January 15, 2014
 */
public class OrderView extends JPanel
{
     DataModel model; //Main database model connected to this view of the database
     
     JButton orderStudNum = new JButton("Student Number"); //JButton to order by student number
     JButton orderLast = new JButton("Last Name"); //JButton to order by student last name
     JButton orderFirst = new JButton("First Name"); //JButton to order by first name
     JButton orderAge = new JButton("Age"); //JButton to order by student age
     JButton orderDivision= new JButton("Division"); //JButton to order by student division
     JButton orderRaceD = new JButton("Race Distance"); //JButton to order by student race distance
     JButton orderBestKmPace = new JButton("Best Km Pace"); //JButton to order by student's best km pace
     
     /** Constructor for the OrderView class
       * @param DataModel model     Links the main Database model to this view
       */
     public OrderView (DataModel model)
     {
          this.model = model; //sets this view's model
          this.model.setOrderGUI(this); //connects this view to its model (Database)
          this.layoutView(); //sets this view's looks
          this.registeredControllers(); //creates component controllers
     }
     
     /**Sets up this OrderView's layout (looks) */
     public void layoutView()
     {
          //sets this view's size
          this.setPreferredSize(new Dimension(1000,60));
          
          //adds all the order buttons to this view
          this.add(orderStudNum);
          this.add(orderLast);
          this.add(orderFirst);
          this.add(orderAge);
          this.add(orderDivision);
          this.add(orderRaceD);
          this.add(orderBestKmPace);
     }
     
     /**Creates controllers for the required components in the Order View class*/
     public void registeredControllers()
     {
          //order by student number button controller
          DataButtonController orderStudNumClicked = new DataButtonController (this.model, this.orderStudNum, "orderStudNum");
          this.orderStudNum.addActionListener(orderStudNumClicked);
          
          //order by student last name button controller
          DataButtonController orderLastClicked = new DataButtonController (this.model, this.orderLast, "orderLast");
          this.orderLast.addActionListener(orderLastClicked);
          
          //order by student first name button controller
          DataButtonController orderFirstClicked = new DataButtonController (this.model, this.orderFirst, "orderFirst");
          this.orderFirst.addActionListener(orderFirstClicked);
          
          //order by student age button controller
          DataButtonController orderAgeClicked = new DataButtonController (this.model, this.orderAge, "orderAge");
          this.orderAge.addActionListener(orderAgeClicked);
          
          //order by student division button controller
          DataButtonController orderDivisionClicked = new DataButtonController (this.model, this.orderDivision, "orderDivision");
          this.orderDivision.addActionListener(orderDivisionClicked);
          
          //order by student race distance button controller
          DataButtonController orderRaceDClicked = new DataButtonController (this.model, this.orderRaceD, "orderRaceD");
          this.orderRaceD.addActionListener(orderRaceDClicked);
          
          //order by student's best km pace button controller
          DataButtonController orderBestKmPaceClicked = new DataButtonController (this.model, this.orderBestKmPace, "orderBestKmPace");
          this.orderBestKmPace.addActionListener(orderBestKmPaceClicked);
     }
}