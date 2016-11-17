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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

public class PayTreatmentsFrame extends JFrame {
	private JTable appsTable;
	public int patientID;
	int appID;
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
			
		setTitle("View/Pay Appointments and Treatments by Patient");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1010, 602);
		
		JButton btnLoadData = new JButton("<html>Load Patient's <br>Appointments</html>");
		btnLoadData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawAppsTable();			
			}

			
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JLabel lblPatientid = new JLabel("PatientID: " + patientID);
		
		JLabel lblAppointmentid = new JLabel("AppointmentID: ");
		
		JButton btnLoadSelApp = new JButton("<html>Load Selected Appointment</>");
		btnLoadSelApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (appsTable.getSelectedRow() < 0) {
					System.out.println("no row selected");
				} 
				else {
					appID = (int)appsTable.getValueAt(appsTable.getSelectedRow(), 0);
					lblAppointmentid.setText("AppointmentID: "+Integer.toString(appID));
					System.out.println(appID);
					drawTreatmentsTable();
				}
			}
		});
		
		JButton btnPayApp = new JButton("<html>Pay Outstanding Balance<br>of selected Appointment</html>");
		btnPayApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (appsTable.getSelectedRow() < 0) {
					System.out.println("no row selected");
				} 
				else {
					appID = (int)appsTable.getValueAt(appsTable.getSelectedRow(), 0);
					if (Integer.valueOf(appsTable.getValueAt(appsTable.getSelectedRow(), 3).toString()) == 0) {
								JOptionPane.showMessageDialog(null, "<html>The treatments performed in the selected <br>appointment have already been paid.</html>", "Appointment Paid", JOptionPane.INFORMATION_MESSAGE);
					} else {
						int p = JOptionPane.showConfirmDialog(null, "<html>Are you sure you wish to set all outstanding <br>treatments in the selected appointment as paid?</html>", "Confirmation", JOptionPane.YES_NO_OPTION);
						if (p==0) {
							Checkout checkout = new Checkout();
							try {						
								checkout.payAppointment(appID);
								drawAppsTable();
								drawTreatmentsTable();
								JOptionPane.showMessageDialog(null, "<html>The treatments performed in the "
										+ "selected <br>appointment have now been paid.</html>", 
										"Appointment Paid", JOptionPane.INFORMATION_MESSAGE);
							}
						 	catch (SQLException e1) {
						 		JOptionPane.showMessageDialog(null, "Server error!", "Error", JOptionPane.ERROR_MESSAGE);
						 		e1.printStackTrace();
						 	}
						}
						else {
							System.out.println("No selected");
						}
					}
				}
			}
		});
		
		JButton btnPaySelectedTreatment = new JButton("Pay selected Treatment");
		btnPaySelectedTreatment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO
				//Get treatment name and appointmentID
				//Change paid to true for this treatment performed.
				if (tmentTable.getSelectedRow() < 0) {
					System.out.println("no row selected");
				} 
				else {
					boolean tmentPaid = (boolean)tmentTable.getValueAt(tmentTable.getSelectedRow(), 3);
					if (tmentPaid) {
						JOptionPane.showMessageDialog(null, "<html>This treatment has already been paid.</html>", "Treatment Paid", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						String tmentName = (String)tmentTable.getValueAt(tmentTable.getSelectedRow(), 0);					
						System.out.println(tmentName);
						int p = JOptionPane.showConfirmDialog(null, "<html>Are you sure you wish to set this treatment as paid?</html>", "Confirmation", JOptionPane.YES_NO_OPTION);
						if (p==0) {
							Checkout checkout = new Checkout();
							try {						
								checkout.payTreatment(appID,tmentName);
								drawTreatmentsTable();
								drawAppsTable();
								JOptionPane.showMessageDialog(null, "<html>The treatment selected has now been paid.</html>", 
										"Appointment Paid", JOptionPane.INFORMATION_MESSAGE);
							}
							catch (SQLException e1) {
								JOptionPane.showMessageDialog(null, "Server error!", "Error", JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
							}
						}
						else {
							System.out.println("No selected");
						}
					}
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnLoadData, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
								.addComponent(lblPatientid, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnPayApp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnPaySelectedTreatment)
								.addComponent(btnLoadSelApp)
								.addComponent(lblAppointmentid, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblPatientid, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addGap(83)
							.addComponent(btnPayApp)))
					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblAppointmentid, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(btnLoadSelApp)
							.addGap(10)
							.addComponent(btnPaySelectedTreatment))))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(52)
					.addComponent(btnLoadData, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(446, Short.MAX_VALUE))
		);
		
		tmentTable = new JTable();
		tmentTable.getTableHeader().setReorderingAllowed(false);
		tmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(tmentTable);
		tmentTable.setFillsViewportHeight(true);
		
		appsTable = new JTable();
		appsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		appsTable.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(appsTable);
		
		appsTable.setFillsViewportHeight(true);
		
		getContentPane().setLayout(groupLayout);
	}
	
	private void drawAppsTable() {
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
	
	private void drawTreatmentsTable() {
		Connection con = null;
		Statement stmt = null;
		try {
		con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
				DBController.DB_User, DBController.DB_Password);
		stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT treatmentName as TreatmentName, cost as Cost, paidByPlan as PaidByHealthcarePlan, paid as PaidFor FROM "
				+ "(SELECT * from TreatmentsPerformed Natural JOIN Treatments WHERE appointmentID = "+appID+") as als;");
		
		tmentTable.setModel(MyDbConverter.resultSetToMyTableModel(rs));
		if (stmt != null) stmt.close();
		if (con != null) con.close();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
}
