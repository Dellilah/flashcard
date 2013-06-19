package com.flashcard.fx.scene.logged.pane;

import com.flashcard.dto.WordDTO;
import com.flashcard.fx.App;
import com.flashcard.fx.component.ImageSelect;
import com.flashcard.fx.scene.logged.UserScene;
import com.flashcard.system.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ksiazelobozow
 * Date: 08.06.13
 * Time: 19:14
 */
@Component
@Lazy
@Scope("prototype")
public class EditPane extends GridPane {
    private final TextField englishWordField;
    private final TextField polishWordField;
    private final Button editButton;
    private Integer wordId;

    @Autowired
    private Service service;
    @Autowired
    private UserScene userScene;
    private WordDTO word;
    @Autowired
    private ImageSelect imageSelect;
    @Autowired
    private Logger logger;

    public EditPane() {
        polishWordField = new TextField();
        englishWordField = new TextField();
        editButton = new Button("Edit");
        wordId = null;
    }

    public EditPane(Integer id_){
        polishWordField = new TextField();
        englishWordField = new TextField();
        editButton = new Button("Edit");

        setup();
        setWordId(id_);
    }

    @PostConstruct
    private void setup() {
        setAlignment(Pos.CENTER);
        setMaxWidth(Double.MAX_VALUE);
        setPadding(new Insets(25, 25, 25, 25));
        setHgap(10);
        setVgap(10);

        Text sceneTitle = new Text("Wordlist");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        Text polishTitle = new Text("Word in Polish");
        polishTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));


        polishWordField.setMaxWidth(Double.MAX_VALUE);

        Text englishTitle = new Text("Word in English");
        englishTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));

        englishWordField.setMaxWidth(Double.MAX_VALUE);

        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent keyEvent) {
                String englishWord = englishWordField.getText();
                String polishWord = polishWordField.getText();

                try {
                    System.out.println("kliknieto editnieto");
                    service.editWord(wordId, englishWord, polishWord, EditPane.this.imageSelect.getSelected());

                    userScene.setMessage("The word has been edited.");
                    App.getInstance().setScene(userScene);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                polishWordField.setText("");
                englishWordField.setText("");
            }
        });

        imageSelect.setMaxSize(300, 300);

        Button searchForImage = new Button("Search for image");
        searchForImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    List<String> words = Arrays.asList(englishWordField.getText(), polishWordField.getText());
                    for (String word : words) {
                        if (word != null && !word.equals("")) {
                            List<String> images = service.getImages(word, 4);
                            for (final String image : images) {
                                imageSelect.addByURI(image);
                            }
                        }
                    }
                    App.getInstance().getPrimaryStage().sizeToScene();
                } catch (Exception e) {
                    e.printStackTrace();  //TODO: implement body of catch statement.
                }
            }
        });

        HBox buttonRow = new HBox(10);
        buttonRow.getChildren().addAll(editButton, searchForImage);

        add(buttonRow, 0, 0, 2, 1);
        add(sceneTitle, 0, 1, 2, 1);
        add(polishTitle, 0, 2, 2, 1);
        add(polishWordField, 0, 3, 2, 1);
        add(englishTitle, 0, 4, 2, 1);
        add(englishWordField, 0, 5, 2, 1);
        add(imageSelect, 0, 6, 2, 1);
        logger.info("Created EditPane");
    }

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(final Integer wordId) {
        this.wordId = wordId;

        try {
            word = service.getWord(this.wordId);
            System.out.println(this.wordId + " " + word.getIn_polish());

            polishWordField.setText(word.getIn_polish());
            englishWordField.setText(word.getIn_english());
            imageSelect.reset();
            if (!word.getImage_url().isEmpty()) {
                imageSelect.addByURI(word.getImage_url());
                imageSelect.setSelected(word.getImage_url());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
