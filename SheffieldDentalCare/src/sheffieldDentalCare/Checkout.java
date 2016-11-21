package sheffieldDentalCare;

import java.sql.*;

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
		Statement stmt2 = null;
		int rowsAdded = 0;
		boolean paidByPlan = false;
		boolean paid = false;
		String possibleCreditTypeColumn;
		int pid = getPatientID(appointmentID);
		
		if (treatmentName != "crown") {
			if (isSubscribed(pid)) {
				switch(treatmentName) {
				case "amalF": possibleCreditTypeColumn = "repairCount";
					break;
				case "hygVisit": possibleCreditTypeColumn = "hygieneCount";
					break;
				case "resinF": possibleCreditTypeColumn = "repairCount";
					break;
				case "checkup": possibleCreditTypeColumn = "checkupCount";
					break;
				default : possibleCreditTypeColumn = null;
					break;
				}
				try {
					con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
							DBController.DB_User, DBController.DB_Password);
					stmt = con.createStatement();
					
					ResultSet rs = stmt.executeQuery("SELECT "+possibleCreditTypeColumn+" FROM TreatmentCredits WHERE patientID = "+pid+";");
					
					rs.next();
					if (rs.getInt(1) > 0) {
						paidByPlan = true;
						paid = true;
						stmt2 = con.createStatement();
						stmt2.executeUpdate("UPDATE TreatmentCredits SET "+possibleCreditTypeColumn+" = "+possibleCreditTypeColumn+"-1 WHERE patientID = "+pid+";");
					}
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
				finally {
					if (stmt != null) stmt.close();
					if (con != null) con.close();
					if (stmt2 != null) stmt2.close();
				}
			}
		}
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			rowsAdded = stmt.executeUpdate("INSERT INTO TreatmentPerformed "
					+ "VALUES(" + appointmentID + ",'" + treatmentName + "', "+paid+","+paidByPlan+");");
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

	/**
	 * sets that all treatments from a specific appointment have 
	 * been paid for
	 * @param appointmentID
	 * @throws SQLException
	 * @returns number of rows updated
	 */
	public int payAppointment(int appointmentID) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		int count = 0;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			count = stmt.executeUpdate("UPDATE TreatmentPerformed SET paid = TRUE "
					+ "WHERE appointmentID = " + appointmentID + ";");
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		return count;
	}
	
	/**
	 * sets that a specific treatment has been paid for
	 * @param appointmentID
	 * @param treatmentName
	 * @throws SQLException
	 * @returns number of rows updated
	 */
	public int payTreatment(int appointmentID, String treatmentName) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		int count = 0;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			count = stmt.executeUpdate("UPDATE TreatmentPerformed SET paid = TRUE "
					+ "WHERE appointmentID = " + appointmentID + " AND treatmentName LIKE '" + treatmentName + "';");
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		return count;
	}
	
	/**
	 * 
	 * @param pid patientID
	 * @return returns true if the patient is subscribed to a healthcare plan 
	 * @throws SQLException
	 */
	private static boolean isSubscribed(int pid) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		int count = 0;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM TreatmentCredits WHERE patientID = "+pid+";");
			while(rs.next()){
				count++;
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}	
		return count > 0;
	}
	
	/**
	 * 
	 * @param appid appointmentID
	 * @return patientID of patient whom's appointment has the provided appointmentID
	 * @throws SQLException
	 */
	private static int getPatientID(int appid) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		int pid = 0;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT patientID FROM Appointment WHERE appointmentID = "+appid+";");
			while(rs.next()) {
				pid = rs.getInt(1);
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		return pid;
	}
	//test method
	public static void main(String[] args) throws SQLException {
	
		//System.out.println(isSubscribed(1));
		
		//Checkout c = new Checkout();
		
		//c.addTreatmentToAppointment(1, "resinF");
	}
	
}
