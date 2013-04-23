package com.flashcard.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * User: ghaxx
 * Date: 29/11/2011
 * Time: 14:25
 */
public class AppWindow extends JFrame {

    private final MainPanel mainPanel;

    public AppWindow() {
        super("FlashCard");
        mainPanel = new MainPanel();
        add(mainPanel.getPanel());
//        add(loginPanel.getPanel());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Edit", true);
        MenuItem settings = new MenuItem("Settings", new MenuShortcut(KeyEvent.VK_COMMA | Event.META_MASK));
        settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                SettingsDialog jFrame = new SettingsDialog(AppWindow.this);
                jFrame.setVisible(true);
            }
        });
        menu.add(settings);
        menuBar.add(menu);
        menuBar.setName("FlashCard");
        setMenuBar(menuBar);
        pack();
//        setResizable(false);
    }
}
