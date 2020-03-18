package com.hyd.fx.builders;

import javafx.scene.control.TextField;

public class TextInputBuilder {

    public static TextField textField(String text, double prefWidth) {
        TextField textField = new TextField(text);
        if (prefWidth > 0) {
            textField.setPrefWidth(prefWidth);
        }
        return textField;
    }

}
