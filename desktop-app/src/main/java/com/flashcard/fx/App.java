package com.flashcard.fx;

import com.flashcard.fx.stage.SignInScene;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        instance = this;
        primaryStage.setTitle("Welcome to Flashcard");
        primaryStage.show();

        setScene(new SignInScene());
    }
}
