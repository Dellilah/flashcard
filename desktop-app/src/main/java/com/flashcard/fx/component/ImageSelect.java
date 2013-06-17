package com.flashcard.fx.component;

import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.apache.http.client.fluent.Request;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 13/06/13
 * Time: 14:38
 */
@Component
@Lazy
@Scope("prototype")
public class ImageSelect extends FlowPane {
    private boolean empty = true;
    private String selected;
    private Text noImagesText;
    private Map<String, ImageView> images = new HashMap<>();

    public ImageSelect() {
        init();
    }

    private void init() {
        noImagesText = new Text("No Images");
        noImagesText.setTextAlignment(TextAlignment.CENTER);
        getChildren().add(noImagesText);
    }

    public void addByURI(final String image) {
        if (empty) {
            getChildren().clear();
            empty = false;
        }
        final HBox box = new HBox();
        box.setPadding(new Insets(5));

        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(-1);
        box.getChildren().add(progressIndicator);

        getChildren().add(box);
        new Thread(new Task<Void>() {

            private ImageView imageView;

            @Override
            protected Void call() throws Exception {
                imageView = new ImageView(new Image(Request.Get(image).execute().returnContent().asStream()));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(80);
                imageView.setFitWidth(240);
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        for (int i = 0; i < getChildren().size(); i++) {
                            getChildren().get(i).getStyleClass().setAll("image-selection-box");
                        }
                        selected = image;
                        box.getStyleClass().setAll("image-selection-box-selected");
                    }
                });
                images.put(image, imageView);
                return null;
            }

            @Override
            protected void succeeded() {
                box.getStyleClass().setAll("image-selection-box");
                box.getChildren().clear();
                box.getChildren().add(imageView);
            }

            @Override
            protected void failed() {
                box.getChildren().clear();
            }
        }).start();
    }

    public String getSelected() {
        return selected;
    }

    public void reset() {
        getChildren().clear();
        getChildren().add(noImagesText);
        empty = true;
    }

    public void setSelected(String selected) {
        this.selected = selected;

        for (int i = 0; i < getChildren().size(); i++) {
            getChildren().get(i).getStyleClass().setAll("image-selection-box");
        }
        if (images.containsKey(selected))
            images.get(selected).getStyleClass().setAll("image-selection-box-selected");
    }
}
