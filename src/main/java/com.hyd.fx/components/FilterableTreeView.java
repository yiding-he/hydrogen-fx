package com.hyd.fx.components;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author yiding.he
 */
public class FilterableTreeView<T> extends TreeView<T> {

    private TreeItem<T> originalRoot;

    public TreeItem<T> getOriginalRoot() {
        return originalRoot;
    }

    public void setOriginalRoot(TreeItem<T> originalRoot) {
        this.originalRoot = originalRoot;
        this.setRoot(originalRoot);
    }

    public void filter(Predicate<T> filter) {
        TreeItem<T> filteredRoot = new TreeItem<>(this.originalRoot.getValue());
        filteredRoot.setExpanded(true);

        iterateTreeItem(this.originalRoot, treeItem -> {
            if (treeItem.getChildren().isEmpty() && filter.test(treeItem.getValue())) {
                addToResult(filteredRoot, treeItem);
            }
        });

        this.setRoot(filteredRoot);
    }

    private void iterateTreeItem(TreeItem<T> root, Consumer<TreeItem<T>> consumer) {
        consumer.accept(root);
        for (TreeItem<T> treeItem : root.getChildren()) {
            iterateTreeItem(treeItem, consumer);
        }
    }

    private void addToResult(TreeItem<T> root, TreeItem<T> item) {
        root.getChildren().add(new TreeItem<>(item.getValue()));
    }
}
