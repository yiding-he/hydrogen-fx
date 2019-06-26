package com.hyd.fx.components;

import com.hyd.fx.utils.Collects;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.scene.control.TreeItem;

public class LazyLoadingTreeItem<T> extends TreeItem<T> {

    private static class LoadingTreeItem extends TreeItem {

        public LoadingTreeItem(Object value) {
            super(value);
        }
    }

    private enum LoadingStatus {
        NOT_LOADED, LOADING, LOADED
    }

    //////////////////////////////////////////////////////////////

    private Function<TreeItem<T>, List<T>> childrenSupplier;

    private String loadingText;

    private volatile LoadingStatus loadingStatus = LoadingStatus.NOT_LOADED;

    public LazyLoadingTreeItem(
        T value, Function<TreeItem<T>, List<T>> childrenSupplier, String loadingText
    ) {
        this(value, childrenSupplier, loadingText, false);
    }

    private LazyLoadingTreeItem(
        T value, Function<TreeItem<T>, List<T>> childrenSupplier, String loadingText, boolean loadChildrenNow
    ) {
        super(value);
        this.childrenSupplier = childrenSupplier;
        this.loadingText = loadingText;
        this.setExpanded(false);

        if (loadChildrenNow) {
            loadChildren();
        } else {
            getChildren().add(new LoadingTreeItem(loadingText));
        }

        this.expandedProperty().addListener((_ob, _old, _new) -> {
            if (loadingStatus == LoadingStatus.LOADING) {
                setExpanded(true);
            } else if (loadingStatus == LoadingStatus.NOT_LOADED && _new) {
                loadingStatus = LoadingStatus.LOADING;
                loadChildren();
            }
        });
    }

    private void loadChildren() {
        ForkJoinPool.commonPool().execute(this::loadChildrenAsync);
    }

    private void loadChildrenAsync() {
        List<T> children = childrenSupplier.apply(this);

        if (Collects.isNotEmpty(children)) {
            List<LazyLoadingTreeItem<T>> childrenItems = children
                .stream()
                .map(t -> new LazyLoadingTreeItem<>(t, childrenSupplier, this.loadingText, false))
                .collect(Collectors.toList());

            loadingStatus = LoadingStatus.LOADED;
            Platform.runLater(() -> this.getChildren().addAll(childrenItems));
        }

        Platform.runLater(() -> this.getChildren().removeIf(item -> item instanceof LoadingTreeItem));
    }
}
