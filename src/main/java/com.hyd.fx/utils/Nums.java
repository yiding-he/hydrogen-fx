package com.hyd.fx.utils;

public class Nums {

    public static double between(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }
}
