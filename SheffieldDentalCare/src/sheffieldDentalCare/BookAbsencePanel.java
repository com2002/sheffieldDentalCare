package sheffieldDentalCare;

import java.awt.event.*;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

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
	private JComboBox<String> dateCbox = new JComboBox<String>();
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
		// Set up date drop down list with dates Monday to Friday 4 weeks in advance
		SimpleDateFormat dateFormat = new SimpleDateFormat("E dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		// If Saturday then skip to Monday by adding two days
		if (dayOfWeek == Calendar.SATURDAY) {
			cal.add(Calendar.DATE, 2);
		// Else if Sunday then skip to Monday by adding one day
		} else if (dayOfWeek == Calendar.SUNDAY){
			cal.add(Calendar.DATE, 1);
		}
		for (int i = 0; i < 20; i++) {
			dateCbox.addItem(dateFormat.format(cal.getTime()));
			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			// If Friday then skip to Monday
			if (dayOfWeek == Calendar.FRIDAY) {
				cal.add(Calendar.DATE, 3);
			// If Saturday then skip to Monday
			} else if (dayOfWeek == Calendar.SATURDAY) {
				cal.add(Calendar.DATE, 2);
			// Else add one day
			} else {
				cal.add(Calendar.DATE, 1);
			}
		}
		// Add times to start time and end time drop down lists
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String strTime = "09:00";
		Date startTime = null;
		try {
			startTime = new SimpleDateFormat("HH:mm").parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(startTime);
		for (int i = 0; i < 24; i++) {
			startTimeCbox.addItem(timeFormat.format(cal2.getTime()));
			cal2.add(Calendar.MINUTE, 20);
			endTimeCbox.addItem(timeFormat.format(cal2.getTime()));
		}
		startTimeCbox.setEnabled(false);
		endTimeCbox.setEnabled(false);
	}
	
	/**
	 * Lays out components in panel
	 */
	private void addComponents() {
		
		JButton btnCancelAnAbsence = new JButton("Cancel an Absence");
		btnCancelAnAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new CancelAppointmentFrame(1);
				}
				catch (SQLException ex){
					JOptionPane.showMessageDialog(null, "Server error!", "Error", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});
		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(titleLbl)
						.addGroup(layout.createSequentialGroup()
							.addComponent(forLbl)
							.addComponent(dentistRBtn)
							.addComponent(hygienistRBtn))
						.addGroup(layout.createSequentialGroup()
							.addComponent(dateLbl)
							.addComponent(dateCbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup()
							.addComponent(timeLbl)
							.addComponent(wholeDayRBtn)
							.addComponent(timePeriodRBtn)
							.addComponent(startTimeLbl)
							.addComponent(startTimeCbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(endTimeLbl)
							.addComponent(endTimeCbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(bookBtn)
						.addComponent(btnCancelAnAbsence))
					.addContainerGap(100, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(titleLbl)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(forLbl)
						.addComponent(dentistRBtn)
						.addComponent(hygienistRBtn))
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(dateLbl)
						.addComponent(dateCbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(timeLbl)
						.addComponent(wholeDayRBtn)
						.addComponent(timePeriodRBtn)
						.addComponent(startTimeLbl)
						.addComponent(startTimeCbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(endTimeLbl)
						.addComponent(endTimeCbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addComponent(bookBtn)
					.addPreferredGap(ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
					.addComponent(btnCancelAnAbsence)
					.addContainerGap())
		);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		this.setLayout(layout);
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
			boolean validEndTime = validateEndTime();
			if (validEndTime) {
				System.out.println("Valid End Time: "+ validEndTime);
				// Confirm with user booking of absence
				int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to book this leave of absence?", "Confirm Absence", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				System.out.println(n);
				if (n == 0) {
					System.out.println("Yes");
					// Blank patient's ID by default is 1
					int patientID = 1;
					// Change date selected into database format
					SimpleDateFormat dateFormat = new SimpleDateFormat("E dd-MM-yyyy");
					Date date = null;
					try {
						date = dateFormat.parse(dateCbox.getSelectedItem().toString());
					} catch (ParseException e2) {
						e2.printStackTrace();
					} 
					SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
