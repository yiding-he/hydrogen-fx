package com.hyd.fx.dialog.form;

import com.hyd.fx.components.IntegerSpinner;
import javafx.scene.layout.GridPane;

/**
 * @author yidin
 */
public class IntegerSpinnerFormField extends FormField<IntegerSpinnerFormField> {

    private final IntegerSpinner spinner = new IntegerSpinner(0, 100, 0, 1);

    public IntegerSpinnerFormField() {
    }

    public IntegerSpinnerFormField min(int min) {
        this.spinner.setMin(min);
        return this;
    }

    public IntegerSpinnerFormField max(int max) {
        this.spinner.setMax(max);
        return this;
    }

    public IntegerSpinnerFormField value(int value) {
        this.spinner.setValue(value);
        return this;
    }

    public IntegerSpinnerFormField editable(boolean editable) {
        this.spinner.setEditable(editable);
        return this;
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
