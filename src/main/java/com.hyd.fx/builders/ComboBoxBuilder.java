package com.hyd.fx.builders;

import com.hyd.fx.cells.ListCellFactory;
import javafx.scene.control.ComboBox;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ComboBoxBuilder<T> {

    public static <T> ComboBoxBuilder<T> of(ComboBox<T> comboBox) {
        return new ComboBoxBuilder<>(comboBox);
    }

    private ComboBox<T> comboBox;

    public ComboBoxBuilder(ComboBox<T> comboBox) {
        this.comboBox = comboBox;
    }

    public ComboBoxBuilder<T> setItems(List<T> items) {
        this.comboBox.getItems().setAll(items);
        return this;
    }

    public ComboBoxBuilder<T> setValue(Predicate<T> matcher) {
        if (matcher == null) {
            return this;
        }

        this.comboBox.getItems().stream()
                .filter(matcher).findFirst().ifPresent(this.comboBox::setValue);

        return this;
    }

    public ComboBoxBuilder<T> setStrFunction(Function<T, String> strFunction) {
        if (strFunction == null) {
            return this;
        }

        this.comboBox.setButtonCell(ListCellFactory.createCell(strFunction));
        this.comboBox.setCellFactory(lv -> ListCellFactory.createCell(strFunction));
        return this;
    }

    public ComboBoxBuilder<T> setOnChange(Consumer<T> itemChanged) {
        if (itemChanged == null) {
            return this;
        }

        this.comboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> itemChanged.accept(newValue));

        return this;
    }
}
