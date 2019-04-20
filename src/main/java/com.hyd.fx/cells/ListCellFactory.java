package com.hyd.fx.cells;

import java.util.function.Consumer;
import java.util.function.Function;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ListCellFactory<T> implements Callback<ListView<T>, ListCell<T>> {

  private Function<T, String> toStringFunction;

  private Function<T, ObservableValue<String>> toStringProperty;

  private Consumer<ListCell<T>> cellInitializer;

  public ListCellFactory<T> setCellInitializer(Consumer<ListCell<T>> cellInitializer) {
    this.cellInitializer = cellInitializer;
    return this;
  }

  public ListCellFactory<T> setToStringFunction(Function<T, String> toStringFunction) {
    if (this.toStringProperty != null) {
      throw new IllegalStateException("You have already assigned toStringProperty.");
    }
    this.toStringFunction = toStringFunction;
    return this;
  }

  public ListCellFactory<T> setToStringProperty(
      Function<T, ObservableValue<String>> toStringProperty) {
    if (this.toStringFunction != null) {
      throw new IllegalStateException("You have already assigned toStringFunction.");
    }
    this.toStringProperty = toStringProperty;
    return this;
  }

  @Override
  public ListCell<T> call(ListView<T> param) {

    ListCell<T> listCell = new ListCell<T>() {
      @Override
      protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (textProperty().isBound()) {
          return;
        }

        if (empty) {
          setText(null);
          return;
        }

        setCellText(this, item);
      }
    };

    if (cellInitializer != null) {
      cellInitializer.accept(listCell);
    }

    return listCell;
  }

  private void setCellText(ListCell<T> cell, T item) {
    if (toStringFunction != null) {
      cell.setText(toStringFunction.apply(item));
    }
    if (toStringProperty != null) {
      cell.textProperty().unbind();
      cell.textProperty().bind(toStringProperty.apply(item));
    }
    if (toStringFunction == null && toStringProperty == null) {
      cell.setText(String.valueOf(item));
    }
  }
}
