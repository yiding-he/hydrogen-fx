package com.hyd.fx.enhancements;

import javafx.scene.control.ListView;

import java.util.function.Consumer;

public interface ListViewEnhancements {

    static <T> void onSelectionChanged(ListView<T> listView, Consumer<T> tConsumer) {
        listView.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> tConsumer.accept(newValue));
    }
}
