package com.hyd.fx.attachable;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author yidin
 */
public class DraggableTest extends AttachableTestBase {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();
        Pane pane = createDemoPane(110, 110);
        root.getChildren().add(pane);
        Draggable.attachTo(pane);

        ///////////////////////////////////////////////////////////////

        Label label = new Label("Draggable Text");
        root.getChildren().add(label);
        Draggable.attachTo(label);

        ///////////////////////////////////////////////////////////////

        Button button = new Button("Draggable Button");
        button.setLayoutX(150);
        root.getChildren().add(button);
        Draggable.attachTo(button);

        ///////////////////////////////////////////////////////////////

        TextArea textArea = new TextArea("Draggable Text Area");
        textArea.setPrefWidth(300);
        textArea.setPrefHeight(100);
        textArea.setLayoutX(20);
        textArea.setLayoutY(50);
        root.getChildren().add(textArea);
        Draggable.attachTo(textArea);

        ///////////////////////////////////////////////////////////////

        Pane[] squares = new Pane[] {
                square(30, 300, 30, "#AA8855"),
                square(90, 300, 30, "#AA8855"),
                square(150, 300, 30, "#AA8855"),
        };

        root.getChildren().addAll(squares);
        Draggable.attachTo(squares);

        ///////////////////////////////////////////////////////////////

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}