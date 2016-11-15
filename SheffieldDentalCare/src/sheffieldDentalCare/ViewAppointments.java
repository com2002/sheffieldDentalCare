package sheffieldDentalCare;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public abstract class ViewAppointments {
	private JPanel selectionPanel;
	private DefaultTableModel tblModel;
	
	public void setSelectionPanel(JPanel sp) {
		selectionPanel = sp;
	}
	
	public void setTblModel(DefaultTableModel tm) {
		tblModel = tm;
	}
	
	public JPanel getSelectionPanel() {
		return selectionPanel;
	}
	
	public DefaultTableModel getTblModel() {
		return tblModel;
	}

	public abstract void makeSelectionPanel();
	public abstract void makeTbl();
}