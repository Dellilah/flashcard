package com.flashcard.fx.component;

import com.flashcard.dto.WordDTO;
import com.flashcard.fx.App;
import com.flashcard.fx.scene.logged.UserScene;
import com.flashcard.system.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * Date: 11/06/13
 * Time: 09:09
 */
@Component
@Lazy
public class WordsTable extends TableView<WordDTO> implements Refreshable {
    @Autowired
    private Service service;
    @Autowired
    private UserScene userScene;

    public WordsTable() {

    }

    @PostConstruct
    private void setup() {
        TableColumn<WordDTO, Integer> action = new TableColumn<WordDTO, Integer>("");
        action.setCellValueFactory(new PropertyValueFactory<WordDTO, Integer>("id"));
        action.setPrefWidth(140);
        action.setCellFactory(new Callback<TableColumn<WordDTO, Integer>, TableCell<WordDTO, Integer>>() {
            @Override
            public TableCell<WordDTO, Integer> call(TableColumn<WordDTO, Integer> tableColumn) {
                return new TableCell<WordDTO, Integer>() {

                    @Override
                    public void updateItem(final Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            final Button btnDelete = new Button("Remove");
                            btnDelete.getStyleClass().add("button-first");
                            btnDelete.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {
                                    try {
                                        service.deleteWord(item);
                                        refresh();
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            });
                            final  Button btnEDT = new Button("Edit");
                            btnEDT.getStyleClass().add("button-last");
                            btnEDT.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    userScene.showEditPane(item);
                                    System.out.println("kliknieto");
                                }
                            });
                            setGraphic(btnEDT);
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                            HBox hBox = new HBox();
                            hBox.getStyleClass().add("button-row");
                            hBox.getChildren().addAll(btnDelete, btnEDT);
                            hBox.setMaxHeight(Double.MAX_VALUE);
                            btnEDT.setAlignment(Pos.CENTER);
                            btnDelete.setAlignment(Pos.CENTER);
                            hBox.setAlignment(Pos.CENTER);
                            setGraphic(hBox);
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        }
                    }
                };
            }
        });

        TableColumn<WordDTO, String> image = new TableColumn<>("Image");
        image.setCellValueFactory(new PropertyValueFactory<WordDTO, String>("image_url"));
        image.setCellFactory(new Callback<TableColumn<WordDTO, String>, TableCell<WordDTO, String>>() {
            @Override
            public TableCell<WordDTO, String> call(TableColumn<WordDTO, String> tableColumn) {
                return new TableCell<WordDTO, String>() {

                    @Override
                    public void updateItem(final String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            final ImageView imageView;
                            try {
                                imageView = new ImageView(new Image(Request.Get(item).execute().returnContent().asStream()));
                                imageView.setPreserveRatio(true);
                                imageView.setFitHeight(40);
                                HBox hBox = new HBox();
                                hBox.getChildren().add(imageView);
                                hBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                                hBox.setAlignment(Pos.CENTER);
                                setGraphic(hBox);
                            } catch (IOException ignored) {
                                setGraphic(new Label("No image"));
                            }
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        }
                    }
                };
            }
        });

        TableColumn<WordDTO, String> polishWord = new TableColumn<>("Polish");
        polishWord.setCellValueFactory(new PropertyValueFactory<WordDTO, String>("in_polish"));

        TableColumn<WordDTO, String> englishWord = new TableColumn<>("English");
        englishWord.setCellValueFactory(new PropertyValueFactory<WordDTO, String>("in_english"));

        TableColumn<WordDTO, String> createDate = new TableColumn<>("Created");
        createDate.setCellValueFactory(new PropertyValueFactory<WordDTO, String>("created_at"));

        TableColumn<WordDTO, String> updateDate = new TableColumn<>("Updated");
        updateDate.setCellValueFactory((new PropertyValueFactory<WordDTO, String>("updated_at")));

        getColumns().addAll(action, image, polishWord, englishWord, createDate, updateDate);
    }

    public void refresh() {
        new Thread(new Task<Void>() {
            private ObservableList<WordDTO> tableData;

            @Override
            protected Void call() throws Exception {
                try {
                    List<WordDTO> dataList = service.wordsIndex();
                    tableData = FXCollections.observableArrayList(dataList);
                    setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                } catch (Exception ignored) {
                }
                return null;
            }

            @Override
            protected void succeeded() {
                setItems(tableData);
            }
        }).start();
    }

}
