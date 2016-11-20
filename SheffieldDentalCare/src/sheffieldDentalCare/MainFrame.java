package sheffieldDentalCare;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * MainFrame.java
 * MainFrame is the main frame that the user will use. Will contain other panels
 * 
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	WelcomePanel welcomePanel = new WelcomePanel();
	public static String USER_TYPE;
	private JMenuBar menuBar = new JMenuBar();
	private JMenuItem logoutMItem = new JMenuItem("Logout");
	
	/**
	 * Class constructor
	 * Initialises frame by setting size and first panel
	 */
	public MainFrame() {
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
		logoutMItem.addActionListener(new LogoutMItemHandler());
		menuBar.add(logoutMItem);
		setContentPane(welcomePanel);
		setVisible(true);
	}
	
	/**
	 * Event handler for enterBtn
	 * Outputs new panel based on user selection of user type
	 */
	private class EnterBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setJMenuBar(menuBar);
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
			System.out.println(USER_TYPE);
		}
	}

	/**
	 * Event handler for exitBtn
	 * Closes application
	 */
	private class ExitBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	
	/**
	 * Event handler for logoutMItem
	 * Returns user to welcome panel
	 */
	private class LogoutMItemHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Logout clicked");
			int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (n == 0) {
				System.out.println("Yes");
				setJMenuBar(null);
				setContentPane(welcomePanel);
				validate();
				repaint();
			} else {
				System.out.println("No");
			}
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
