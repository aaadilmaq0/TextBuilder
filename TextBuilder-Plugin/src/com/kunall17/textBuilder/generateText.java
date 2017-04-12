package com.kunall17.textBuilder;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;

public class generateText extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setVisible((project != null && editor != null && editor.getSelectionModel().hasSelection()));
        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();
        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String text = selectionModel.getSelectedText();
                addPlaceHolders aph = new addPlaceHolders(text,document,start,end);
                aph.setBounds(10,10,400,400);
                aph.setVisible(true);
            }
        };
        WriteCommandAction.runWriteCommandAction(project, runnable);
        selectionModel.removeSelection();

    }

    @Override
    public void update(AnActionEvent e) {

    }

}
