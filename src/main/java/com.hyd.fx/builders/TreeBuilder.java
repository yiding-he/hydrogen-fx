package com.hyd.fx.builders;

import javafx.scene.control.TreeItem;

/**
 * @author yiding.he
 */
public class TreeBuilder {

    @SafeVarargs
    public static <T> TreeItem<T> treeItem(T value, TreeItem<T>... children) {
        TreeItem<T> treeItem = new TreeItem<>(value);
        treeItem.setExpanded(true);
        treeItem.getChildren().addAll(children);
        return treeItem;
    }
}
