package sheffieldDentalCare;

import java.awt.*;
import javax.swing.*;

public class PatientPanel extends JPanel {
	private FindPatientPanel searchPanel = new FindPatientPanel(this);
	private PatientDetailsPanel resultsPanel;
	
	public PatientPanel() {
		setLayout(new GridLayout(0,1));
		add(searchPanel);
		
		resultsPanel = new PatientDetailsPanel(searchPanel.searchPerformed(), searchPanel.getFirstName(), 
				searchPanel.getSurname(), searchPanel.getDOB(), searchPanel.getHouseNo(), searchPanel.getPostcode());
		add(resultsPanel);
		validate();
		repaint();
	}
	
	public void updatePanel() {
		this.remove(resultsPanel);
		resultsPanel = new PatientDetailsPanel(searchPanel.searchPerformed(), searchPanel.getFirstName(), 
				searchPanel.getSurname(), searchPanel.getDOB(), searchPanel.getHouseNo(), searchPanel.getPostcode());
		this.add(resultsPanel);
		this.validate();
		this.repaint();
	}
	
}
