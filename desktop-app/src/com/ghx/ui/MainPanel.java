package com.ghx.ui;

import com.ghx.system.Settings;
import com.google.gson.Gson;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * User: ghaxx
 * Date: 29/11/2011
 * Time: 14:09
 */
public class MainPanel {
    private JPanel panel;
    private JComboBox orderFileBox;
    private JComboBox cardValidationFileBox;
    private JComboBox creditCheckFileBox;
    private JTextField wordField;
    private JCheckBox alwayOnTopCheckBox;
    private JButton toEnglishButton;
    private JButton toPolishButton;
    private JTextPane translation;
    private JButton quitButton;
    private JButton addButton;
    private JLabel translationField;
    private Window parentWindow;

    public MainPanel() {
    }

    public MainPanel(Window parentWindow) {
        this.parentWindow = parentWindow;
    }

    private Vector<String> buildPath(String root, String relativePath) {
        File rootFile = new File(root);
        Vector<String> list = new Vector<String>();
        if (rootFile.exists()) {
            File[] files = rootFile.listFiles();
            for (File file : files) {
                if (!file.isHidden()) {
                    if (file.isDirectory()) {
                        list.addAll(buildPath(root + "/" + file.getName(), file.getName()));
                    } else {
                        list.add((relativePath.isEmpty() ? "" : relativePath + "/") + file.getName());
                    }
                }
            }
        }
        return list;
    }

    private void createUIComponents() {
        translation = new JTextPane();
        wordField = new JTextField();
        toPolishButton = new JButton("To Polish");
        toEnglishButton = new JButton("To English");
        translationField = new JLabel();
        toEnglishButton.addActionListener(new Listener("pl"));
        toPolishButton.addActionListener(new Listener("en"));

        // Always on top
        alwayOnTopCheckBox = new JCheckBox("Always on Top", Settings.getAlwaysOnTop());
        alwayOnTopCheckBox.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                Settings.setAlwaysOnTop(alwayOnTopCheckBox.isSelected());
                try {
                    Settings.writeSettings();
                    if (parentWindow != null) {
                        parentWindow.setAlwaysOnTop(alwayOnTopCheckBox.isSelected());
                    }
                } catch (Exception e) {
                    e.printStackTrace();  //TODO: implement body of catch statement.
                }
            }
        });

        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getOrderId() {
        return wordField.getText();
    }

    public String getOrderFileName() {
        return ((String) orderFileBox.getSelectedItem()).replace(" ", "%20");
    }

    public String getCreditCheckFileName() {
        return ((String) creditCheckFileBox.getSelectedItem()).replace(" ", "%20");
    }

    public String getCardValidationCheckFileName() {
        return ((String) cardValidationFileBox.getSelectedItem()).replace(" ", "%20");
    }

    private class Listener implements ActionListener {
        private String lng;

        public Listener(String lng) {
            this.lng = lng;
        }

        public void actionPerformed(ActionEvent actionEvent) {
            try {
                String uri = Settings.getHost() + "/api/words/from_" + lng + "/" + wordField.getText() + ".json?api_token=" + Settings.getToken();
                Content s = Request.Get(uri).execute().returnContent();
                System.out.println(s.asString());
                Gson gson = new Gson();
                String[] l = gson.fromJson(s.asString(), String[].class);
                StringBuilder t = new StringBuilder();
                for (String s1 : l) {
                    t.append(s1).append("\n");
                }
                translation.setText(t.toString());
            } catch (IOException e) {
                e.printStackTrace();  //TODO: implement body of catch statement.
            }
        }
    }
}
