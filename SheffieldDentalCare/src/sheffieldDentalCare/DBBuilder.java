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
				  "checkupCount INT,"+
				  "hygieneCount INT,"+
				  "repairCount INT,"+
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
				+ "pCosmetic BOOLEAN,"
				+ "PRIMARY KEY (treatmentName)"
				+ ");";
		
		String q7 = "CREATE TABLE TreatmentsPerformed("+
				  "appointmentID INT,"+
				  "treatmentName VARCHAR(255),"+
				  "paid BOOLEAN,"+
				  "PRIMARY KEY (appointmentID, treatmentName),"+
				  "FOREIGN KEY (appointmentID) REFERENCES Appointments(appointmentID),"+
				  "FOREIGN KEY (treatmentName) REFERENCES Treatments(treatmentName)"+
				");";
		
		// Dummy data
		// Address
		String q8 = "INSERT INTO Address(houseNumber, streetName, district, city, postCode)"
				+ "VALUES(123, 'Sesame Street', 'District', 'London', 'NW1 8AS'),"
				+ "(120, 'Maple Street', 'District', 'London', 'NW4 2QD');";
		
		// Patients
		String q9 = "INSERT INTO Patients(title, firstName, surName, dateOb, phoneNumber, addressID)"
				+ "VALUES('Mr', 'Chandler', 'Bing', '1969-08-19', '07923415233', 1), "
				+ "('Mrs', 'Monica', 'Bing', '1964-06-15', '07784563187', 1), "
				+ "('Mrs', 'Phoebe', 'Buffay-Hannigan', '1964-03-01', '07145632587', 2) ;";
		
		// Healthcare plans
		String q10 = "INSERT INTO HealthcarePlan Values('nhsfPlan', 2, 2, 6, 0),('maintPlan', 2, 2, 0, 15),('ohPlan', 2, 4, 0, 21),('drPlan', 2, 4, 2, 36);";
		// Treatments
		String q11 = "INSERT INTO Treatments Values('hygVisit', 45, 0), ('amalF', 90, 0), ('resinF', 150, 0), ('crown', 500, 1);";
		
		// Appointments
		String q12 = "INSERT INTO Appointments(patientID, pHygienist,date, startTime, endTime)"
				+ "VALUES(1, false, '2016-11-8','15:00', '16:00'),"
				+ "(1, false, '2016-11-15','10:00', '11:00'),"
				+ "(1, true, '2016-11-10','10:20', '10:40'),"
				+ "(2, true, '2016-11-15','9:00', '9:20'),"
				+ "(3, true, '2016-11-15','9:20', '9:40');";
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
