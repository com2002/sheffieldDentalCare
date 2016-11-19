package sheffieldDentalCare;

import java.awt.*;
import javax.swing.*;

public class PatientPanel extends JPanel {
	private FindPatientPanel searchPanel = new FindPatientPanel(this);
	private PatientDetailsPanel resultsPanel;
	private GridBagConstraints c = new GridBagConstraints();
	
	public PatientPanel() {
		setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		add(searchPanel, c);
		
		resultsPanel = new PatientDetailsPanel(searchPanel.searchPerformed(), searchPanel.getFirstName(), 
				searchPanel.getSurname(), searchPanel.getDOB(), searchPanel.getHouseNo(), searchPanel.getPostcode());
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
	
	public void updatePanel() {
		this.remove(resultsPanel);
		resultsPanel = new PatientDetailsPanel(searchPanel.searchPerformed(), searchPanel.getFirstName(), 
				searchPanel.getSurname(), searchPanel.getDOB(), searchPanel.getHouseNo(), searchPanel.getPostcode());
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0.9;
		c.gridx = 0;
		c.gridy = 1;
		this.add(resultsPanel, c);
		this.validate();
		this.repaint();
	}
	
}
