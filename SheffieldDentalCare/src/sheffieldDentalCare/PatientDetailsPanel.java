package sheffieldDentalCare;

import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

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
	private PatientPlan plan;
	
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
				this.add(new JLabel("Patient not found", JLabel.CENTER));
				return;
			} 
			plan = new PatientPlan(this, patientID);
			try {
				plan.getPatientPlanDetails();
			}
			catch (SQLException ex) {
				this.add(new JLabel("Patient plan not found", JLabel.CENTER));
				return;
			} 
			
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
			plan.displayPlanDetails();
			
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
					try {					
					new CancelAppointmentFrame(patientID);
					}
					catch (SQLException ex){
						ex.printStackTrace();
					}
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
	
}
