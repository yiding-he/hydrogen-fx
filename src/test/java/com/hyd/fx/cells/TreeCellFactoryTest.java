package com.hyd.fx.cells;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static com.hyd.fx.builders.ImageBuilder.image;
import static com.hyd.fx.builders.TreeBuilder.treeItem;

public class TreeCellFactoryTest extends Application {

    public static void main(String[] args) {
        Application.launch(TreeCellFactoryTest.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(new BorderPane(buildTreeView()), 500, 400));
        primaryStage.show();
    }

    private Node buildTreeView() {
        TreeView<String> treeView = new TreeView<>();
        treeView.setRoot(treeItem("1",
            treeItem("1.1"),
            treeItem("1.2"),
            treeItem("1.3",
                treeItem("1.3.1"),
                treeItem("1.3.2"),
                treeItem("1.3.3")
            ),
            treeItem("1.4"),
            treeItem("1.5")
        ));

        treeView.setCellFactory(new TreeCellFactory<String>()
            .setOnDoubleClick(System.out::println)
            .setIconSupplier(item -> item.getChildren().isEmpty() ? image("/music.png") : image("/folder.png"))
        );

        return treeView;
    }
}