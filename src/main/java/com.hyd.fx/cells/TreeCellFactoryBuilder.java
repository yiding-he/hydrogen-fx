package com.hyd.fx.cells;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author yiding.he
 */
public class TreeCellFactoryBuilder<T> {

    private Consumer<T> onDoubleClick;

    private Function<T, String> toString;

    public TreeCellFactoryBuilder<T> setOnDoubleClick(Consumer<T> onDoubleClick) {
        this.onDoubleClick = onDoubleClick;
        return this;
    }

    public TreeCellFactoryBuilder<T> setToString(Function<T, String> toString) {
        this.toString = toString;
        return this;
    }

    public void setTo(TreeView<T> treeView) {
        treeView.setCellFactory(tv -> {

            TreeCell<T> treeCell = new TreeCell<T>() {
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
                treeCell.setOnMouseClicked(event -> {
                    if (!treeCell.isEmpty() && event.getClickCount() == 2) {
                        onDoubleClick.accept(treeCell.getItem());
                    }
                });
            }

            return treeCell;
        });
    }
}
