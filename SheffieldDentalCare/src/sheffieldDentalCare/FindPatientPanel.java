package sheffieldDentalCare;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.*;

public class FindPatientPanel extends JPanel {
	private boolean searchPerformed;
	private String firstName;
	private String surname;
	private String dOB;
	private int houseNo;
	private String postcode;
	
	public FindPatientPanel() {
		searchPerformed = false;
		firstName = null;
		surname = null;
		dOB = null;
		houseNo = 0;
		postcode = null;
		
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
				String houseNoString = houseNoField.getText();
				postcode = postcodeField.getText();
				
				String[] textFields = {firstName, surname, dOB, houseNoString, postcode};
				
				if (allFieldsInput(textFields) && validDOB(dOB) && validHouseNo(houseNoString)) {
					System.out.println("Search Accepted");
					searchPerformed = true;
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
	
}
