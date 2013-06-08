package com.flashcard.fx.scene.logged.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created with IntelliJ IDEA.
 * User: ksiazelobozow
 * Date: 08.06.13
 * Time: 22:50
 */
public class MessagePane extends GridPane {
    private static MessagePane instance;

    public MessagePane(String mess){
        setAlignment(Pos.CENTER);
        setMaxWidth(Double.MAX_VALUE);
        setPadding(new Insets(25, 25, 25, 25));
        setHgap(10);
        setVgap(10);

        Text message = new Text(mess);
        message.setFill(Color.RED);
        message.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        add(message, 0, 0, 2, 1);
    }

    public static MessagePane getInstance(String mess) {
        if (instance == null)
            instance = new MessagePane(mess);
        return instance;
    }

}
