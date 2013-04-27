package com.flashcard.fx.component.pane;

import com.flashcard.dto.WordDTO;
import com.flashcard.fx.App;
import com.flashcard.fx.scene.UserScene;
import com.flashcard.system.Service;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.List;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * Created with IntelliJ IDEA.
 * User: ksiazelobozow
 * Date: 27.04.13
 * Time: 14:03
 */
public class WordListPane extends VBox {
    private static WordListPane instance;
    TableView<WordDTO> wordList;

    public WordListPane() {
        setAlignment(Pos.CENTER);
        //setHgap(10);
        //setVgap(10);
        setMaxWidth(Double.MAX_VALUE);
        setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Wordlist");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));


        wordList = new TableView<WordDTO>();

        TableColumn<WordDTO, Integer> action = new TableColumn<WordDTO, Integer>("Action");
        action.setCellValueFactory(new PropertyValueFactory<WordDTO, Integer>("id"));
        action.setCellFactory(new Callback<TableColumn<WordDTO, Integer>, TableCell<WordDTO, Integer>>() {
            @Override
            public TableCell<WordDTO, Integer> call(TableColumn<WordDTO, Integer> tableColumn) {
                TableCell cell = new TableCell<WordDTO, Integer>() {

                    @Override
                    public void updateItem(final Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            final Button btnPrint = new Button("Remove");
                            btnPrint.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {
                                    try {
                                        Service.deleteWord(item);
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            });
                            setGraphic(btnPrint);
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        }
                    }
                };
                return cell;
            }
        });

        TableColumn polishWord = new TableColumn("Polish");
        polishWord.setCellValueFactory(new PropertyValueFactory<WordDTO, String>("in_polish"));

        TableColumn englishWord = new TableColumn("English");
        englishWord.setCellValueFactory(new PropertyValueFactory<WordDTO, String>("in_english"));

        TableColumn createDate = new TableColumn("Created");
        createDate.setCellValueFactory(new PropertyValueFactory<WordDTO, String>("created_at"));

        TableColumn updateDate = new TableColumn("Updated");
        createDate.setCellValueFactory((new PropertyValueFactory<WordDTO, String>("updated_at")));

        try {
            List<WordDTO> dataList = Service.wordsIndex();
            ObservableList<WordDTO> tableData = FXCollections.observableArrayList(dataList);

            wordList.setItems(tableData);

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        wordList.getColumns().addAll(action, polishWord, englishWord, createDate, updateDate);

        HBox tableBox = new HBox();
        tableBox.setAlignment(Pos.BOTTOM_RIGHT);
        tableBox.getChildren().addAll(sceneTitle, wordList);

        getChildren().add(sceneTitle);
        getChildren().add(wordList);

    }

    public static WordListPane getInstance() {
        if (instance == null)
            instance = new WordListPane();
        return instance;
    }
}
