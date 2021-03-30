package com.hyd.fx.cells;

import com.hyd.fx.builders.ImageBuilder;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.util.Callback;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author yiding.he
 */
public class TreeCellFactory<T> implements Callback<TreeView<T>, TreeCell<T>> {

    private Consumer<T> onDoubleClick;

    private Function<T, String> toString;

    private Function<TreeItem<T>, Image> iconSupplier;

    public TreeCellFactory<T> setOnDoubleClick(Consumer<T> onDoubleClick) {
        this.onDoubleClick = onDoubleClick;
        return this;
    }

    public TreeCellFactory<T> setToString(Function<T, String> toString) {
        this.toString = toString;
        return this;
    }

    public TreeCellFactory<T> setIconSupplier(Function<TreeItem<T>, Image> iconSupplier) {
        this.iconSupplier = iconSupplier;
        return this;
    }

    @Override
    public TreeCell<T> call(TreeView<T> param) {
        TreeCell<T> treeCell = new TreeCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setCellText(item);
                    setCellIcon(this.getTreeItem());
                }
            }

            private void setCellIcon(TreeItem<T> treeItem) {
                if (iconSupplier != null) {
                    Image image = iconSupplier.apply(treeItem);
                    setGraphic(ImageBuilder.imageView(image, 16));
                }
            }

            private void setCellText(T item) {
                setText(toString != null? toString.apply(item): String.valueOf(item));
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
    }
}
