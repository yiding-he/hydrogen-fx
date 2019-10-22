package com.hyd.fx.fxtest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.stage.Stage;

/**
 * @author yiding.he
 */
public class CheckBoxTreeTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(tree(), 400, 300));
        primaryStage.show();
    }

    @SuppressWarnings("unchecked")
    private TreeView<String> tree() {
        TreeView<String> treeView = new TreeView<>();
        // create the tree model
        CheckBoxTreeItem<String> jonathanGiles = new CheckBoxTreeItem<String>("Jonathan");
        CheckBoxTreeItem<String> juliaGiles = new CheckBoxTreeItem<String>("Julia");
        CheckBoxTreeItem<String> mattGiles = new CheckBoxTreeItem<String>("Matt");
        CheckBoxTreeItem<String> sueGiles = new CheckBoxTreeItem<String>("Sue");
        CheckBoxTreeItem<String> ianGiles = new CheckBoxTreeItem<String>("Ian");

        CheckBoxTreeItem<String> gilesFamily = new CheckBoxTreeItem<String>("Giles Family");
        gilesFamily.setExpanded(true);
        gilesFamily.getChildren().addAll(jonathanGiles, juliaGiles, mattGiles, sueGiles, ianGiles);
        treeView.setRoot(gilesFamily);

        // set the cell factory
        treeView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        return treeView;
    }
}
