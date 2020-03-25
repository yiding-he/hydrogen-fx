package com.hyd.fx.components;

import com.hyd.fx.utils.Collects;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.scene.control.TreeItem;

/**
 * 懒加载子节点的树。所有的叶子节点一开始都是折叠的，仅当展开时才去获取子节点
 */
public class LazyLoadingTreeItem<T> extends TreeItem<T> {

    /**
     * 展示 loadingText 文本的节点
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static class LoadingTreeItem extends TreeItem {

        public LoadingTreeItem(Object value) {
            super(value);
        }
    }

    private enum LoadingStatus {
        NOT_LOADED, LOADING, LOADED
    }

    //////////////////////////////////////////////////////////////

    private Function<TreeItem<T>, List<T>> childrenSupplier; // 获取子节点的方法

    private String loadingText;  // 加载中文本

    private volatile LoadingStatus loadingStatus = LoadingStatus.NOT_LOADED; // 当前节点是否已经加载过子节点

    private ExecutorService loadingThreadPool = ForkJoinPool.commonPool();

    public LazyLoadingTreeItem(
        T value, Function<TreeItem<T>, List<T>> childrenSupplier, String loadingText
    ) {
        this(value, childrenSupplier, loadingText, false, null);
    }

    /**
     * 构造方法
     *
     * @param value             结点的值对象
     * @param childrenSupplier  获得子节点的方法
     * @param loadingText       加载中文本
     * @param loadChildrenNow   是否初始化时就加载子节点
     * @param loadingThreadPool 执行 childrenSupplier 的线程池，默认为 {@link ForkJoinPool#commonPool()}
     */
    @SuppressWarnings("unchecked")
    private LazyLoadingTreeItem(
        T value, Function<TreeItem<T>, List<T>> childrenSupplier, String loadingText, boolean loadChildrenNow,
        ExecutorService loadingThreadPool
    ) {
        super(value);
        this.childrenSupplier = childrenSupplier;
        this.loadingText = loadingText;
        this.setExpanded(false);

        if (loadingThreadPool != null) {
            this.loadingThreadPool = loadingThreadPool;
        }

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

    public void addChild(T t) {
        getChildren().add(new LazyLoadingTreeItem<>(
            t, childrenSupplier, this.loadingText, false, this.loadingThreadPool
        ));
    }

    private void loadChildren() {
        this.loadingThreadPool.execute(this::loadChildrenAsync);
    }

    private void loadChildrenAsync() {
        List<T> children = childrenSupplier.apply(this);

        if (Collects.isNotEmpty(children)) {
            List<LazyLoadingTreeItem<T>> childrenItems = children
                .stream()
                .map(t -> new LazyLoadingTreeItem<>(
                    t, childrenSupplier, this.loadingText, false, this.loadingThreadPool
                ))
                .collect(Collectors.toList());

            loadingStatus = LoadingStatus.LOADED;
            Platform.runLater(() -> this.getChildren().addAll(childrenItems));
        }

        Platform.runLater(() -> this.getChildren().removeIf(item -> item instanceof LoadingTreeItem));
    }
}
