package com.hyd.fx.builders;

import javafx.scene.control.CheckBox;

import java.util.function.Consumer;

public class CheckBoxBuilder {

    public static CheckBox checkBox(String label, Consumer<Boolean> onChange) {
        CheckBox checkBox = new CheckBox(label);
        if (onChange != null) {
            checkBox.setOnAction(event -> onChange.accept(((CheckBox) event.getSource()).isSelected()));
        }
        return checkBox;
    }
}
