package sheffieldDentalCare;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class SecretaryPanel extends JPanel implements Panel {
	private JLabel titleLbl = new JLabel("Secretary - Main");
	private JTabbedPane patientsTPane = new JTabbedPane();
	private JTabbedPane appointmentsTPane = new JTabbedPane();
	private JSplitPane splitPane;
	
	public SecretaryPanel() {
		initComponents();
		addComponents();
	}
	
	@Override
	public void initComponents() {
		RegisterPanel registerPanel = new RegisterPanel();
		PatientPanel patientPanel = new PatientPanel();
		
		ViewAppointmentsPanel dentistPanel = new ViewAppointmentsPanel("Week", "Dentist");
		ViewAppointmentsPanel hygienistPanel = new ViewAppointmentsPanel("Week", "Hygienist");
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, patientsTPane, appointmentsTPane);
		splitPane.setDividerLocation(0.5);
		// Add register panel to patientsTPane
		patientsTPane.add("Register New Patient", registerPanel);
		// Add find patient panel to patientsTPane
		patientsTPane.add("Find Patient", patientPanel);
		// Create tabbed pane for appointments side
		// Add week to view calendar for dentist to appointmentsTPane
		appointmentsTPane.add("Dentist", dentistPanel);
		// Add week to view calendar for hygienist to appointmentsTPane
		appointmentsTPane.add("Hygienist", hygienistPanel);
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
