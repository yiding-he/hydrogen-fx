package com.hyd.fx.dialog;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.hyd.fx.FxUI.button;
import static com.hyd.fx.FxUI.image;

/**
 * @author yiding.he
 */
public class BasicDialogTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(new BorderPane(
                button("打开对话框", () -> new MyDialog(primaryStage).show())
        ), 400, 300));
        primaryStage.show();
    }

    ////////////////////////////////////////////////////////////////////////////////

    private static class MyDialog extends BasicDialog {

        public MyDialog(Stage owner) {
            new DialogBuilder()
                    .owner(owner)
                    .logo(image("/logo.png"))
                    .body("/components/dialog-body.fxml")
                    .buttons(ButtonType.NEXT)
                    .applyTo(this);
        }
    }
}