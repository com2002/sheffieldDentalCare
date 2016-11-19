package sheffieldDentalCare;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * DentistCheckoutPanel.java
 * To check out and add treatments to an appointment with a patient
 * @author ting
 *
 */
@SuppressWarnings("serial")
public class DentistCheckoutPanel extends JPanel {
	private JLabel titleLbl = new JLabel("Checkout Patient");
	private JLabel appointmentLbl = new JLabel("Select Appointment");
	private JComboBox<String> appointmentCbox = new JComboBox<String>();
	private JLabel treatmentsLbl = new JLabel("Treatments");
	private JCheckBox checkUpChBox = new JCheckBox("Check-Up (£45)");
	private JCheckBox amalgamChBox = new JCheckBox("Silver Amalgam Filling (£90)");
	private JCheckBox resinChBox = new JCheckBox("White Composite Resin Filling (£150)");
	private JCheckBox crownChBox = new JCheckBox("Gold Crown Fitting (£500)");
	private JButton checkoutBtn = new JButton("Checkout");
	
	public DentistCheckoutPanel() {
		initComponents();
		addComponents();
	}
	
	private void initComponents() {
		// Populate appointments drop down list
	}
	
	private void addComponents() {
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		this.setLayout(layout);
		
		// Add components to layout
		// Position components in the horizontal
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(titleLbl)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(appointmentLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(appointmentCbox)
					)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(treatmentsLbl)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(checkUpChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(amalgamChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(resinChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(crownChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(checkoutBtn)
				)
				)
		);

		// Position components in the vertical
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(titleLbl)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(appointmentLbl)
					.addComponent(appointmentCbox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(treatmentsLbl)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(checkUpChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(amalgamChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(resinChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(crownChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(checkoutBtn)
				)
		);
	}
}
