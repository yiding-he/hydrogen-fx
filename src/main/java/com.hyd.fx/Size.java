package com.hyd.fx;

import javafx.geometry.Bounds;

/**
 * (description)
 * created at 2017/10/24
 *
 * @author yidin
 */
public class Size {

    public static Size fromBounds(Bounds bounds) {
        return new Size(bounds.getWidth(), bounds.getHeight());
    }

    private double width;

    private double height;

    public Size(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Size multiply(double d) {
        return new Size(d * getWidth(), d * getHeight());
    }
}
