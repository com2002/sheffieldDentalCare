package sheffieldDentalCare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class WeekViewAppointments extends ViewAppointments {
	private String calendarFor;
	private JLabel weeksLbl = new JLabel("Select Week Commencing");
	public JComboBox<String> weeksCbox = new JComboBox<String>();
	
	public WeekViewAppointments(String cf) {
		calendarFor = cf;
		makeSelectionPanel();
		makeTbl();
	}
	
	public void makeSelectionPanel() {
		JPanel panel = new JPanel();
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
				weeksCbox.addItem("Week " + i + ": " + sdf.format(cal.getTime()) + " (Current)");
				weeksCbox.setSelectedIndex(i-1);
			} else {
				weeksCbox.addItem("Week " + i + ": " + sdf.format(cal.getTime()));
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
						.addComponent(weeksLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(weeksCbox)
					)
				)
				)
		);
				
		// Position components in the vertical
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(weeksLbl)
				    .addComponent(weeksCbox)
				    		
				)
		);
		setSelectionPanel(panel);
	}

	public void makeTbl() {
		int weekNo = getWeekNo();
		// Set column names as dates starting from week selected
		SimpleDateFormat dateFormat = new SimpleDateFormat("E dd-MM");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, weekNo);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String[] cols = new String[6];
		cols[0] = "";
		for (int i = 1; i < 6; i++) {
			cols[i] = dateFormat.format(cal.getTime());
			System.out.println(dateFormat.format(cal.getTime()));
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
		String[][] data = new String[9][6];
		SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm");
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 6; j++) {
				if (j == 0) {
					data[i][j] = timeFormat.format(cal2.getTime());
					cal2.add(Calendar.HOUR, 1);
				}
			}
		}
		// Add data and column names to a table model
		setTblModel(new DefaultTableModel(data, cols));
	}
	
	private int getWeekNo() {
		// Get week number from drop down list
		String week = weeksCbox.getSelectedItem().toString();
		String weekNo = week.substring(5, 7);
		if (weekNo.contains(":")) {
			weekNo = weekNo.replace(":", "");
		}
		return Integer.parseInt(weekNo);
	}
}
