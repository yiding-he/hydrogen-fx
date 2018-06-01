package com.hyd.fx.attachable;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author yidin
 */
public class DraggableTest extends AttachableTestBase {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Pane pane = createDemoPane();
        root.getChildren().add(pane);

        // 使 pane 可拖动
        Draggable.attachTo(pane);

        Label label = new Label("Draggable Text");
        root.getChildren().add(label);

        Draggable.attachTo(label);

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}