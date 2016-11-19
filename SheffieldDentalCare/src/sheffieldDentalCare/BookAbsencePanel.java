package sheffieldDentalCare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;

/**
 * BookAbsencePanel.java
 * Books a leave of absence for a partner
 * @author ting
 *
 */
@SuppressWarnings("serial")
public class BookAbsencePanel extends JPanel {
	private JLabel titleLbl = new JLabel("Book Absence");
	private JLabel forLbl = new JLabel("For");
	private JRadioButton dentistRBtn = new JRadioButton("Dentist");
	private JRadioButton hygienistRBtn = new JRadioButton("Hygienist");
	private JLabel dateLbl = new JLabel("Date");
	private SpinnerModel dateModel;
	private JSpinner dateSpinner = new JSpinner();
	private JLabel timeLbl = new JLabel("Time");
	private JLabel startTimeLbl = new JLabel("Start Time");
	private JRadioButton wholeDayRBtn = new JRadioButton("Whole Day");
	private JRadioButton timePeriodRBtn = new JRadioButton("Time Period");
	private JComboBox<String> startTimeCbox = new JComboBox<String>();
	private JLabel endTimeLbl = new JLabel("End Time");
	private JComboBox<String> endTimeCbox = new JComboBox<String>();
	private JButton bookBtn = new JButton("Book");
	
	/**
	 * Class constructor
	 */
	public BookAbsencePanel() {
		initComponents();
		addComponents();
	}
	
	/**
	 * Initialises components. Sets radio buttons, date selector and time drop down lists
	 */
	private void initComponents() {
		// Set dentist radio button by default as selected
		dentistRBtn.setSelected(true);
		// Create button group for for radio buttons
		ButtonGroup forBtnGroup = new ButtonGroup();
		forBtnGroup.add(dentistRBtn);
		forBtnGroup.add(hygienistRBtn);
		// Create button group for time radio buttons
		wholeDayRBtn.setSelected(true);
		wholeDayRBtn.setActionCommand("Whole Day");
		timePeriodRBtn.setActionCommand("Time Period");
		ButtonGroup timeBtnGroup = new ButtonGroup();
		timeBtnGroup.add(wholeDayRBtn);
		timeBtnGroup.add(timePeriodRBtn);
		// Set up date selector
		dateModel = new SpinnerDateModel();
		dateSpinner = new JSpinner(dateModel);
		JSpinner.DateEditor de = new JSpinner.DateEditor(dateSpinner, "E dd-MM-yyyy");
		dateSpinner.setEditor(de);
		// Add times to start time and end time drop down lists
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String strTime = "09:00";
		Date startTime = null;
		try {
			startTime = new SimpleDateFormat("HH:mm").parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		for (int i = 0; i < 24; i++) {
			startTimeCbox.addItem(timeFormat.format(cal.getTime()));
			cal.add(Calendar.MINUTE, 20);
			endTimeCbox.addItem(timeFormat.format(cal.getTime()));
		}
		startTimeCbox.setEnabled(false);
		endTimeCbox.setEnabled(false);
	}
	
	/**
	 * Lays out components in panel
	 */
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
						.addComponent(forLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(dentistRBtn)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(hygienistRBtn)
					)
				)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(dateLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(dateSpinner)
					)
				)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(timeLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(wholeDayRBtn)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(timePeriodRBtn)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(startTimeLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(startTimeCbox)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(endTimeLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(endTimeCbox)
					)
				)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(bookBtn)
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
					.addComponent(forLbl)
					.addComponent(dentistRBtn)
					.addComponent(hygienistRBtn)			    		
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(dateLbl)
					.addComponent(dateSpinner)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(timeLbl)
					.addComponent(wholeDayRBtn)
					.addComponent(timePeriodRBtn)
					.addComponent(startTimeLbl)
					.addComponent(startTimeCbox)
					.addComponent(endTimeLbl)
					.addComponent(endTimeCbox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(bookBtn)
				)
		);
		// Add action listeners for radio buttons
		wholeDayRBtn.addActionListener(new RBtnHandler());
		timePeriodRBtn.addActionListener(new RBtnHandler());
		// Add action listener for book button
		bookBtn.addActionListener(new BookBtnHandler());
	}
	/**
	 * Event handler for book button
	 */
	private class BookBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Book button clicked");
			boolean valid = validateEndTime();
			if (valid) {
				System.out.println("Valid End Time: "+ valid);
				// Confirm with user booking of absence
				int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to book this leave of absence?", "Confirm Absence", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				System.out.println(n);
				if (n == 0) {
					System.out.println("Yes");
					// Blank patient's ID by default is 1
					int patientID = 1;
					// Change date selected into database format
					SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date date =	(Date) dateSpinner.getValue();
					String strDate = dbDateFormat.format(date);
					boolean pHygienist = false;
					if (hygienistRBtn.isSelected()) {
						pHygienist = true;
					}
					// Assume whole day is selected
					String startTime = "09:00";
					String endTime = "17:00";
					if (timePeriodRBtn.isSelected()) {
						startTime = startTimeCbox.getSelectedItem().toString();
						endTime = endTimeCbox.getSelectedItem().toString();
					}
					DPCalendar dpCal = new DPCalendar();
					// Check availability
					String availability = null;
					try {
						System.out.println(patientID);
						System.out.println("Hygienist: " + pHygienist);
						System.out.println(strDate);
						System.out.println(startTime);
						System.out.println(endTime);
						availability = dpCal.checkAvailability(patientID, pHygienist, strDate, startTime, endTime);
						System.out.println(availability);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if (availability.equals("")) {
						System.out.println("Available");
						// Add absence as an appointment for blank patient in database
						int appID = 0;
						try {
							appID = dpCal.addAppointment(patientID, pHygienist, strDate, startTime, endTime);
							System.out.println("Appointment ID: " + appID);
						} catch (SQLException e1) {
							// Output error message
							JOptionPane.showMessageDialog(null, e1.toString(), "Error - Database", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						} finally {
							// Output confirmation for user
							String confirmMsg = null;
							if (!pHygienist) {
								confirmMsg = "Absence booked on " + strDate + " between " + startTime + " and " + endTime + " for the dentist.";
							} else {
								confirmMsg = "Absence booked on " + strDate + " between " + startTime + " and " + endTime + " for the hygienist.";
							}
							JOptionPane.showMessageDialog(null, confirmMsg, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						System.out.println("Not Available");
						// Output error message if not available to book appointment
						String errorMsg = "Cannot book leave of absence when there is an appointment. " + availability;
						JOptionPane.showMessageDialog(null, errorMsg, "Error - Appointment Clash with Absence", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					System.out.println("No");
				}
			} else {
				// Output error message if end time not valid
				String errorMsg = "End time of " + endTimeCbox.getSelectedItem().toString() + " cannot be before start time of " 
								+ startTimeCbox.getSelectedItem().toString() + ".";
				JOptionPane.showMessageDialog(null, errorMsg, "Error - Invalid End Time", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Event handler for radio buttons
	 */
	private class RBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Radio button changed");
			System.out.println(e.getActionCommand());
			if (e.getActionCommand() == "Time Period") {
				startTimeCbox.setEnabled(true);
				endTimeCbox.setEnabled(true);
			} else {
				startTimeCbox.setEnabled(false);
				endTimeCbox.setEnabled(false);
			}
		}
	}
	
	/**
	 * Checks whether end time is valid
	 * @return valid
	 */
	private boolean validateEndTime() {
		boolean valid = true;
		// If whole day then ignore what is in drop down lists
		if (timePeriodRBtn.isSelected()) {
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			Calendar cal = Calendar.getInstance();
			Date startTime = null;
			try {
				startTime = timeFormat.parse(startTimeCbox.getSelectedItem().toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.setTime(startTime);
			Calendar cal2 = Calendar.getInstance();
			Date endTime = null;
			try {
				endTime = timeFormat.parse(endTimeCbox.getSelectedItem().toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal2.setTime(endTime);
			// Check if start time selected is before end time selected
			valid = cal.getTime().before(cal2.getTime());
			System.out.println("Valid End Time: " + valid);
		}
		return valid;
	}
}
