package com.hyd.fx.dialog.form;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

public class CheckBoxFormField extends FormField<CheckBoxFormField> {

    private final CheckBox checkBox;

    public CheckBoxFormField() {
        this.checkBox = new CheckBox();
    }

    @Override
    public CheckBoxFormField label(String labelText) {
        this.checkBox.setText(labelText);
        return this;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public boolean isSelected() {
        return checkBox.isSelected();
    }

    public CheckBoxFormField selected(boolean selected) {
        this.checkBox.setSelected(selected);
        return this;
    }

    @Override
    public void renderTo(GridPane contentPane, int rowIndex) {
        contentPane.add(checkBox, 1, rowIndex);
    }
}
