package com.hyd.fx.cells;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.stage.Stage;

public class ListCellFactoryTest extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {

    ListView<String> listView = new ListView<>(
        FXCollections.observableArrayList("111", "222", "333", "444", "555", "666"));

    listView.setCellFactory(
        new ListCellFactory<String>()
            .setToStringFunction(s -> s + ":" + s.hashCode())
            .setOnMouseEntered(listCell -> {
              MultipleSelectionModel<String> sm = listCell.getListView().getSelectionModel();
              if (sm.getSelectedItem() == null || !sm.getSelectedItem().equals(listCell.getItem())) {
                listCell.getStyleClass().add("list-cell-hover");
              }
            })
            .setOnMouseExited(listCell -> listCell.getStyleClass().remove("list-cell-hover"))
    );

    Scene scene = new Scene(listView, 400, 300);
    scene.getStylesheets().add("test.css");

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}