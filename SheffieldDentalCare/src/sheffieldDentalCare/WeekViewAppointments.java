package sheffieldDentalCare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;

public class WeekViewAppointments extends ViewAppointments {
	private String calendarFor;
	private JLabel selectWeekLbl = new JLabel("Select Week Commencing");
	private JLabel viewLbl = new JLabel("View");
	private JComboBox<String> patientsCbox = new JComboBox<String>();
	private JComboBox<String> weekCbox = new JComboBox<String>();
	private JRadioButton allPatientsRBtn = new JRadioButton("All Patients");
	private JRadioButton singlePatientRBtn = new JRadioButton("Single Patient");
	public JButton viewBtn = new JButton("View");
	
	public WeekViewAppointments(String cf) {
		calendarFor = cf;
		makeSelectionPanel();
		makeTbl();
	}
	
	public void makeSelectionPanel() {
		JPanel panel = new JPanel();
		// Set default selected as by week
		allPatientsRBtn.setSelected(true);
		allPatientsRBtn.setActionCommand("All Patients");
		singlePatientRBtn.setActionCommand("Single Patient");
		// Create radio button group
		ButtonGroup rBtnGroup = new ButtonGroup();
		rBtnGroup.add(allPatientsRBtn);
		rBtnGroup.add(singlePatientRBtn);
		// Set patients drop down list as disabled by default
		patientsCbox.setEnabled(false);
		// Populate drop down list with start date of each week in the current year
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		for (int i = 1; i <= 52; i++) {	
			cal.set(Calendar.WEEK_OF_YEAR, i);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			// Set default selected item of drop down list
			Calendar cal2 = Calendar.getInstance();
			// If current week number is equivalent to added week number then set as default
			if (cal2.get(Calendar.WEEK_OF_YEAR) == cal.get(Calendar.WEEK_OF_YEAR)) {
				weekCbox.addItem("Week " + i + ": " + sdf.format(cal.getTime()) + " (Current)");
				weekCbox.setSelectedIndex(i-1);
			} else {
				weekCbox.addItem("Week " + i + ": " + sdf.format(cal.getTime()));
			}
		}

		GroupLayout layout = new GroupLayout(panel);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		panel.setLayout(layout);
		
		// Add components to layout
		// Position components in the horizontal
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(selectWeekLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(weekCbox)
					)
				)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(viewLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(allPatientsRBtn)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(singlePatientRBtn)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(patientsCbox)
					)
				)
				.addComponent(viewBtn)
				)
		);
				
		// Position components in the vertical
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(selectWeekLbl)
				    .addComponent(weekCbox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(viewLbl)
					.addComponent(allPatientsRBtn)
					.addComponent(singlePatientRBtn)
					.addComponent(patientsCbox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(viewBtn)
				)
		);
		setSelectionPanel(panel);
		allPatientsRBtn.addActionListener(new RBtnHandler());
		singlePatientRBtn.addActionListener(new RBtnHandler());
	}

	public void makeTbl() {
		int weekNo = getWeekNo();
		// Set column names as dates starting from week selected
		SimpleDateFormat dateFormat = new SimpleDateFormat("E dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, weekNo);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String[] cols = new String[6];
		cols[0] = "";
		for (int i = 1; i < 6; i++) {
			cols[i] = dateFormat.format(cal.getTime());
			cal.add(Calendar.DATE, 1);
		}
		
		// Set up times
		String strTime = "09:00";
		Date startTime = null;
		try {
			startTime = new SimpleDateFormat("H:mm").parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(startTime);
		Object[][] data = new Object[9][6];
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		// Get appointments
		DPCalendar dpCal = new DPCalendar();
		AppointmentPlot[] appPlot = null;
		try {
			if (calendarFor == "Dentist") {
				// Date fixed for now as testing
				appPlot = dpCal.getAppointments(false, "2016-11-14");
			} else {
				// Date fixed for now as testing
				appPlot = dpCal.getAppointments(true, "2016-11-14");
			}
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 6; j++) {
				if (j == 0) {
					data[i][j] = timeFormat.format(cal2.getTime());
				}
				for (int k = 0; k < appPlot.length; k++)  {
					// Date format from database
					SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					// Wanted date format
					String date = null;
					try {
						date = dateFormat.format(dbDateFormat.parse(appPlot[k].DATE));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("DB Date: " + date);
					System.out.println("DB Time: " + appPlot[k].STARTTIME);
					System.out.println("Col Date: " + cols[j]);
					System.out.println("Row Time: " + timeFormat.format(cal2.getTime()));
					System.out.println("");
					if (date.equals(cols[j]) && appPlot[k].STARTTIME.equals(timeFormat.format(cal2.getTime()))) {
						data[i][j] = appPlot[k].STARTTIME + " - " + appPlot[k].ENDTIME + " PatientID: " + appPlot[k].PATIENTID;
					}
				}
			}
			cal2.add(Calendar.HOUR, 1);
		}

		// Add data and column names to a table model
		setTblModel(new DefaultTableModel(data, cols));
	}
	
	private int getWeekNo() {
		// Get week number from drop down list
		String week = weekCbox.getSelectedItem().toString();
		String weekNo = week.substring(5, 7);
		if (weekNo.contains(":")) {
			weekNo = weekNo.replace(":", "");
		}
		return Integer.parseInt(weekNo);
	}
	
	private void setSelectionCboxBySinglePatient() {
		// Populate drop down list with all patients
		patientsCbox.setEnabled(true);
		// For testing purposes
		patientsCbox.addItem("Steven Universe");
	}
	
	// Event handler for radio buttons
	private class RBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Radio button changed");
			System.out.println(e.getActionCommand());
			if (e.getActionCommand() == "Single Patient") {
				setSelectionCboxBySinglePatient();
				// Show only all of a patient's appointment for that week
			} else {
				patientsCbox.setEnabled(false);
				// Show all patients for that week
			}

		}
	}
}
