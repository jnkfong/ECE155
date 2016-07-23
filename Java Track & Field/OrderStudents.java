import java.util.*;

/**
 * OrderStudents Class
 * This is the class that orders runners within the database. The various methods use lists from the DataModel class to
 * order runners by their general information, including last name, first name, age, race distance, and best km pace
 * within their division.
 * 
 * @author James Fong, Prajvin Jalan & Simon Zhang
 * @since January 15, 2014
 */
public class OrderStudents
{
     DataModel model; //database model that this class is linked to (to access lists)
     
     /**
      * Constructor for OrderStudents class
      * @param DataModel     dataModel the database model linked to this class
      */ 
     public OrderStudents (DataModel dataModel)
     {
          super();
          this.model = dataModel;
     }
     
     /**
      * Sorts the data model's array list of string values based on the type of information to sort by (last name, first name, rank)
      * @param String type     the type of information (last name, first name, rank)
      */ 
     public void sortString(String type)
     {
          Runner temp; //temporary runner to save old runner (when swapping)
          boolean swap; //to check if a swap was conducted or not
          
          //algorithm that orders based on student last name
          if (type.equals("last")) {
               do {
                    swap = false;
                    for (int position = 0; position < this.model.listString.size() - 1; position++) {
                         if (this.model.listString.get(position).last.compareToIgnoreCase (this.model.listString.get(position+1).last) > 0) {
                              temp = this.model.listString.get(position);
                              this.model.listString.set(position, this.model.listString.get(position+1));
                              this.model.listString.set(position+1, temp);
                              swap = true;
                         }
                    }
               } while (swap == true);
          }
          //algorithm that orders based on student first name
          else if (type.equals("first")) {
               do {
                    swap = false;
                    for (int position = 0; position < this.model.listString.size() - 1; position++) {
                         if (this.model.listString.get(position).first.compareToIgnoreCase (this.model.listString.get(position+1).first) > 0) {
                              temp = this.model.listString.get(position);
                              this.model.listString.set(position, this.model.listString.get(position+1));
                              this.model.listString.set(position+1, temp);
                              swap = true;
                         }
                    }
               } while (swap == true);
          }
          //algorithm that orders based on student division
          else {
               do {
                    swap = false;
                    for (int position = 0; position < this.model.listString.size() - 1; position++) {
                         if (this.model.listString.get(position).division.compareToIgnoreCase (this.model.listString.get(position+1).division) > 0) {
                              temp = this.model.listString.get(position);
                              this.model.listString.set(position, this.model.listString.get(position+1));
                              this.model.listString.set(position+1, temp);
                              swap = true;
                         }
                    }
               } while (swap == true);
          }
     }
     
     /**Sorts the data model's array list of byte values (by age)*/
     public void sortByte()
     {
          Runner temp; //temporary runner to save old runner (when swapping)
          boolean swap; //to check if a swap was conducted or not
          
          //algorithm that orders based on student age
          do {
               swap = false;
               for (int x = 1; x < this.model.listByte.size(); x++) {
                    if (this.model.listByte.get(x-1).age > this.model.listByte.get(x).age) {
                         temp = this.model.listByte.get(x-1);
                         this.model.listByte.set(x-1, this.model.listByte.get(x));
                         this.model.listByte.set(x, temp);
                         swap = true;
                    }
               }
          }while (swap == true);
     }
     
     /**Sorts the passed in array list of float values
       * @param ArrayList <Runner> floatList     the list of float values to be sorted
       */
     public void sortFloat(ArrayList <Runner> floatList)
     {
          Runner temp; //temporary runner to save old runner (when swapping)
          boolean swap; //to check if a swap was conducted or not
          
          //algorithm that orders the float values
          do {
               swap = false;
               for (int x = 1; x < floatList.size(); x++) {
                    if (floatList.get(x-1).raceD > floatList.get(x).raceD) {
                         temp = floatList.get(x-1);
                         floatList.set(x-1, floatList.get(x));
                         floatList.set(x, temp);
                         swap = true;
                    }
               }
          }while (swap == true);
     }
}