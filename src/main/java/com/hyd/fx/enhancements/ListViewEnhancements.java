package com.hyd.fx.enhancements;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ListViewEnhancements {

    static <T> void onSelectionChanged(ListView<T> listView, Consumer<T> tConsumer) {
        listView.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> tConsumer.accept(newValue));
    }

    static <T> void setOnKeyReleasedAndHaveSelection(
        ListView<T> listView, BiConsumer<KeyEvent, List<T>> selectedItemsConsumer
    ) {
        listView.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            ObservableList<T> list = listView.getSelectionModel().getSelectedItems();
            if (!list.isEmpty()) {
                selectedItemsConsumer.accept(event, list);
            }
        });
    }
}
