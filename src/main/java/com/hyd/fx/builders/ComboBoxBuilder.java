package com.hyd.fx.builders;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

@SuppressWarnings("unchecked")
public class ComboBoxBuilder<T> {

  public static <T> ComboBoxBuilder<T> of(ComboBox<T> comboBox) {
    return new ComboBoxBuilder<>(comboBox);
  }

  private ComboBox<T> comboBox;

  private ComboBoxBuilder(ComboBox<T> comboBox) {
    this.comboBox = comboBox;
  }

  public ComboBoxBuilder<T> setCellFactory(Callback<ListView<T>, ListCell<T>> cellFactory) {
    this.comboBox.setCellFactory(cellFactory);
    this.comboBox.setButtonCell(cellFactory.call(null));
    return this;
  }

  public ComboBoxBuilder<T> setButtonCellToStringFunction(Function<T, String> toStringFunction) {
    this.comboBox.setButtonCell(new ComboBoxListCell<>(new StringConverter<T>() {
      @Override
      public String toString(T object) {
        return toStringFunction.apply(object);
      }

      @Override
      public T fromString(String string) {
        return null;
      }
    }));
    return this;
  }

  public ComboBoxBuilder<T> setItems(Collection<T> tCollection) {
    this.comboBox.getItems().setAll(tCollection);
    return this;
  }

  @SafeVarargs
  public final ComboBoxBuilder<T> setItems(T... tCollection) {
    this.comboBox.getItems().setAll(tCollection);
    return this;
  }

  public ComboBoxBuilder<T> setInitialValue(Predicate<T> valueMatcher) {
    this.comboBox.getSelectionModel().select(
        this.comboBox.getItems().stream().filter(valueMatcher).findFirst().orElse(null)
    );
    return this;
  }

  public ComboBoxBuilder<T> setOnChange(Consumer<T> newValueConsumer) {
    this.comboBox.getSelectionModel().selectedItemProperty().addListener(
        (_ob, _old, _new) -> newValueConsumer.accept(_new)
    );
    return this;
  }
}
