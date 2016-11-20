package sheffieldDentalCare;

import java.sql.SQLException;

public class DBBuilder {
	public void dropTables() throws SQLException{
		DBController dbc = new DBController();
		int rowsUpdated = 0;
		
		String[] qs = new String[7];
			
		qs[0] = "DROP TABLE TreatmentPerformed;";
		qs[1] = "DROP TABLE Treatment;";
		qs[2] = "DROP TABLE Appointment;";
		qs[3] = "DROP TABLE TreatmentCredits;";
		qs[4] = "DROP TABLE HealthcarePlan;";
		qs[5] = "DROP TABLE Patient;";
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
	
	@SuppressWarnings("static-access")
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
		
		String q2 = "CREATE TABLE Patient("+
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
				  "FOREIGN KEY(patientID) REFERENCES Patient(patientID),"+
				  "FOREIGN KEY(planName) REFERENCES HealthcarePlan(planName)"+
				");";
		
		String q5 = "CREATE TABLE Appointment("+
				  "appointmentID INT NOT NULL AUTO_INCREMENT,"+
				  "patientID INT,"+
				  "pHygienist BOOLEAN,"+
				  "date DATE,"+
				  "startTime TIME,"+
				  "endTime TIME,"+
				  "PRIMARY KEY (appointmentID),"+
				  "FOREIGN KEY (patientID) REFERENCES Patient(patientID)"+
				");";
		
		String q6 = "CREATE TABLE Treatment("
				+ "treatmentName VARCHAR(255),"
				+ "cost INT,"
				+ "pCosmetic BOOLEAN,"
				+ "PRIMARY KEY (treatmentName)"
				+ ");";
		
		String q7 = "CREATE TABLE TreatmentPerformed("+
				  "appointmentID INT,"+
				  "treatmentName VARCHAR(255),"+
				  "paid BOOLEAN,"+
				  "paidByPlan BOOLEAN,"+
				  "PRIMARY KEY (appointmentID, treatmentName),"+
				  "FOREIGN KEY (appointmentID) REFERENCES Appointment(appointmentID),"+
				  "FOREIGN KEY (treatmentName) REFERENCES Treatment(treatmentName)"+
				");";
		
		// Dummy data
		// Address
		String q8 = "INSERT INTO Address(houseNumber, streetName, district, city, postCode)"
				+ "VALUES(null, null, null, null, null),"
				+ "(123, 'Sesame Street', 'District', 'London', 'NW18AS'),"
				+ "(120, 'Maple Street', 'District', 'London', 'NW42QD');";
		
		// Create blank patient for booking absences
		String q9 = "INSERT INTO Patient(title, firstName, surName, addressID)"
				+ "VALUES('Blank', 'Blank', 'Blank', 1)";
		
		// Patient
		String q10 = "INSERT INTO Patient(title, firstName, surName, dateOb, phoneNumber, addressID)"
				+ "VALUES('Mr', 'Chandler', 'Bing', '1969-08-19', '07923415233', 2), "
				+ "('Mrs', 'Monica', 'Bing', '1964-06-15', '07784563187', 2), "
				+ "('Mrs', 'Phoebe', 'Buffay-Hannigan', '1964-03-01', '07145632587', 3) ;";
		
		// Healthcare plans
		String q11 = "INSERT INTO HealthcarePlan Values('nhsfPlan', 2, 2, 6, 0),('maintPlan', 2, 2, 0, 15),('ohPlan', 2, 4, 0, 21),('drPlan', 2, 2, 2, 36);";
		// Treatment
		String q12 = "INSERT INTO Treatment Values('hygVisit', 45, 0), ('amalF', 90, 0), ('resinF', 150, 0), ('crown', 500, 1), ('checkup', 45 ,0);";
		
		// Appointment
		String q13 = "INSERT INTO Appointment(patientID, pHygienist,date, startTime, endTime)"
				+ "VALUES(2, false, '2016-11-8','15:00', '16:00'),"
				+ "(2, false, '2016-11-14','14:00', '14:20'),"
				+ "(3, false, '2016-11-14','14:40', '15:00'),"
				+ "(2, false, '2016-11-15','10:00', '11:00'),"
				+ "(1, false, '2016-11-17','9:00', '17:00'),"
				+ "(1, false, '2016-11-18','16:00', '17:00'),"
				+ "(3, false, '2016-11-21','11:00', '12:00'),"
				+ "(4, false, '2016-11-25','14:00', '14:20'),"
				+ "(2, true, '2016-11-10','10:20', '10:40'),"
				+ "(3, true, '2016-11-15','9:00', '9:20'),"
				+ "(4, true, '2016-11-15','9:20', '9:40'),"
				+ "(1, true, '2016-11-17','9:00', '17:00'),"
				+ "(2, false, '2016-11-16','13:00', '14:00'),"
				+ "(4, true, '2016-11-21','9:00', '9:20'),"
				+ "(2, true, '2016-11-22','9:40', '10:00');";
		
		// TreatmentPerformed
		String q14 = "INSERT INTO TreatmentPerformed VALUES(1, 'hygVisit', 0, 0),(1,'amalF', 1, 1),(2,'resinF', 0, 0);";
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
		
		try {
			rowsUpdated = dbc.update(q13);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Rows updated: "+rowsUpdated);
		
		try {
			rowsUpdated = dbc.update(q14);
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
