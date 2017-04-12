package com.kunall17.textBuilder;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import kunall17.textbuilder.GenerateTextInterface;
import kunall17.textbuilder.PlaceholderForm;

public class generateText extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(CommonDataKeys.PROJECT);
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setVisible((project != null && editor != null && editor.getSelectionModel().hasSelection()));
        project = e.getProject();
        if (project == null) {
            return;
        }
        editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();
        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        Project finalProject = project;
        Runnable runnable = () -> {
            String text = selectionModel.getSelectedText();

            GenerateTextInterface gti = (String finalText) -> {
                WriteCommandAction.runWriteCommandAction(finalProject, () -> document.replaceString(start, end, finalText));
            };
            PlaceholderForm pf = new PlaceholderForm(text, start, end, gti);
            pf.setBounds(10, 10, 400, 400);
            pf.setVisible(true);

        };
        WriteCommandAction.runWriteCommandAction(project, runnable);
        selectionModel.removeSelection();

    }
}
