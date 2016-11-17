/*************************************************************************************************
 * Based on rs2xml.jar's DbUtils' source code (particular static method resultSetToTableModel() ) (Edited to suit my requirements)
 * 
 * rs2xml.jar is available here: https://sourceforge.net/projects/finalangelsanddemons/
 *************************************************************************************************/
package sheffieldDentalCare;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.Vector;

public class MyDbConverter {
    public static MyTableModel resultSetToMyTableModel(ResultSet rs) {
	try {
	    ResultSetMetaData metaData = rs.getMetaData();
	    int numberOfColumns = metaData.getColumnCount();
	    Vector<String> columnNames = new Vector<String>();

	    // Get the column names
	    for (int column = 0; column < numberOfColumns; column++) {
		columnNames.addElement(metaData.getColumnLabel(column + 1));
	    }

	    // Get all rows.
	    Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

	    while (rs.next()) {
		Vector<Object> newRow = new Vector<Object>();

		for (int i = 1; i <= numberOfColumns; i++) {
		    newRow.addElement(rs.getObject(i));
		}

		rows.addElement(newRow);
	    }

	    return new MyTableModel(rows, columnNames);
	} catch (Exception e) {
	    e.printStackTrace();

	    return null;
	}
    }
}
