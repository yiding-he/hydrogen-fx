package com.hyd.fx.dialog.form;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * (description)
 * created at 2017/7/12
 *
 * @author yidin
 */
public class TextFormField extends FormField<TextFormField> {

    private final TextField textField = new TextField();

    public TextFormField() {
    }

    public TextFormField text(String text) {
        this.textField.setText(text);
        return this;
    }

    public TextField getTextField() {
        return textField;
    }

    public String getText() {
        return this.textField.getText();
    }

    public void setEditable(boolean editable) {
        this.textField.setEditable(editable);
    }

    @Override
    public void renderTo(GridPane contentPane, int rowIndex) {
        GridPane.setHgrow(this.textField, Priority.ALWAYS);
        contentPane.add(getLabel(), 0, rowIndex);
        contentPane.add(textField, 1, rowIndex);
    }
}
