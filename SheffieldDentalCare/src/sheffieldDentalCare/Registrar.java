package sheffieldDentalCare;

import java.sql.*;
import java.util.ArrayList;

public class Registrar {
	
	/**
	 * 
	 * @param housenum
	 * @param street
	 * @param district
	 * @param city
	 * @param pocode
	 * @return the addressID of the added address as int
	 * @throws SQLException
	 */
	public int addAddress(int housenum, String street, String district, String city, String pocode) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		Statement stmt2 = null;
		int id = 0;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			stmt2 = con.createStatement();
			ResultSet existsRes = stmt2.executeQuery("SELECT COUNT(*) AS total FROM Address "
					+ "WHERE houseNumber = '"+housenum+"' AND postCode = '"+pocode+"';");
			//if address doesn't already exist
			boolean exists = false;
			while (existsRes.next()) {
				exists = existsRes.getInt(1) != 0;
			}
			if (exists == false) {
				System.out.println("Adding new address...");			
				System.out.println("Rows updates: " + 
						stmt.executeUpdate("INSERT INTO Address(houseNumber, streetName, district, city, postCode)" +
										   "VALUES(" + housenum + ",'"+ street + "','" + district + "','" + city + "','" + pocode +"');"));
				ResultSet res = stmt.executeQuery("SELECT MAX(addressID) FROM Address;");
				while (res.next()) {
					id = res.getInt(1);				
				}
			}
			else {
				System.out.println("Address already exists. Existing addressID returned.");
				//existsRes.next();
				id = getAddressID(housenum, pocode);
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally {
			if (stmt2 != null) stmt2.close();
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		return id;
	}
	
	
	/**
	 * 
	 * @param title
	 * @param fname
	 * @param sname
	 * @param dob as a string in the form "YYYY-MM-DD"
	 * @param phone
	 * @param addressID
	 * @return the patientID of the added patient
	 * @throws SQLException
	 */
	public int addPatient(String title, String fname, String sname, String dob, String phone, int addressID) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		int id = 0;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			ResultSet exists1Res = stmt.executeQuery("SELECT COUNT(*) AS total FROM Patient "
					+ "WHERE firstName = '"+fname+"' AND surName = '"+sname+"' AND dateOB = '"+dob+"';");
			//if patient doesn't already exist
			boolean exists1 = false;
			while (exists1Res.next()) {
				exists1 = exists1Res.getInt(1) != 0;
			}
			if (exists1 == false) {
			System.out.println("Rows updates: " + 
					stmt.executeUpdate("INSERT INTO Patient(title, firstName, surName, dateOB, phoneNumber, addressID)"
							+ "VALUES('" + title + "','" + fname + "','" + sname + "','" + dob + "','" + phone + "'," + addressID +")"));
			ResultSet res = stmt.executeQuery("SELECT MAX(patientID) FROM Patient;");
			while (res.next()) {
				id = res.getInt(1);
			}
			}
			else {
				System.out.println("Patient already exists. Existing patientID returned.");
				//existsRes.next();
				id = getPatientID(fname, sname, dob, addressID);
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		return id;
	}
	
	/**
	 * replaces any previous subscription credits
	 * @param patientID
	 * @param planName choose from: nhsfPlan, maintPlan, ohPlan or drPlan
	 * @return number of rows inserted to the TreatmentCredits table.
	 * @throws SQLException
	 */
	public int subscribeTo(int patientID, String planName) throws SQLException {
		int count = 0;
		Connection con = null;
		Statement stmt = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			//delete any previous TreatmentCredits entries for this patient
			if (stmt.executeUpdate("DELETE FROM TreatmentCredits WHERE patientID = '" + patientID + "';") > 0) {
				System.out.println("Previous subscription removed.");
			}
			int initialCheckUps = 0;
			int initialHygVisits = 0;
			int initialRepairs = 0;
			ResultSet rs = stmt.executeQuery("SELECT * FROM HealthcarePlan WHERE planName = '" + planName + "';");
			while (rs.next()) {
				initialCheckUps = rs.getInt(2);
				initialHygVisits = rs.getInt(3);
				initialRepairs = rs.getInt(4);
			}
			count = stmt.executeUpdate("INSERT INTO TreatmentCredits VALUES(" + patientID + "," + initialCheckUps + "," + 
													initialHygVisits + "," + initialRepairs + ",'" + planName + "');");
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
	 * removes whatever subscriptions the patient has.
	 * @param patientID
	 * @throws SQLException
	 */
	public void unsubscribe(int patientID) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			if (stmt.executeUpdate("DELETE FROM TreatmentCredits WHERE patientID = '" + patientID + "';") > 0) {
				System.out.println("Subscription cancelled.");
			}
			else System.out.println("Patient has no subscription.");
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
	}
	
	/**
	 * 
	 * @param houseNumber
	 * @param postcode
	 * @return the addressID
	 * @throws SQLException
	 */
	public int getAddressID(int houseNumber, String postcode) throws SQLException {
		int id = 0;
		Connection con = null;
		Statement stmt = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			
			String q1 = "Select addressID from Address ";
			String q2 = "WHERE houseNumber = "+houseNumber+" AND postCode LIKE '"+postcode+"';";
			
			ResultSet rs = stmt.executeQuery(q1+q2);
			while (rs.next()) {
				id = rs.getInt(1);
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		return id;
	}
	
	/**
	 * can use with getAddressID as last parameter to uniquely identify a patient
	 * @param fName
	 * @param sName
	 * @param dob
	 * @param addressID
	 * @return the patientID
	 * @throws SQLException
	 */
	public int getPatientID(String fName, String sName, String dob, int addressID) throws SQLException {
		int id = 0;
		Connection con = null;
		Statement stmt = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			
			String q1 = "Select patientID from Patient ";
			String q2 = "WHERE firstName LIKE '" + fName + "' AND surName LIKE '" + sName + "' AND dateOB = '" + dob + "' AND addressID = " + addressID + ";";
			
			ResultSet rs = stmt.executeQuery(q1+q2);
			while (rs.next()) {
				id = rs.getInt(1);
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		return id;
	}
	
	public ArrayList<String> getForAllPatientsSomeDetails() throws SQLException {
		ArrayList<String> data = new ArrayList<String>();
		Connection con = null;
		Statement stmt = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			String query = "SELECT Patient.patientID, Patient.firstName, Patient.surName, "
					     + "Patient.dateOb, Address.houseNumber, Address.postCode "
					     + "FROM Patient, Address "
					     + "WHERE Patient.addressID = Address.addressID;";
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				data.add(Integer.toString(rs.getInt(1)) + ": " + rs.getString(2) + " " + rs.getString(3)
				        + ", " + rs.getString(4) + ", " + rs.getInt(5) + ", " + rs.getString(6));
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		return data;
		}
	
	//test
	public static void main(String[] args) throws SQLException {
		Registrar reg = new Registrar();
		//subscribe a patient to a healthcare plan
		//System.out.println(reg.subscribeTo(2, "maintPlan"));
		
		//unsubscribe a patient to whatever plan they are subscirbed to
		//System.out.println(reg.unsubscribe(2));
		
		//add a patient to an existing address
		// reg.addPatient("Mr", "Harry", "Potter", "1980-07-31", "07454456321", 2);
		
		//add a patient and their address at the same time
		//reg.addPatient("Mr", "Donald", "Duck", "1934-06-9", "1-541-754-3010", reg.addAddress(1, "Park Street", "Disney Land", "Chicago", "QU4 CK1"));
		
		System.out.println(reg.addAddress(4, "Privet Drive", "Surrey", "London", "RH7 8AJ"));
		
		//System.out.println(reg.getAddressID(4, "RH7 8AJ"));
		
		//get a patients ID by their name, dob, housenumber and postcode
		//System.out.println(reg.getPatientID("harry", "potter", "1980-07-31", reg.getAddressID(4, "rh7 8aj")));
		
		//ArrayList<String> data = new ArrayList<String>();
		//data = reg.getForAllPatientsSomeDetails();
		//for (int i = 0; i < data.size(); i++) {
		//	System.out.println(data.get(i));
		//}	
	}
}
