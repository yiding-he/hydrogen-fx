package com.hyd.fx.components;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

        if (!filteredRoot.getChildren().isEmpty()) {
            this.setRoot(filteredRoot.getChildren().get(0));
        } else {
            this.setRoot(null);
        }
    }

    private void iterateTreeItem(TreeItem<T> root, Consumer<TreeItem<T>> consumer) {
        consumer.accept(root);
        for (TreeItem<T> treeItem : root.getChildren()) {
            iterateTreeItem(treeItem, consumer);
        }
    }

    private void addToResult(TreeItem<T> root, TreeItem<T> item) {
        List<TreeItem<T>> parents = getParentList(item);
        TreeItem<T> latestParent = buildParentsToFilteredRoot(root, parents, 0);
        latestParent.getChildren().add(new TreeItem<>(item.getValue()));
    }

    private TreeItem<T> buildParentsToFilteredRoot(TreeItem<T> root, List<TreeItem<T>> parents, int pointer) {

        T value = parents.get(pointer).getValue();
        TreeItem<T> treeItem = findValue(root, value);
        if (treeItem == null) {
            treeItem = new TreeItem<>(value);
            treeItem.setExpanded(true);
            root.getChildren().add(treeItem);
        }

        if (pointer == parents.size() - 1) {
            return treeItem;
        } else {
            return buildParentsToFilteredRoot(treeItem, parents, pointer + 1);
        }
    }

    private TreeItem<T> findValue(TreeItem<T> parent, T value) {
        return parent.getChildren().stream()
                .filter(item -> Objects.equals(item.getValue(), value))
                .findFirst().orElse(null);
    }

    private List<TreeItem<T>> getParentList(TreeItem<T> item) {
        List<TreeItem<T>> parents = new ArrayList<>();
        TreeItem<T> parent = item.getParent();
        while (parent != null) {
            parents.add(parent);
            parent = parent.getParent();
        }
        Collections.reverse(parents);
        return parents;
    }
}
