package com.hyd.fx.cells;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;

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
}
