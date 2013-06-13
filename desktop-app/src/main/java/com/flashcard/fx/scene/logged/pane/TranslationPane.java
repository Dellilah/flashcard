package com.flashcard.fx.scene.logged.pane;

import com.flashcard.dto.WordDTO;
import com.flashcard.fx.App;
import com.flashcard.fx.component.Refreshable;
import com.flashcard.fx.scene.logged.UserScene;
import com.flashcard.system.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Date: 26/04/2013
 * Time: 10:57
 */
@Component
public class TranslationPane extends GridPane implements Refreshable {
    private final VBox resultsBox;
    private final TextField wordTextField;

    private Service service = Service.getInstance();

    private TranslationPane() {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(this, Priority.ALWAYS);
        setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Translate");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        add(sceneTitle, 0, 0, 2, 1);


        wordTextField = new TextField();
        wordTextField.setMaxWidth(Double.MAX_VALUE);
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

        wordTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                resultsBox.getChildren().removeAll(resultsBox.getChildren());
            }
        });
    }

    @Override
    public void refresh() {

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
                final String wordToBeTranslated = wordTextField.getText();
                List<String> resultList = service.getTranslation(from, wordToBeTranslated);
                for (String s : resultList) {
                    final String ss = s;
                    Text text = new Text(s);
                    HBox box = new HBox(10);
                    Button addButton = new Button("+");
                    addButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent keyEvent) {
                            String englishWord = "";
                            String polishWord = "";
                            if (from == Service.Language.en) {
                                polishWord = ss;
                                englishWord = wordToBeTranslated;
                            } else {
                                englishWord = ss;
                                polishWord = wordToBeTranslated;
                            }

                            try {
                                //service.addNewWord(englishWord, polishWord);
                                App.getInstance().setScene(new UserScene(new AddNewWordPane(polishWord, englishWord)));
                            } catch (Exception e1) {
                                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }
                        }
                    });
                    box.getChildren().add(addButton);
                    box.getChildren().add(text);
                    resultsBox.getChildren().add(box);
                }
            } catch (Exception ignored) {

            }
            App.getInstance().getPrimaryStage().sizeToScene();
        }
    }
}
