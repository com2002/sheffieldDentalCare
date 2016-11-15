package sheffieldDentalCare;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	WelcomePanel welcomePanel = new WelcomePanel();
	public static String USER_TYPE;
	
	public MainFrame() {
		setTitle("Sheffield Dental Care");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenDimensions = toolkit.getScreenSize();
		setSize(screenDimensions.width/2, screenDimensions.height/2);
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Add action listener to Enter button from WelcomePanel
		welcomePanel.getEnterBtn().addActionListener(new EnterBtnHandler());
		// Add action listener to Exit button from WelcomePanel
		welcomePanel.getExitBtn().addActionListener(new ExitBtnHandler());
		setContentPane(welcomePanel);
		//pack();
		setVisible(true);
	}
	
	/* Event Handlers */
	// Event handler for Enter button
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

	// Event handler for Exit button
	private class ExitBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	public static void main(String[] args) {
		new MainFrame();
	}
}
