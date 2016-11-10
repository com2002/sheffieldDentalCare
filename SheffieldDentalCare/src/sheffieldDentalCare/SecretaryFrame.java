package sheffieldDentalCare;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class SecretaryFrame extends JFrame implements Frame {
	private JLabel titleLbl = new JLabel("Secretary - Main");
	private JButton patientsBtn = new JButton("Patients");
	
	public SecretaryFrame() {
		initFrame();
		addComponents();
	}
	
	public void initComponents() {};
	
	public void initFrame() {
		setTitle("Sheffield Dental Care - Secretary");
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
						.addComponent(patientsBtn)
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
					.addComponent(patientsBtn)
				)
		);
		
		Container contentPane = getContentPane();
		contentPane.add(mainPanel);
	}
	
	public static void main(String[] args) {
		new SecretaryFrame();
	}
}
