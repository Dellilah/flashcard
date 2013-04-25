package com.flashcard.fx.stage;

import com.flashcard.fx.App;
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
 * Time: 01:04
 */
public class TranslationScene extends Scene {

    public TranslationScene() {
        super(new Content());
    }


    public static class Content extends GridPane {
        private Content() {
            setAlignment(Pos.CENTER);
            setHgap(10);
            setVgap(10);
            setPadding(new Insets(25, 25, 25, 25));

            Text scenetitle = new Text("Translate");
            scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            add(scenetitle, 0, 0, 2, 1);

        }
    }
}
