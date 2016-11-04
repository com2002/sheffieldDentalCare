package sheffieldDentalCare;
import java.sql.*;

public class DBTest {
	public static void main(String[] args) throws SQLException {
		DBController db = new DBController();
		// db.openConnection(); //open up connection //REDUNDANT
		
		db.update("DROP TABLE Test;"); //delete the previous test table
		
		int count1 = db.update("CREATE TABLE Test(Number int, Age int, PRIMARY KEY(Number));");		
		System.out.println(count1); //should print 0
		
		db.update("INSERT INTO Test VALUES(2,43);");
		int count2 = db.update("INSERT INTO Test VALUES(1,43);");
		System.out.println(count2); //should print 1
		
		ResultSet rs = db.query("SELECT * FROM Test;"); 
		rs.next(); //iterate to first row
		System.out.println(rs.getInt(2)); //should print 43 
		
		ResultSet rs1 = db.query("SELECT * FROM Test WHERE Age = 43;");
		rs1.next();
		System.out.println(rs1.getInt(1));
		
		rs.next();
		System.out.println(rs.getInt(1));
		db.closeConnection(); //close down connection
		
		
	}
}
