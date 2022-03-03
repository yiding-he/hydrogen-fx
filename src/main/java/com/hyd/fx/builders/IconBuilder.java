package com.hyd.fx.builders;

import com.hyd.fx.FxException;
import javafx.scene.image.Image;

import java.net.URL;

public class IconBuilder {

    public static Image icon(String resourcePath) {
        URL resource = LayoutBuilder.class.getResource(resourcePath);
        if (resource == null) {
            throw new FxException("Resource '" + resourcePath + "' not found.");
        }
        return new Image(resource.toExternalForm());
    }

}
