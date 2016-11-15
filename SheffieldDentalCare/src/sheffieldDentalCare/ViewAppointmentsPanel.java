package sheffieldDentalCare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ViewAppointmentsPanel extends JPanel implements Panel {
	private String calendarFor;
	private String viewType;
	private JLabel titleLbl = new JLabel();
	private JPanel selectionPanel;
	private JTable tbl;
	private DefaultTableModel tblModel;
	private JScrollPane scrollPane;
	private WeekViewAppointments weekView;
	
	public ViewAppointmentsPanel(String vt, String cf) {
		viewType = vt;
		calendarFor = cf;
		initComponents();
		addComponents();
	}
	
	@Override
	public void initComponents() {
		if (calendarFor == "Dentist") {
			titleLbl.setText("View Dentist Appointments");
		} else {
			titleLbl.setText("View Hygienist Appointments");
		}
		if (viewType == "Week") {
			weekView = new WeekViewAppointments(calendarFor);
			selectionPanel = weekView.getSelectionPanel();
			tblModel = weekView.getTblModel();
			tbl = new JTable(tblModel);
			scrollPane = new JScrollPane(tbl);
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
						.addComponent(selectionPanel)
					)
				)
				.addComponent(scrollPane)
				)
		);
				
		// Position components in the vertical
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(titleLbl)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(selectionPanel)				    		
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(scrollPane)
				)
		);
		
		weekView.weeksCbox.addActionListener(new WeeksCboxHandler());
	}
	
	// Event handler for drop down list for weeks
	private class WeeksCboxHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Drop down list changed");
			weekView.makeTbl();
			tblModel = weekView.getTblModel();
			tbl.setModel(tblModel);
			tbl.invalidate();
			invalidate();
			validate();
			repaint();
		}
	}
}
