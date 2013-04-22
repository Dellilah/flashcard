package com.ghx.ui;

import com.ghx.system.Settings;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

/**
 * User: ghaxx
 * Date: 22/04/2013
 * Time: 13:45
 */
public class LoginPanel {
    private JTextField loginField;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JPanel panel;
    private JLabel message;

    public LoginPanel(final LoginWindow loginWindow) {
        loginField.setText(Settings.getLogin());
        passwordField1.setText(Settings.getPassword());
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                String url = Settings.getHost() + "/api/login.json";

                LoginDTO l = new LoginDTO();
                try {
                    Content s = Request.Post(url)
                        .bodyForm(
                                Form.form()
                                        .add("email", loginField.getText())
                                        .add("password", new String(passwordField1.getPassword()))
                                        .build()
                        ).execute().returnContent();
                    System.out.println(s.asString());
                    Gson gson = new Gson();
                    l = gson.fromJson(s.asString(), LoginDTO.class);
                    System.out.println(l.getApi_token());
                    if (l.getMessage() == null && l.getApi_token() != null) {
                        System.out.println("...");
                        loginWindow.setVisible(false);
                        System.out.println("Showing app window");
                        AppWindow window = new AppWindow();
                        window.setVisible(true);
                        window.setAlwaysOnTop(Settings.getAlwaysOnTop());
                    } else {
                        message.setText(l.getMessage());
                    }
                    Settings.setToken(l.getApi_token());
                } catch (Exception e) {
                    e.printStackTrace();
                    message.setText("The supplied credentials could not be regarded as correct (G)");
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
