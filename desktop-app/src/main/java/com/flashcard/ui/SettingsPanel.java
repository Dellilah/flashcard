package com.flashcard.ui;

import com.flashcard.system.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * User: ghaxx
 * Date: 05/12/2011
 * Time: 13:03
 */
public class SettingsPanel {
    private JTextField loginField;
    private JTextField passwordField;
    private JButton saveButton;
    private JPanel panel;
    private JTextField serverField;
    private JButton cancelButton;

    public JPanel getPanel() {
        return panel;
    }

    private void createUIComponents() {

        passwordField = new JTextField(Settings.getPassword());
        serverField = new JTextField(Settings.getHost());
        loginField = new JTextField(Settings.getLogin());
        
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Settings.setPassword(passwordField.getText().trim());
                Settings.setHost(serverField.getText().trim());
                Settings.setLogin(loginField.getText().trim());
                try {
                    Settings.writeSettings();
                } catch (IOException e) {
                    e.printStackTrace();  //TODO: implement body of catch statement.
                }
            }
        });
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
