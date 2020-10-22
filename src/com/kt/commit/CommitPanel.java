package com.kt.commit;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;

import javax.swing.*;
import java.io.File;

public class CommitPanel {
    private JPanel mainPanel;
    private JComboBox changeType;
    private JComboBox changeScope;
    private JTextField shortDescription;
    private JTextArea longDescription;
    private JTextField closedIssues;
    private JTextArea breakingChanges;
    private JTextField taskNumber;
    private JLabel gitUsername;

    CommitPanel(Project project)
    {
        for (ChangeType type : ChangeType.values()) {
            changeType.addItem(type);
        }

        File workingDirectory = VfsUtil.virtualToIoFile(project.getBaseDir());
        Command.Result result = new Command(workingDirectory, "git log --all --format=%s | grep -Eo '^[a-z]+(\\(.*\\)):.*$' | sed 's/^.*(\\(.*\\)):.*$/\\1/' | uniq").execute();

        if (result.isSuccess()) {
            result.getOutput().forEach(changeScope::addItem);
        }

        Command.Result result2 = new Command(workingDirectory, "git branch | grep '*'").execute();

        if (result2.isSuccess()) {
            String result3 = result2.getOutput().toString().substring(3);
            result3 = "["+result3.substring(0, result3.length() - 1)+"] ";
            taskNumber.setText(result3);
        }

        Command.Result result5 = new Command(workingDirectory, "git config user.name && git config user.email").execute();

        if (result5.isSuccess()) {
            gitUsername.setText(result5.getOutput().toString().substring(1).substring(0, result5.getOutput().toString().length() - 2));
        }
    }

    JPanel getMainPanel() {
        return mainPanel;
    }

    CommitMessage getCommitMessage() {
        return new CommitMessage(
                (ChangeType) changeType.getSelectedItem(),
                (String) changeScope.getSelectedItem(),
                taskNumber.getText(),
                shortDescription.getText().trim(),
                longDescription.getText().trim(),
                closedIssues.getText().trim(),
                breakingChanges.getText().trim()
        );
    }

}
