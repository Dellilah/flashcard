package com.flashcard.fx.scene.logged.pane;

import com.flashcard.fx.App;
import com.flashcard.fx.scene.signin.SignInScene;
import com.flashcard.system.Settings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * User: ghaxx
 * Date: 27/04/2013
 * Time: 13:33
 */
@Component
@Lazy
public class UserPane extends VBox {
    private static UserPane instance;
    private HBox container;
    private ToggleButton translateButton;
    private ToggleButton addWordButton;
    private ToggleButton wordListButton;
    private SignInScene signInScene;
    private TranslationPane translationPane;
    private AddNewWordPane addNewWordPane;
    private WordListPane wordListPane;
    private EditPane editPane;
    //    private ToggleButton editWordButton;

    public UserPane() {
        init();
    }

    public UserPane(Integer id){
        init();
        setEditPane(id);
    }

    public UserPane(Pane pane) {
        init();
        setContent(pane);
    }

    public UserPane(String message){
        init();
        setMessagePane(message);
    }

    private void init() {
        ToolBar toolBar = new ToolBar();

        translateButton = new ToggleButton("Translate");
        addWordButton = new ToggleButton("Add Word");
        wordListButton = new ToggleButton("Word List");
//        editWordButton = new ToggleButton("Edit");

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
                    UserPane.this.setContent(translationPane);
                } else if (new_toggle == wordListButton) {
                    wordListPane.refresh();
                    UserPane.this.setContent(wordListPane);
                } else /*if (new_toggle == addWordButton)*/ {
                    UserPane.this.setContent(addNewWordPane);
                }
//                    if (new_toggle == null)
//                        rect.setFill(Color.WHITE);
//                    else
//                        rect.setFill(
//                            (Color) group.getSelectedToggle().getUserData()
//                        );
            }
        });
        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu menuUser = new Menu("Hello, " + Settings.getLogin());
        menuUser.getStyleClass().add("plain-menu");
        MenuItem add = new MenuItem("Log out");
        add.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                App.getInstance().setScene(signInScene);
            }
        });
        menuUser.getItems().addAll(add);
        menuBar.getMenus().addAll(menuUser);
        menuBar.getStyleClass().add("plain-menuBar");
        Text text = new Text("Hello, " + Settings.getLogin());
        HBox textBox = new HBox();
        textBox.setPadding(new Insets(0, 0, 0, 20));
        HBox.setHgrow(textBox, Priority.ALWAYS);
        textBox.getChildren().add(menuBar);
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

        container.getChildren().add(App.getInstanceContext().getBean(TranslationPane.class));
    }

    public void setEditPane(Integer id){
        translateButton.setSelected(false);
        addWordButton.setSelected(false);
        wordListButton.setSelected(false);

        container.getChildren().clear();
        container.getChildren().add(editPane);
        editPane.setWordId(id);
        App.getInstance().getPrimaryStage().sizeToScene();
    }

    public void setMessagePane(String message){
        translateButton.setSelected(false);
        addWordButton.setSelected(false);
        wordListButton.setSelected(false);

        container.getChildren().clear();
        container.getChildren().add(MessagePane.getInstance(message));
    }

    public void setContent(Parent content) {
        container.getChildren().clear();
        container.getChildren().add(content);
        HBox.setHgrow(content, Priority.ALWAYS);
        App.getInstance().getPrimaryStage().sizeToScene();
    }

    public SignInScene getSignInScene() {
        return signInScene;
    }

    @Autowired
    public void setSignInScene(SignInScene signInScene) {
        this.signInScene = signInScene;
    }

    public TranslationPane getTranslationPane() {
        return translationPane;
    }

    @Autowired
    public void setTranslationPane(TranslationPane translationPane) {
        this.translationPane = translationPane;
    }

    public AddNewWordPane getAddNewWordPane() {
        return addNewWordPane;
    }

    @Autowired
    public void setAddNewWordPane(AddNewWordPane addNewWordPane) {
        this.addNewWordPane = addNewWordPane;
    }

    public WordListPane getWordListPane() {
        return wordListPane;
    }

    @Autowired
    public void setWordListPane(WordListPane wordListPane) {
        this.wordListPane = wordListPane;
    }

    public EditPane getEditPane() {
        return editPane;
    }

    @Autowired
    public void setEditPane(EditPane editPane) {
        this.editPane = editPane;
    }
}
