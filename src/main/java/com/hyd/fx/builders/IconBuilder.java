package com.hyd.fx.builders;

import de.jensd.fx.glyphs.GlyphIcons;
import de.jensd.fx.glyphs.GlyphsDude;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class IconBuilder {

    public static Text icon(GlyphIcons icon, String color) {
        return icon(icon, "16px", color);
    }

    public static Text icon(GlyphIcons icon, String size, String color) {
        Text text = GlyphsDude.createIcon(icon, size);
        text.setFill(Color.web(color));
        return text;
    }

}
