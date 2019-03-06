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

  private Consumer<ListCell<T>> onMouseEntered;

  private Consumer<ListCell<T>> onMouseExited;

  public ListCellFactory<T> setOnMouseEntered(Consumer<ListCell<T>> onMouseEntered) {
    this.onMouseEntered = onMouseEntered;
    return this;
  }

  public ListCellFactory<T> setOnMouseExited(Consumer<ListCell<T>> onMouseExited) {
    this.onMouseExited = onMouseExited;
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
    return new ListCell<T>() {
      @Override
      protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
          setText(null);
          return;
        }

        setCellText(item);
        setCellMouseEvents();
      }

      private void setCellMouseEvents() {
        if (onMouseEntered != null) {
          this.setOnMouseEntered(event -> onMouseEntered.accept(this));
        }
        if (onMouseExited != null) {
          this.setOnMouseExited(event -> onMouseExited.accept(this));
        }
      }

      private void setCellText(T item) {
        if (toStringFunction != null) {
          setText(toStringFunction.apply(item));
        }
        if (toStringProperty != null) {
          textProperty().unbind();
          textProperty().bind(toStringProperty.apply(item));
        }
        if (toStringFunction == null && toStringProperty == null) {
          setText(String.valueOf(item));
        }
      }
    };
  }
}
