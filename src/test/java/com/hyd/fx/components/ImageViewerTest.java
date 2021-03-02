package com.hyd.fx.components;

import com.hyd.fx.builders.ComboBoxBuilder;
import com.hyd.fx.builders.ImageBuilder;
import com.hyd.fx.dialog.FileDialog;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static com.hyd.fx.builders.ButtonBuilder.button;
import static com.hyd.fx.builders.LabelBuilder.label;
import static com.hyd.fx.builders.LayoutBuilder.hbox;

public class ImageViewerTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ImageViewer imageViewer = new ImageViewer();
        Label messageLabel = new Label();

        ChangeListener<Number> sizeChanged = (observable, oldValue, newValue) -> {
            Bounds b = imageViewer.getLayoutBounds();
            double s = imageViewer.getImageScale();
            messageLabel.setText(String.format(
                "ImageView size:(%.2f,%.2f), Scale: %.2f, Smooth: %s",
                b.getWidth(), b.getHeight(), s, imageViewer.isSmooth()
            ));
        };

        Runnable openFile = () -> {
            try {
                File file = FileDialog.showOpenFile(primaryStage, "", "*.*", "All Files");
                if (file != null) {
                    imageViewer.setImage(ImageBuilder.imageFromFile(file));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        ComboBox<ImageViewer.ViewMode> comboBox = new ComboBox<>();
        ComboBoxBuilder.of(comboBox)
            .setItems(ImageViewer.ViewMode.values())
            .setInitialValue(viewMode -> viewMode == ImageViewer.ViewMode.Fit)
            .setOnChange(imageViewer::setViewMode);

        BorderPane root = new BorderPane(imageViewer);
        root.setTop(hbox(5, 5, Pos.BASELINE_LEFT,
            button("Open...", openFile),
            label("Mode: "),
            comboBox,
            messageLabel
        ));

        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.widthProperty().addListener(sizeChanged);
        primaryStage.heightProperty().addListener(sizeChanged);
        primaryStage.show();
    }
}