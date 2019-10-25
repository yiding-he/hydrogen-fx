package com.hyd.fx.dialog.form;

import com.hyd.fx.app.AppThread;
import com.hyd.fx.dialog.AlertDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.util.concurrent.ForkJoinPool;

public class ProgressDialog extends Stage {

    private final Button cancelButton = new Button("取消");

    private StringProperty progressMessage = new SimpleStringProperty();

    private ProgressTask progressTask;

    private Label messageLabel = new Label();

    public static abstract class ProgressTask {

        protected boolean canceled = false;

        public abstract void run(StringProperty progressMessage) throws Exception;

        public void cancel() {
            this.canceled = true;
        }

    }

    public ProgressDialog(Window owner, ProgressTask progressTask, String initMessage) {
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(owner);

        this.messageLabel.setText(initMessage);
        this.progressTask = progressTask;
        this.progressMessage.addListener((observable, oldValue, newValue) ->
            AppThread.runUIThread(() -> this.messageLabel.setText(newValue))
        );

        this.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> startProgressTask());

        this.cancelButton.setOnAction(event -> {
            System.out.println("key searching cancelled.");
            this.progressTask.cancel();
            this.close();
        });

        HBox buttons = new HBox(cancelButton);
        buttons.setPadding(new Insets(10, 0, 0, 0));

        BorderPane root = new BorderPane(
            this.messageLabel, null, null, buttons, null
        );

        root.setPadding(new Insets(10));
        root.setBackground(new Background(new BackgroundFill(Color.web("#AAAAAA"), null, null)));
        this.setScene(new Scene(root, 300, 50));
    }

    private void startProgressTask() {
        ForkJoinPool.commonPool().execute(() -> {
            try {
                this.progressTask.run(progressMessage);
                AppThread.runUIThread(() -> {
                    if (this.isShowing()) {
                        this.close();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                AlertDialog.error("错误", e);
            }
        });
    }
}
