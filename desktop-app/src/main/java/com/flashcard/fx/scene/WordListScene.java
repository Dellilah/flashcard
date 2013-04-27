package com.flashcard.fx.scene;

import com.flashcard.fx.component.pane.WordListPane;
import javafx.scene.Scene;

/**
 * Created with IntelliJ IDEA.
 * User: ksiazelobozow
 * Date: 27.04.13
 * Time: 14:04
 */
public class WordListScene extends Scene {

    public WordListScene() {
        super(new WordListPane());
    }
}
