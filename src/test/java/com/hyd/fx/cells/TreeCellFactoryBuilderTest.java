package com.hyd.fx.cells;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static com.hyd.fx.builders.ImageBuilder.image;
import static com.hyd.fx.builders.TreeBuilder.treeItem;

public class TreeCellFactoryBuilderTest extends Application {

    public static void main(String[] args) {
        Application.launch(TreeCellFactoryBuilderTest.class, args);
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
                treeItem("1.3"),
                treeItem("1.4"),
                treeItem("1.5")
        ));

        new TreeCellFactoryBuilder<String>()
                .setOnDoubleClick(System.out::println)
                .setIconSupplier(treeItem ->
                        treeItem.getChildren().isEmpty() ? image("/music.png") : image("/folder.png"))
                .setTo(treeView);

        return treeView;
    }
}