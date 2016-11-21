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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RegisterDetailsPanel extends JPanel {
	private String title;
	private String fName;
	private String sName;
	private String dOB;
	private String phone;
	private int houseNum;
	private String street;
	private String district;
	private String city;
	private String pocode;
	private int addressID;
	private int patientID;
	private boolean addValid;

	private Registrar reg = new Registrar();
	private PatientPanel patient = new PatientPanel();
	
	public RegisterDetailsPanel(boolean addValid, String title, String fName, String sName, String dOB, String phone, 
			int houseNum, String street, String district, String city, String pocode) {
		this.title = title;
		this.fName = fName;
		this.sName = sName;
		this.dOB = dOB;
		this.phone = phone;
		this.houseNum = houseNum;
		this.street = street;
		this.district = district;
		this.city = city;
		this.pocode = pocode;
	
		
	
		setLayout(new GridLayout(0,1));
		if (!addValid) {
			add(new JLabel("Enter patient details", JLabel.CENTER));
		}
		else {
			registerPatient();
			System.out.println("should appear here");
		}
	}
	
	private void registerPatient() {
			try {				
				addressID = reg.addAddress(houseNum, street, district, city, pocode);
			} catch (SQLException x) {
				System.out.println("address invalid");;
			}
			
			try {
				patientID = reg.addPatient(title, fName, sName, dOB, phone, addressID);
			} catch (SQLException x) {
				x.printStackTrace();
			}
			
			this.add(new JLabel("Patient added", JLabel.CENTER));
			
			// add patient's details
			this.add(new JLabel("Patient ID:             " + patientID, JLabel.LEFT));
			this.add(new JLabel("Name:                    " + title + " " + fName + " " + sName, JLabel.LEFT));
			this.add(new JLabel("Date of Birth:        " + dOB, JLabel.LEFT));
			this.add(new JLabel("Phone Number:   " + phone, JLabel.LEFT));
			this.add(new JLabel("Address: ", JLabel.LEFT));
			this.add(new JLabel(houseNum + " " + street, JLabel.CENTER));
			this.add(new JLabel(district, JLabel.CENTER));
			this.add(new JLabel(city, JLabel.CENTER));
			this.add(new JLabel(pocode, JLabel.CENTER));
			
			// add buttons for booking initial appointments
			JPanel intialAppointments = new JPanel();
			intialAppointments.setLayout(new GridLayout(0,1));
			JButton bookinitialAppBtn = new JButton("Book initial appointments");
			bookinitialAppBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new BookAppointmentFrame(patientID);
				}
			});

			intialAppointments.add(bookinitialAppBtn);
	
			this.add(new JLabel("Patient Options:", JLabel.LEFT));
			this.add(bookinitialAppBtn);
			
	
	}
	
	
}
