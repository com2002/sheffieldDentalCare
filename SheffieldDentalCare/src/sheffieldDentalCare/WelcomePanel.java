package sheffieldDentalCare;

import javax.swing.*;

/**
 * WelcomePanel.java
 * To select user type to enter system
 * @author ting
 *
 */
@SuppressWarnings("serial")
public class WelcomePanel extends JPanel implements Panel {
	private JLabel titleLbl = new JLabel("Welcome to Sheffield Dental Care");
	private JLabel userTypeLbl = new JLabel("User");
	private JComboBox<String> userTypeCbox = new JComboBox<String>();
	private JButton enterBtn = new JButton("Enter");
	private JButton exitBtn = new JButton("Exit");
	
	/**
	 * Class constructor
	 * Initialises panel
	 */
	public WelcomePanel() {
		initComponents();
		addComponents();
	}
	
	/**
	 * Get methods
	 */
	public JComboBox<String> getUserTypeCbox() {return userTypeCbox;}
	public JButton getEnterBtn() {return enterBtn;}
	public JButton getExitBtn() {return exitBtn;}
	
	/**
	 * Adds user type options
	 */
	@Override
	public void initComponents() {
		userTypeCbox.addItem("Secretary");
		userTypeCbox.addItem("Dentist");
		userTypeCbox.addItem("Hygienist");
	}
	
	/**
	 * Lays out components in panel
	 */
	@Override
	public void addComponents() {
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		this.setLayout(layout);

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
	}
}
