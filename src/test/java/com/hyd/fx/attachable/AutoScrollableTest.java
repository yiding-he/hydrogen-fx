package com.hyd.fx.attachable;

import com.hyd.fx.builders.ImageBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AutoScrollableTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ImageView imageView = new ImageView(ImageBuilder.image("/sample-bg.png"));
        primaryStage.setScene(new Scene(buildScrollPane(imageView), 400, 200));
        primaryStage.setTitle("Drag LMB to edge");
        primaryStage.show();
    }

    // Add auto scrolling functionality
    private ScrollPane buildScrollPane(ImageView imageView) {
        ScrollPane scrollPane = new ScrollPane(imageView);
        AutoScrollable.attachTo(scrollPane);
        return scrollPane;
    }
}