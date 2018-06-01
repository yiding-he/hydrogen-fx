package com.hyd.fx.style;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * (description)
 * created at 2017/6/2
 *
 * @author yidin
 */
public class FxStyle {

    public static Background background(String webColor) {
        return background(Color.web(webColor));
    }

    public static Background background(Paint color) {
        return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
    }

    public static Border border(Paint color, double width) {
        return border(color, width, BorderStrokeStyle.SOLID);
    }

    public static Border border(Paint color, double width, BorderStrokeStyle strokeStyle) {
        return new Border(new BorderStroke(color, strokeStyle, CornerRadii.EMPTY, new BorderWidths(width)));
    }

    public static Border dashedBorder(String color) {
        return new Border(new BorderStroke(Color.web(color),
                BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(1)));
    }

    public static void setBound(Region region, double x, double y, double width, double height) {
        region.setLayoutX(x);
        region.setLayoutY(y);
        region.setPrefWidth(width);
        region.setPrefHeight(width);
    }
}
