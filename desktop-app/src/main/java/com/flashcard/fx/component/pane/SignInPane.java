package com.flashcard.fx.component.pane;

import com.flashcard.fx.App;
import com.flashcard.fx.scene.TranslationScene;
import com.flashcard.system.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    public SignInPane() {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Please sign in");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("Email:");
        add(userName, 0, 1);

        emailTextField = new TextField();
        add(emailTextField, 1, 1);

        Label pw = new Label("Password:");
        add(pw, 0, 2);

        passwordField = new PasswordField();
        add(passwordField, 1, 2);

        Button btn = new Button("Continue");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        add(hbBtn, 1, 4);

        final Text message = new Text();
        add(message, 1, 6);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                message.setFill(Color.FIREBRICK);
                message.setText("Verifying data...");

                try {
                    Service.signIn(emailTextField.getText(), passwordField.getText());
                    App.getInstance().setScene(new TranslationScene());
                } catch (Exception e) {
                    e.printStackTrace();
                    message.setText(e.getMessage());
                    App.getInstance().getPrimaryStage().sizeToScene();
                }
            }
        });
    }
}
