package sheffieldDentalCare;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

public class PayTreatmentsFrame extends JFrame {
	private JTable appsTable;
	public int patientID;
	private JTable tmentTable;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PayTreatmentsFrame frame = new PayTreatmentsFrame(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PayTreatmentsFrame(int patientID) {
		this.patientID = patientID;
		
		setAlwaysOnTop(true);
		setTitle("View/Pay Appointments and Treatments by Patient");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1010, 602);
		
		JButton btnLoadData = new JButton("<html>Load Patient's <br>Appointments</html>");
		btnLoadData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Connection con = null;
				Statement stmt = null;
				try {
				con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
						DBController.DB_User, DBController.DB_Password);
				stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT appointmentID as AppointmentID, date as Date, SUM(cost) as TotalCost, SUM(CASE WHEN paid = 0 THEN cost ELSE 0 END) as BalanceOutstanding "
						+ "FROM (SELECT appointmentID, date, cost, paid FROM Appointments NATURAL JOIN (SELECT * "
						+ "FROM TreatmentsPerformed NATURAL JOIN Treatments) AS srt "
						+ "WHERE Appointments.appointmentID = srt.appointmentID AND patientID = "+ patientID +") AS srr GROUP BY appointmentID;");
				
				appsTable.setModel(MyDbConverter.resultSetToMyTableModel(rs));
				
				if (stmt != null) stmt.close();
				if (con != null) con.close();
				}
				catch (SQLException ex){
					ex.printStackTrace();
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JLabel lblPatientid = new JLabel("PatientID: " + patientID);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnLoadData, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPatientid, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)))
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(133, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPatientid, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(52)
					.addComponent(btnLoadData, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(446, Short.MAX_VALUE))
		);
		
		tmentTable = new JTable();
		tmentTable.setFillsViewportHeight(true);
		tmentTable.setColumnSelectionAllowed(true);
		tmentTable.setCellSelectionEnabled(true);
		scrollPane_1.setColumnHeaderView(tmentTable);
		
		appsTable = new JTable();
		scrollPane.setViewportView(appsTable);
		
		appsTable.setFillsViewportHeight(true);
		
		getContentPane().setLayout(groupLayout);
	}
}
