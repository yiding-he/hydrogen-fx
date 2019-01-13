package com.hyd.fx.cells;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListCell;

import java.util.function.Function;

public class ListCellFactory {

    public static <T> ListCell<T> createCell(Function<T, String> toStringFunction) {
        return new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(toStringFunction.apply(item));
                }
            }
        };
    }

    public static <T> ListCell<T> createCellWithProp(
            Function<T, ObservableValue<String>> strPropFactory) {

        return new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    textProperty().unbind();
                } else {
                    textProperty().bind(strPropFactory.apply(item));
                }
            }
        };
    }
}
