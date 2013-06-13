package com.flashcard.fx.scene;

import com.flashcard.fx.scene.logged.pane.WordListPane;
import javafx.scene.Scene;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: ksiazelobozow
 * Date: 27.04.13
 * Time: 14:04
 */
@Component
@Scope("prototype")
public class WordListScene extends Scene {

    public WordListScene() {
        super(new WordListPane());
    }
}
