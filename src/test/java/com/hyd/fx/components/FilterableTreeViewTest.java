package com.hyd.fx.components;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static org.junit.Assert.*;

/**
 * @author yiding.he
 */
public class FilterableTreeViewTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FilterableTreeView<String> treeView = new FilterableTreeView<>();
        TreeItem<String> root = new TreeItem<>("HAHAHAHAHA");
        root.getChildren().addAll(
                new TreeItem<>("12345"),
                new TreeItem<>("123456"),
                new TreeItem<>("1234567")
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