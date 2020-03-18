package com.hyd.fx.builders;

import javafx.scene.control.Label;

public class LabelBuilder {

    public static Label label(String text) {
        return new Label(text);
    }

    public static Label label(String text, double maxWidth) {
        Label label = label(text);
        if (maxWidth > 0) {
            label.setWrapText(true);
            label.setMaxWidth(maxWidth);
        }
        return label;
    }

}
