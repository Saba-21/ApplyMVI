package com.app3null.applymvi.action;

import com.app3null.applymvi.UI.CreateFileDialog;
import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.util.IconLoader;
import com.intellij.psi.PsiDirectory;

public class CreateFileAction extends AnAction {

    public CreateFileAction() {
        getTemplatePresentation().setIcon(IconLoader.getIcon("/icons/ic_action.png"));
    }

    @Override
    public void update(AnActionEvent e) {
        IdeView ideView = e.getRequiredData(LangDataKeys.IDE_VIEW);

        if (ideView.getDirectories().length == 1) {
            PsiDirectory directory = ideView.getOrChooseDirectory();
            if (directory != null) {
                if (directory.getName().equals("presentation")) {
                    e.getPresentation().setEnabledAndVisible(true);
                } else {
                    e.getPresentation().setEnabledAndVisible(false);
                }
            }
        } else {
            e.getPresentation().setEnabledAndVisible(false);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        new CreateFileDialog(e).setVisible(true);
    }
}
