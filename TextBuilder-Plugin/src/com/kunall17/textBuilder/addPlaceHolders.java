package com.kunall17.textBuilder;

import com.intellij.openapi.editor.Document;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;

public class addPlaceHolders extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea1;
    private JButton addPlaceHolderButton;
    int noOfPlaceHolders = 0;
    public final String PLACEHOLDER_TEXT = "placeHolder*&^!=?";

    Document document;
    int start;
    int end;
    String text;

    public addPlaceHolders(String text, Document document, int start, int end) {
        textArea1.setText(text);
        this.text = text;
        this.document = document;
        this.start = start;
        this.end = end;
        setTitle("Add PlaceHolders");

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        addPlaceHolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.replaceSelection("");
                if (textArea1.getCaretPosition() == 0) {
                    try {
                        textArea1.setCaretPosition(textArea1.getLineEndOffset(textArea1.getLineCount()) - 1);
                    } catch (BadLocationException e1) {
                        e1.printStackTrace();
                    }
                }
                textArea1.insert(PLACEHOLDER_TEXT + noOfPlaceHolders++, textArea1.getCaretPosition());
            }
        });
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

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


    }

    private void onOK() {
        if (noOfPlaceHolders == 0) {
            JOptionPane.showMessageDialog(null, "Atleast one placeHolder must be added!");
            return;
        }

        BuildText bt = new BuildText(document, textArea1.getText(), start, end, noOfPlaceHolders);
        bt.setBounds(10, 10, 400, 400);
        bt.setVisible(true);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {

    }
}
