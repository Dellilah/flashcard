package com.flashcard.fx.scene.logged.pane;

import com.flashcard.system.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created with IntelliJ IDEA.
 * User: ksiazelobozow
 * Date: 08.06.13
 * Time: 19:14
 */
public class EditPane extends GridPane {
    private static EditPane instance;
    private final TextField englishWordField;
    private final TextField polishWordField;
    private final Button editButton;

    public EditPane(){setAlignment(Pos.CENTER);
        setMaxWidth(Double.MAX_VALUE);
        setPadding(new Insets(25, 25, 25, 25));
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);

        Text sceneTitle = new Text("Wordlist");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        add(sceneTitle, 0, 0, 2, 1);

        Text polishTitle = new Text("Word in Polish");
        polishTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        add(polishTitle, 0, 1, 2, 1);

        polishWordField = new TextField();
        polishWordField.setMaxWidth(Double.MAX_VALUE);
        add(polishWordField, 0, 2, 2, 2);

        Text englishTitle = new Text("Word in English");
        englishTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        add(englishTitle, 0, 4, 2, 1);

        englishWordField = new TextField();
        englishWordField.setMaxWidth(Double.MAX_VALUE);
        add(englishWordField, 0, 5, 2, 2);

        editButton = new Button("Edit");
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent keyEvent) {
                String englishWord = englishWordField.getText();
                String polishWord = polishWordField.getText();

                try {
                    System.out.println("kliknięto editnięto");
                    Service.editWord(22, englishWord, polishWord);//zamienić 22 na ID zmienianego słowa
                    //Service.addNewWord(englishWord, polishWord);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                polishWordField.setText("");
                englishWordField.setText("");
            }
        });
        add(editButton, 0, 7, 2, 1);
    }

    public static EditPane getInstance() {
        if (instance == null)
            instance = new EditPane();
        return instance;
    }
}
