package com.flashcard.fx.component.pane;

import com.flashcard.fx.App;
import com.flashcard.fx.scene.TranslationScene;
import com.flashcard.system.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

/**
 * User: ghaxx
 * Date: 26/04/2013
 * Time: 10:57
 */
public class TranslationPane extends GridPane {

    private final VBox resultsBox;
    private final TextField wordTextField;

    public TranslationPane() {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Translate");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        add(sceneTitle, 0, 0, 2, 1);


        wordTextField = new TextField();
        add(wordTextField, 0, 1, 2, 1);


        Button toEnglishButton = new Button("To English");
//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(toEnglishButton);
        add(toEnglishButton, 0, 2);

        Button toPolishButton = new Button("To Polish");
        add(toPolishButton, 1, 2);

        Text resultsTitle = new Text("Results:");
        resultsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
        add(resultsTitle, 0, 3, 2, 1);

        resultsBox = new VBox(10);
        resultsBox.setPadding(new Insets(5));
        ScrollPane pane = new ScrollPane();
        pane.setContent(resultsBox);
        pane.setMaxHeight(140);
        add(pane, 0, 4, 2, 1);

        toEnglishButton.setOnAction(new Translate(Service.Language.pl));
        toPolishButton.setOnAction(new Translate(Service.Language.en));
    }

    private class Translate implements EventHandler<ActionEvent> {
        private Service.Language from;

        private Translate(Service.Language from) {
            this.from = from;
        }

        @Override
        public void handle(ActionEvent e) {
            resultsBox.getChildren().removeAll(resultsBox.getChildren());
            try {
                List<String> resultList = Service.getTranslation(from, wordTextField.getText());
                for (String s : resultList) {
                    resultsBox.getChildren().add(new Text(s));
                }
            } catch (Exception e1) {

            }
            App.getInstance().getPrimaryStage().sizeToScene();
        }
    }
}
