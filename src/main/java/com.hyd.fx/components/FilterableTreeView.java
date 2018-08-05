package com.hyd.fx.components;

import com.hyd.fx.helpers.TreeViewHelper;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

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
        TreeItem<T> filteredRoot = TreeViewHelper.buildSubTree(originalRoot, filter);
        this.setRoot(filteredRoot);
    }
}
