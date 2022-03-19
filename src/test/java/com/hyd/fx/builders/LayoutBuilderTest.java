package com.hyd.fx.builders;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class LayoutBuilderTest {

    public static void main(String[] args) {
        launch(TestApp.class);
    }

    public static class TestApp extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {
            Parent root = new LayoutBuilder() {{
                vBox(
                    padding = all(10), spacing = 10, alignment = Pos.CENTER,
                    label(text = "Hello"),
                    button(
                        text = "你好", graph = icon("/logo.png", 16, 16),
                        action = () -> System.out.println("Hello")
                    )
                );
            }}.build();

            primaryStage.setScene(new Scene(root, 400, 300));
            primaryStage.show();
        }

    }
}