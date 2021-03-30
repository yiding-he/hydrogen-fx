package com.hyd.fx.builders;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LayoutBuilderTest extends Application {

    public static void main(String[] args) {
        launch(LayoutBuilderTest.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = new LayoutBuilder() {{
            vBox(
                padding = 10, spacing = 10, alignment = Pos.CENTER,
                LabelBuilder.label("Hello")
            );
        }}.build();
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }
}