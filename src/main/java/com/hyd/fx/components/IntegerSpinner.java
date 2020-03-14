package com.hyd.fx.components;

import javafx.beans.NamedArg;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.util.StringConverter;

/**
 * 只接受整数的 Spinner，增强方面：可以直接赋值，可以获取最小值最大值
 */
public class IntegerSpinner extends Spinner<Integer> {

    public IntegerSpinner(
        @NamedArg(value = "min", defaultValue = "0") int min,
        @NamedArg(value = "max", defaultValue = "100") int max,
        @NamedArg(value = "initialValue", defaultValue = "0") int initialValue,
        @NamedArg(value = "amountToStepBy", defaultValue = "1") int amountToStepBy
    ) {
        super(min, max, initialValue, amountToStepBy);

        // 允许用户在输入的过程中即时改变控件的值，同时不允许用户输入非法字符或超出范围的值
        getEditor().textProperty().addListener((_ob, _old, _new) -> {
            SpinnerValueFactory<Integer> valueFactory = getValueFactory();
            StringConverter<Integer> converter = valueFactory.getConverter();
            try {
                valueFactory.setValue(Math.min(getMax(), Math.max(getMin(), converter.fromString(_new))));
            } catch (NumberFormatException e) {
                // ignore this error
            }
            getEditor().setText(converter.toString(valueFactory.getValue()));
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
