package sheffieldDentalCare;

import java.awt.*;
import javax.swing.*;

/**
 * PatientPanel.java
 * Creates a panel for finding, viewing and making changes to patients.
 * Contains sub-panels (FindPatientPanel and PatientDetailsPanel) to deal with the different parts.
 * @author Lewis
 * 
 */

@SuppressWarnings("serial")
public class PatientPanel extends JPanel {
	private FindPatientPanel searchPanel = new FindPatientPanel(this);
	private PatientDetailsPanel resultsPanel;
	private GridBagConstraints c = new GridBagConstraints();
	
	// add patient search form and initial results panel
	public PatientPanel() {
		setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.VERTICAL;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		add(searchPanel, c);
		
		resultsPanel = new PatientDetailsPanel(searchPanel.searchPerformed(), searchPanel.getFirstName(), 
				searchPanel.getSurname(), searchPanel.getDOB(), searchPanel.getHouseNo(), searchPanel.getPostcode());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		add(resultsPanel, c);
		validate();
		repaint();
	}
	
	// method to remove the old set of results and replace it with the latest set
	public void updatePanel() {
		this.remove(resultsPanel);
		resultsPanel = new PatientDetailsPanel(searchPanel.searchPerformed(), searchPanel.getFirstName(), 
				searchPanel.getSurname(), searchPanel.getDOB(), searchPanel.getHouseNo(), searchPanel.getPostcode());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0.6;
		c.gridx = 0;
		c.gridy = 1;
		this.add(resultsPanel, c);
		this.validate();
		this.repaint();
	}
	
}
