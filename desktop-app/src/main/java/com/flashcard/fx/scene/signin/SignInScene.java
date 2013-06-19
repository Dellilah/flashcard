package com.flashcard.fx.scene.signin;

import com.flashcard.fx.App;
import com.flashcard.fx.scene.signin.pane.SignInPane;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

/**
 * User: ghaxx
 * Date: 26/04/2013
 * Time: 00:51
 */
@Component
public class SignInScene extends Scene {

    @Autowired
    private SignInPane signInPane;

    @Autowired
    private Logger logger;

    public SignInScene() {
        super(null);
    }

    @PostConstruct
    private void setup() {
        setRoot(signInPane);
        logger.info("Created SignInScene");
    }


}
