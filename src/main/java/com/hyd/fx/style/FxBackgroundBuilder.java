package com.hyd.fx.style;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Utility for creating {@link Background} instances.
 */
public class FxBackgroundBuilder {

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

}
