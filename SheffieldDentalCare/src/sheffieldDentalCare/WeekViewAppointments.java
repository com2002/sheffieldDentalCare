package sheffieldDentalCare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;

/**
 * WeekViewAppointments.java
 * Uses the ViewAppointments abstract class and creates a selection panel and table model
 * @author ting
 *
 */
public class WeekViewAppointments extends ViewAppointments {
	private String calendarFor;
	private JLabel selectWeekLbl = new JLabel("Select Week Commencing");
	private JLabel viewLbl = new JLabel("View");
	private JComboBox<String> patientsCbox = new JComboBox<String>();
	private JComboBox<String> weekCbox = new JComboBox<String>();
	private JRadioButton allPatientsRBtn = new JRadioButton("All Patients");
	private JRadioButton singlePatientRBtn = new JRadioButton("Single Patient");
	public JButton viewBtn = new JButton("View");
	private ArrayList<String> patients = new ArrayList<String>();
	
	/**
	 * Class constructor
	 * Makes/sets selection panel and table model
	 * @param cf	Either for "Dentist" or "Hygienist"
	 */
	public WeekViewAppointments(String cf) {
		calendarFor = cf;
		setPatients();
		makeSelectionPanel();
		makeTblModel();
	}
	
	/**
	 * Creates a selection panel that provides the option to view by all patients or a single patient
	 */
	@Override
	public void makeSelectionPanel() {
		JPanel panel = new JPanel();
		// Set default selected as view by all patients
		allPatientsRBtn.setSelected(true);
		allPatientsRBtn.setActionCommand("All Patients");
		singlePatientRBtn.setActionCommand("Single Patient");
		// Create radio button group
		ButtonGroup rBtnGroup = new ButtonGroup();
		rBtnGroup.add(allPatientsRBtn);
		rBtnGroup.add(singlePatientRBtn);
		// Set patients drop down list as disabled by default
		patientsCbox.setEnabled(false);
		// Populate drop down list with all patients
		for (int i = 0; i < patients.size(); i++) {
			patientsCbox.addItem(patients.get(i));
			System.out.println(patients.get(i));
		}
		// Populate drop down list with start date of each week in the current year
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		for (int i = 1; i <= 52; i++) {	
			cal.set(Calendar.WEEK_OF_YEAR, i);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			// Set default selected item of drop down list
			Calendar cal2 = Calendar.getInstance();
			// If current week number is equivalent to the week number to be added then set as default
			if (cal2.get(Calendar.WEEK_OF_YEAR) == cal.get(Calendar.WEEK_OF_YEAR)) {
				// Allow easier identification of current week
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
		// Set created panel as selection panel
		setSelectionPanel(panel);
		// Add action listeners for radio buttons
		allPatientsRBtn.addActionListener(new RBtnHandler());
		singlePatientRBtn.addActionListener(new RBtnHandler());
	}
	
	/**
	 * Creates a table model that shows appointments and places them is a cell according to time and date
	 */
	@Override
	public void makeTblModel() {
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
			startTime = new SimpleDateFormat("HH:mm").parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(startTime);
		Object[][] data = new Object[24][6];
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		// Date format from database
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Get appointments
		DPCalendar dpCal = new DPCalendar();
		AppointmentPlot[] appPlot = null;
		try {
			String weekCommencingDate = dbDateFormat.format(dateFormat.parse(cols[1]));
			// According to calendarFor, get their appointments
			if (calendarFor == "Hygienist") {
				appPlot = dpCal.getAppointments(true, weekCommencingDate);
			} else {
				appPlot = dpCal.getAppointments(false, weekCommencingDate);
			}
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		int patientID = 0;
		if (singlePatientRBtn.isSelected()) {
			patientID = getPatientID(patientsCbox.getSelectedItem().toString());
		}
		// Place appointments in cell according to time and date
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 6; j++) {
				if (j == 0) {
					data[i][j] = timeFormat.format(cal2.getTime());
				}
				for (int k = 0; k < appPlot.length; k++)  {
					// Wanted date format
					String date = null;
					Date time = null;
					try {
						date = dateFormat.format(dbDateFormat.parse(appPlot[k].DATE));
						time = new SimpleDateFormat("HH:mm").parse(appPlot[k].STARTTIME);
						Calendar cal3 = Calendar.getInstance();
						cal3.setTime(time);
						System.out.println(timeFormat.format(cal3.getTime()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					System.out.println("DB Date: " + date);
					System.out.println("DB Time: " + appPlot[k].STARTTIME);
					System.out.println("Col Date: " + cols[j]);
					System.out.println("Row Time: " + timeFormat.format(cal2.getTime()));
					System.out.println("");
					// If view by single patient then no need to add patient details to every appointment
					if (singlePatientRBtn.isSelected()) {
						if (date.equals(cols[j]) && appPlot[k].STARTTIME.equals(timeFormat.format(cal2.getTime())) && patientID == appPlot[k].PATIENTID) {
							data[i][j] = "(" + appPlot[k].APPOINTMENTID + ") " + appPlot[k].STARTTIME + " - " + appPlot[k].ENDTIME;
						}
					} else {
						if (date.equals(cols[j]) && appPlot[k].STARTTIME.equals(timeFormat.format(cal2.getTime()))) {
							String patient = null;
							for (int l = 0; l < patients.size(); l++) {
								if (appPlot[k].PATIENTID == getPatientID(patients.get(l))) {
									patient = patients.get(l);
								}
							}
							data[i][j] = "(" + appPlot[k].APPOINTMENTID + ") " + appPlot[k].STARTTIME + " - " + appPlot[k].ENDTIME + ": " + patient;
						}
					}
				}
			}
			cal2.add(Calendar.MINUTE, 20);
		}
		// Add data and column names to a table model
		setTblModel(new DefaultTableModel(data, cols));
	}
	
	/**
	 * Gets all patients from database and sets to patients variable
	 */
	private void setPatients() {
		Registrar reg = new Registrar();
		try {
			patients = reg.getForAllPatientsSomeDetails();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		patients.set(0, "1: Absence");
	}
	
	/**
	 * Gets week number from week drop down list
	 * @return week number
	 */
	private int getWeekNo() {
		String week = weekCbox.getSelectedItem().toString();
		String weekNo = week.substring(5, 7);
		if (weekNo.contains(":")) {
			weekNo = weekNo.replace(":", "");
		}
		return Integer.parseInt(weekNo);
	}
	
	/**
	 * Gets patient ID from selected patient drop down list
	 * @param p		Selected patient
	 * @return patient ID
	 */
	private int getPatientID(String p) {
		String patient = p;
		int patientID = 0;
		int indexOfColon = patient.indexOf(":");
		patientID = Integer.parseInt(patient.substring(0, indexOfColon));
		System.out.println(patientID);
		return patientID;
	}
	
	/**
	 * Event handler for radio buttons
	 */
	private class RBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Radio button changed");
			System.out.println(e.getActionCommand());
			if (e.getActionCommand() == "Single Patient") {
				patientsCbox.setEnabled(true);
			} else {
				patientsCbox.setEnabled(false);
			}
		}
	}
}
