package com.flashcard.fx.component.pane;

import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * User: ghaxx
 * Date: 27/04/2013
 * Time: 13:33
 */
public class UserPane extends VBox {

    private HBox container;

    public UserPane() {
        init();
    }

    private void init() {
        ToolBar toolBar = new ToolBar();

        Region spacer = new Region();
        spacer.getStyleClass().setAll("spacer");

        HBox ToggleButtonBar = new HBox();
        ToggleGroup group = new ToggleGroup();
        ToggleButton translateButton = new ToggleButton("Translate");
        translateButton.setToggleGroup(group);
        ToggleButton addWordButton = new ToggleButton("Add Word");
        translateButton.setToggleGroup(group);
        ToggleButton wordListButton = new ToggleButton("Word List");
        translateButton.setToggleGroup(group);
        toolBar.getItems().addAll(translateButton, addWordButton, wordListButton);

        getChildren().add(toolBar);
        container = new HBox();
        getChildren().add(container);
        container.getChildren().add(new TranslationPane());
    }

    public void setContent(Parent parent) {
        container.getChildren().removeAll(container.getChildren());

        container.getChildren().add(parent);
    }
}
