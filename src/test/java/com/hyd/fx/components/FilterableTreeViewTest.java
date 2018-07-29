package com.hyd.fx.components;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static com.hyd.fx.builders.TreeBuilder.treeItem;
import static org.junit.Assert.*;

/**
 * @author yiding.he
 */
public class FilterableTreeViewTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FilterableTreeView<String> treeView = new FilterableTreeView<>();
        TreeItem<String> root = treeItem("root",
                treeItem("node1",
                        treeItem("node11"),
                        treeItem("node12",
                                treeItem("node121"),
                                treeItem("node122"),
                                treeItem("node123"),
                                treeItem("node124")
                        ),
                        treeItem("node13")
                ),
                treeItem("node2",
                        treeItem("node21"),
                        treeItem("node22")
                ),
                treeItem("node3")
        );
        treeView.setOriginalRoot(root);

        TextField textField = new TextField();
        textField.textProperty().addListener((ob, oldValue, newValue) -> {
            System.out.println("Filtering by " + newValue);
            treeView.filter(s -> s.contains(newValue));
        });

        BorderPane borderPane = new BorderPane(treeView);
        borderPane.setTop(textField);

        primaryStage.setScene(new Scene(borderPane, 400, 400));
        primaryStage.show();
    }
}