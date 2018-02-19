package com.hyd.fx.cells;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.function.Function;

/**
 * @author yidin
 */
public class TreeViewHelper {

    public static <T> void iterate(
            TreeView<T> treeView, Function<TreeItem<T>, Boolean> processor) {

        if (processor.apply(treeView.getRoot())) {
            iterate(treeView.getRoot(), processor);
        }
    }

    private static <T> void iterate(
            TreeItem<T> treeItem, Function<TreeItem<T>, Boolean> processor) {

        for (TreeItem<T> child : treeItem.getChildren()) {
            if (processor.apply(child)) {
                iterate(child, processor);
            }
        }
    }
}
