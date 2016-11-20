package sheffieldDentalCare;

import java.awt.EventQueue;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class CancelAppointmentFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	int patientID;
	private JButton btnCancelApp;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CancelAppointmentFrame frame = new CancelAppointmentFrame(2);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public CancelAppointmentFrame(int patientID) throws SQLException {
		this.patientID = patientID;
		setTitle("Cancel Appointment by Patient. Current Patient's ID is: "+patientID);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane futureAppsScrPane = new JScrollPane();
		futureAppsScrPane.setBorder(BorderFactory.createTitledBorder("Patient's Future Appointment"));
		setVisible(true);
		btnCancelApp = new JButton("<html>Cancel Selected <br>Appointment</html>");
		btnCancelApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				///////////////////////////////////////
				if (table.getSelectedRow() < 0) {
					System.out.println("no row selected");
				}
				else {
					int appIDCancel = (int)table.getValueAt(table.getSelectedRow(), 0);
					System.out.println(appIDCancel);
					int p = JOptionPane.showConfirmDialog(null, "<html>Are you sure you wish to cancel the selected appointment?</html>", "Confirmation", JOptionPane.YES_NO_OPTION);
					if (p==0) {
						Connection con = null;
						Statement stmt = null;
						try {
						con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
								DBController.DB_User, DBController.DB_Password);
						stmt = con.createStatement();
						
						stmt.executeUpdate("DELETE FROM Appointment WHERE appointmentID = "+appIDCancel+";");
						
						JOptionPane.showMessageDialog(null, "<html>The selected appointment was successfully cancelled.</html>", 
								"Appointment Cancelled", JOptionPane.INFORMATION_MESSAGE);
						drawTable();
						}
						catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, "Server error!", "Error", JOptionPane.ERROR_MESSAGE);
							ex.printStackTrace();
						}
						finally {
							try {
							if (stmt != null) stmt.close();
							if (con != null) con.close();
							}
							catch (SQLException ex) {
								ex.printStackTrace();
							}
						}
					}
				}
				//////////////////////////////////////////
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(futureAppsScrPane, GroupLayout.PREFERRED_SIZE, 445, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCancelApp, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCancelApp, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
						.addComponent(futureAppsScrPane, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		futureAppsScrPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
		
		drawTable();
	}
	
	private void drawTable() throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
		con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
				DBController.DB_User, DBController.DB_Password);
		stmt = con.createStatement();
		/////////////////////////////
		ResultSet rs = stmt.executeQuery("SELECT appointmentID as AppointmentID, "
				+ "(CASE WHEN pHygienist = 1 THEN 'Hygienist' ELSE 'Dentist' END) as Partner, "
				+ "date as Date, "
				+ "startTime as StartTime, "
				+ "endTime as EndTime FROM Appointment "
				+ "WHERE patientID = "+patientID
				+ " AND date > CURDATE() OR "
				+ "(date = CURDATE() AND startTime > CURTIME())"
				+ ";");
		
		table.setModel(MyDbConverter.resultSetToMyTableModel(rs));
		/////////////////////////////
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
	}
}
