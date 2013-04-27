package com.flashcard.fx.scene;

import com.flashcard.fx.component.pane.UserPane;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * User: ghaxx
 * Date: 27/04/2013
 * Time: 13:32
 */
public class UserScene extends Scene {
    public UserScene() {
        super(new UserPane());
    }
}
