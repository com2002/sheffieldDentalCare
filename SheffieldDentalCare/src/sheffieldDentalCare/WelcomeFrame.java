/**
 * WelcomeFrame.java
 * 
 * WelcomeFrame class creates a frame and adds components for the user to select what
 * user they are. This will be the first window the user sees.
 * 
 */

package sheffieldDentalCare;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class WelcomeFrame extends JFrame implements Frame {
	// All components to be added to frame
	private JLabel titleLbl = new JLabel("Welcome to Sheffield Dental Care");
	private JLabel userTypeLbl = new JLabel("User");
	private JComboBox<String> userTypeCbox = new JComboBox<String>();
	private JButton enterBtn = new JButton("Enter");
	private JButton exitBtn = new JButton("Exit");
	
	public WelcomeFrame() {
		initComponents();
		initFrame();
		addComponents();
	}
	
	public void initComponents() {
		// Add user type options
		userTypeCbox.addItem("Secretary");
		userTypeCbox.addItem("Dentist");
		userTypeCbox.addItem("Hygienist");
	}
	
	public void initFrame() {
		setTitle("Sheffield Dental Care");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenDimensions = toolkit.getScreenSize();
		setSize(screenDimensions.width/4, screenDimensions.height/4);
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void addComponents() {	
		JPanel mainPanel = new JPanel();
		GroupLayout layout = new GroupLayout(mainPanel);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		mainPanel.setLayout(layout);

		// Add components to layout
		// Position components in the horizontal
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(titleLbl)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(userTypeLbl)
						.addComponent(enterBtn)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(userTypeCbox)
						.addComponent(exitBtn)
					)
				)
				)
		);
		
		// Position components in the vertical
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(titleLbl)
			    )
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(userTypeLbl)
			    	.addComponent(userTypeCbox)
			    		
			    )
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(enterBtn)
			    	.addComponent(exitBtn)
			    )
		);
		
		// Add action listener to Enter button
		enterBtn.addActionListener(new EnterBtnHandler());
		// Add action listener to Exit button
		exitBtn.addActionListener(new ExitBtnHandler());
		
		Container contentPane = getContentPane();
		contentPane.add(mainPanel);
	}
	
	
	/* Event Handlers */
	// Event handler for Enter button
	private class EnterBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String userType = userTypeCbox.getSelectedItem().toString();
			if (userType == "Secretary") {
				new SecretaryFrame();
				setVisible(false);
			}
		}
	}

	// Event handler for Exit button
	private class ExitBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	
	/* Main Function */
	public static void main(String[] args) {
		new WelcomeFrame();
	}
}
