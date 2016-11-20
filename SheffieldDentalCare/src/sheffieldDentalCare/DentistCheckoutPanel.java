package sheffieldDentalCare;

import java.awt.event.*;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import javax.swing.*;

/**
 * DentistCheckoutPanel.java
 * To check out patient and add treatments to an appointment
 * @author ting
 *
 */
@SuppressWarnings("serial")
public class DentistCheckoutPanel extends JPanel {
	private final String £ = "\u00A3";
	private JLabel titleLbl = new JLabel("Checkout Patient");
	private JLabel appointmentLbl = new JLabel("Select Appointment");
	private JComboBox<String> appointmentCbox = new JComboBox<String>();
	private JLabel treatmentsLbl = new JLabel("Treatments");
	private JCheckBox checkUpChBox = new JCheckBox("Check-Up ("+ £ + "45)");
	private JCheckBox amalgamChBox = new JCheckBox("Silver Amalgam Filling ("+ £ +"90)");
	private JCheckBox resinChBox = new JCheckBox("White Composite Resin Filling ("+ £ +"150)");
	private JCheckBox crownChBox = new JCheckBox("Gold Crown Fitting ("+ £ +"500)");
	private JButton checkoutBtn = new JButton("Checkout");
	private ArrayList<String> patients = new ArrayList<String>();
	
	/**
	 * Class constructor
	 */
	public DentistCheckoutPanel() {
		setPatients();
		initComponents();
		addComponents();
	}
	
	/**
	 * Populates appointments drop down list
	 */
	private void initComponents() {
		// Populate appointments drop down list
		// Get appointments for current day
		DPCalendar dpCal = new DPCalendar();
		AppointmentPlot[] appPlot = null;
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			// If Saturday then skip to Monday by adding two days
			if (dayOfWeek == Calendar.SATURDAY) {
				cal.add(Calendar.DATE, 2);
				// Else if Sunday then skip to Monday by adding one day
			} else if (dayOfWeek == Calendar.SUNDAY){
				cal.add(Calendar.DATE, 1);
			}
			String date = dbDateFormat.format(cal.getTime());
			appPlot = dpCal.getAppointmentForDate(false, date);
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < appPlot.length; i++) {
			String patient = null;
			// Find patient's details
			for (int j = 0; j < patients.size(); j++) {
				if (appPlot[i].PATIENTID == getPatientID(patients.get(j))) {
					patient = patients.get(j);
				}
			}
			// Add appointment as item to drop down list
			appointmentCbox.addItem("(" + appPlot[i].APPOINTMENTID + ") " + appPlot[i].STARTTIME + " - " + appPlot[i].ENDTIME + ": " + patient);
			System.out.println("(" + appPlot[i].APPOINTMENTID + ") " + appPlot[i].STARTTIME + " - " + appPlot[i].ENDTIME + ": " + patient);
		}
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
						.addComponent(appointmentLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(appointmentCbox)
					)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(treatmentsLbl)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(checkUpChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(amalgamChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(resinChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(crownChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(checkoutBtn)
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
					.addComponent(appointmentLbl)
					.addComponent(appointmentCbox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(treatmentsLbl)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(checkUpChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(amalgamChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(resinChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(crownChBox)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(checkoutBtn)
				)
		);
		checkoutBtn.addActionListener(new CheckoutBtnHandler());
	}
	
	/**
	 * Event handler for checkout button
	 */
	private class CheckoutBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Checkout button clicked");
			int appointmentID = getAppointmentID();
			boolean[] treatmentSelected = {checkUpChBox.isSelected(), amalgamChBox.isSelected(),
										   resinChBox.isSelected(), crownChBox.isSelected()};
			String[] treatmentName = {"checkup", "amalF", "resinF", "crown"};
			// Confirm that user wants to checkout patient
			int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to checkout this patient?", "Confirm Absence", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (n == 0) {
				System.out.println("Yes");
				Checkout checkout = new Checkout();
				for (int i = 0; i < treatmentName.length; i++) {
					if (treatmentSelected[i]) {
						try {
							int rowsAdded = checkout.addTreatmentToAppointment(appointmentID, treatmentName[i]);
							System.out.println("Rows added: " + rowsAdded);
						} catch (SQLException e1) {
							// Output error message
							JOptionPane.showMessageDialog(null, e1.toString(), "Error - Database", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						} finally {
							// Output confirmation for user
							JOptionPane.showMessageDialog(null, "Patient checked out.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			} else {
				System.out.println("No");
			}
		}
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
	 * Gets patient ID from selected patient drop down list
	 * @param p		Selected patient
	 * @return patientID
	 */
	private int getPatientID(String p) {
		String patient = p;
		int patientID = 0;
		int indexOfColon = patient.indexOf(":");
		patientID = Integer.parseInt(patient.substring(0, indexOfColon));
		return patientID;
	}
	
	/**
	 * Gets appointmentID from appointment drop down list
	 * @return appointmentID
	 */
	private int getAppointmentID() {
		String appointment = appointmentCbox.getSelectedItem().toString();
		int indexOfBracket = appointment.indexOf(")");
		String appointmentID = appointment.substring(1, indexOfBracket);
		System.out.println("Appointment ID: " + appointmentID);
		return Integer.parseInt(appointmentID);
	}
}
