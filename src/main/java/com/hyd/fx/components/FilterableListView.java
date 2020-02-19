package com.hyd.fx.components;


import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class FilterableListView<T> extends ListView<T> {

    private ObservableList<T> originalItems;

    public void setOriginalItems(List<T> originalItems) {
        this.originalItems = FXCollections.observableArrayList(originalItems);
        this.getItems().setAll(originalItems);
    }

    public void filter(Predicate<T> filter) {
        List<T> filteredItems = originalItems.stream().filter(filter).collect(Collectors.toList());
        this.getItems().setAll(filteredItems);
    }
}
