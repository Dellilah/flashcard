package com.flashcard.fx.scene.logged.pane;

import com.flashcard.fx.App;
import com.flashcard.system.Settings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * User: ghaxx
 * Date: 27/04/2013
 * Time: 13:33
 */
public class UserPane extends VBox {

    private HBox container;
    private ToggleButton translateButton;
    private ToggleButton addWordButton;
    private ToggleButton wordListButton;

    public UserPane() {
        init();
    }

    private void init() {
        ToolBar toolBar = new ToolBar();

        translateButton = new ToggleButton("Translate");
        addWordButton = new ToggleButton("Add Word");
        wordListButton = new ToggleButton("Word List");

        ToggleGroup group = new ToggleGroup();
        translateButton.setSelected(true);
        translateButton.getStyleClass().add("button-first");
        translateButton.setToggleGroup(group);
        addWordButton.setToggleGroup(group);
        addWordButton.getStyleClass().add("button");
        wordListButton.setToggleGroup(group);
        wordListButton.getStyleClass().add("button-last");

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) {
                if (new_toggle == translateButton) {
                    UserPane.this.setContent(TranslationPane.getInstance());
                } else if (new_toggle == wordListButton) {
                    UserPane.this.setContent(WordListPane.getInstance());
                } else {
                    UserPane.this.setContent(new HBox());
                }
//                    if (new_toggle == null)
//                        rect.setFill(Color.WHITE);
//                    else
//                        rect.setFill(
//                            (Color) group.getSelectedToggle().getUserData()
//                        );
            }
        });

        Text text = new Text("Hello, " + Settings.getLogin());
        HBox textBox = new HBox();
        textBox.setPadding(new Insets(0, 0, 0, 20));
        HBox.setHgrow(textBox, Priority.ALWAYS);
        textBox.getChildren().add(text);
        textBox.setAlignment(Pos.CENTER_RIGHT);
        textBox.setMaxWidth(Double.MAX_VALUE);

        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(0);
        buttonsBox.getStyleClass().add("button-row");
        buttonsBox.setAlignment(Pos.BASELINE_LEFT);
        buttonsBox.getChildren().addAll(translateButton, addWordButton, wordListButton);
        toolBar.getItems().addAll(buttonsBox, textBox);
        toolBar.setMaxWidth(Double.MAX_VALUE);

        container = new HBox();
        container.setMaxWidth(Double.MAX_VALUE);
        HBox.getHgrow(container);

        getChildren().add(toolBar);
        getChildren().add(container);

        container.getChildren().add(TranslationPane.getInstance());
    }

    public void setContent(Parent content) {
        container.getChildren().removeAll(container.getChildren());
        container.getChildren().add(content);
        HBox.setHgrow(content, Priority.ALWAYS);
        App.getInstance().getPrimaryStage().sizeToScene();
    }
}
