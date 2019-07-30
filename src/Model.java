
import java.util.*;
import java.net.*;
import java.text.*;
import java.lang.*;
import java.io.*;
import java.sql.*;

public class Model 
{
	private Connection conDB;   // Connection to the database system.
    private String       url;   // URL: Which database?
    private LinkedList<String> cats = new LinkedList<String>();   // List of categories.
    private Integer   custID;   // Customer ID.
    private String  custName;   // Name of that customer.
    private String  custCity;   // City of that customer.
    private String offeredBy;   // Club offering min price
    
    public Model()
    {
    	try {
            // Register the driver with DriverManager.
            Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // URL: Which database?
        url = "jdbc:db2:c3421m";

        // Initialise the connection.
        try {
            // Connect with a fall-thru id & password
            conDB = DriverManager.getConnection(url);
        } catch(SQLException e) {
            System.out.print("\nSQL: database connection error.\n");
            System.out.println(e.toString());
            System.exit(0);
        }    

        // Let's have auto commit turned off.  No particular reason here.
        try {
            conDB.setAutoCommit(false);
        } catch(SQLException e) {
            System.out.print("\nFailed trying to turn autocommit off.\n");
            e.printStackTrace();
            System.exit(0);
        }
    }
    /**
     * @return boolean if the customer was found or not.
     */
    public boolean find_customer(int a) 
    {
    	custID = a;
        String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
        ResultSet         answers   = null;   // A cursor.

        boolean           inDB      = false;  // Return.

        queryText =
            "SELECT *       "
          + "FROM yrb_customer "
          + "WHERE cid = " + getCustID();

        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Execute the query.
        try {
            answers = querySt.executeQuery();
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Any answer?
        try {
            if (answers.next()) {
                inDB = true;
                custName = answers.getString("name");
                custCity = answers.getString("city");
            } else {
                inDB = false;
                custName = null;
                custCity = null;
            }
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Close the cursor.
        try {
            answers.close();
        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // We're done with the handle.
        try {
            querySt.close();
        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        return inDB;
    }
    /**
     * Fetches  the categories and saves them in cats list
     */
    public void fetch_categories() 
    {
    	String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
        ResultSet         answers   = null;   // A cursor.
        queryText =
            "SELECT distinct cat       "
          + "FROM yrb_category ";
        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);
        } catch(SQLException e) {
            System.out.println("SQL#2 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Execute the query.
        try {
            answers = querySt.executeQuery();
        } catch(SQLException e) {
            System.out.println("SQL#2 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
    	}
        // Any answer?
        try
        {
            for(int i  = 0;answers.next(); i++) 
            {
            	String x = answers.getString("cat");
            	cats.add(x);
            }
        } catch(SQLException e) {
            System.out.println("SQL#2 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }
        // Close the cursor.
        try {
            answers.close();
        } catch(SQLException e) {
            System.out.print("SQL#2 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // We're done with the handle.
        try {
            querySt.close();
        } catch(SQLException e) {
            System.out.print("SQL#2 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }
    }
    /**
     * 
     * @param book
     * @param catChosen
     * @return a list of books with same name and category
     */
    public LinkedList<String> find_book(String book, String catChosen) 
    {
    	LinkedList<String> out = new LinkedList<String>();
    	String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
        ResultSet         answers   = null;   // A cursor.
        queryText =
            "SELECT title, year, language, weight       "
          + "FROM yrb_book "
          + "WHERE title = '" + book + "' and cat = '"+ catChosen +"'";
        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);
        } catch(SQLException e) {
            System.out.println("SQL#3 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }
        // Execute the query.
        try {
            answers = querySt.executeQuery();
        } catch(SQLException e) {
            System.out.println("SQL#3 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
    	}
        // Any answer?
        try {
            for(;answers.next();) 
            {
               out.add(answers.getString("title")
            		   +" "+answers.getInt("year")
            		   +" "+answers.getString("language")
            		   +" "+answers.getInt("weight"));
            }
        } catch(SQLException e) {
            System.out.println("SQL#3 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }
     // Close the cursor.
        try {
            answers.close();
        } catch(SQLException e) {
            System.out.print("SQL#2 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // We're done with the handle.
        try {
            querySt.close();
        } catch(SQLException e) {
            System.out.print("SQL#2 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }
        return out;
    }
    /**
     * @param book
     * @param catChosen
     * @param yearChosen
     * @return minimum price offered by the clubs this customer is a
     * member of
     */
    public float min_price(String book, String catChosen, int yearChosen) 
    {
    	float min = 0;
    	String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
        ResultSet         answers   = null;   // A cursor.
        queryText =
            "SELECT club,price      "
          + "FROM yrb_offer "
          + "WHERE title = '" + book + "'"
          + " and year = " + yearChosen + " and club IN(SELECT club"
          + " FROM yrb_member "
          + "WHERE cid = " + custID + ")";
     // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);
        } catch(SQLException e) {
            System.out.println("SQL#4 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }
        // Execute the query.
        try {
            answers = querySt.executeQuery();
        } catch(SQLException e) {
            System.out.println("SQL#4 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
    	}
     // Any answer?
        try {
        		if (answers.next()) 
        		{
        			min = answers.getFloat("price");
        			offeredBy = answers.getString("club");
        			for(;answers.next();) 
        			{
        				float compare = answers.getFloat("price");
        				if( compare < min)
        				{
        					offeredBy = answers.getString("club");
        					min = compare;
        				}
        			}
        	}
        } catch(SQLException e) {
            System.out.println("SQL#4 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }
     // Close the cursor.
        try {
            answers.close();
        } catch(SQLException e) {
            System.out.print("SQL#2 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // We're done with the handle.
        try {
            querySt.close();
        } catch(SQLException e) {
            System.out.print("SQL#2 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }
        return min;
    }
    /**
     * Inserts the chosen book in the yrb_purchases DB
     * @param cid
     * @param club
     * @param title
     * @param year
     * @param qty
     */
    public void insert_purchase(int cid, String club,String title, int year, int qty)
    {
    	
    	String            queryText = "";     // The SQL text.
        Statement querySt   = null;   // The query handle.
        queryText =
            "INSERT INTO yrb_purchase VALUES(" 
            + cid + ", '" + club + "'," + "'" + title + "'," 
    		+ year + ", '" + new Timestamp(System.currentTimeMillis())+"'," 
        	 + qty+")" ;
          
        // Prepare the query.
        try {
            querySt = conDB.createStatement();
        } catch(SQLException e) {
            System.out.println("SQL#5 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }
        // Execute the query.
        try {
            querySt.executeUpdate(queryText);
            conDB.commit();
            System.out.println("ok");
        } catch(SQLException e) {
            System.out.println("SQL#5 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
    	}
    }
    
    /**
	 * @return the custID
	 */
	public Integer getCustID() {
		return custID;
	}
	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @return the custCity
	 */
	public String getCustCity() {
		return custCity;
	}
	
	public LinkedList<String> getCats() {
		return cats;
	}
	
	public String getOfferedBy()
	{
		return offeredBy;
	}
}
