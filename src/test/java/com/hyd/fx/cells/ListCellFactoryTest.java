package com.hyd.fx.cells;

import static com.hyd.fx.cells.ListCellEnhancements.acceptDrag;
import static com.hyd.fx.cells.ListCellEnhancements.addClassOnMouseHover;
import static com.hyd.fx.cells.ListCellEnhancements.canDrag;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

public class ListCellFactoryTest extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {

    ListView<String> listView1 = new ListView<>(
        FXCollections.observableArrayList("111", "222", "333", "444", "555", "666"));

    listView1.setCellFactory(new ListCellFactory<String>()
        .setToStringFunction(s -> s + ":" + s.hashCode())
        .setCellInitializer(listCell -> {
          addClassOnMouseHover(listCell, "list-cell-hover");
          canDrag(listCell, Cell::getItem, () -> {
            String value = listCell.getItem();
            listCell.getListView().getItems().remove(value);
          });
        })
    );

    ListView<String> listView2 = new ListView<>(
        FXCollections.observableArrayList("000"));

    listView2.setCellFactory(new ListCellFactory<String>()
        .setCellInitializer(listCell -> {
          acceptDrag(listCell, data -> {
            listCell.getListView().getItems().add(data.toString());
          });
        })
    );

    Scene scene = new Scene(new SplitPane(
        listView1, listView2
    ), 400, 300);
    scene.getStylesheets().add("test.css");

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}