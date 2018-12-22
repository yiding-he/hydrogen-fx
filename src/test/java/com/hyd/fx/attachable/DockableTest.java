package com.hyd.fx.attachable;

import javafx.collections.FXCollections;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author yidin
 */
public class DockableTest extends AttachableTestBase {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Pane pane = createDemoPane(110, 110);
        root.getChildren().add(pane);

        // 使 pane 可改变大小
        Dockable.attachTo(pane)
                .dock(Side.TOP, createComboBox())
                .dock(Side.BOTTOM, new Label("方块可拖动。"));

        Draggable.attachTo(pane);
        Resizable.attachTo(pane);

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    private ComboBox<String> createComboBox() {
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(
                "宋体", "楷体", "黑体"
        ));
        comboBox.getSelectionModel().select(0);
        return comboBox;
    }
}