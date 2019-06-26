package com.hyd.fx.components;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LazyLoadingTreeItemTest extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(new BorderPane(new TreeView<>(new LazyLoadingTreeItem<>(
            "root", this::loadChildren, "加载中..."
        ))), 300, 300));
        primaryStage.show();
    }

    private List<String> loadChildren(TreeItem<String> parent) {
        String parentValue = parent.getValue();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (parentValue.length() >= 6) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(
                parentValue + "1",
                parentValue + "2",
                parentValue + "3"
            );
        }
    }

}