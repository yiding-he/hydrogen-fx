package com.hyd.fx.components;

import javafx.beans.NamedArg;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * 只接受 double 的 Spinner，增强方面：可以直接赋值，可以获取最小值最大值
 */
public class DoubleSpinner extends Spinner<Double> {

    public DoubleSpinner(
        @NamedArg(value = "min", defaultValue = "0") double min,
        @NamedArg(value = "max", defaultValue = "100") double max,
        @NamedArg(value = "initialValue", defaultValue = "0") double initialValue,
        @NamedArg(value = "amountToStepBy", defaultValue = "1") double amountToStepBy
    ) {
        super(min, max, initialValue, amountToStepBy);

        final TextField editor = getEditor();
        editor.focusedProperty().addListener((_ob, _old, _new) -> {
            if (_old && !_new) {
                SpinnerValueFactory<Double> valueFactory = getValueFactory();
                StringConverter<Double> converter = valueFactory.getConverter();
                try {
                    valueFactory.setValue(
                        Math.min(getMax(), Math.max(getMin(), converter.fromString(editor.getText())))
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
    public void setValue(double value) {
        DoubleSpinnerValueFactory valueFactory = getSpinnerValueFactory();
        if (value < valueFactory.getMin() || value > valueFactory.getMax()) {
            throw new IllegalArgumentException(
                "Value out of bounds(" + valueFactory.getMin() + "~" + valueFactory.getMax() + ")."
            );
        }
        valueFactory.setValue(value);
    }

    public DoubleSpinnerValueFactory getSpinnerValueFactory() {
        return (DoubleSpinnerValueFactory) getValueFactory();
    }

    public double getMin() {
        return getSpinnerValueFactory().getMin();
    }

    public double getMax() {
        return getSpinnerValueFactory().getMax();
    }

}
