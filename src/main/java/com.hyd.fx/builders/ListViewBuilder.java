package com.hyd.fx.builders;

import com.hyd.fx.cells.ListCellFactory;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;
import java.util.function.Function;

public class ListViewBuilder<T> {

    public static <T> ListViewBuilder<T> of(ListView<T> listView) {
        return new ListViewBuilder<>(listView);
    }

    private ListView<T> listView;

    public ListViewBuilder(ListView<T> listView) {
        this.listView = listView;
    }

    public ListViewBuilder<T> setStringFunction(Function<T, String> strFunction) {
        this.listView.setCellFactory(lv -> ListCellFactory.createCell(strFunction));
        return this;
    }

    public ListViewBuilder<T> setStringProperty(Function<T, ObservableValue<String>> strPropFactory) {
        this.listView.setCellFactory(lv -> ListCellFactory.createCellWithProp(strPropFactory));
        return this;
    }

    public ListViewBuilder<T> setOnItemDoubleClick(Consumer<T> itemDoubleClicked) {
        if (itemDoubleClicked == null) {
            return this;
        }

        this.listView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            T selectedItem = listView.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                return;
            }

            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                itemDoubleClicked.accept(selectedItem);
            }
        });

        return this;
    }
}
