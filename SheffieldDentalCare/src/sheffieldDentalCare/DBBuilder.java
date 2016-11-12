package sheffieldDentalCare;

import java.sql.SQLException;

public class DBBuilder {
	public void dropTables() throws SQLException{
		DBController dbc = new DBController();
		int rowsUpdated = 0;
		
		String[] qs = new String[7];
			
		qs[0] = "DROP TABLE TreatmentsPerformed;";
		qs[1] = "DROP TABLE Treatments;";
		qs[2] = "DROP TABLE Appointments;";
		qs[3] = "DROP TABLE TreatmentCredits;";
		qs[4] = "DROP TABLE HealthcarePlan;";
		qs[5] = "DROP TABLE Patients;";
		qs[6] = "DROP TABLE Address;";
		
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
				  "pHygienist BOOLEAN,"+
				  "date DATE,"+
				  "startTime TIME,"+
				  "endTime TIME,"+
				  "PRIMARY KEY (appointmentID),"+
				  "FOREIGN KEY (patientID) REFERENCES Patients(patientID)"+
				");";
		
		String q6 = "CREATE TABLE Treatments("
				+ "treatmentName VARCHAR(255),"
				+ "cost INT,"
				+ "repair BOOLEAN,"
				+ "PRIMARY KEY (treatmentName)"
				+ ");";
		
		String q7 = "CREATE TABLE TreatmentsPerformed("+
				  "treatmentID INT NOT NULL AUTO_INCREMENT,"+
				  "appointmentID INT,"+
				  "treatmentName VARCHAR(255),"+
				  "paid BOOLEAN,"+
				  "PRIMARY KEY (treatmentID),"+
				  "FOREIGN KEY (appointmentID) REFERENCES Appointments(appointmentID),"+
				  "FOREIGN KEY (treatmentName) REFERENCES Treatments(treatmentName)"+
				");";
		
		// Dummy data
		String q8 = "INSERT INTO Address(houseNumber, streetName, district, city, postCode)"
				+ "VALUES(123, 'Sesame Street', 'District', 'London', 'NW1 8AS');";
		
		String q9 = "INSERT INTO Patients(title, firstName, surName, dateOb, phoneNumber, addressID)"
				+ "VALUES('Mr', 'Chandler', 'Bing', '1969-08-19', '07923415233', 1);";
		
		String q10 = "INSERT INTO Patients(title, firstName, surName, dateOb, phoneNumber, addressID)"
				+ "VALUES('Mrs', 'Monica', 'Bing', '1964-06-15', '07784563187', 1);";
		
		//healthcareplans
		String q11 = "insert into HealthcarePlan Values('nhsfPlan', 2, 2, 6, 0),('maintPlan', 2, 2, 0, 15),('ohPlan', 2, 4, 0, 21),('drPlan', 2, 4, 2, 36);";
		//Treatments
		String q12 = "insert into Treatments Values('hygVisit', 45, 0), ('amalF', 90, 1), ('resinF', 150, 1), ('crown', 500, 1);";
			  
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
		
		try {
			rowsUpdated = dbc.update(q8);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Rows updated: "+rowsUpdated);
		
		try {
			rowsUpdated = dbc.update(q9);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Rows updated: "+rowsUpdated);
		
		try {
			rowsUpdated = dbc.update(q10);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Rows updated: "+rowsUpdated);
		
		try {
			rowsUpdated = dbc.update(q11);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Rows updated: "+rowsUpdated);
		
		try {
			rowsUpdated = dbc.update(q12);
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
