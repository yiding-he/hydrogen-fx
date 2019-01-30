package com.hyd.fx.helpers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author yidin
 */
public class TableViewHelper {

    public static <S, T> void setColumnValueFactory(
            TableColumn<S, T> tableColumn,
            Function<S, T> function
    ) {
        tableColumn.setCellValueFactory(
                f -> new ReadOnlyObjectWrapper<>(function.apply(f.getValue())));
    }

    public static <T> TableColumn<T, String> createStrColumn(String text, Function<T, String> toString) {
        TableColumn<T, String> tableColumn = new TableColumn<>(text);
        tableColumn.setCellValueFactory(param -> new SimpleStringProperty(toString.apply(param.getValue())));
        return tableColumn;
    }

    public static <T> TableColumn<T, String> createStrPropColumn(
            String text, Function<T, ObservableValue<String>> toStringProperty) {

        TableColumn<T, String> tableColumn = new TableColumn<>(text);
        tableColumn.setCellValueFactory(param -> toStringProperty.apply(param.getValue()));
        return tableColumn;
    }

    public static <T> TableColumn<T, Integer> createIntColumn(String text, Function<T, Integer> toInt) {
        TableColumn<T, Integer> tableColumn = new TableColumn<>(text);
        tableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(toInt.apply(param.getValue())));
        return tableColumn;
    }

    public static <T> TableColumn<T, Integer> createIntPropColumn(
            String text, Function<T, ObservableValue<Integer>> toIntProperty) {

        TableColumn<T, Integer> tableColumn = new TableColumn<>(text);
        tableColumn.setCellValueFactory(cell -> toIntProperty.apply(cell.getValue()));
        return tableColumn;
    }

    public static <T, R> TableColumn<T, R> createPropColumn(
            String text, Function<T, ObservableValue<R>> toValueProperty) {

        TableColumn<T, R> tableColumn = new TableColumn<>(text);
        tableColumn.setCellValueFactory(cell -> toValueProperty.apply(cell.getValue()));
        return tableColumn;
    }

    @SuppressWarnings("unchecked")
    public static <T> TableColumn<T, Boolean> createCheckboxColumn(
            String text, Function<T, Boolean> converter, Consumer<T> onItemClick) {

        TableColumn<T, Boolean> tableColumn = new TableColumn<>(text);
        tableColumn.setCellValueFactory(param -> new SimpleBooleanProperty(converter.apply(param.getValue())));
        tableColumn.setCellFactory(list -> {
            CheckBoxTableCell<T, Boolean> cell = new CheckBoxTableCell<>(null, null);
            if (onItemClick != null) {
                cell.setOnMouseClicked(event -> {
                    onItemClick.accept((T) cell.getTableRow().getItem());
                    if (tableColumn.getTableView() != null) {
                        tableColumn.getTableView().refresh();
                    }
                });
            }
            return cell;
        });
        return tableColumn;
    }

    public static <T, R> TableColumn<T, R> createColumn(String text, Function<T, R> converter) {
        TableColumn<T, R> tableColumn = new TableColumn<>(text);
        tableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(converter.apply(param.getValue())));
        return tableColumn;
    }

    public static <T> TableColumn<T, String> createDateColumn(String text, Function<T, Date> toDate, String datePattern) {
        TableColumn<T, String> tableColumn = new TableColumn<>(text);
        tableColumn.setCellValueFactory(param -> {
            Date date = toDate.apply(param.getValue());
            if (date == null) {
                return new SimpleStringProperty("");
            } else {
                return new SimpleStringProperty(new SimpleDateFormat(datePattern).format(date));
            }
        });
        return tableColumn;
    }

    public static <T> void setColumns(TableView<T> tableView, TableColumn<T, ?>... columns) {
        tableView.getColumns().addAll(columns);
    }

    public static void setColumnWidths(TableView tableView, double... widths) {
        for (int i = 0; i < widths.length; i++) {
            double width = widths[i];
            TableColumn column = (TableColumn) tableView.getColumns().get(i);
            column.setPrefWidth(width);
        }
    }

    public static <T> void setOnItemSelect(TableView<T> tableView, Consumer<T> tConsumer) {
        tableView.getSelectionModel().selectedItemProperty().addListener((_ob, _old, _new) -> {
            if (_new != null) {
                tConsumer.accept(_new);
            }
        });
    }

    public static <T> void setOnItemSelectChange(TableView<T> tableView, BiConsumer<T, T> biTConsumer) {
        tableView.getSelectionModel().selectedItemProperty()
                .addListener((_ob, _old, _new) -> biTConsumer.accept(_old, _new));
    }

    public static <T> void setOnClicked(TableView<T> tableView, int clickCount, Consumer<T> tConsumer) {
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == clickCount) {
                T selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    tConsumer.accept(selectedItem);
                    tableView.refresh();
                }
            }
        });
    }

    public static <T> void setOnTableEmpty(TableView<T> tableView, Runnable action) {
        tableView.getItems().addListener((ListChangeListener<T>) c -> {
            if (tableView.getItems().isEmpty()) {
                action.run();
            }
        });
    }

    public static void setItemRelatedNodes(TableView tableView, Node... nodes) {
        for (Node node : nodes) {
            node.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
        }
    }
}
