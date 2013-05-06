package com.flashcard.fx;

import com.flashcard.fx.scene.signin.SignInScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * User: ghaxx
 * Date: 26/04/2013
 * Time: 00:27
 */
public class App extends Application {
    private static App instance = null;

    private Stage primaryStage = null;

    public static void main(String[] args) {
        launch(args);
    }

    public static App getInstance() {
        if (instance == null)
            throw new NullPointerException("Instance not yet initialized");
        return instance;
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
        primaryStage.show();

        setScene(new SignInScene());
    }
}
