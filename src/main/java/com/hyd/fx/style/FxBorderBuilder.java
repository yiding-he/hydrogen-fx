package com.hyd.fx.style;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Utility for creating {@link Border} instances.
 */
public class FxBorderBuilder {

    /**
     * 生成一个边框对象
     *
     * @param color 边框颜色
     * @param width 边框厚度
     *
     * @return 边框对象
     */
    public static Border border(Paint color, double width) {
        return border(color, width, BorderStrokeStyle.SOLID);
    }

    public static Border border(Paint color, double width, BorderStrokeStyle strokeStyle) {
        return new Border(new BorderStroke(color, strokeStyle, CornerRadii.EMPTY, new BorderWidths(width)));
    }

    public static Border dashedBorder(String color) {
        return dashedBorder(Color.web(color));
    }

    public static Border dashedBorder(Paint color) {
        return new Border(new BorderStroke(color,
            BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(1)));
    }

}
