package com.hyd.fx.builders;

import com.hyd.fx.style.FxStyle;
import com.hyd.fx.utils.Str;
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

    public static Label label(String text, double fontSize, boolean bold) {
        return label(text, fontSize, bold, null);
    }

    public static Label label(String text, double fontSize, boolean bold, String color) {

        FxStyle style = new FxStyle()
            .putStyleValueIf(fontSize > 0, "-fx-font-size", fontSize)
            .putStyleValueIf(bold, "-fx-font-weight", "bold")
            .putStyleValueIf(Str.isNotBlank(color), "-fx-text-fill", color);

        Label label = label(text);
        style.setToNode(label);
        return label;
    }
}
