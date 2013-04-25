package com.flashcard.fx.stage;

import com.flashcard.fx.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
 * Time: 00:51
 */
public class SignInScene extends Scene {

    public SignInScene() {
        super(new Content());
    }


    public static class Content extends GridPane {
        private Content() {
            setAlignment(Pos.CENTER);
            setHgap(10);
            setVgap(10);
            setPadding(new Insets(25, 25, 25, 25));

            Text scenetitle = new Text("Please sign in");
            scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            add(scenetitle, 0, 0, 2, 1);

            Label userName = new Label("Email:");
            add(userName, 0, 1);

            TextField userTextField = new TextField();
            add(userTextField, 1, 1);

            Label pw = new Label("Password:");
            add(pw, 0, 2);

            PasswordField pwBox = new PasswordField();
            add(pwBox, 1, 2);

            Button btn = new Button("Continue");
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
            add(hbBtn, 1, 4);

            final Text actiontarget = new Text();
            add(actiontarget, 1, 6);

            btn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Sign in button pressed");
                    App.getInstance().setScene(new TranslationScene());
                }
            });
        }
    }
}
