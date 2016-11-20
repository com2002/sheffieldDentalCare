package sheffieldDentalCare;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * ViewAppointmentPanel.java
 * Creates a panel for viewing appointments.
 * @author ting
 * 
 */

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
	private DayViewAppointments dayView;
	
	/**
	 * Class constructor
	 * Initialises panel
	 * @param vt	Either "Week" or "Day"
	 * @param cf	Either for "Dentist" or "Hygienist"
	 */
	public ViewAppointmentsPanel(String vt, String cf) {
		viewType = vt;
		calendarFor = cf;
		initComponents();
		addComponents();
	}
	
	/**
	 * Initialises components. Sets titleLbl, selectionPanel and table
	 */
	@Override
	public void initComponents() {
		if (calendarFor == "Dentist") {
			titleLbl.setText("View Dentist Appointment");
		} else {
			titleLbl.setText("View Hygienist Appointment");
		}
		if (viewType == "Week") {
			weekView = new WeekViewAppointments(calendarFor);
			selectionPanel = weekView.getSelectionPanel();
			tblModel = weekView.getTblModel();
			tbl = new JTable(tblModel);
			scrollPane = new JScrollPane(tbl);
		} else {
			dayView = new DayViewAppointments(calendarFor);
			selectionPanel = dayView.getSelectionPanel();
			tblModel = dayView.getTblModel();
			tbl = new JTable(tblModel);
			scrollPane = new JScrollPane(tbl);
		}
		tbl.getTableHeader().setReorderingAllowed(false);
		tbl.setCellSelectionEnabled(false);
	}
	
	/**
	 * Lays out components in panel and add
	 */
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
		// Add action listeners to viewBtn according to view type
		if (viewType == "Week") {
			weekView.viewBtn.addActionListener(new ViewBtnHandler());
		} else {
			dayView.viewBtn.addActionListener(new ViewBtnHandler());
		}
	}
	
	/**
	 * Event handler for drop down list for weeks
	 */
	private class ViewBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Selection options changed");
			// Update table model
			// Get updated table model
			if (viewType == "Week") {
				weekView.makeTblModel();
				tblModel = weekView.getTblModel();
			} else {
				dayView.makeTblModel();
				tblModel = dayView.getTblModel();
			}
			tbl.setModel(tblModel);
			tbl.invalidate();
			invalidate();
			validate();
			repaint();
		}
	}
}
