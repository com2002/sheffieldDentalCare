package sheffieldDentalCare;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegisterPanel extends JPanel{
	private RegisterPatientPanel regPanel = new RegisterPatientPanel(this);
	private RegisterDetailsPanel completedPanel;
	private GridBagConstraints c = new GridBagConstraints();
	
	public RegisterPanel() {
		setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		add(regPanel, c);
			completedPanel = new RegisterDetailsPanel(regPanel.addValid(), regPanel.getTitle(), regPanel.getFirstName(), 
					regPanel.getSurname(), regPanel.getDOB(), regPanel.getPhone(), regPanel.getHouseNum(),
					regPanel.getStreet(), regPanel.getDistrict(), regPanel.getCity(), regPanel.getPostcode());
			c.fill = GridBagConstraints.VERTICAL;
			c.anchor = GridBagConstraints.PAGE_START;
			c.gridwidth = 1;
			c.gridheight = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 1;
		add(completedPanel,c);
		validate();
		repaint();
	}
	
	public void updateRegisterPanel() {
		this.remove(completedPanel);
			completedPanel = new RegisterDetailsPanel(regPanel.addValid(), regPanel.getTitle(), regPanel.getFirstName(), 
					regPanel.getSurname(), regPanel.getDOB(), regPanel.getPhone(), regPanel.getHouseNum(),
					regPanel.getStreet(), regPanel.getDistrict(), regPanel.getCity(), regPanel.getPostcode());
			c.fill = GridBagConstraints.VERTICAL;
			c.anchor = GridBagConstraints.CENTER;
			c.gridwidth = 1;
			c.gridheight = 1;
			c.weightx = 1;
			c.weighty = 0.6;
			c.gridx = 0;
			c.gridy = 1;
		this.add(completedPanel,c);
		this.validate();
		this.repaint();
	}
}