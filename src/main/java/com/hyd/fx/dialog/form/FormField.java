package com.hyd.fx.dialog.form;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

/**
 * (description)
 * created at 2017/7/12
 *
 * @author yidin
 */
@SuppressWarnings("unchecked")
public abstract class FormField<T extends FormField<?>> {

    private final Label label;

    private Consumer<T> validation;

    public FormField() {
        this.label = new Label();
        this.label.setMinWidth(USE_PREF_SIZE);
        this.label.setTranslateY(5);
    }

    public T label(String labelText) {
        this.label.setText(labelText);
        return (T) this;
    }

    protected Label getLabel() {
        return label;
    }

    public T validation(Consumer<T> validation) {
        this.validation = validation;
        return (T)this;
    }

    public void validate() {
        if (this.validation != null) {
            this.validation.accept((T) this);
        }
    }

    public abstract void renderTo(GridPane contentPane, int rowIndex);
}
