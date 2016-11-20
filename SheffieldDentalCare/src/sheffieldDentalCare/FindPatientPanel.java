package sheffieldDentalCare;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * FindPatientPanel.java
 * Creates a panel for finding patients.
 * Appears in PatientPanel.
 * @author Lewis
 * 
 */

@SuppressWarnings("serial")
public class FindPatientPanel extends JPanel {
	private boolean searchPerformed = false;
	private String firstName = null;
	private String surname = null;
	private String dOBDay = null;
	private String dOBMonth = null;
	private String dOBYear = null;
	private int houseNo = 0;
	private String postcode = null;
	private String houseNoString = null;
	
	public FindPatientPanel(PatientPanel patientPanel) {
		setLayout(new GridLayout(0,1));
	
		// first name input panel
		JPanel firstNamePanel = new JPanel();
		JTextField firstNameField = new JTextField(20);
		firstNamePanel.add(new JLabel("First Name:", JLabel.RIGHT));
		firstNamePanel.add(firstNameField);
		
		// surname input panel
		JPanel surnamePanel = new JPanel();
		JTextField surnameField = new JTextField(20);
		surnamePanel.add(new JLabel("Surname:", JLabel.RIGHT));
		surnamePanel.add(surnameField);
		
		// DoB input panel
		JPanel dOBPanel = new JPanel();
		JTextField dOBDayField = new JTextField(2);
		JTextField dOBMonthField = new JTextField(2);
		JTextField dOBYearField = new JTextField(4);
		dOBPanel.add(new JLabel("Date of Birth: ", JLabel.RIGHT));
		dOBPanel.add(new JLabel("Day", JLabel.RIGHT));
		dOBPanel.add(dOBDayField);
		dOBPanel.add(new JLabel("Month", JLabel.RIGHT));
		dOBPanel.add(dOBMonthField);
		dOBPanel.add(new JLabel("Year", JLabel.RIGHT));
		dOBPanel.add(dOBYearField);

		// house number input panel
		JPanel houseNoPanel = new JPanel();
		JTextField houseNoField = new JTextField(20);
		houseNoPanel.add(new JLabel("House Number:", JLabel.RIGHT));
		houseNoPanel.add(houseNoField);
		
		// postcode input panel
		JPanel postcodePanel = new JPanel();
		JTextField postcodeField = new JTextField(20);
		postcodePanel.add(new JLabel("Postcode:", JLabel.RIGHT));
		postcodePanel.add(postcodeField);
		
		// 'find patient' button
		JButton findPatientButton = new JButton("Find Patient");
		findPatientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get patient details from input fields
				firstName = firstNameField.getText();
				surname = surnameField.getText();
				dOBDay = dOBDayField.getText();
				dOBMonth = dOBMonthField.getText();
				dOBYear = dOBYearField.getText();
				houseNoString = houseNoField.getText();
				postcode = postcodeField.getText().replace(" ", "");
				
				// check that all the fields contain valid data for the specific details
				String[] textFields = {firstName, surname, dOBDay, dOBMonth, dOBYear, houseNoString, postcode};
				if (allFieldsInput(textFields) && validDOB(dOBDay, dOBMonth, dOBYear) 
						&& validHouseNo(houseNoString)) {
					if (dOBDay.length()==1) {
						dOBDay="0"+dOBDay;
					}
					if (dOBMonth.length()==1) {
						dOBMonth="0"+dOBMonth;
					}
					searchPerformed = true;
					patientPanel.updatePanel();
				}
			}
		});
		
		// add all input panels to this FindPatientPanel
		add(firstNamePanel);
		add(surnamePanel);
		add(dOBPanel);
		add(houseNoPanel);
		add(postcodePanel);
		add(findPatientButton);
	}
	
	private boolean allFieldsInput(String[] textFields) {
		for (int x=0; x<textFields.length; x++) {
			if ((textFields[x]).isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	// check that the given date of birth is valid 
	private boolean validDOB(String day, String month, String year) {
		int dayInt = 0;
		int monthInt = 0;
		int yearInt = 0;
		// check day input is an int
		try {
			dayInt = Integer.parseInt(day);
		} catch (NumberFormatException f) {
			System.out.println("Invalid day");
			return false;
		}
		// check month input is an int
		try {
			monthInt = Integer.parseInt(month);
		} catch (NumberFormatException f) {
			System.out.println("Invalid month");
			return false;
		}
		// check year input is an int
		try {
			yearInt = Integer.parseInt(year);
		} catch (NumberFormatException f) {
			System.out.println("Invalid year");
			return false;
		}
		if (dayInt<1 || dayInt>31 || monthInt<1 || monthInt>12 || yearInt<1900 || yearInt>2016) {
			System.out.println("Invalid date input");
			return false;
		}
		return true;
	}
	
	// check that the house number given is an int
	private boolean validHouseNo(String houseNoString) {
		try {
			houseNo = Integer.parseInt(houseNoString);
		} catch (NumberFormatException f) {
			return false;
		}
		return true;
	}
	
	public boolean searchPerformed() {
		return searchPerformed;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getDOB() {
		String dOB = dOBYear+"-"+dOBMonth+"-"+dOBDay;
		return dOB;
	}
	
	public int getHouseNo() {
		return houseNo;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
}
