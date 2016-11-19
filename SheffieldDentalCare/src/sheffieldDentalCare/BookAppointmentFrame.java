package sheffieldDentalCare;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * BookAppointmentFrame.java
 * Books an appointment for a specified patient
 * @author ting
 *
 */
@SuppressWarnings("serial")
public class BookAppointmentFrame extends JFrame {
	private int patientID = 0;
	private JLabel titleLbl = new JLabel("Book Appointment");
	private JLabel withLbl = new JLabel("With");
	private JRadioButton dentistRBtn = new JRadioButton("Dentist");
	private JComboBox<String> dentistCbox = new JComboBox<String>();
	private JRadioButton hygienistRBtn = new JRadioButton("Hygienist");
	private JLabel dateLbl = new JLabel("Date");
	private JComboBox<String> dateCbox = new JComboBox<String>();
	private JLabel startTimeLbl = new JLabel("Start Time");
	private JComboBox<String> startTimeCbox = new JComboBox<String>();
	private JButton bookBtn = new JButton("Book"); 
	
	/**
	 * Class constructor
	 * Set patientID, initialises frame by setting size and components
	 * @param p		Patient ID
	 */
	public BookAppointmentFrame(int p) {
		patientID = p;
		setTitle("Book Appointment");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenDimensions = toolkit.getScreenSize();
		setSize(screenDimensions.width/2, screenDimensions.height/2);
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		initComponents();
		addComponents();
	}
	
	/**
	 * Initialises components. Sets titleLbl, radio buttons and drop down list
	 */
	private void initComponents() {
		// Set dentist radio button by default as selected
		dentistRBtn.setSelected(true);
		dentistRBtn.setActionCommand("Dentist");
		hygienistRBtn.setActionCommand("Hygienist");
		// Create radio button group
		ButtonGroup rBtnGroup = new ButtonGroup();
		rBtnGroup.add(dentistRBtn);
		rBtnGroup.add(hygienistRBtn);
		// Add types of appointments for dentist drop down list
		dentistCbox.addItem("Check-Up (20 minutes)");
		dentistCbox.addItem("Treatment (1 hour)");
		// Set up date drop down list with dates Monday to Friday 4 weeks in advance
		SimpleDateFormat dateFormat = new SimpleDateFormat("E dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		// If Saturday then skip to Monday
		if (dayOfWeek == Calendar.SATURDAY) {
			cal.add(Calendar.DATE, 2);
			// Else add one day
		} else {
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
		setStartTimeCbox(24);
	}
	
	/**
	 * Lays out components in panel and add
	 */
	private void addComponents() {
		Container contentPane = getContentPane();
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		panel.setLayout(layout);
		contentPane.add(panel);
		
		// Add components to layout
		// Position components in the horizontal
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(titleLbl)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(withLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(dentistRBtn)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(dentistCbox)
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
						.addComponent(dateCbox)
					)
				)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(startTimeLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(startTimeCbox)
					)
					)
				.addComponent(bookBtn)
				)
		);
				
		// Position components in the vertical
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(titleLbl)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(withLbl)
					.addComponent(dentistRBtn)
					.addComponent(dentistCbox)
					.addComponent(hygienistRBtn)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(dateLbl)
					.addComponent(dateCbox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(startTimeLbl)
					.addComponent(startTimeCbox)
				)
				.addComponent(bookBtn)
		);
		// Add event listeners for radio buttons
		dentistRBtn.addActionListener(new RBtnHandler());
		hygienistRBtn.addActionListener(new RBtnHandler());
		// Add event listener for dentist drop down list
		dentistCbox.addActionListener(new DentistCboxHandler());
		// Add event listener for book button
		bookBtn.addActionListener(new BookBtnHandler());
	}
	
	/**
	 * Adds times to start time drop down list
	 */
	private void setStartTimeCbox(int j) {
		// Add times to start time drop down list
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
		for (int i = 0; i < j; i++) {
			startTimeCbox.addItem(timeFormat.format(cal2.getTime()));
			cal2.add(Calendar.MINUTE, 20);
		}
	}
	
	/**
	 * Event handler for radio buttons
	 */
	private class RBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Radio button changed");
			System.out.println(e.getActionCommand());
			if (e.getActionCommand() == "Hygienist") {
				dentistCbox.setEnabled(false);
				startTimeCbox.removeAllItems();
				setStartTimeCbox(24);
			} else {
				dentistCbox.setEnabled(true);
			}
		}
	}
	
	/**
	 * Event handler for dentist drop down list
	 */
	private class DentistCboxHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Dentist drop down list selected item changed");
			startTimeCbox.removeAllItems();
			if (dentistCbox.getSelectedItem() == "Treatment (1 hour)" && dentistRBtn.isSelected()) {
				setStartTimeCbox(22);
			} else {
				setStartTimeCbox(24);
			}
		}
	}
	
	// Event handler for radio buttons
	private class BookBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Book button clicked");
			// Confirm with user booking of appointment
			int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to book this appointment?", "Confirm Appointment", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			System.out.println(n);
			if (n == 0) {
				System.out.println("Yes");
				DPCalendar dpCal = new DPCalendar();
				// Change date selected into database format
				SimpleDateFormat dateFormat = new SimpleDateFormat("E dd-MM-yyyy");
				SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = dateFormat.parse(dateCbox.getSelectedItem().toString());
				} catch (ParseException e2) {
					e2.printStackTrace();
				} 
				String strDate = dbDateFormat.format(date);
				// Calculate end time
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
				Calendar cal = Calendar.getInstance();
				String strStartTime = startTimeCbox.getSelectedItem().toString();
				Date startTime = null;
				try {
					startTime = timeFormat.parse(strStartTime);
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				cal.setTime(startTime);
				boolean pHygienist = false;
				// Set end time according to user selection
				String endTime = null;
				if (dentistRBtn.isSelected()) {
					System.out.println(dentistCbox.getSelectedItem().toString());
					if (dentistCbox.getSelectedItem().toString() == "Check-Up (20 minutes)") {
						cal.add(Calendar.MINUTE, 20);
						endTime = timeFormat.format(cal.getTime());
					} else {
						cal.add(Calendar.HOUR, 1);
						endTime = timeFormat.format(cal.getTime());
					}
				} else {
					pHygienist = true;
					cal.add(Calendar.MINUTE, 20);
					endTime = timeFormat.format(cal.getTime());
				}
				// Check availability
				String availability = null;
				try {
					System.out.println(patientID);
					System.out.println(pHygienist);
					System.out.println(strDate);
					System.out.println(strStartTime);
					System.out.println(endTime);
					availability = dpCal.checkAvailability(patientID, pHygienist, strDate, strStartTime, endTime);
					System.out.println(availability);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (availability.equals("")) {
					System.out.println("Available");
					// Add appointment to database
					int addedRows = 0;
					try {
						addedRows = dpCal.addAppointment(patientID, pHygienist, strDate, strStartTime, endTime);
						System.out.println("Added Rows: " + addedRows);
					} catch (SQLException e1) {
						// Output error message
						JOptionPane.showMessageDialog(null, e1.toString(), "Error - Database", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} finally {
						// Output confirmation for user
						String confirmMsg = null;
						if (!pHygienist) {
							confirmMsg = "Appointment booked on " + strDate + " between " + strStartTime + " and " + endTime + " with the dentist.";
						} else {
							confirmMsg = "Appointment booked on " + strDate + " between " + strStartTime + " and " + endTime + " with the hygienist.";
						}
						JOptionPane.showMessageDialog(null, confirmMsg, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					System.out.println("Not Available");
					// Output error message if not available to book appointment
					JOptionPane.showMessageDialog(null, availability, "Error - Appointment Clash", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				System.out.println("No");
			}
		}
	}
}
