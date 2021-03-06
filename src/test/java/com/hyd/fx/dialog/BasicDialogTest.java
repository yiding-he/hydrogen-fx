package com.hyd.fx.dialog;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static com.hyd.fx.builders.ButtonBuilder.button;
import static com.hyd.fx.builders.ImageBuilder.image;

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