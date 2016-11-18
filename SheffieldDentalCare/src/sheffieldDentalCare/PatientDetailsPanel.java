package sheffieldDentalCare;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PatientDetailsPanel extends JPanel {
	private int patientID;
	private String title;
	private String firstName;
	private String surname;
	private String dOB;
	private String phoneNumber;
	private int addressID;
	private int houseNo;
	private String streetName;
	private String district;
	private String city;
	private String postcode;
	
	private Registrar reg = new Registrar();
	
	public PatientDetailsPanel(boolean searchValid, String firstName, String surname,
			String dOB, int houseNo, String postcode) {
		this.firstName = firstName;
		this.surname = surname;
		this.dOB = dOB;
		this.houseNo = houseNo;
		this.postcode = postcode;
		
		setLayout(new GridLayout(0,1));
		if (!searchValid) {
			add(new JLabel("Not valid search", JLabel.CENTER));
		}
		else {
			displayResults();
		}
	}
	
	private void displayResults() {
		if (addressExists() && patientExists()) {
			try {
				getPatientDetails();
			}
			catch (SQLException ex) {
				System.out.println("Error: getPatientDetails failed");
				this.add(new JLabel("Patient not found", JLabel.CENTER));
				return;
			}
			this.add(new JLabel("Patient found", JLabel.CENTER));
			this.add(new JLabel("Patient ID: " + patientID, JLabel.LEFT));
			this.add(new JLabel("Name: " + title + " " + firstName + " " + surname, JLabel.LEFT));
			this.add(new JLabel("Date of Birth: " + dOB, JLabel.LEFT));
			this.add(new JLabel("Address: ", JLabel.LEFT));
			this.add(new JLabel(houseNo + " " + streetName, JLabel.CENTER));
			this.add(new JLabel("" + district, JLabel.CENTER));
			this.add(new JLabel("" + city, JLabel.CENTER));
			this.add(new JLabel("" + postcode, JLabel.CENTER));
		}
		else {
			this.add(new JLabel("Patient not found", JLabel.CENTER));
		}
	}
	
	private boolean addressExists() {
		try {
			addressID=reg.getAddressID(houseNo, postcode);
		}
		catch (SQLException ex) {
			return false;
		}
		if (addressID==0) {
			return false;
		}
		return true;
	}
	
	private boolean patientExists() {
		try {
			patientID=reg.getPatientID(firstName, surname, dOB, addressID);
			System.out.println(reg.getPatientID(firstName, surname, dOB, addressID));
		}
		catch (SQLException ex) {
			return false;
		}
		if (patientID==0) {
			return false;
		}
		return true;
	}
	
	private void getPatientDetails() throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT title, phoneNumber, streetName, district, city "
					+ "FROM Patients p, Address a "
					+ "WHERE p.addressID = a.addressID "
					+ "AND p.firstName = '" + firstName + "' "
					+ "AND p.surName = '" + surname + "' "
					+ "AND p.dateOB = '" + dOB + "' "
					+ "AND a.houseNumber = " + houseNo
					+ " AND a.postCode LIKE '" + postcode + "';");
			while(res.next()) {
				title = res.getString(1);
				phoneNumber = res.getString(2);
				streetName = res.getString(3);
				district = res.getString(4);
				city = res.getString(5);
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
	}
	
}
