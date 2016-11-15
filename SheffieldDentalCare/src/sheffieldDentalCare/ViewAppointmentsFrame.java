package sheffieldDentalCare;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class ViewAppointmentsFrame extends JFrame {
	ViewAppointmentsPanel dentistPanel = new ViewAppointmentsPanel("Secretary", false, "Week");
	ViewAppointmentsPanel hygienistPanel = new ViewAppointmentsPanel("Secretary", true, "Week");
	
	public ViewAppointmentsFrame() {
		setTitle("View Appointments");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenDimensions = toolkit.getScreenSize();
		setSize(screenDimensions.width/4, screenDimensions.height/4);
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Dentist", dentistPanel);
		tabbedPane.add("Hygienist", hygienistPanel);
		setContentPane(tabbedPane);
		//pack();
		setVisible(true);
	}
	public static void main(String[] args) {
		new ViewAppointmentsFrame();
	}

}
