package com.hyd.fx.components;


import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;

/**
 * 可过滤内容的 ListView。注意，{@link #getItems()} 的返回值将不可修改元素，
 * 如果要修改，使用 {@link #getSource()} 方法来获得原始的列表对象。
 */
public class FilterableListView<T> extends ListView<T> {

    private FilteredList<T> filteredList;

    public FilterableListView() {
        replaceCurrentItems();
    }

    public FilterableListView(ObservableList<T> items) {
        super(items);
        replaceCurrentItems();
    }

    public void filter(Predicate<T> filter) {
        if (!(this.getItems() instanceof FilteredList<?>)) {
            replaceCurrentItems();
        }
        this.filteredList.setPredicate(filter);
    }

    private void replaceCurrentItems() {
        this.filteredList = new FilteredList<>(this.getItems());
        this.setItems(filteredList);
    }

    @SuppressWarnings("unchecked")
    public ObservableList<T> getSource() {
        return (ObservableList<T>) this.filteredList.getSource();
    }
}
