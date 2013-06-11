package com.flashcard.fx.component;

import com.flashcard.dto.WordDTO;
import com.flashcard.fx.App;
import com.flashcard.fx.scene.WordListScene;
import com.flashcard.fx.scene.logged.UserScene;
import com.flashcard.fx.scene.logged.pane.WordListPane;
import com.flashcard.system.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.List;

/**
 * Date: 11/06/13
 * Time: 09:09
 */
public class WordsTable extends TableView<WordDTO> {
    private Service service = Service.getInstance();

    public WordsTable() {
        TableColumn<WordDTO, Integer> action = new TableColumn<WordDTO, Integer>("");
        action.setCellValueFactory(new PropertyValueFactory<WordDTO, Integer>("id"));
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
                                    App.getInstance().setScene(new UserScene(item));
                                    System.out.println("kliknieto");
                                }
                            });
                            setGraphic(btnEDT);
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                            HBox hBox = new HBox();
                            hBox.getStyleClass().add("button-row");
                            hBox.getChildren().addAll(btnDelete, btnEDT);
                            setGraphic(hBox);
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        }
                    }
                };
            }
        });

        TableColumn<WordDTO, String> image = new TableColumn<>("Image");
        image.setCellValueFactory(new PropertyValueFactory<WordDTO, String>("imageAddress"));
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
                                setGraphic(imageView);
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

        TableColumn<WordDTO, Integer> editWordButton = new TableColumn<WordDTO, Integer>("Edit");
        editWordButton.setCellValueFactory(new PropertyValueFactory<WordDTO, Integer>("id"));

        editWordButton.setCellFactory(new Callback<TableColumn<WordDTO, Integer>, TableCell<WordDTO, Integer>>() {
            @Override
            public TableCell<WordDTO, Integer> call(TableColumn<WordDTO, Integer> wordDTOIntegerTableColumn) {
                TableCell cell = new TableCell<WordDTO, Integer>(){

                    @Override
                    public void updateItem(final Integer item, boolean empty){
                        super.updateItem(item, empty);

                        //System.out.println(item.toString());
                        if(!empty){
                            final  Button btnEDT = new Button("Edit");
                            btnEDT.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    App.getInstance().setScene(new UserScene(item));
                                    System.out.println("kliknieto");
                                }
                            });
                            setGraphic(btnEDT);
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        }
                    }
                };
                return cell;
            }
        });

        getColumns().addAll(action, image, polishWord, englishWord, createDate, updateDate);

        refresh();


    }

    private void refresh() {
        try {
            List<WordDTO> dataList = service.wordsIndex();
            ObservableList<WordDTO> tableData = FXCollections.observableArrayList(dataList);

            setItems(tableData);
        } catch (Exception ignored) {
        }
    }
}
