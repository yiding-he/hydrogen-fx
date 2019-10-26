package com.hyd.fx.dialog.form;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

public class CheckBoxFormField extends FormField {

    private final CheckBox checkBox;

    public CheckBoxFormField(String labelName) {
        super("");
        this.checkBox = new CheckBox(labelName);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public boolean isSelected() {
        return checkBox.isSelected();
    }

    public void setSelected(boolean selected) {
        this.checkBox.setSelected(selected);
    }

    @Override
    public void renderTo(GridPane contentPane, int rowIndex) {
        contentPane.add(checkBox, 1, rowIndex);
    }
}
