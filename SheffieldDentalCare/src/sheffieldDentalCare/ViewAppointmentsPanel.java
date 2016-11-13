package sheffieldDentalCare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ViewAppointmentsPanel extends JPanel implements Panel {
	private String userType;
	private Boolean pHygienist;
	private String viewType;
	private JLabel titleLbl = new JLabel();
	private JLabel weeksLbl = new JLabel("Select Week Commencing");
	public JComboBox<String> weeksCbox = new JComboBox<String>();
	private JScrollPane weekTimetable;
	
	public ViewAppointmentsPanel(String ut, Boolean ph, String vt) {
		userType = ut;
		pHygienist = ph;
		viewType = vt;
		initComponents();
		addComponents();
	}
	
	@Override
	public void initComponents() {
		if (pHygienist) {
			titleLbl.setText("View Hygienist Appointments");
		} else {
			titleLbl.setText("View Dentist Appointments");
		}
		// Populate drop down list with start date of each week in the current year
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		for (int i = 1; i <= 52; i++) {	
			cal.set(Calendar.WEEK_OF_YEAR, i);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			weeksCbox.addItem("Week " + i + ": " + sdf.format(cal.getTime()));
			// Set default selected item of drop down list
			Calendar cal2 = Calendar.getInstance();
			// If current week number is equivalent to added week number then set as default
			if (cal2.get(Calendar.WEEK_OF_YEAR) == cal.get(Calendar.WEEK_OF_YEAR)) {
				weeksCbox.setSelectedIndex(i-1);
			}
		}
		weekTimetable = new JScrollPane(createWeekTimetable());
	}

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
						.addComponent(weeksLbl)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(weeksCbox)
					)
				)
				.addComponent(weekTimetable)
				)
		);
				
		// Position components in the vertical
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(titleLbl)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(weeksLbl)
				    .addComponent(weeksCbox)
				    		
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(weekTimetable)
				)
		);
	}
	
	
	private int getWeekNo() {
		String week = weeksCbox.getSelectedItem().toString();
		String weekNo = week.substring(5, 7);
		//System.out.println(weekNo.contains(":"));
		if (weekNo.contains(":")) {
			weekNo = weekNo.replace(":", "");
		}
		//System.out.println(weekNo + "=");
		return Integer.parseInt(weekNo);
	}
	
	public JTable createWeekTimetable() {
		int weekNo = getWeekNo();
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
		
		String strTime = "09:00";
		Date startTime = null;
		try {
			startTime = new SimpleDateFormat("H:mm").parse(strTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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

	    DefaultTableModel tblModel = new DefaultTableModel(data, cols);
	    JTable weekTimetable = new JTable (tblModel);
		return weekTimetable;
	}

	private class WeeksCboxHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
