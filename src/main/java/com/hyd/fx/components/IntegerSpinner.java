package com.hyd.fx.components;

import javafx.beans.NamedArg;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * 只接受整数的 Spinner，增强方面：可以直接赋值，可以获取最小值最大值
 */
public class IntegerSpinner extends Spinner<Integer> {

    public IntegerSpinner(
        @NamedArg(value = "min") Integer min,
        @NamedArg(value = "max") Integer max,
        @NamedArg(value = "initialValue", defaultValue = "0") Integer initialValue,
        @NamedArg(value = "amountToStepBy", defaultValue = "1") Integer amountToStepBy
    ) {
        super(
            (min == null ? Integer.MIN_VALUE : min),
            (max == null ? Integer.MAX_VALUE : max),
            initialValue, amountToStepBy
        );

        final TextField editor = getEditor();
        editor.focusedProperty().addListener((_ob, _old, _new) -> {
            if (_old && !_new) {
                SpinnerValueFactory<Integer> valueFactory = getValueFactory();
                StringConverter<Integer> converter = valueFactory.getConverter();
                try {
                    String text = editor.getText();
                    if (text == null || text.trim().length() == 0) {
                        text = String.valueOf(getMin());
                    }
                    valueFactory.setValue(
                        Math.min(getMax(), Math.max(getMin(), converter.fromString(text)))
                    );
                } catch (NumberFormatException e) {
                    // ignore this error
                }
                editor.setText(converter.toString(valueFactory.getValue()));
            }
        });
    }

    /**
     * 赋值，当 value 超出最大最小值范围时抛出异常
     */
    public void setValue(int value) {
        IntegerSpinnerValueFactory valueFactory = getSpinnerValueFactory();
        if (value < valueFactory.getMin() || value > valueFactory.getMax()) {
            throw new IllegalArgumentException(
                "Value out of bounds(" + valueFactory.getMin() + "~" + valueFactory.getMax() + ")."
            );
        }
        valueFactory.setValue(value);
    }

    public void setMax(int max) {
        getSpinnerValueFactory().setMax(max);
    }

    public void setMin(int min) {
        getSpinnerValueFactory().setMin(min);
    }

    public IntegerSpinnerValueFactory getSpinnerValueFactory() {
        return (IntegerSpinnerValueFactory) getValueFactory();
    }

    public int getMin() {
        return getSpinnerValueFactory().getMin();
    }

    public int getMax() {
        return getSpinnerValueFactory().getMax();
    }
}
