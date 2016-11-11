package sheffieldDentalCare;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DPCalendar {

	private static boolean checkClash(String start1, String end1, String start2, String end2) {
		//convert times in strings to ints
		int s1 = Integer.parseInt((start1.replaceAll("[^\\d.]", "")));
		int e1 = Integer.parseInt((end1.replaceAll("[^\\d.]", "")));
		int s2 = Integer.parseInt((start2.replaceAll("[^\\d.]", "")));
		int e2 = Integer.parseInt((end2.replaceAll("[^\\d.]", "")));
		//check for overlaps
		return (s1 <= s2 && s2 < e1) || (s2 <= s1 && s1 < e2);
	}
	
	private int addAppointment(int patientID, boolean pHygienist, String date, String startTime, String endTime) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		int appointmentID = 0;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			int count = stmt.executeUpdate("INSERT INTO Appointments(patientID, pHygienist, date, startTime, endTime) "
					  + "VALUES(" + patientID + ", " + pHygienist + ", '" + date + "', '" + startTime + "', '" + endTime + "');");
			System.out.println("Rows updated: " + count);
			ResultSet res = stmt.executeQuery("SELECT MAX(appointmentID) FROM Appointments;");
			while (res.next()) {
				appointmentID = res.getInt(1);
			}
		} 
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		return appointmentID;
	}
	
	private int deleteAppointment(int appointmentID) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		int count = 0;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			count = stmt.executeUpdate("DELETE FROM Appointments "
				  + "WHERE appointmentID = " + appointmentID + ";");
			System.out.println("Rows updated: " + count);
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
	
	// Test addAppointment
	public static void main(String[] args) throws SQLException {
		DPCalendar calendar = new DPCalendar();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
		String date = dateFormat.format(new Date());
		String startTime = "18:00";
		String endTime = "18:20";
		System.out.println(date);
		//System.out.println("Appointment ID: " + calendar.addAppointment(2, true, date, startTime, endTime));
		//int id = calendar.addAppointment(1, true, date, startTime, endTime);
		//System.out.println("Appointment ID: " + id);
		//System.out.println(calendar.deleteAppointment(id));
		//System.out.println(checkClash("12:00","16:00","10:00","12:00"));
	}

}