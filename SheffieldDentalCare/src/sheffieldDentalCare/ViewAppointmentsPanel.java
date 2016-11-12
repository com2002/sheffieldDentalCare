package sheffieldDentalCare;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class ViewAppointmentsPanel extends JPanel implements Panel {
	private String userType;
	private Boolean pHygienist;
	private String viewType;
	private JLabel titleLbl = new JLabel();
	private JLabel weeksLbl = new JLabel("Select Week Commencing");
	private JComboBox<String> weeksCbox = new JComboBox<String>();
	private JTable appointmentsTbl = new JTable(9,6);
	
	public ViewAppointmentsPanel(String ut, Boolean ph, String vt) {
		userType = ut;
		pHygienist = ph;
		viewType = vt;
		initComponents();
		addComponents();
	}
	
	@Override
	public void initComponents() {
		if (pHygienist) {
			titleLbl.setText("View Hygienist Appointments");
		} else {
			titleLbl.setText("View Dentist Appointments");
		}
		// Populate drop down list with start date of each week in the current year
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		for (int i = 1; i <= 52; i++) {	
			cal.set(Calendar.WEEK_OF_YEAR, i);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			weeksCbox.addItem("Week " + i + ": " + sdf.format(cal.getTime()));
			// Set default selected item of drop down list
			Calendar cal2 = Calendar.getInstance();
			// If current week number is equivalent to added week number then set as default
			if (cal2.get(Calendar.WEEK_OF_YEAR) == cal.get(Calendar.WEEK_OF_YEAR)) {
				weeksCbox.setSelectedIndex(i-1);
			}
		}
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
						.addComponent(weeksLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(weeksCbox)
					)
				)
				.addComponent(appointmentsTbl)
				)
		);
				
		// Position components in the vertical
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(titleLbl)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(weeksLbl)
				    .addComponent(weeksCbox)
				    		
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(appointmentsTbl)
				)
		);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
