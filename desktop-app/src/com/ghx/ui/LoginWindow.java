package com.ghx.ui;

import com.ghx.system.Settings;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.net.URI;

/**
 * User: ghaxx
 * Date: 29/11/2011
 * Time: 14:25
 */
public class LoginWindow extends JFrame {

    private final LoginPanel loginPanel;

    public LoginWindow() {
        super("FlashCard");
        loginPanel = new LoginPanel(this);
        add(loginPanel.getPanel());
        pack();
    }

    public void login() {
//        setVisible(false);
//        dispose();
    }
}
