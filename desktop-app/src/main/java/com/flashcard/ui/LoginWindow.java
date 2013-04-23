package com.flashcard.ui;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.swing.*;

/**
 * User: ghaxx
 * Date: 29/11/2011
 * Time: 14:25
 */
public class LoginWindow extends JFrame {

    private final LoginPanel loginPanel;

    public LoginWindow() {
        super("FlashCard");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        loginPanel = new LoginPanel(this);
        add(loginPanel.getPanel());
        pack();
    }

    public void login() {
//        setVisible(false);
//        dispose();
    }
}
