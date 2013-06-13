package com.flashcard.fx.scene.logged.pane;

import com.flashcard.dto.WordDTO;
import com.flashcard.fx.component.Refreshable;
import com.flashcard.fx.component.WordsTable;
import com.flashcard.system.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.List;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: ksiazelobozow
 * Date: 27.04.13
 * Time: 14:03
 */
@Component
public class WordListPane extends VBox implements Refreshable {
    WordsTable wordList;

    public WordListPane() {
        setAlignment(Pos.CENTER);
        setMaxWidth(Double.MAX_VALUE);
        setPadding(new Insets(25, 25, 25, 25));

        wordList = new WordsTable();
        wordList.setMaxWidth(Double.MAX_VALUE);
        wordList.setMinWidth(850);
        getChildren().add(wordList);
    }

    @Override
    public void refresh() {
        wordList.refresh();
    }
}
