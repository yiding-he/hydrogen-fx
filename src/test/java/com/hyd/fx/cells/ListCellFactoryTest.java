package com.hyd.fx.cells;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import static com.hyd.fx.enhancements.ListCellEnhancements.*;

public class ListCellFactoryTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ListView<String> listView1 = new ListView<>(
            FXCollections.observableArrayList("111", "111", "111", "222", "333", "444", "555", "666"));

        listView1.setCellFactory(new ListCellFactory<String>()
            .setCellInitializer(this::initializeCell)
        );

        ListView<String> listView2 = new ListView<>(
            FXCollections.observableArrayList("000"));

        listView2.setCellFactory(new ListCellFactory<String>()
            .setCellInitializer(this::initializeCell)
        );

        Scene scene = new Scene(new SplitPane(
            listView1, listView2
        ), 400, 300);
        scene.getStylesheets().add("test.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeCell(ListCell<String> listCell) {
        addClassOnDragEnter(listCell, "list-cell-drag-hover");
        canDrag(listCell, Cell::getItem);
        acceptDrag(listCell, data -> {
            listCell.getListView().getItems().add(listCell.getIndex() + 1, String.valueOf(data));
            listCell.getListView().getSelectionModel().select(listCell.getIndex() + 1);
        });
    }
}