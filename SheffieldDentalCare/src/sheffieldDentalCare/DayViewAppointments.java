package sheffieldDentalCare;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.table.DefaultTableModel;

public class DayViewAppointments extends ViewAppointments {
	private String calendarFor;
	private JLabel dateLbl = new JLabel("Select Date");
	private SpinnerModel dateModel;
	private JSpinner dateSpinner;
	private ArrayList<String> patients = new ArrayList<String>();
	public JButton viewBtn = new JButton("View");

	public DayViewAppointments(String cf) {
		calendarFor = cf;
		setPatients();
		makeSelectionPanel();
		makeTbl();
	}

	@Override
	public void makeSelectionPanel() {
		JPanel panel = new JPanel();
		// Initialise date selector component
		dateModel = new SpinnerDateModel();
		dateSpinner = new JSpinner(dateModel);
		JSpinner.DateEditor de = new JSpinner.DateEditor(dateSpinner, "E dd-MM-yyyy");
		dateSpinner.setEditor(de);

		GroupLayout layout = new GroupLayout(panel);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		panel.setLayout(layout);

		// Add components to layout
		// Position components in the horizontal
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(dateLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(dateSpinner)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(viewBtn)
					)
				)
		);

		// Position components in the vertical
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(dateLbl)
					.addComponent(dateSpinner)
					.addComponent(viewBtn)
				)
		);
		setSelectionPanel(panel);
	}

	@Override
	public void makeTbl() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("E dd-MM-yyyy");
		// Set up column names
		String[] cols = new String [2];
		cols[0] = "";
		cols[1] = dateFormat.format(dateSpinner.getValue());
		// Set up times
		String strTime = "09:00";
		Date startTime = null;
		try {
			startTime = new SimpleDateFormat("HH:mm").parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		// Set up date
		Object[][] data = new Object[27][2];
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		// Date format from database
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Get appointments
		DPCalendar dpCal = new DPCalendar();
		AppointmentPlot[] appPlot = null;
		try {
			String date = dbDateFormat.format(dateFormat.parse(cols[1]));
			if (calendarFor == "Hygienist") {
				appPlot = dpCal.getAppointmentsForDate(true, date);
			} else {
				appPlot = dpCal.getAppointmentsForDate(false, date);
			}
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 27; i++) {
			for (int j = 0; j < 2; j++) {
				if (j == 0) {
					data[i][j] = timeFormat.format(cal.getTime());
				}
				for (int k = 0; k < appPlot.length; k++)  {
					// Wanted date format
					String date = null;
					Date time = null;
					try {
						date = dateFormat.format(dbDateFormat.parse(appPlot[k].DATE));
						time = new SimpleDateFormat("HH:mm").parse(appPlot[k].STARTTIME);
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(time);
						System.out.println(timeFormat.format(cal2.getTime()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("DB Date: " + date);
					System.out.println("DB Time: " + appPlot[k].STARTTIME);
					System.out.println("Col Date: " + cols[j]);
					System.out.println("Row Time: " + timeFormat.format(cal.getTime()));
					System.out.println("");

					if (date.equals(cols[j]) && appPlot[k].STARTTIME.equals(timeFormat.format(cal.getTime()))) {
						String patient = null;
						for (int l = 0; l < patients.size(); l++) {
							if (appPlot[k].PATIENTID == getPatientID(patients.get(l))) {
								patient = patients.get(l);
							}
						}
						data[i][j] = appPlot[k].STARTTIME + " - " + appPlot[k].ENDTIME + ": " + patient;
					}
				}
			}
			cal.add(Calendar.MINUTE, 20);
		}

		setTblModel(new DefaultTableModel(data, cols));
	}
	
	private void setPatients() {
		Registrar reg = new Registrar();
		try {
			patients = reg.getForAllPatientsSomeDetails();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int getPatientID(String p) {
		String patient = p;
		int patientID = 0;
		int indexOfColon = patient.indexOf(":");
		patientID = Integer.parseInt(patient.substring(0, indexOfColon));
		System.out.println(patientID);
		return patientID;
	}
}