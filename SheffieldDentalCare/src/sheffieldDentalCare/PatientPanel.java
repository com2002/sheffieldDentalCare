package sheffieldDentalCare;

import java.awt.*;
import javax.swing.*;

public class PatientPanel extends JPanel {
	private FindPatientPanel searchPanel = new FindPatientPanel();
	
	public PatientPanel() {
		add(searchPanel);
	}
	
}
