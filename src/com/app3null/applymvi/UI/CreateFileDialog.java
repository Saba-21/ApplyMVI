package com.app3null.applymvi.UI;

import com.app3null.applymvi.CreateFile.CreateFile;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.apache.http.util.TextUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateFileDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField;

    public CreateFileDialog(AnActionEvent e) {

        setTitle("New screen directory");
        setContentPane(contentPane);
        setModal(true);
        setMinimumSize(new Dimension(660, 420));
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e1 -> onConfirm(e));

        buttonCancel.addActionListener(e1 -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e1 -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onConfirm(AnActionEvent e) {
        if (TextUtils.isEmpty(textField.getText())) {
            Messages.showErrorDialog("Generation failed, " +
                            "your must enter class name",
                    "Class Name Is Null");
        } else {
            new CreateFile(e, textField.getText()).execute();
            dispose();
        }
    }

    private void onCancel() {
        dispose();
    }

}