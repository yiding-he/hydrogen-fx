package com.hyd.fx.components;

import com.hyd.fx.builders.ComboBoxBuilder;
import com.hyd.fx.builders.ImageBuilder;
import com.hyd.fx.dialog.FileDialog;
import com.hyd.fx.helpers.DragDropHelper;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.DataFormat;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.hyd.fx.builders.ButtonBuilder.button;
import static com.hyd.fx.builders.CheckBoxBuilder.checkBox;
import static com.hyd.fx.builders.LabelBuilder.label;
import static com.hyd.fx.builders.LayoutBuilder.hbox;
import static com.hyd.fx.builders.LayoutBuilder.vbox;
import static com.hyd.fx.style.FxBackgroundBuilder.background;

public class ImageViewerTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private CheckBox zoomEnabled = new CheckBox("Zoom by wheel");

    @Override
    public void start(Stage primaryStage) throws Exception {
        ImageViewer imageViewer = new ImageViewer();
        imageViewer.setBackground(background("#4499dd"));
        addDropFileSupportTo(imageViewer);
        addZoomSupportTo(imageViewer);

        Label messageLabel = new Label();
        ChangeListener<Number> sizeChanged = (ob, _1, _2) -> onImageViewerSizeChanged(imageViewer, messageLabel);

        BorderPane root = new BorderPane(imageViewer);
        root.setTop(vbox(5, 5,
            hbox(0, 5, Pos.BASELINE_LEFT,
                button("Open...", () -> openFile(primaryStage, imageViewer)),
                label("Mode: "),
                createViewModeComboBox(imageViewer),
                checkBox("Drop Shadow", imageViewer::setImageDropShadow),
                zoomEnabled,
                checkBox("Pannable", imageViewer::setPannable)
            ),
            messageLabel
        ));

        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.widthProperty().addListener(sizeChanged);
        primaryStage.heightProperty().addListener(sizeChanged);
        primaryStage.show();
    }

    private void addZoomSupportTo(ImageViewer imageViewer) {
        imageViewer.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (!zoomEnabled.isSelected()) {
                return;
            }
            if (event.getDeltaY() > 0) {
                imageViewer.zoomIn();
            } else {
                imageViewer.zoomOut();
            }
        });
    }

    //////////////////////////////////////////////////////////////

    private void addDropFileSupportTo(ImageViewer imageViewer) {
        DragDropHelper.of(imageViewer)
            .acceptDrop(DataFormat.FILES)
            .whenDataDropped(DataFormat.FILES, data -> {
                try {
                    List<File> fileList = (List<File>) data;
                    imageViewer.setImage(ImageBuilder.imageFromFile(fileList.iterator().next()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }

    private void onImageViewerSizeChanged(ImageViewer imageViewer, Label messageLabel) {
        Bounds b = imageViewer.getLayoutBounds();
        double s = imageViewer.getImageScale();
        messageLabel.setText(String.format(
            "ImageView size:(%.2f,%.2f), Scale: %.2f, Smooth: %s",
            b.getWidth(), b.getHeight(), s, imageViewer.isSmooth()
        ));
    }

    private ComboBox<ImageViewer.ViewMode> createViewModeComboBox(ImageViewer imageViewer) {
        ComboBox<ImageViewer.ViewMode> comboBox = new ComboBox<>();
        ComboBoxBuilder.of(comboBox)
            .setItems(ImageViewer.ViewMode.values())
            .setInitialValue(viewMode -> viewMode == ImageViewer.ViewMode.Fit)
            .setOnChange(imageViewer::setViewMode);
        return comboBox;
    }

    private void openFile(Stage primaryStage, ImageViewer imageViewer) {
        try {
            File file = FileDialog.showOpenFile(primaryStage, "", "*.*", "All Files");
            if (file != null) {
                imageViewer.setImage(ImageBuilder.imageFromFile(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}