package com.hyd.fx.attachable;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author yidin
 */
public class ResizableTest extends AttachableTestBase {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Pane pane = createDemoPane(110, 110);
        root.getChildren().add(pane);

        // 使 pane 可改变大小
        Resizable.attachTo(pane);

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

}