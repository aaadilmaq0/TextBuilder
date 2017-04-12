package com.kunall17.textBuilder;

import com.intellij.openapi.editor.Document;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class BuildText extends JDialog {
    private static final String PLACEHOLDER_TEXT = "placeHolder*&^!=?";
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table1;
    private JButton generateButton;
    Document document;
    int start;
    int end;
    String text;
    public static int NumberOfPlaceHolders = 4;
    MyTableModel tm;
    JMenuBar menuBar;
    JMenu menu, submenu;
    JMenuItem menuItem;

    public BuildText(Document document, String text, int start, int end, int NumberOfPlaceHolders) {
        this.document = document;
        this.text = text;
        this.start = start;
        this.end = end;
        this.NumberOfPlaceHolders = NumberOfPlaceHolders;
        setupMenuBar();
        tm = new MyTableModel();
        setupMenuBar();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        getRootPane().setJMenuBar(menuBar);
        table1.setModel(tm);
        setTitle("Build Text");
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
// add your code here
        String t1 = text;

        StringBuilder finalString = new StringBuilder();
        for (int i = 0; i < tm.getRowCount(); i++) {

            for (int j = 0; j < NumberOfPlaceHolders; j++) {
                text = text.replace(PLACEHOLDER_TEXT + j, tm.getValueAt(i, j).toString());
            }
            finalString.append(text + "\n");
            text = t1;
        }

        document.replaceString(start, end, finalString.toString());
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    static BuildText dialog;

    public static void main(String[] args) {
//        BuildText = new BuildText();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);


    }


    public void setupMenuBar() {

//Create the menu bar.
        menuBar = new JMenuBar();

//Build the first menu.
        menu = new JMenu("Fill With");
        menuBar.add(menu);

//a group of JMenuItems


        submenu = new JMenu("Add Rows");


        menuItem = new JMenuItem("Add Rows");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object result = JOptionPane.showInputDialog(contentPane, "Enter Number of Rows to be added?");
                try {
                    int no = Integer.parseInt(result.toString());
                    for (int i = 0; i < no; i++) {
                        tm.addRow();
                    }
                } catch (Exception e1) {
                    showMessageDialog(contentPane, e1.toString());
                }
            }
        });
        submenu.add(menuItem);

        menuItem = new JMenuItem("Add 1 Row");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tm.addRow();
            }
        });
        submenu.add(menuItem);

        menu.add(submenu);

        menuItem = new JMenuItem("Delete Row");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tm.deleteRow();
            }
        });

        menu.add(menuItem);
        menu.addSeparator();


        menuItem = new JMenuItem("FileNames");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (table1.getSelectedColumnCount() == 0) {
                    showMessageDialog(null, "No selected Column");

                } else {

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                    fileChooser.setMultiSelectionEnabled(true);
                    int result = fileChooser.showOpenDialog(contentPane);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File[] selectedFile = fileChooser.getSelectedFiles();
                        if (selectedFile.length == 1) {
                            tm.setValueAt(selectedFile[0].getAbsoluteFile().toString(), table1.getSelectedRow(), table1.getSelectedColumn());
                        } else {

                            for (int i = 0; i < selectedFile.length; i++) {
                                if (table1.getSelectedRow() + i >= table1.getRowCount()) tm.addRow();
                                tm.setValueAt(selectedFile[i].getAbsoluteFile(), table1.getSelectedRow() + i, table1.getSelectedColumn());
                            }

                        }


                    }

                }

            }
        });

        menu.add(menuItem);


        menuItem = new JMenuItem("Full Path");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (table1.getSelectedColumnCount() == 0) {
                    showMessageDialog(null, "No selected Column");

                } else {

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                    fileChooser.setMultiSelectionEnabled(true);
                    int result = fileChooser.showOpenDialog(contentPane);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File[] selectedFile = fileChooser.getSelectedFiles();
                        if (selectedFile.length == 1) {
                            tm.setValueAt(selectedFile[0].getAbsolutePath().toString(), table1.getSelectedRow(), table1.getSelectedColumn());
                        } else {

                            for (int i = 0; i < selectedFile.length; i++) {
                                if (table1.getSelectedRow() + i >= table1.getRowCount()) tm.addRow();
                                tm.setValueAt(selectedFile[i].getAbsolutePath(), table1.getSelectedRow() + i, table1.getSelectedColumn());
                            }

                        }


                    }

                }

            }
        });

        menu.add(menuItem);

        menu.addSeparator();
        menuItem = new JMenuItem("Numbers");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table1.getSelectedColumnCount() == 0) {
                    showMessageDialog(null, "No selected Column");

                } else {
                    Object result = JOptionPane.showInputDialog(null, "Enter Numbers seperated by - (Eg. 4-11)");
                    String[] sd = String.valueOf(result).split("-");
                    int n1 = Integer.parseInt(sd[0]);
                    int n2 = Integer.parseInt(sd[1]);

                    for (int i = 0; i <= n2 - n1; i++) {
                        if (table1.getSelectedRow() + i >= table1.getRowCount()) tm.addRow();
                        for (int j = 0; j < table1.getSelectedColumnCount(); j++) {

                            tm.setValueAt(n1 + i, table1.getSelectedRow() + i, table1.getSelectedColumn() + j);
                        }
                    }
                }
            }
        });

        menu.add(menuItem);

        menuItem = new JMenuItem("Alphabets");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table1.getSelectedColumnCount() == 0) {
                    showMessageDialog(null, "No selected Column");

                } else {
                    String s = (String) JOptionPane.showInputDialog(contentPane,
                            "Enter the alphabets (Same case alphabets) Eg. A-Z",
                            "Alphabets", JOptionPane.OK_CANCEL_OPTION, null,
                            null, "A-Z");

                    String[] d = s.split("-");
                    char c1 = d[0].charAt(0);
                    char c2 = d[1].charAt(0);

                    int i = 0;
                    do {
                        if (table1.getSelectedRow() + i >= table1.getRowCount()) tm.addRow();
                        for (int j = 0; j < table1.getSelectedColumnCount(); j++) {
                            tm.setValueAt(c1, table1.getSelectedRow() + i, table1.getSelectedColumn() + j);
                        }

                        c1 = ((char) (getInt(c1) + 1));
                        if (i++ > 55) break;
                    } while (c1 != c2);

                    if (table1.getSelectedRow() + i >= table1.getRowCount()) tm.addRow();
                    for (int j = 0; j < table1.getSelectedColumnCount(); j++) {
                        tm.setValueAt(c1, table1.getSelectedRow() + i, table1.getSelectedColumn() + j);
                    }
                }

            }
        });

        menu.add(menuItem);
//a submenu

//Build second menu in the menu bar.
        menu = new JMenu("Clear");
        menu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                tm.clearAll();
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });


        menuBar.add(menu);

        menu = new JMenu("About");
        menu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                showMessageDialog(contentPane, "Plugin Developed by Kunal (kunall17) \n\n Open Source: https://github.com/kunall17/textBuilder-plugin", "About", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        menuBar.add(menu);
    }

    public int getInt(char c) {
        return (int) c;
    }

}


class RowData {
    int size;
    String[] data;

    public RowData(int size) {
        this.size = size;
        data = new String[size];
    }

    public String[] getData() {
        return data;
    }

    public String getData(int index) {
        return data[index];
    }

    public void setData(String data, int index) {
        this.data[index] = data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = "";
        }
    }
}


class MyTableModel extends AbstractTableModel {

    public int NumberOfPlaceHolders = BuildText.NumberOfPlaceHolders;
    Boolean DEBUG = true;
    java.util.List<RowData> rows;

    public MyTableModel() {
        rows = new ArrayList<RowData>();

        for (int i = 0; i < 5; i++) {
            RowData rd = new RowData(NumberOfPlaceHolders);
            for (int j = 0; j < NumberOfPlaceHolders; j++) {
                rd.setData("", j);
            }
            rows.add(rd);
        }

    }

    public void addRow() {
        RowData rd = new RowData(NumberOfPlaceHolders);
        for (int j = 0; j < NumberOfPlaceHolders; j++) {
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
        return NumberOfPlaceHolders;
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
