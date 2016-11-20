package sheffieldDentalCare;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.*;

public class RegisterPatientPanel extends JPanel {
	private boolean addValid = false;
	private String title = null;
	private String fName = null;
	private String sName = null;
	private String dOB = null;
	private String phone = null;
	private int houseNum = 0;
	private String houseNumString = null; 
	private String street = null;
	private String district = null;
	private String city = null;
	private String pocode = null;
	private int addressID = 0;
	private int patientID = 0;
	
	private Registrar reg = new Registrar();
	
	private boolean allFieldsInput(String[] textFields) {
		boolean fieldsInput = true;
		for (int x=0; x<textFields.length; x++) {
			if ((textFields[x]).isEmpty()) {
				fieldsInput = false;
			}
		}
		return fieldsInput;
	}
	
	public boolean addValid(){
		return addValid;
	}
	
	public String getTitle() {
		return title;
	}
		
	public String getFirstName() {
		return fName;
	}
	
	public String getSurname() {
		return sName;
	}
	
	public String getDOB() {
		return dOB;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getHouseNumString() {
		return houseNumString;
	}
	private boolean validHouseNum(String houseNumString) {
		try {
			houseNum = Integer.parseInt(houseNumString);
		} catch (NumberFormatException f) {
			return false;
		}
		return true;
	}
	
	public int getHouseNum() {
		return houseNum;
	}
	
	public String getStreet() {
		return street;
	}
	
	public String getDistrict() {
		return district;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getPostcode() {
		return pocode;
	}
	
	public int getAddressID() {
		return addressID;
	}
	
	public int patientID() {
		return patientID;
	}
	
	public RegisterPatientPanel(RegisterPanel registerPanel) {
		setLayout(new GridLayout(0,1));
	
		// title input panel
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JComboBox<String> titleCbox = new JComboBox<String>();
		titleCbox.addItem("Mr");
		titleCbox.addItem("Miss");
		titleCbox.addItem("Mrs");
		titlePanel.add(new JLabel("Title: ", JLabel.RIGHT));
		titlePanel.add(titleCbox);
				
		
		// first name input panel
		JPanel fNamePanel = new JPanel();
		fNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JTextField fNameField = new JTextField(20);
		fNamePanel.add(new JLabel("First Name:", JLabel.RIGHT));
		fNamePanel.add(fNameField);
		
		// surname input panel
		JPanel sNamePanel = new JPanel();
		sNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JTextField sNameField = new JTextField(20);
		sNamePanel.add(new JLabel("Surname:  ", JLabel.RIGHT));
		sNamePanel.add(sNameField);
		
		// DoB input panel
		JPanel dOBPanel = new JPanel();
		dOBPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JTextField dOBField = new JTextField(10);
		dOBPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):", JLabel.RIGHT));
		dOBPanel.add(dOBField);
		
		// Phone number input panel
		JPanel phonePanel = new JPanel();
		phonePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JTextField phoneField = new JTextField(20);
		phonePanel.add(new JLabel("Phone Number:", JLabel.RIGHT));
		phonePanel.add(phoneField);
		
		// house number input panel
		JPanel houseNumPanel = new JPanel();
		houseNumPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JTextField houseNumField = new JTextField(20);
		houseNumPanel.add(new JLabel("House Number:", JLabel.RIGHT));
		houseNumPanel.add(houseNumField);
		
		// street input panel
		JPanel streetPanel = new JPanel();
		streetPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JTextField streetField = new JTextField(20);
		streetPanel.add(new JLabel("Street:", JLabel.RIGHT));
		streetPanel.add(streetField);		
		
		// district input panel
		JPanel districtPanel = new JPanel();
		districtPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JTextField districtField = new JTextField(20);
		districtPanel.add(new JLabel("District:", JLabel.RIGHT));
		districtPanel.add(districtField);
		
		// city input panel
		JPanel cityPanel = new JPanel();
		cityPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JTextField cityField = new JTextField(20);
		cityPanel.add(new JLabel("City:", JLabel.RIGHT));
		cityPanel.add(cityField);		
		
		// postcode input panel
		JPanel pocodePanel = new JPanel();
		pocodePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JTextField pocodeField = new JTextField(20);
		pocodePanel.add(new JLabel("Postcode:", JLabel.RIGHT));
		pocodePanel.add(pocodeField);
		
		
		// 'add patient' button
		JButton addPatientButton = new JButton("Register Patient");
		addPatientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button Clicked");
				
				title = titleCbox.getSelectedItem().toString();
				fName = fNameField.getText();
				sName = sNameField.getText();
				dOB = dOBField.getText();
				phone = phoneField.getText();
				houseNumString = houseNumField.getText();
				street = streetField.getText();
				district = districtField.getText();
				city = cityField.getText();
				pocode = pocodeField.getText().replace(" ", "");
						
				validHouseNum(houseNumString);
			
				
				String[] textFields = {title, fName, sName, dOB, phone, houseNumString, street, district, city, pocode};
				if (allFieldsInput(textFields)) {
					System.out.println("Details Accepted");
					addValid = true;
					
					try {
						addressID = reg.addAddress(houseNum, street, district, city, pocode);
					} catch (SQLException x) {
						System.out.println("address invalid");;
					}
					
					try {
						patientID = reg.addPatient(title, fName, sName, dOB, phone, addressID);
					} catch (SQLException x) {
						x.printStackTrace();
					}
					registerPanel.updateRegisterPanel();
				}
				else {
					System.out.println("Details not entered");
				}
			} 
		});
		
		// add all input panels to this RegisterPatientPanel
		add(titlePanel);
		add(fNamePanel);
		add(sNamePanel);
		add(dOBPanel);
		add(phonePanel);
		add(houseNumPanel);
		add(streetPanel);
		add(districtPanel);
		add(cityPanel);
		add(pocodePanel);
		add(addPatientButton);
		
	}
	
		
}
