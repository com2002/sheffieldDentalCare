package sheffieldDentalCare;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class SecretaryFrame extends JFrame{
	
	public SecretaryFrame() {
		initFrame();
	}
	
	public void initFrame() {
		setTitle("Sheffield Dental Care - Secretary");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenDimensions = toolkit.getScreenSize();
		setSize(screenDimensions.width/5, screenDimensions.height/8);
		setLocation(new Point(screenDimensions.width/4, screenDimensions.height/4));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
