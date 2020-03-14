package com.hyd.fx.dialog;

import com.hyd.fx.app.AppPrimaryStage;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * (description)
 * created at 2018/2/6
 *
 * @author yidin
 */
public class DialogBuilderTest extends Application {

    public String label1DefaultValue = "Hello!";

    public Label label1;

    public Label label2;

    public Label label3;

    public TextField txtUsername;

    public void initialize() {
        this.label1.setText(label1DefaultValue);
    }

    ////////////////////////////////////////////////////////////

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button = new Button("Test DialogBuilder");
        button.setOnAction(actionEvent -> createDialog());

        BorderPane borderPane = new BorderPane(button);
        borderPane.setStyle("-fx-padding: 50");

        AppPrimaryStage.setPrimaryStage(primaryStage);
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.show();
    }

    private void createDialog() {

        ButtonType buttonType = new DialogBuilder()
            .owner(AppPrimaryStage.getPrimaryStage())
            .title("Hello!")
            .body("/components/dialog-body.fxml", this)
            .resizable(true)
            .buttons(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL)
            .onNoButtonClicked(Event::consume)
            .onStageShown(event -> txtUsername.requestFocus())
            .showAndWait()
            .orElse(ButtonType.CANCEL);

        System.out.println(buttonType);
        System.out.println(label1.getText());
    }
}