package sheffieldDentalCare;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FindPatientPanel extends JPanel {
	
	public FindPatientPanel() {
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
				// ACTION FOR BUTTON CLICK HERE
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
	
}
