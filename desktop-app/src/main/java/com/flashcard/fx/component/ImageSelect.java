package com.flashcard.fx.component;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.apache.http.client.fluent.Request;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Date: 13/06/13
 * Time: 14:38
 */
@Component
@Lazy
@Scope("prototype")
public class ImageSelect extends FlowPane {
    private boolean empty = true;
    public ImageSelect() {
        init();
    }

    private void init() {
        Text text = new Text("No Images");
        text.setTextAlignment(TextAlignment.CENTER);
        getChildren().add(text);

    }

    public void addByURI(String image) {
        if (empty) {
            getChildren().clear();
            empty = false;
        }
        final ImageView imageView;
        try {
            imageView = new ImageView(new Image(Request.Get(image).execute().returnContent().asStream()));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(80);
            imageView.setFitWidth(240);
            final HBox box = new HBox();
            box.getStyleClass().setAll("image-selection-box");
            box.setPadding(new Insets(5));
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for (int i = 0; i < getChildren().size(); i++) {
                        getChildren().get(i).getStyleClass().setAll("image-selection-box");
                    }
                    box.getStyleClass().setAll("image-selection-box-selected");
                }
            });
            box.getChildren().add(imageView);
            getChildren().add(box);
        } catch (IOException ignored) {

        }
    }
}
