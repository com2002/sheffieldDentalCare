package sheffieldDentalCare;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PatientDetailsPanel extends JPanel {
	
	public PatientDetailsPanel(Boolean searchValid, String firstName, String surname,
			String dOB, String houseNo, String postcode) {
		setLayout(new GridLayout(0,1));
		if (!searchValid) {
			add(new JLabel("Not valid search", JLabel.CENTER));
		}
		else {
			add(new JLabel("Results will appear here", JLabel.CENTER));
			add(new JLabel("Name: " + firstName + " " + surname, JLabel.LEFT));
			add(new JLabel("Date of Birth: " + dOB, JLabel.LEFT));
			add(new JLabel("House Number: " + houseNo, JLabel.LEFT));
			add(new JLabel("Postcode: " + postcode, JLabel.LEFT));
		}
	}
	
}
