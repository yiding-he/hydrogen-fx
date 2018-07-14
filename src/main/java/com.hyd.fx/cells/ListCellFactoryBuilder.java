package com.hyd.fx.cells;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.function.Function;

/**
 * @author yidin
 */
public class ListCellFactoryBuilder<T> {

    private Runnable onDoubleClick;

    private Function<T, String> toString;

    public ListCellFactoryBuilder<T> setOnDoubleClick(Runnable onDoubleClick) {
        this.onDoubleClick = onDoubleClick;
        return this;
    }

    public ListCellFactoryBuilder<T> setToString(Function<T, String> toString) {
        this.toString = toString;
        return this;
    }

    public void setTo(ListView<T> listView) {
        listView.setCellFactory(lv -> {

            ListCell<T> listCell = new ListCell<T>() {
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

            if (onDoubleClick != null) {
                listCell.setOnMouseClicked(event -> {
                    if (!listCell.isEmpty() && event.getClickCount() == 2) {
                        onDoubleClick.run();
                    }
                });
            }

            return listCell;
        });
    }
}
