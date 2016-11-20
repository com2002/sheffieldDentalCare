package sheffieldDentalCare;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

/**
 * MainFrame.java
 * MainFrame is the main frame that the user will use. Will contain other panels
 * 
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	WelcomePanel welcomePanel = new WelcomePanel();
	public static String USER_TYPE;
	
	/**
	 * Class constructor
	 * Initialises frame by setting size and first panel
	 */
	public MainFrame() {
		goToHome();
	}
	
	// method to go to home screen (also used to 'log out' from different parts of system)
	public void goToHome() {
		setTitle("Sheffield Dental Care");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenDimensions = toolkit.getScreenSize();
		setSize(screenDimensions.width, screenDimensions.height);
		setLocationByPlatform(true);
		setLocation(new Point(0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Add action listener to Enter button from WelcomePanel
		welcomePanel.getEnterBtn().addActionListener(new EnterBtnHandler());
		// Add action listener to Exit button from WelcomePanel
		welcomePanel.getExitBtn().addActionListener(new ExitBtnHandler());
		setContentPane(welcomePanel);
		setVisible(true);
	}
	
	/**
	 * Event handler for enterBtn
	 * Outputs new panel based on user selection of user type
	 */
	private class EnterBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			USER_TYPE = welcomePanel.getUserTypeCbox().getSelectedItem().toString();
			if (USER_TYPE == "Secretary") {
				SecretaryPanel secretaryPanel = new SecretaryPanel();
				setContentPane(secretaryPanel);
				validate();
				repaint();
			} else if (USER_TYPE == "Dentist") {
				PartnerPanel dentistPanel = new PartnerPanel();
				setContentPane(dentistPanel);
				validate();
				repaint();
			} else {
				PartnerPanel hygienistPanel = new PartnerPanel();
				setContentPane(hygienistPanel);
				validate();
				repaint();
			}
			//System.out.println(USER_TYPE);
		}
	}

	/**
	 * Event handler for exitrBtn
	 * Closes application
	 */
	private class ExitBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	
	/**
	 * Main method
	 * Creates main frame
	 */
	public static void main(String[] args) {
		new MainFrame();
	}
}
