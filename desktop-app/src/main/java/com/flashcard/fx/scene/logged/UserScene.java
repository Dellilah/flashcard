package com.flashcard.fx.scene.logged;

import com.flashcard.fx.scene.logged.pane.UserPane;
import javafx.scene.Scene;

/**
 * User: ghaxx
 * Date: 27/04/2013
 * Time: 13:32
 */
public class UserScene extends Scene {
    public UserScene() {
        super(new UserPane());
        getStylesheets().add("main.css");
    }

    public UserScene(Integer id) {
        super(new UserPane(id));
        getStylesheets().add("main.css");
    }

    public UserScene(String message){
        super(new UserPane(message));
    }
}
