package com.hyd.fx.builders;

import com.hyd.fx.app.AppLogo;
import javafx.scene.image.Image;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;

/**
 * @author SYSTEM
 */
public class ImageBuilder {

    public static Image image(String resourcePath) {
        if (StringUtils.isBlank(resourcePath)) {
            return null;
        }

        InputStream resource = AppLogo.class.getResourceAsStream(resourcePath);
        if (resource == null) {
            return null;
        }

        return new Image(resource);
    }
}
