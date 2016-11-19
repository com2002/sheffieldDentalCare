package sheffieldDentalCare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PatientPlan {
	private PatientDetailsPanel pdPanel;
	private int patientID;
	private int checkupCount;
	private int hygieneCount;
	private int repairCount;
	private String planName;
	
	public PatientPlan(PatientDetailsPanel pdPanel, int patientID) {
		this.pdPanel = pdPanel;
		this.patientID = patientID;
	}
	
	public void displayPlanDetails() {
		String planNameString;
		
		// NEED TO ADD OPTIONS FOR HEALTHCARE PLAN
		// IF NOT SUBSCRIBED TO A PLAN, DISPLAY A DROPDOWN MENU WITH THE POSSIBLE PLANS, WITH A 'SUBSCRIBE' BUTTON
		// IF ALREADY SUBSCRIBED TO A PLAN, DISPLAY AN 'UNSUBSCRIBE' BUTTON
		
		// get the patient's plan name, or 'none' if they don't have one 
		if (planName==null) {
			planNameString = "None";	
		}
		else {
			switch (planName) {
        		case "nhsfPlan":  planNameString = "NHS Free Plan";
        			break;
        		case "maintPlan":  planNameString = "Maintenance Plan";
         			break;
        		case "ohPlan":  planNameString = "Oral Health Plan";
        			break;
        		case "drPlan": planNameString = "Dental Repair Plan";
        			break;
        		default: planNameString = "None";
        			break;
			}
		}
		
		pdPanel.add(new JLabel("Healthcare Plan:     " + planNameString, JLabel.LEFT));
		JPanel planPanel = new JPanel();
		
		// if the patient is not on a plan, give the user the option to subscribe to a plan
		// if they are, give the user the option to unsubscribe from the plan
		if (planName==null) {
			JComboBox<String> plansCbox = new JComboBox<String>();
			plansCbox.addItem("NHS Free Plan");
			plansCbox.addItem("Maintenance Plan");
			plansCbox.addItem("Oral Health Plan");
			plansCbox.addItem("Dental Repair Plan");
			JButton subscribeBtn = new JButton("Subscribe to Plan");
			subscribeBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String planSelected = plansCbox.getSelectedItem().toString();
					switch (planSelected) {
	        			case "NHS Free Plan":  {
	        				try {
	        					subToPlan(patientID, "nhsfPlan");
	        				} catch (SQLException ex) {} 
	        				}
	        				break;
	        			case "Maintenance Plan":  {
	        				try {
	        					subToPlan(patientID, "maintPlan");
	        				} catch (SQLException ex) {} 
	        				}
	         				break;
	        			case "Oral Health Plan":  {
	        				try {
	        					subToPlan(patientID, "ohPlan");
	        				} catch (SQLException ex) {} 
	        				}
	        				break;
	        			case "Dental Repair Plan": {
	        				try {
	        					subToPlan(patientID, "drPlan");
	        				} catch (SQLException ex) {} 
	        				}
	        				break;
	        			default: System.out.println("No plan selected");
	        				break;
					}
				}
			});
			planPanel.add(plansCbox);
			planPanel.add(subscribeBtn);
			pdPanel.add(planPanel);
		}
		else {
			JButton unsubscribeBtn = new JButton("Unsubscribe from Plan");
			unsubscribeBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						unsubFromPlan(patientID);
    				} catch (SQLException ex) {}
				}
			});
			pdPanel.add(unsubscribeBtn);
		}
		
		// display number of treatment credits remaining
		pdPanel.add(new JLabel("Check-up Credits Remaining: " + checkupCount, JLabel.LEFT));
		pdPanel.add(new JLabel("Hygiene Credits Remaining:   " + hygieneCount, JLabel.LEFT));
		pdPanel.add(new JLabel("Repair Credits Remaining:     " + repairCount, JLabel.LEFT));
	}
	
	private void subToPlan(int patientID, String plan) throws SQLException {
		System.out.println("Plan " + plan + " subscribed for Patient " + patientID);
	}
	
	private void unsubFromPlan(int patientID) throws SQLException {
		System.out.println("Patient" + patientID + " unsubscribed from plan");
	}
	
	public void getPatientPlanDetails() throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT checkupCount, hygieneCount, repairCount, tc.planName "
					+ "FROM TreatmentCredits tc, HealthcarePlan hp "
					+ "WHERE tc.planName = hp.planName "
					+ "AND patientID = '" + patientID + "';");
			while(res.next()) {
				checkupCount = res.getInt(1);
				hygieneCount = res.getInt(2);
				repairCount = res.getInt(3);
				planName = res.getString(4);
				System.out.println("Check up count: " + checkupCount);
				System.out.println("hygiene count: " + hygieneCount);
				System.out.println("repair count" + repairCount);
				System.out.println("plan name: "+planName);
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
	}
	
	public int getCheckupCount() {return checkupCount;}
	
	public int getHygieneCount() {return hygieneCount;}
	
	public int getRepairCount() {return repairCount;}
	
	public String getPlanName() {return planName;}
	
}
