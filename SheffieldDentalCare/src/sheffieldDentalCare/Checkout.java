package sheffieldDentalCare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Checkout {
	
	public void getTreatmentsOutstanding(int patientID) throws SQLException {
		
	}
	
	/**
	 * adds a treatment to a given appointment
	 * @param appointmentID
	 * @param treatmentName
	 * @throws SQLException
	 * @returns number of rows updated
	 */
	public int addTreatmentToAppointment(int appointmentID, String treatmentName) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		int rowsAdded = 0;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			rowsAdded = stmt.executeUpdate("INSERT INTO TreatmentsPerformed "
					+ "VALUES(" + appointmentID + ",'" + treatmentName + "', 0);");
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		return rowsAdded;
	}

	public void payAppointment(int appointmentID) throws SQLException {
		Connection con = null;
		Statement stmt = null;
	}
	
	public void payTreatment(int treatmentID) throws SQLException {
		Connection con = null;
		Statement stmt = null;
	}
	
}
