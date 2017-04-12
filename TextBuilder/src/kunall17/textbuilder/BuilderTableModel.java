/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kunall17.textbuilder;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

class BuilderTableModel extends AbstractTableModel {

    public int numberOfPlaceholders = -1;
    Boolean DEBUG = true;
    List<RowData> rows;

    public BuilderTableModel(int numberOfPlaceholders) {
        this.numberOfPlaceholders = numberOfPlaceholders;
        rows = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            RowData rd = new RowData(numberOfPlaceholders);
            for (int j = 0; j < numberOfPlaceholders; j++) {
                rd.setData("", j);
            }
            rows.add(rd);
        }

    }

    public void addRow() {
        RowData rd = new RowData(numberOfPlaceholders);
        for (int j = 0; j < numberOfPlaceholders; j++) {
            rd.setData("", j);
        }
        rows.add(rd);

        fireTableRowsInserted(rows.size() - 1, rows.size() - 1);
    }

    public void deleteRow() {
        rows.remove(rows.size() - 1);
        fireTableRowsInserted(rows.size() - 1, rows.size() - 1);
    }

    public void clearAll() {

        for (RowData rd : rows) {
            rd.clear();
        }
        fireTableDataChanged();
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).size; j++) {
                fireTableCellUpdated(i, j);
            }
        }
        System.out.println("cleared ALl");
        Object result = JOptionPane.showInputDialog(null, "Enter Numbers seperated by - (Eg. 4-11)");

    }

    public int getColumnCount() {
        return numberOfPlaceholders;
    }

    public int getRowCount() {
        return rows.size();
    }

    public String getColumnName(int col) {
        return "PlaceHolder " + col;
    }

    public Object getValueAt(int row, int col) {
        return rows.get(row).getData(col);
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    public void setValueAt(Object value, int row, int col) {

        rows.get(row).setData(value.toString(), col);
        fireTableCellUpdated(row, col);
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
//                System.out.print("  " + data[i][j]);
            }
        }
    }
}
