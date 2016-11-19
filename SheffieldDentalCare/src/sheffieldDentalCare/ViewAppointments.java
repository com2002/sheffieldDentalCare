package sheffieldDentalCare;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 * ViewAppointments.java
 * Abstract class for viewing appointments.
 * @author ting
 * 
 */

public abstract class ViewAppointments {
	private JPanel selectionPanel;
	private DefaultTableModel tblModel;
	
	/**
	 * Set method for selectionPanel
	 * @param sp
	 */
	public void setSelectionPanel(JPanel sp) {
		selectionPanel = sp;
	}
	
	/**
	 * Set method for tblModel
	 * @param tm
	 */
	public void setTblModel(DefaultTableModel tm) {
		tblModel = tm;
	}
	
	/**
	 * Get method for selectionPanel
	 * @return selectionPanel
	 */
	public JPanel getSelectionPanel() {
		return selectionPanel;
	}
	
	/**
	 * Get method for tblModel
	 * @return tblModel
	 */
	public DefaultTableModel getTblModel() {
		return tblModel;
	}
	
	/**
	 * Abstract method to make a selection panel
	 */
	public abstract void makeSelectionPanel();
	
	/**
	 * Abstract method to make a table model
	 */
	public abstract void makeTblModel();
}