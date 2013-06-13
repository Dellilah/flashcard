package com.flashcard.fx.scene.logged;

import com.flashcard.fx.App;
import com.flashcard.fx.scene.logged.pane.UserPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * User: ghaxx
 * Date: 27/04/2013
 * Time: 13:32
 */
@Component
public class UserScene extends Scene {
    public UserScene() {
        super(App.getInstanceContext().getBean(UserPane.class));
        getStylesheets().add("main.css");
    }

    public UserScene(Pane pane){
        super(App.getInstanceContext().getBean(UserPane.class));
        App.getInstanceContext().getBean(UserPane.class).setContent(pane);
        getStylesheets().add("main.css");
    }

    public UserScene(Integer id) {
        super(App.getInstanceContext().getBean(UserPane.class));
        App.getInstanceContext().getBean(UserPane.class).setEditPane(id);
        getStylesheets().add("main.css");
    }

    public UserScene(String message){
        super(App.getInstanceContext().getBean(UserPane.class));
        App.getInstanceContext().getBean(UserPane.class).setMessagePane(message);
    }

    public void setPane(Parent pane) {
        App.getInstanceContext().getBean(UserPane.class).setContent(pane);
    }
}
