package com.hyd.fx.attachable;

import com.hyd.fx.builders.ImageBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class SecondaryMousePannableTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createScrollPane(), 500, 300));
        primaryStage.setTitle("Drag using right mouse button");
        primaryStage.show();
    }

    private ScrollPane createScrollPane() {
        ScrollPane scrollPane = new ScrollPane(ImageBuilder.imageView("/sample-bg.png"));
        SecondaryMousePannable.attachTo(scrollPane);
        return scrollPane;
    }
}