package sheffieldDentalCare;

import java.sql.SQLException;

public class DBBuilder {
	public void dropTables() throws SQLException{
		DBController dbc = new DBController();
		int rowsUpdated = 0;
		
		String[] qs = new String[7];
				
		qs[0] = "DROP TABLE TreatmentCredits;";
		qs[1] = "drop table HealthcarePlan;";
		qs[2] = "drop table TreatmentsPerformed;";
		qs[3] = "drop table Appointments;";
		qs[4] = "drop table Patients;";
		qs[5] = "drop table Address;";
		qs[6] = "drop table Treatments;";
		
		for (int i=0;i<7;i++) {
			try {
				rowsUpdated = dbc.update(qs[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Rows updated: "+rowsUpdated);
		}
	}
	
	public void rebuild() throws SQLException{
		DBController dbc = new DBController();
		
		String q1 = "CREATE TABLE Address(" +
				"addressID INT NOT NULL AUTO_INCREMENT,"+
				"houseNumber INT,"+
				"streetName VARCHAR(255),"+
				"district VARCHAR(255),"+
				"city VARCHAR(255),"+
				"postCode VARCHAR(255),"+				
				"PRIMARY KEY (addressID)"+
				");";
		
		String q2 = "CREATE TABLE Patients("+
				  "patientID INT NOT NULL AUTO_INCREMENT,"+
				  "title VARCHAR(255) NOT NULL,"+
				  "firstName VARCHAR(255) NOT NULL,"+
				  "surName VARCHAR(255) NOT NULL,"+
				  "dateOB DATE,"+
				  "phoneNumber VARCHAR(255),"+
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
		
		String q7 = "CREATE TABLE Treatments("
				+ "treatmentName VARCHAR(255),"
				+ "cost INT,"
				+ "repair BOOLEAN,"
				+ "PRIMARY KEY (treatmentName)"
				+ ");";
		
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
		
		try {
			rowsUpdated = dbc.update(q7);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Rows updated: "+rowsUpdated);
		
		dbc.closeConnection();
	}
	//test
	public static void main(String args[]) throws SQLException {
		DBBuilder dbb = new DBBuilder();
		dbb.dropTables();
		dbb.rebuild();
	}

}