package com.hyd.fx.components;


import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class FilterableListView<T> extends ListView<T> {

    private ObservableList<T> originalItems;

    private Predicate<T> filter;

    public void setOriginalItems(ObservableList<T> originalItems) {
        this.originalItems = originalItems;
        this.getItems().setAll(originalItems);

        this.originalItems.addListener((ListChangeListener<T>) c -> refresh());
    }

    public ObservableList<T> getOriginalItems() {
        return originalItems;
    }

    public void filter(Predicate<T> filter) {
        this.filter = filter;
        refresh();
    }

    @Override
    public void refresh() {
        super.refresh();
        if (filter != null) {
            List<T> filteredItems = originalItems.stream().filter(filter).collect(Collectors.toList());
            this.getItems().setAll(filteredItems);
        } else {
            this.getItems().setAll(originalItems);
        }
    }
}
