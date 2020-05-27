package com.hyd.fx.dialog.form;

import com.hyd.fx.components.IntegerSpinner;
import javafx.scene.layout.GridPane;

/**
 * @author yidin
 */
public class IntegerSpinnerFormField extends FormField {

    private final IntegerSpinner spinner;

    public IntegerSpinnerFormField(String labelName, int min, int max, int init, boolean editable) {
        super(labelName);
        this.spinner = new IntegerSpinner(min, max, init, 1);
        this.spinner.setEditable(editable);
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
