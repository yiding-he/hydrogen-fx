package com.hyd.fx.helpers;

import javafx.scene.control.ComboBox;

import java.util.function.Function;

public class ComboBoxHelper {

    public static <T> void setValue(ComboBox<T> comboBox, Function<T, Boolean> matcher) {
        T selectedItem = comboBox.getItems().stream()
            .filter(matcher::apply).findFirst()
            .orElse(null);

        comboBox.setValue(selectedItem);
    }
}
