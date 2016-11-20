package sheffieldDentalCare;

import javax.swing.*;

@SuppressWarnings("serial")
public class PartnerPanel extends JPanel implements Panel {
	private JLabel titleLbl = new JLabel("Partner - Main");
	private JPanel checkoutPanel = new JPanel();
	private JPanel appointmentsPanel;
	private JSplitPane splitPane;
	
	public PartnerPanel() {
		initComponents();
		addComponents();
	}
	
	@Override
	public void initComponents() {
		//System.out.println(MainFrame.USER_TYPE);
		// Depending on current user, output their calendar
		if (MainFrame.USER_TYPE == "Dentist") {
			// Created as week view for now for testing purposes
			appointmentsPanel = new ViewAppointmentsPanel("Day", "Dentist");
			checkoutPanel = new DentistCheckoutPanel();
		} else {
			// Created as week view for now for testing purposes
			appointmentsPanel = new ViewAppointmentsPanel("Day", "Hygienist");
			checkoutPanel = new HygienistCheckoutPanel();
		}
		// Add panels to split pane
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, checkoutPanel, appointmentsPanel);
		splitPane.setDividerLocation(0.5);
	}

	@Override
	public void addComponents() {
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
						.addComponent(splitPane)
					)
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
					.addComponent(splitPane)
				)
		);
	}
}
