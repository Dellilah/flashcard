package com.flashcard.fx.scene.logged.pane;

import com.flashcard.fx.App;
import com.flashcard.fx.component.ImageSelect;
import com.flashcard.fx.scene.logged.UserScene;
import com.flashcard.system.Service;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import org.apache.http.client.fluent.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ksiazelobozow
 * Date: 08.06.13
 * Time: 17:03
 */

@Component
@Lazy
public class AddNewWordPane extends GridPane{
    private final TextField englishWordField = new TextField();
    private final TextField polishWordField = new TextField();
    private final Button addButton = new Button("Add");
    @Autowired
    private Service service;
    private UserScene userScene;
    private String imageURL = "";

    public AddNewWordPane(){
        init();
    }

    public AddNewWordPane(String wordPolish, String wordEnglish){
        init();
        presetWords(wordPolish, wordEnglish);
    }

    public void presetWords(String wordPolish, String wordEnglish) {
        englishWordField.setText(wordEnglish);
        polishWordField.setText(wordPolish);
    }

    public void init(){
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        HBox buttons = new HBox(10);
        add(buttons, 0, 0, 2, 1);

        Text polishTitle = new Text("Word in Polish");
        polishTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        add(polishTitle, 0, 1, 2, 1);

        polishWordField.setMaxWidth(Double.MAX_VALUE);
        add(polishWordField, 0, 2, 2, 2);

        Text englishTitle = new Text("Word in English");
        englishTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        add(englishTitle, 0, 4, 2, 1);

        englishWordField.setMaxWidth(Double.MAX_VALUE);
        add(englishWordField, 0, 5, 2, 2);

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent keyEvent) {
                String englishWord = englishWordField.getText();
                String polishWord = polishWordField.getText();

                try {
                    service.addNewWord(englishWord, polishWord);
                    userScene.setPane(new MessagePane("The word has been added."));
                } catch (Exception e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                polishWordField.setText("");
                englishWordField.setText("");
            }
        });
        buttons.getChildren().add(addButton);
        final ImageSelect imageSelect = new ImageSelect();
        Button button = new Button("Search for image");
        button.setOnAction(new EventHandler<ActionEvent>() {
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

        buttons.getChildren().add(button);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(imageSelect);
        scrollPane.setMaxHeight(310);
        scrollPane.setMaxWidth(Double.MAX_VALUE);
        scrollPane.setPrefWidth(350);
        scrollPane.setPrefHeight(160);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.fitToWidthProperty();
        add(scrollPane, 0, 8, 2, 1);
    }

    public UserScene getUserScene() {
        return userScene;
    }

    @Autowired
    public void setUserScene(UserScene userScene) {
        this.userScene = userScene;
    }
}
