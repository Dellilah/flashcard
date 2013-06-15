package com.flashcard.fx.scene.signin.pane;

import com.flashcard.fx.App;
import com.flashcard.fx.scene.logged.UserScene;
import com.flashcard.fx.scene.logged.pane.UserPane;
import com.flashcard.system.Service;
import com.flashcard.system.Settings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Date: 26/04/2013
 * Time: 10:57
 */
@Component
public class SignInPane extends GridPane {

    private final TextField emailTextField;
    private final PasswordField passwordField;
    private final SignInPane.LoadingBar loadingBar;

    //    @Autowired
    private UserScene userScene;
    private UserPane userPane;

    @Autowired
    private Service service;

    public SignInPane() {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        loadingBar = new LoadingBar();

        Text sceneTitle = new Text("Please sign in");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        Label userName = new Label("Email:");

        emailTextField = new TextField(Settings.getLogin());
        emailTextField.setOnAction(new SignInHandler(loadingBar));
        add(emailTextField, 1, 1);

        Label pw = new Label("Password:");

        passwordField = new PasswordField();
        passwordField.setOnAction(new SignInHandler(loadingBar));
        passwordField.setText(Settings.getPassword());

        Button signInButton = new Button("Continue");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(signInButton);

        signInButton.setOnAction(new SignInHandler(loadingBar));

        add(sceneTitle, 0, 0, 2, 1);
        add(userName, 0, 1);
        add(pw, 0, 2);
        add(passwordField, 1, 2);
        add(hbBtn, 0, 4, 2, 1);
        add(loadingBar, 0, 6, 2, 1);
    }

    private class SignInHandler implements EventHandler<ActionEvent> {
        private final LoadingBar loadingBar;

        public SignInHandler(LoadingBar loadingBar) {
            this.loadingBar = loadingBar;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            loadingBar.loading("Verifying data...");
            new Thread(new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        service.signIn(emailTextField.getText(), passwordField.getText());
                    } catch (NullPointerException e) {
                        System.err.println("NPE");
                        loadingBar.done("Something went terribly wrong...");
                        throw e;
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        loadingBar.done(e.getMessage());
                        throw e;
                    } finally {
                        App.getInstance().getPrimaryStage().sizeToScene();
                    }
                    return null;
                }

                @Override
                protected void succeeded() {
//                    userScene.setRoot(userPane);
                    App.getInstance().setScene(userScene);
//                    App.getInstance().setScene(App.getInstanceContext().getBean(UserScene.class));
                }

            }).start();
        }
    }

    public UserScene getUserScene() {
        return userScene;
    }

    @Autowired
    public void setUserScene(UserScene userScene) {
        this.userScene = userScene;
    }

    public UserPane getUserPane() {
        return userPane;
    }

    @Autowired
    public void setUserPane(UserPane userPane) {
        this.userPane = userPane;
    }

    private class LoadingBar extends HBox {

        private final ProgressIndicator progressIndicator;
        private Text message;

        public LoadingBar() {
            final ProgressBar pb = new ProgressBar();
            pb.setProgress(-1);
            pb.setMaxHeight(1);
            pb.setMaxWidth(Double.MAX_VALUE);

            progressIndicator = new ProgressIndicator();
            progressIndicator.setProgress(-1);
            progressIndicator.setMaxSize(20, 20);

            message = new Text("");
            message.setFill(Color.FIREBRICK);

            setSpacing(5);
        }

        public void go(boolean showSpin, String text) {
            getChildren().clear();
            message.setText(text);
            if (showSpin)
                getChildren().add(progressIndicator);
            getChildren().add(message);
            App.getInstance().getPrimaryStage().sizeToScene();
        }

        public void loading(String text) {
            go(true, text);
        }

        public void done(String text) {
            go(false, text);
        }
    }
}

