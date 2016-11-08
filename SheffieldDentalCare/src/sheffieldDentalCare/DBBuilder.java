package sheffieldDentalCare;

import java.sql.SQLException;

public class DBBuilder {
	public void rebuild() throws SQLException{
		DBController dbc = new DBController();
		
		String q1 = "CREATE TABLE Address(" +
				"addressID INT NOT NULL AUTO_INCREMENT,"+
				"houseNumber INT,"+
				"streetName VARCHAR(255),"+
				"postCode VARCHAR(255),"+
				"county VARCHAR(255),"+
				"PRIMARY KEY (addressID)"+
				");";
		
		String q2 = "CREATE TABLE Patients("+
				  "patientID INT NOT NULL AUTO_INCREMENT,"+
				  "firstName VARCHAR(255) NOT NULL,"+
				  "surName VARCHAR(255) NOT NULL,"+
				  "dateOB DATE,"+
				  "addressID INT,"+
				  "PRIMARY KEY (patientID),"+
				  "FOREIGN KEY (addressID) REFERENCES Address(addressID)"+
				  ");";
		
		String q3 = "CREATE TABLE HealthcarePlan("+
				  "planName VARCHAR(255),"+
				  "initialCheckUps INT,"+
				  "initialHygVisits INT,"+
				  "initialRepairs INT,"+
				  "monthlyCost INT,"+
				  "PRIMARY KEY (planName)"+
				");";
		
		String q4 = "CREATE TABLE TreatmentCredits("+
				  "patientID INT,"+
				  "cuCount INT,"+
				  "hygCount INT,"+
				  "repCount INT,"+
				  "planName VARCHAR(255),"+
				  "PRIMARY KEY(patientID),"+
				  "FOREIGN KEY(patientID) REFERENCES Patients(patientID),"+
				  "FOREIGN KEY(planName) REFERENCES HealthcarePlan(planName)"+
				");";
		
		String q5 = "CREATE TABLE Appointments("+
				  "appointmentID INT NOT NULL AUTO_INCREMENT,"+
				  "patientID INT,"+
				  "pHygenist BOOLEAN,"+
				  "date DATE,"+
				  "startTime INT,"+
				  "endTime INT,"+
				  "PRIMARY KEY (appointmentID),"+
				  "FOREIGN KEY (patientID) REFERENCES Patients(patientID)"+
				");";
		
		String q6 = "CREATE TABLE TreatmentsPerformed("+
				  "appointmentID INT,"+
				  "hygCount INT,"+
				  "cuCount INT,"+
				  "repCount INT,"+
				  "paid BOOLEAN,"+
				  "PRIMARY KEY (appointmentID),"+
				  "FOREIGN KEY (appointmentID) REFERENCES Appointments(appointmentID)"+
				");";
		
		int rowsUpdated = 0;
		
		try {
			rowsUpdated = dbc.update(q1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Rows updated: "+rowsUpdated);
		
		try {
			rowsUpdated = dbc.update(q2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Rows updated: "+rowsUpdated);
		
		try {
			rowsUpdated = dbc.update(q3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Rows updated: "+rowsUpdated);
		
		try {
			rowsUpdated = dbc.update(q4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Rows updated: "+rowsUpdated);
		
		try {
			rowsUpdated = dbc.update(q5);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Rows updated: "+rowsUpdated);
		
		try {
			rowsUpdated = dbc.update(q6);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Rows updated: "+rowsUpdated);
		
		dbc.closeConnection();
	}
	//test
/*	public static void main(String args[]) throws SQLException {
		DBBuilder dbb = new DBBuilder();
		dbb.rebuild();
	}
*/
}
