package com.hyd.fx.builders;


import com.hyd.fx.helpers.TableViewHelper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Date;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 一次性完成对 TableView 的所有配置
 *
 * @author yidin
 */
public class TableViewBuilder<T> {

    public static <T> TableViewBuilder<T> of(TableView<T> tableView) {
        return new TableViewBuilder<>(tableView);
    }

    private TableView<T> tableView;

    private TableViewBuilder(TableView<T> tableView) {
        this.tableView = tableView;
    }

    public TableViewBuilder<T> addStrColumn(String text, Function<T, String> toString) {
        this.tableView.getColumns().add(TableViewHelper.createStrColumn(text, toString));
        return this;
    }

    public TableViewBuilder<T> addNodeColumn(String text, Function<T, Node> toNode) {
        TableColumn<T, T> column = new TableColumn<>(text);
        column.setCellFactory(col -> new TableCell<T, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);

                if (!empty) {
                    setGraphic(toNode.apply(item));
                }
            }
        });
        this.tableView.getColumns().add(column);
        return this;
    }

    public TableViewBuilder<T> addIntColumn(String text, Function<T, Integer> toInt) {
        this.tableView.getColumns().add(TableViewHelper.createIntColumn(text, toInt));
        return this;
    }

    public TableViewBuilder<T> addDateColumn(String text, Function<T, Date> toDate, String datePattern) {
        this.tableView.getColumns().add(TableViewHelper.createDateColumn(text, toDate, datePattern));
        return this;
    }

    public <R> TableViewBuilder<T> addPropColumn(String text, Function<T, ObservableValue<R>> func) {
        this.tableView.getColumns().add(TableViewHelper.createPropColumn(text, func));
        return this;
    }

    public TableViewBuilder<T> addCheckboxColumn(
            String text, Function<T, Boolean> toBoolean, Consumer<T> onItemClick) {

        this.tableView.getColumns().add(TableViewHelper.createCheckboxColumn(text, toBoolean, onItemClick));
        return this;
    }

    public TableViewBuilder<T> setColumnWidths(double... widths) {
        TableViewHelper.setColumnWidths(this.tableView, widths);
        return this;
    }

    public TableViewBuilder<T> setOnItemSelect(Consumer<T> itemConsumer) {
        TableViewHelper.setOnItemSelect(tableView, itemConsumer);
        return this;
    }

    public TableViewBuilder<T> setOnItemChange(BiConsumer<T, T> itemConsumer) {
        TableViewHelper.setOnItemSelectChange(tableView, itemConsumer);
        return this;
    }

    public TableViewBuilder<T> setOnItemDoubleClick(Consumer<T> func) {
        TableViewHelper.setOnClicked(this.tableView, 2, func);
        return this;
    }

    public TableViewBuilder<T> setOnItemClick(Consumer<T> func) {
        TableViewHelper.setOnClicked(this.tableView, 1, func);
        return this;
    }

    public TableViewBuilder<T> setItemRelatedNodes(Node... nodes) {
        TableViewHelper.setItemRelatedNodes(this.tableView, nodes);
        return this;
    }

    public TableViewBuilder<T> setOnTableEmpty(Runnable action) {
        TableViewHelper.setOnTableEmpty(this.tableView, action);
        return this;
    }
}
