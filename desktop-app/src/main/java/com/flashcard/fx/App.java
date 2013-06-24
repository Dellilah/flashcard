package com.flashcard.fx;

import com.flashcard.fx.scene.signin.SignInScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;

/**
 * Date: 26/04/2013
 * Time: 00:27
 */
public class App extends Application {
    private static App instance = null;

    private Logger logger;

    private Stage primaryStage = null;
    private AnnotationConfigApplicationContext context = null;

    public static void main(String[] args) {
        launch(args);
    }

    public static App getInstance() {
        if (instance == null)
            throw new NullPointerException("Instance not yet initialized");
        return instance;
    }

    public static AnnotationConfigApplicationContext getInstanceContext() {
        return getInstance().getContext();
    }

    public void setScene(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        logger = getContext().getBean(Logger.class);
        logger.info("Starting application");

        this.primaryStage = primaryStage;
        primaryStage.setTitle("Welcome to Flashcard");

        setScene(getContext().getBean(SignInScene.class));
        getPrimaryStage().sizeToScene();

        primaryStage.show();
    }

    public AnnotationConfigApplicationContext getContext() {
        if (context == null) {
            context = new AnnotationConfigApplicationContext();
            context.scan("com.flashcard");
            context.refresh();
        }
        return context;
    }
}
