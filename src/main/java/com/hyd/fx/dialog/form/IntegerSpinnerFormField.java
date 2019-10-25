package com.hyd.fx.dialog.form;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;

/**
 * @author yidin
 */
public class IntegerSpinnerFormField extends FormField {

    private Spinner<Integer> spinner = new Spinner<>();

    public IntegerSpinnerFormField(String labelName, int min, int max) {
        super(labelName);
        ((SpinnerValueFactory.IntegerSpinnerValueFactory) spinner.getValueFactory()).setMin(min);
        ((SpinnerValueFactory.IntegerSpinnerValueFactory) spinner.getValueFactory()).setMax(max);
    }

    public void setEditable(boolean editable) {
        this.spinner.setEditable(editable);
    }

    public int getValue() {
        return this.spinner.getValue();
    }

    public void setValue(int value) {
        this.spinner.getValueFactory().setValue(value);
    }

    @Override
    public void renderTo(GridPane contentPane, int rowIndex) {
        contentPane.add(getLabel(), 0, rowIndex);
        contentPane.add(spinner, 1, rowIndex);
    }
}
