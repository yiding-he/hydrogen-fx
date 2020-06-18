package com.hyd.fx.style;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FxStyleTest {

    @Test
    public void testEmptyString() throws Exception {
        FxStyle fxStyle = new FxStyle("");
        assertTrue(fxStyle.getStyleElements().isEmpty());
        fxStyle.putStyleValue("k1", "v1");
        assertEquals(1, fxStyle.getStyleElements().size());
        fxStyle.putStyleValue("k2", "v2");
        assertEquals("k1:v1;k2:v2", fxStyle.toStyleString());
    }
}