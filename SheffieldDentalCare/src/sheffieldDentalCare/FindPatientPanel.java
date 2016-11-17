package sheffieldDentalCare;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.*;

public class FindPatientPanel extends JPanel {
	private boolean searchPerformed = false;
	private String firstName = null;
	private String surname = null;
	private String dOB = null;
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
		JTextField dOBField = new JTextField(20);
		dOBPanel.add(new JLabel("Date of Birth:", JLabel.RIGHT));
		dOBPanel.add(dOBField);
		
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
				System.out.println("Button Clicked");
				
				firstName = firstNameField.getText();
				surname = surnameField.getText();
				dOB = dOBField.getText();
				houseNoString = houseNoField.getText();
				postcode = postcodeField.getText();
				
				String[] textFields = {firstName, surname, dOB, houseNoString, postcode};
				
				if (allFieldsInput(textFields) && validDOB(dOB) && validHouseNo(houseNoString)) {
					System.out.println("Search Accepted");
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
		boolean fieldsInput = true;
		for (int x=0; x<textFields.length; x++) {
			if ((textFields[x]).isEmpty()) {
				fieldsInput = false;
			}
		}
		return fieldsInput;
	}
	
	private boolean validDOB(String dOB) {
		return true;
	}
	
	private boolean validHouseNo(String houseNoString) {
		boolean validHouseNo = true;
		try {
			houseNo = Integer.parseInt(houseNoString);
		} catch (NumberFormatException f) {
			validHouseNo = false;
		}
		return validHouseNo;
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
		return dOB;
	}
	
	public String getHouseNo() {
		return houseNoString;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
}
