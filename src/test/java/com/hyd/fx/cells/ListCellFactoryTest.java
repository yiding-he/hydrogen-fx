package com.hyd.fx.cells;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import static com.hyd.fx.builders.MenuBuilder.menuItem;

public class ListCellFactoryTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ListView<String> listView1 = new ListView<>(
            FXCollections.observableArrayList("111", "111", "111", "222", "333", "444", "555", "666"));

        listView1.setCellFactory(new ListCellFactory<String>()
            .setContextMenuBuilder(item -> new ContextMenu(
                menuItem("选择 " + item, null),
                menuItem("选择 " + item, null),
                menuItem("选择 " + item, null)
            ))
        );

        ListView<String> listView2 = new ListView<>(
            FXCollections.observableArrayList("000"));

        Scene scene = new Scene(new SplitPane(
            listView1, listView2
        ), 400, 300);
        scene.getStylesheets().add("test.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}