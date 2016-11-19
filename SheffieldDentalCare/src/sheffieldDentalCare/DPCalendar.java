package sheffieldDentalCare;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class DPCalendar {

	public String checkAvailability(int patientID, boolean pHygienist, String date, String start, String end) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		String output1 = "";
		String output2 = "";
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			//check patient clashes
			// Clashes for a patient only matter if patient is not the "blank"/"absence" patient
			if (patientID != 1) {
				ResultSet rs = stmt.executeQuery("SELECT * FROM Appointments WHERE "
						+ "patientID = " + patientID + " AND date = '" + date + "';");
				while (rs.next()) {
					if (timeClash(rs.getString(5),rs.getString(6), start, end)) {					
						output1 = "Not available: clash with patient's appointment with ID " + rs.getInt(1)+ ". ";
					}
				}
			}
			//check partner clashes
			ResultSet rt = stmt.executeQuery("SELECT * FROM Appointments WHERE "
					+ "pHygienist = " + pHygienist + " AND date = '" + date + "';");
			while (rt.next()) {
				if (timeClash(rt.getString(5),rt.getString(6), start, end)) {
					output2 = "Not available: clash with partner's appointment with ID " + rt.getInt(1)+ ". ";
				}
			}			
		}
		catch (SQLException ex){
			ex.printStackTrace();
		} 
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		return output1 + output2;
	}
	
	private static boolean timeClash(String start1, String end1, String start2, String end2) {
		//convert times in strings to ints of the form HHMM
		if (start1.length() > 5) start1 = start1.substring(0, start1.length()-3);
		if (end1.length() > 5) end1 = end1.substring(0, end1.length()-3);
		if (start2.length() > 5) start2 = start2.substring(0, start2.length()-3);
		if (end1.length() > 5) end1 = end1.substring(0, end1.length()-3);
		int s1 = Integer.parseInt((start1.replaceAll("[^\\d.]", "")));
		int e1 = Integer.parseInt((end1.replaceAll("[^\\d.]", "")));
		int s2 = Integer.parseInt((start2.replaceAll("[^\\d.]", "")));
		int e2 = Integer.parseInt((end2.replaceAll("[^\\d.]", "")));
		//check for overlaps
		return (s1 <= s2 && s2 < e1) || (s2 <= s1 && s1 < e2);
	}
	/**
	 * @param patientID
	 * @param pHygeinist true for hygienist appointments, false for dentist appointments.
	 * @param date as a string in the format yyyy-MM-dd
	 * @param startTime as a string in the format HH:mm
	 * @param endTime as a string in the format HH:mm
	 * @return an int of the number of rows added
	 * @throws SQLException
	 */
	public int addAppointment(int patientID, boolean pHygienist, String date, String startTime, String endTime) throws SQLException {
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
	
	public int deleteAppointment(int appointmentID) throws SQLException {
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
	
	/**
	 * 
	 * @param pHygeinist true for hygienist appointments, false for dentist appointments.
	 * @param wbdate date of the monday of the working week.
	 * @return a list of AppointmentPlots, see AppointmentPlot doc for info
	 * @throws ParseException, SQLException
	 */
	public AppointmentPlot[] getAppointments(boolean pHygeinist, String wbdate) throws SQLException, ParseException {
		AppointmentPlot[] applots = null;
		String[] days = new String[5];
		Connection con = null;
		Statement stmt = null;
		
		//set up dates of this working week
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(wbdate));
		for (int i=0; i<5; i++) {
			days[i] = sdf.format(c.getTime());
			c.add(Calendar.DATE, 1);
			//System.out.println(days[i]);
		}	
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			//pull up all appointments of the specified partner from the week
			String q = "SELECT * FROM Appointments WHERE pHygienist = "+pHygeinist+" AND "
					+ "(date = '" + days[0] + "' OR date = '" + days[1] + "' OR date = '" + days[2] + "' OR "
					+ "date = '" + days[3] + "' OR date = '" + days[4] + "');";
			//get the length of the result set initialise list
			ResultSet rss = stmt.executeQuery(q);
			int rsLength = 0;
			while (rss.next()) {
				rsLength++;
			}
			ResultSet rs = stmt.executeQuery(q);			
			applots = new AppointmentPlot[rsLength];
			int j = 0;
			while (rs.next()) {
				applots[j] = new AppointmentPlot(rs.getString("date"), rs.getInt("appointmentID"), rs.getInt("patientID"), rs.getString("startTime"), rs.getString("endTime"));
				j++;
			}		
		} 
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		return applots;		
	}
	
	public AppointmentPlot[] getAppointmentsForDate(boolean pHygeinist, String date) throws SQLException, ParseException {
		AppointmentPlot[] applots = null;
		String day = new String();
		Connection con = null;
		Statement stmt = null;
		// Set up given date in db date format
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(date));	
		day = sdf.format(c.getTime());
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			//pull up all appointments of the specified partner from the week
			String q = "SELECT * FROM Appointments WHERE pHygienist = "+pHygeinist+" AND "
					+ "date = '" + day + "';";
			//get the length of the result set initialise list
			ResultSet rss = stmt.executeQuery(q);
			int rsLength = 0;
			while (rss.next()) {
				rsLength++;
			}
			ResultSet rs = stmt.executeQuery(q);			
			applots = new AppointmentPlot[rsLength];
			int j = 0;
			while (rs.next()) {
				applots[j] = new AppointmentPlot(rs.getString("date"), rs.getInt("appointmentID"), rs.getInt("patientID"), rs.getString("startTime"), rs.getString("endTime"));
				j++;
			}		
		} 
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		return applots;		
	}
	
	// Test addAppointment
	public static void main(String[] args) throws SQLException, ParseException {
		DPCalendar calendar = new DPCalendar();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
		String date = dateFormat.format(new Date());
		//String startTime = "18:00";
		//String endTime = "18:20";
		System.out.println(date);
		//System.out.println("Appointment ID: " + calendar.addAppointment(2, true, date, startTime, endTime));
		//int id = calendar.addAppointment(2, true, date, startTime, endTime);
		//System.out.println("Appointment ID: " + id);
		//System.out.println(calendar.deleteAppointment(id));
		//System.out.println(timeClash("17:50", "18:50","18:00","18:20"));
		//System.out.println(calendar.checkAvailability(1, true, "2016-11-12", "17:50", "18:50"));
		//System.out.println(ap.ENDTIME);
		//calendar.getDentistAppointments(true, "2016-11-30");
		AppointmentPlot[] ap = calendar.getAppointmentsForDate(true, "2016-11-15");
		for (int i = 0; i < ap.length; i++) {
			System.out.println(ap[i].STARTTIME + " - " + ap[i].ENDTIME + " " + ap[i].PATIENTID);
		}		
	}

}