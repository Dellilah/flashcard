package com.flashcard.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: ghaxx
 * Date: 05/12/2011
 * Time: 13:19
 */
public class SettingsDialog extends JDialog {
    public SettingsDialog(JFrame parent) throws HeadlessException {
        super(parent, "Settings");

        SettingsPanel settingsPanel = new SettingsPanel();
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        };
        settingsPanel.getSaveButton().addActionListener(actionListener);
        settingsPanel.getCancelButton().addActionListener(actionListener);
        add(settingsPanel.getPanel());
        setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);

        pack();
        setResizable(false);
    }
}
