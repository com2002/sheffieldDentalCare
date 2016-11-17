package sheffieldDentalCare;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
public class MyTableModel extends DefaultTableModel {

	public MyTableModel(Vector<Vector<Object>> rows, Vector<String> columnNames) {
		super(rows, columnNames);
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return super.getColumnCount();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return super.getRowCount();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return super.getValueAt(arg0, arg1);
	}
	 @Override
	 public boolean isCellEditable(int row, int column) {
		 return false;
	 }

}
