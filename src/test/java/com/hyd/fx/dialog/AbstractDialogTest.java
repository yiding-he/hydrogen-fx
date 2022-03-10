package com.hyd.fx.dialog;

import com.hyd.fx.app.AppPrimaryStage;
import com.hyd.fx.window.WindowHelper;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.Collections;

import static com.hyd.fx.builders.ButtonBuilder.button;

public class AbstractDialogTest {

    public static void main(String[] args) {
        Application.launch(AbstractDialogTestApp.class);
    }

    private static void openDialog() {
        Stage primaryStage = AppPrimaryStage.getPrimaryStage();
        CustomDialog customDialog = new CustomDialog();
        customDialog.initOwner(primaryStage);
        customDialog.initModality(Modality.APPLICATION_MODAL);
        customDialog.setOnShown(event -> WindowHelper.relocateDialog(customDialog, primaryStage));
        customDialog.show();
    }

    public static class AbstractDialogTestApp extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {
            AppPrimaryStage.setPrimaryStage(primaryStage);
            primaryStage.setX(300);
            primaryStage.setY(300);
            primaryStage.setScene(new Scene(root(), 400, 300));
            primaryStage.show();
        }

        private Parent root() {
            return new BorderPane(button("Open Dialog", AbstractDialogTest::openDialog));
        }
    }

    public static class CustomDialog extends AbstractDialog {

        @Override
        protected Node getDialogContent() {
            BorderPane borderPane = new BorderPane(new Label("Hello!"));
            borderPane.setPrefWidth(500);
            borderPane.setPrefHeight(500);
            return borderPane;
        }

        @Override
        protected Collection<? extends ButtonType> getButtonTypes() {
            return Collections.singletonList(ButtonType.CLOSE);
        }
    }
}
