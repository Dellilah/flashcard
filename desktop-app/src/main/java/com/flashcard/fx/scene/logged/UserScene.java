package com.flashcard.fx.scene.logged;

import com.flashcard.fx.App;
import com.flashcard.fx.scene.logged.pane.UserPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * User: ghaxx
 * Date: 27/04/2013
 * Time: 13:32
 */
@Component
@Lazy
public class UserScene extends Scene {

    private UserPane userPane;

    public UserScene() {
        super(new VBox());
        getStylesheets().add("main.css");
    }

    @PostConstruct
    public void setup() {
        setRoot(userPane);
    }

    public void showEditPane(Integer id) {

        userPane.setEditPane(id);
    }

    public void setPane(Parent pane) {
        userPane.setContent(pane);
    }

    public void setMessage(String s) {
        userPane.setMessagePane(s);
    }

    public UserPane getUserPane() {
        return userPane;
    }

    @Autowired
    public void setUserPane(UserPane userPane) {
        this.userPane = userPane;
    }
}
