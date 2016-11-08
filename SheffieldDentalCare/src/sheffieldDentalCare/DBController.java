package sheffieldDentalCare;
import java.sql.*;

@SuppressWarnings("unused")
class DBController {
	//DB Details
	static String DB_Server = "stusql.dcs.shef.ac.uk";
	static String DB_Name = "team026";
	static String DB_User = "team026";
	static String DB_Password = "11c7ef91";
	
	//an open or closed DB connection
	static Connection con = null;
	
	//new DBcontroller
	public DBController() {	
	}
	
	//open connection to DB
	public static void openConnection() throws SQLException {
		if (con == null) {
			// connection to a database
			try { 
			 con = DriverManager.getConnection("jdbc:mysql://" + DB_Server + "/" + DB_Name, DB_User, DB_Password);
			 
			}
			catch (SQLException ex) {
			 ex.printStackTrace();
			}
		}
	}
	
	//close connection to DB (whether it's open or closed it will be closed)
	public static void closeConnection() throws SQLException {
		if (con != null) {
			con.close();
			con = null;
		}
	}
	
	//executes a supplied SQL "Update statement" - Use for INSERT, DELETE and UPDATE statements
	//returns the number of rows that were updated (or zero)
	public int update(String sql) throws SQLException {
		openConnection(); //open the connection 'con'
		Statement stmt = null;
		int count = 0; //count of rows updated
		
		try {
			stmt = con.createStatement(); //open the statement
			count = stmt.executeUpdate(sql); //execute the statement
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			 if (stmt != null) stmt.close(); //close the statement
			 closeConnection(); //close the connection 'con'
		}		
		return count;
	}
	
	//executes a supplied SQL "Query statement" - Use for SELECT statements.
	//returns a java.sql.ResultSet - see java.sql.ResultSet documentation for how to use this object.
	public ResultSet query(String sql) throws SQLException {
		openConnection();
		Statement stmt = null;
		ResultSet res = null;
		try{
			stmt = con.createStatement();
			res = stmt.executeQuery(sql);
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			// if (stmt != null) stmt.close();
			//closeConnection();
		}
		return res;
	}
}
