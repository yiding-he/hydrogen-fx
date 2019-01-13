package com.hyd.fx.attachable;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author yidin
 */
public class CustomizableTextAreaTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextArea textArea = createNormalTextArea();
        Pane root = new Pane(textArea);

        Resizable.attachTo(textArea);

        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }

    private TextArea createNormalTextArea() {
        TextArea textArea = new TextArea();
        textArea.setLayoutX(100);
        textArea.setLayoutY(100);
        textArea.setPrefWidth(300);
        textArea.setPrefHeight(100);
        return textArea;
    }
}
