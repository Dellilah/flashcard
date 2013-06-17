package com.flashcard.fx.scene.signin;

import com.flashcard.fx.App;
import com.flashcard.fx.scene.signin.pane.SignInPane;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: ghaxx
 * Date: 26/04/2013
 * Time: 00:51
 */
@Component
public class SignInScene extends Scene {

    @Autowired
    private SignInPane signInPane = null;

    public SignInScene() {
        super(App.getInstanceContext().getBean(SignInPane.class));
    }
}
