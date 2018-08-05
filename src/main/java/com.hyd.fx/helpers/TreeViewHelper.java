package com.hyd.fx.helpers;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author yidin
 */
public class TreeViewHelper {

    public static <T> void iterate(
            TreeView<T> treeView, Function<TreeItem<T>, Boolean> processor) {
        iterate(treeView.getRoot(), processor);
    }

    public static <T> void iterate(
            TreeItem<T> root, Function<TreeItem<T>, Boolean> processor) {

        if (processor.apply(root)) {
            iterate0(root, processor);
        }
    }

    private static <T> void iterate0(
            TreeItem<T> treeItem, Function<TreeItem<T>, Boolean> processor) {

        for (TreeItem<T> child : treeItem.getChildren()) {
            if (processor.apply(child)) {
                iterate0(child, processor);
            }
        }
    }

    ///////////////////////////////////////////////

    public static <T> TreeItem<T> buildSubTree(TreeItem<T> root, Predicate<T> selected) {
        TreeItem<T> filteredRoot = new TreeItem<>(root.getValue());
        filteredRoot.setExpanded(true);

        iterateTreeItem(root, treeItem -> {
            if (treeItem.getChildren().isEmpty() && selected.test(treeItem.getValue())) {
                addToResult(filteredRoot, treeItem);
            }
        });

        if (!filteredRoot.getChildren().isEmpty()) {
            return filteredRoot.getChildren().get(0);
        } else {
            return null;
        }
    }

    private static <T> void iterateTreeItem(TreeItem<T> root, Consumer<TreeItem<T>> consumer) {
        consumer.accept(root);
        for (TreeItem<T> treeItem : root.getChildren()) {
            iterateTreeItem(treeItem, consumer);
        }
    }

    private static <T> void addToResult(TreeItem<T> root, TreeItem<T> item) {
        List<TreeItem<T>> parents = getParentList(item);
        TreeItem<T> latestParent = buildParentsToFilteredRoot(root, parents, 0);
        latestParent.getChildren().add(new TreeItem<>(item.getValue()));
    }

    private static <T> TreeItem<T> buildParentsToFilteredRoot(TreeItem<T> root, List<TreeItem<T>> parents, int pointer) {

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

    private static <T> TreeItem<T> findValue(TreeItem<T> parent, T value) {
        return parent.getChildren().stream()
                .filter(item -> Objects.equals(item.getValue(), value))
                .findFirst().orElse(null);
    }

    private static <T> List<TreeItem<T>> getParentList(TreeItem<T> item) {
        List<TreeItem<T>> parents = new ArrayList<>();
        TreeItem<T> parent = item.getParent();
        while (parent != null) {
            parents.add(parent);
            parent = parent.getParent();
        }
        Collections.reverse(parents);
        return parents;
    }

    ///////////////////////////////////////////////
}
