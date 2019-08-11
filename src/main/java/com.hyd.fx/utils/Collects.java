package com.hyd.fx.utils;

import java.util.Collection;

public class Collects {

    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }

    public static boolean isNotEmpty(Collection c) {
        return !isEmpty(c);
    }
}
