package sheffieldDentalCare;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
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
	private int checkupCount;
	private int hygieneCount;
	private int repairCount;
	private String planName;
	
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
			try {
				getPatientPlanDetails();
			}
			catch (SQLException ex) {
				System.out.println("Error: getPatientPlanDetails failed");
				this.add(new JLabel("Patient plan not found", JLabel.CENTER));
				return;
			} 
			this.add(new JLabel("Patient found", JLabel.CENTER));
			
			// add patient's details
			this.add(new JLabel("Patient ID:             " + patientID, JLabel.LEFT));
			this.add(new JLabel("Name:                    " + title + " " + firstName + " " + surname, JLabel.LEFT));
			this.add(new JLabel("Date of Birth:        " + dOB, JLabel.LEFT));
			this.add(new JLabel("Phone Number:   " + phoneNumber, JLabel.LEFT));
			this.add(new JLabel("Address: ", JLabel.LEFT));
			this.add(new JLabel(houseNo + " " + streetName, JLabel.CENTER));
			this.add(new JLabel(district, JLabel.CENTER));
			this.add(new JLabel(city, JLabel.CENTER));
			this.add(new JLabel(postcode, JLabel.CENTER));
			
			// add patient's healthcare plan details
			displayPlanDetails();
			
			// add buttons for booking/viewing/paying for appointments
			JPanel appointmentsActions = new JPanel();
			appointmentsActions.setLayout(new GridLayout(0,3));
			JButton bookAppointmentBtn = new JButton("Book");
			bookAppointmentBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new BookAppointmentFrame(patientID);
				}
			});
			JButton payAppointmentsBtn = new JButton("Pay");
			payAppointmentsBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new PayTreatmentsFrame(patientID);
				}
			});
			JButton cancelAppointmentsBtn = new JButton("Cancel");
			cancelAppointmentsBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			appointmentsActions.add(bookAppointmentBtn);
			appointmentsActions.add(payAppointmentsBtn);
			appointmentsActions.add(cancelAppointmentsBtn);
			this.add(new JLabel("Appointment Options:", JLabel.LEFT));
			this.add(appointmentsActions);
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
			ResultSet res = stmt.executeQuery("SELECT title, firstName, surname, dateOB, phoneNumber, streetName, district, city, postcode "
					+ "FROM Patients p, Address a "
					+ "WHERE p.addressID = a.addressID "
					+ "AND firstName = '" + firstName + "' "
					+ "AND surName = '" + surname + "' "
					+ "AND dateOB = '" + dOB + "' "
					+ "AND houseNumber = " + houseNo
					+ " AND postCode = '" + postcode + "';");
			while(res.next()) {
				title = res.getString(1);
				firstName = res.getString(2);
				surname = res.getString(3);
				dOB = res.getString(4);
				phoneNumber = res.getString(5);
				streetName = res.getString(6);
				district = res.getString(7);
				city = res.getString(8);
				postcode = res.getString(9);
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
	
	private void displayPlanDetails() {
		String planNameString;
		if (planName==null) {
			planNameString = "None";
		}
		else {
			switch (planName) {
        	case "nhsfPlan":  planNameString = "NHS Free Plan";
        		break;
        	case "maintPlan":  planNameString = "Maintenance Plan";
         		break;
        	case "ohPlan":  planNameString = "Oral Health Plan";
        		break;
        	case "drPlan": planNameString = "Dental Repair Plan";
        		break;
        	default: planNameString = "None";
        		break;
		}
    }
		this.add(new JLabel("Healthcare Plan: " + planNameString, JLabel.LEFT));
		if (planName!=null) {
			this.add(new JLabel("Check-up Credits Remaining: " + checkupCount, JLabel.LEFT));
			this.add(new JLabel("Date of Birth:        " + dOB, JLabel.LEFT));
			this.add(new JLabel("Phone Number:   " + phoneNumber, JLabel.LEFT));
		}
	}
	
	private void getPatientPlanDetails() throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT checkupCount, hygieneCount, repairCount, tc.planName "
					+ "FROM TreatmentCredits tc, HealthcarePlan hp "
					+ "WHERE tc.planName = hp.planName "
					+ "AND patientID = '" + patientID + "';");
			while(res.next()) {
				checkupCount = res.getInt(1);
				hygieneCount = res.getInt(2);
				repairCount = res.getInt(3);
				planName = res.getString(4);
				System.out.println("Check up count: " + checkupCount);
				System.out.println("hygiene count: " + hygieneCount);
				System.out.println("repair count" + repairCount);
				System.out.println("plan name: "+planName);
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
