package sheffieldDentalCare;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PartnerPanel extends JPanel implements Panel {
	private JLabel titleLbl = new JLabel("Partner - Main");
	
	public PartnerPanel() {
		addComponents();
	}
	@Override
	public void initComponents() {}

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
				)
		);
				
		// Position components in the vertical
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(titleLbl)
				)
		);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
