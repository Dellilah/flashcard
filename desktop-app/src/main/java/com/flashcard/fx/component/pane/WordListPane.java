package com.flashcard.fx.component.pane;

import com.flashcard.dto.WordDTO;
import com.flashcard.fx.App;
import com.flashcard.fx.scene.TranslationScene;
import com.flashcard.fx.scene.UserScene;
import com.flashcard.system.Service;
import com.flashcard.system.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.util.List;
import java.util.Observable;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created with IntelliJ IDEA.
 * User: ksiazelobozow
 * Date: 27.04.13
 * Time: 14:03
 */
public class WordListPane extends GridPane {
    TableView<WordDTO> wordList;

   public WordListPane() {
       setAlignment(Pos.CENTER);
       //setHgap(10);
       //setVgap(10);
       setPadding(new Insets(25, 25, 25, 25));

       Text sceneTitle = new Text("Wordlist");
       sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));


       wordList = new TableView<WordDTO>();

       TableColumn polishWord = new TableColumn("Polish");
       polishWord.setCellValueFactory(new PropertyValueFactory<WordDTO, String>("in_polish"));

       TableColumn englishWord = new TableColumn("English");
       englishWord.setCellValueFactory(new PropertyValueFactory<WordDTO,String>("in_english"));

       TableColumn createDate = new TableColumn("Created");
       createDate.setCellValueFactory(new PropertyValueFactory<WordDTO, String>("created_at"));

       TableColumn updateDate = new TableColumn("Updated");
       createDate.setCellValueFactory((new PropertyValueFactory<WordDTO, String>("updated_at")));

       try {
           List<WordDTO> dataList = Service.wordsIndex();
           ObservableList <WordDTO> tableData = FXCollections.observableArrayList(dataList);

           wordList.setItems(tableData);

       } catch (Exception e) {
           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       }


       wordList.getColumns().addAll(polishWord, englishWord, createDate, updateDate);

       HBox tableBox = new HBox();
       tableBox.setAlignment(Pos.BOTTOM_RIGHT);
       tableBox.getChildren().addAll(sceneTitle, wordList);

       add(sceneTitle, 0, 2, 2, 1);
       add(wordList, 0, 5, 2, 1);

   }

}
