package sheffieldDentalCare;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

/**
 * PatientPlan.java
 * Class which complements PatientDetailsPanel, allowing the patient's healthcare plan to be changed.
 * Used in PatientDetailsPanel.
 * @author Lewis
 * 
 */

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
	        			case "NHS Free Plan":
	        				try {
	        					subToPlan("nhsfPlan");
	        				} catch (SQLException ex) {} 
	        				break;
	        			case "Maintenance Plan":
	        				try {
	        					subToPlan("maintPlan");
	        				} catch (SQLException ex) {} 
	         				break;
	        			case "Oral Health Plan":
	        				try {
	        					subToPlan("ohPlan");
	        				} catch (SQLException ex) {}
	        				break;
	        			case "Dental Repair Plan":
	        				try {
	        					subToPlan("drPlan");
	        				} catch (SQLException ex) {} 
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
						unsubFromPlan();
    				} catch (SQLException ex) {}
				}
			});
			pdPanel.add(unsubscribeBtn);
		}
		
		// display number of treatment credits remaining
		pdPanel.add(new JLabel("Check-up Credits Remaining:  " + checkupCount, JLabel.LEFT));
		pdPanel.add(new JLabel("Hygiene Credits Remaining:     " + hygieneCount, JLabel.LEFT));
		pdPanel.add(new JLabel("Repair Credits Remaining:        " + repairCount, JLabel.LEFT));
	}
	
	private void subToPlan(String plan) throws SQLException {
		int initCheckupCount = 0;
		int initHygieneCount = 0;
		int initRepairCount = 0;
		
		Connection con = null;
		Statement stmt = null;
		
		// get initial credits
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT initialCheckUps, initialHygVisits, initialRepairs "
					+ "FROM HealthcarePlan WHERE planName = '" + plan + "';");
			while(res.next()) {
				initCheckupCount = res.getInt(1);
				initHygieneCount = res.getInt(2);
				initRepairCount = res.getInt(3);
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		
		// update the TreatmentCredits table in database with new plan for patient
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO TreatmentCredits "
					+ "VALUES("+ patientID +", " + initCheckupCount + ", "
							+ initHygieneCount + ", " + initRepairCount + ", '" + plan + "');");
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		pdPanel.updateDetailsDisplayed();
		System.out.println("Plan " + plan + " subscribed for Patient " + patientID);
	}
	
	private void unsubFromPlan() throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DBController.DB_Server + "/" + DBController.DB_Name, 
					DBController.DB_User, DBController.DB_Password);
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM TreatmentCredits WHERE patientID = " + patientID + ";");
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (stmt != null) stmt.close();
			if (con != null) con.close();
		}
		pdPanel.updateDetailsDisplayed();
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
	
}
