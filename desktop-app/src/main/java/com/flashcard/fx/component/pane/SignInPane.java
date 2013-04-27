package com.flashcard.fx.component.pane;

import com.flashcard.fx.App;
import com.flashcard.fx.scene.UserScene;
import com.flashcard.system.Service;
import com.flashcard.system.Settings;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * User: ghaxx
 * Date: 26/04/2013
 * Time: 10:57
 */
public class SignInPane extends GridPane {

    private final TextField emailTextField;
    private final PasswordField passwordField;
    private final Text message;

    public SignInPane() {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        message = new Text();

        Text sceneTitle = new Text("Please sign in");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        Label userName = new Label("Email:");

        emailTextField = new TextField(Settings.getLogin());
        emailTextField.setOnAction(new SignInHandler(message));
        add(emailTextField, 1, 1);

        Label pw = new Label("Password:");

        passwordField = new PasswordField();
        passwordField.setOnAction(new SignInHandler(message));
        passwordField.setText(Settings.getPassword());


        Button signInButton = new Button("Continue");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(signInButton);

        signInButton.setOnAction(new SignInHandler(message));

        add(sceneTitle, 0, 0, 2, 1);
        add(userName, 0, 1);
        add(pw, 0, 2);
        add(passwordField, 1, 2);
        add(message, 1, 6);
        add(hbBtn, 1, 4);
    }

    private class SignInHandler implements EventHandler<ActionEvent> {
        private final Text message;

        public SignInHandler(Text message) {
            this.message = message;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            message.setFill(Color.FIREBRICK);
            message.setText("Verifying data...");

            try {
                if(Service.signIn(emailTextField.getText(), passwordField.getText()))
                    App.getInstance().setScene(new Scene(new WordListPane()));
            } catch (Exception e) {
                e.printStackTrace();
                message.setText(e.getMessage());
                App.getInstance().getPrimaryStage().sizeToScene();
            }
        }
    }
}
