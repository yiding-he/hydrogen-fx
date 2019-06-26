package com.hyd.fx.dialog;

import com.hyd.fx.builders.ButtonBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertDialogTest extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(new VBox(
            ButtonBuilder.button("单行输入框", () -> AlertDialog.input("input", "请输入这个那个", false)),
            ButtonBuilder.button("多行输入框", () -> AlertDialog.input("input", "请输入这个那个", true))
        ), 300, 300));

        primaryStage.show();
    }
}