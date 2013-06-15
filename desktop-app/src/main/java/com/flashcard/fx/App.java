package com.flashcard.fx;

import com.flashcard.fx.scene.signin.SignInScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Date: 26/04/2013
 * Time: 00:27
 */
public class App extends Application {
    private static App instance = null;

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
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Welcome to Flashcard");

        SignInScene scene = getContext().getBean(SignInScene.class);
        setScene(scene);
        getPrimaryStage().sizeToScene();

        primaryStage.show();
    }

    public AnnotationConfigApplicationContext getContext() {
        if (context == null) {
            context = new AnnotationConfigApplicationContext();
//            context.register(
//                    com.flashcard.system.Service.class,
//                    com.flashcard.fx.scene.logged.UserScene.class,
//                    com.flashcard.fx.scene.signin.SignInScene.class,
//                    com.flashcard.fx.scene.signin.pane.SignInPane.class
//            );
            context.scan("com.flashcard");
            context.refresh();
        }
        return context;
    }
}
