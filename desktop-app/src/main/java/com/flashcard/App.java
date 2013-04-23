package com.flashcard;

import com.flashcard.system.Settings;
import com.flashcard.ui.LoginWindow;

/**
 * User: ghaxx
 * Date: 29/11/2011
 * Time: 14:20
 */
public class App {
    public static void main(String[] args) {
        LoginWindow window = new LoginWindow();
        window.setVisible(true);
        window.setAlwaysOnTop(Settings.getAlwaysOnTop());
    }
}
