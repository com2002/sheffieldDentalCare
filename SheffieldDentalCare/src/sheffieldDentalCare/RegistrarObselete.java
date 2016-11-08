package sheffieldDentalCare;

import java.sql.*;
/**
 * Use a Registrar to add addresses and patients to the database. 
 * The patient's address must already be on the database before you can add the patient.
 * @author Charlie D
 *
 */
public class RegistrarObselete {
	
	/**
	 * Use to add an address to the database
	 * @param houseNum
	 * @param streetName
	 * @param postCode
	 * @param county
	 * @return the addressID of the added address
	 * @throws SQLException
	 */	
	public int addAddress(int houseNum,String streetName,String postCode,String county) throws SQLException {
		DBController dbc = new DBController();
		RegistrarObselete reg = new RegistrarObselete();
		
		String q1 = "INSERT INTO Address (houseNumber,streetName,postCode,county) ";
		String q2 = "VALUES ('"+houseNum+"','"+streetName+"','"+postCode+"','"+county+"');";		
		
		dbc.update(q1+q2);//make an entry onto the Address table using the above statements
				
		int id = reg.getAddressID(houseNum, postCode);
		
		dbc.closeConnection(); //close off connection
		return id;
	}
	
	/**
	 * Use to get the addressID of an address by housenumber and postcode
	 * @param houseNum
	 * @param postCode
	 * @return the addressID of the provided address by housenumber and postcode
	 * @throws SQLException
	 */
	public int getAddressID(int houseNum, String postCode) throws SQLException {
		int addressID = 0;
		DBController dbc = new DBController();
		
		String q1 = "Select addressID from Address ";
		String q2 = "WHERE houseNumber = "+houseNum+" AND postCode LIKE '"+postCode+"';";
		
		ResultSet rs = dbc.query(q1+q2);//get the addressID of the address provided by the queries above
		rs.next();
		addressID = rs.getInt(1);
		
		dbc.closeConnection();		
		return addressID;
	}
	/**
	 * Use to add a patient to the DB once their addressID has been found
	 * @param firstName
	 * @param surName
	 * @param dob in the format YYYY-MM-DD
	 * @param addressID
	 * @return number of rows added (should be 1)
	 * @throws SQLException
	 */
	public int addPatient(String firstName, String surName, String dob, int addressID) throws SQLException {
		DBController dbc = new DBController();
		
		String q1 = "INSERT INTO Patients (firstName, surName, dateOB, addressID) ";
		String q2 = "VALUES ('"+firstName+"','"+surName+"','"+dob+"','"+addressID+"');";
		
		int updates = dbc.update(q1+q2);//Make and entry onto the Patients table using the above statements	
		dbc.closeConnection();
		
		return updates;
		
		
	}
	
	
	
	//test method
	public static void main(String[] args) throws SQLException {
		RegistrarObselete reg = new RegistrarObselete();
		System.out.println(reg.addAddress(27, "Maple Street", "RD12 6NG", "Middlesex"));
	/*	
		System.out.println(reg.getAddressID(17,"RD12 6NG")); //should return 5
	*/
	}

}
