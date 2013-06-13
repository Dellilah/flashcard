package com.flashcard.system;

import com.flashcard.fx.scene.logged.UserScene;
import com.flashcard.fx.scene.logged.pane.UserPane;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Date: 13/06/13
 * Time: 15:11
 */
//@Configuration
public class ComponentFactory {


    @Bean
    public UserPane buildUserPane() {
        return new UserPane();
    }

    @Bean
    public UserScene buildUserScene() {
        UserScene userScene = new UserScene();
        userScene.setRoot(buildUserPane());
        return userScene;
    }
}
