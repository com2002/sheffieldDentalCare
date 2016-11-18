package sheffieldDentalCare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;

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
	
	public BookAbsencePanel() {
		initComponents();
		addComponents();
	}
	
	private void initComponents() {
		// Set dentist radio button by default as selected
		dentistRBtn.setSelected(true);
		// Create button group for for radio buttons
		ButtonGroup forBtnGroup = new ButtonGroup();
		forBtnGroup.add(dentistRBtn);
		forBtnGroup.add(hygienistRBtn);
		// Create button group for time radio buttons
		wholeDayRBtn.setSelected(true);
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
		for (int i = 0; i < 27; i++) {
			startTimeCbox.addItem(timeFormat.format(cal.getTime()));
			endTimeCbox.addItem(timeFormat.format(cal.getTime()));
			cal.add(Calendar.MINUTE, 20);
		}
	}
	
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
	}
}
