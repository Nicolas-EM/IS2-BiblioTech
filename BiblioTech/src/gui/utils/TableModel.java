package gui.utils;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import model.dao.SimulatedObject;

public class TableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	protected int columns;
	
	private List<SimulatedObject> objectList;
	
	public TableModel(int columns) {
		objectList = new ArrayList<>();
		this.columns = columns;
	}

	@Override
	public int getRowCount() {
		return objectList.size();
	}

	@Override
	public int getColumnCount() {
		return columns;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
//		return this.objectList.get(rowIndex).getAttributes().get(columnIndex);
		return null;
		// TODO: fix this
	}
}
