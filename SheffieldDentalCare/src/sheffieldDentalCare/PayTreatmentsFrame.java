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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;

import net.proteanit.sql.DbUtils;

public class PayTreatmentsFrame extends JFrame {
	private JTable table;
	public int patientID;
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
		
		JButton btnLoadData = new JButton("Load Data");
		btnLoadData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Connection con = null;
				Statement stmt = null;
				try {
				con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
						DBController.DB_User, DBController.DB_Password);
				stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT appointmentID, date, SUM(cost) as balanceOutstanding "
						+ "FROM (SELECT appointmentID, date, cost FROM Appointments NATURAL JOIN (SELECT * "
						+ "FROM TreatmentsPerformed NATURAL JOIN Treatments WHERE paid = 0) AS srt "
						+ "WHERE Appointments.appointmentID = srt.appointmentID AND patientID = "+ patientID +") AS srr GROUP BY appointmentID;");
				
				table.setModel(DbUtils.resultSetToTableModel(rs));
				//Test
				//rs.next();
				//System.out.println(rs.getInt(3));
				////Test end
				if (stmt != null) stmt.close();
				if (con != null) con.close();
				}
				catch (SQLException ex){
					ex.printStackTrace();
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLoadData, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(233, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnLoadData, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		
		getContentPane().setLayout(groupLayout);
	}
}
