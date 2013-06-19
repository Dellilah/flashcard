package com.flashcard.fx.scene.logged.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: ksiazelobozow
 * Date: 08.06.13
 * Time: 22:50
 */
@Component
@Lazy
public class MessagePane extends GridPane {
    private Text message;

    public MessagePane() {
        init();
    }

    public MessagePane(String mess){
        init();
        setMessage(mess);
    }

    private void init() {
        setAlignment(Pos.CENTER);
        setMaxWidth(Double.MAX_VALUE);
        setPadding(new Insets(25, 25, 25, 25));
        setHgap(10);
        setVgap(10);

        message = new Text();
        message.setFill(Color.RED);
        message.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        add(message, 0, 0, 2, 1);
    }

    private void setMessage(String mess) {
        message.setText(mess);
    }

    public MessagePane update(String message) {
        setMessage(message);
        return this;
    }
}
