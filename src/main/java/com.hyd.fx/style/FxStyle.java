package com.hyd.fx.style;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * 帮助快速设置界面元素样式的工具类
 *
 * @author yidin
 */
public class FxStyle {

    /**
     * 生成一个背景对象
     *
     * @param webColor 背景色
     *
     * @return 背景对象
     */
    public static Background background(String webColor) {
        return background(Color.web(webColor));
    }

    /**
     * 生成一个背景对象
     *
     * @param color 背景色
     *
     * @return 背景对象
     */
    public static Background background(Paint color) {
        return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
    }

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

    public static void setBound(Region region, double x, double y, double width, double height) {
        region.setLayoutX(x);
        region.setLayoutY(y);
        region.setPrefWidth(width);
        region.setPrefHeight(width);
    }
}
