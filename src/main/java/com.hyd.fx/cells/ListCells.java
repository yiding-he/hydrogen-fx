package com.hyd.fx.cells;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.function.Function;

/**
 * (description)
 * created at 2018/2/11
 *
 * @author yidin
 */
public class ListCells {

    public static <T> Callback<ListView<T>, ListCell<T>> cellFactory(Function<T, String> toString) {
        return lv -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                } else {
                    setText(toString.apply(item));
                }
            }
        };
    }
}
