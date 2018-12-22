package com.hyd.fx.builders;

import com.hyd.fx.app.AppLogo;
import com.hyd.fx.utils.Nullable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;

/**
 * @author SYSTEM
 */
public class ImageBuilder {

    @Nullable
    public static Image image(String resourcePath) {
        if (StringUtils.isBlank(resourcePath)) {
            return null;
        }

        InputStream resource = ImageBuilder.class.getResourceAsStream(resourcePath);
        if (resource == null) {
            return null;
        }

        return new Image(resource);
    }

    @Nullable
    public static ImageView imageView(String resourcePath) {
        return imageView(image(resourcePath));
    }

    @Nullable
    public static ImageView imageView(Image image) {
        return image != null ? new ImageView(image) : null;
    }

    @Nullable
    public static ImageView imageView(String resourcePath, int size) {
        return imageView(image(resourcePath), size);
    }

    @Nullable
    public static ImageView imageView(Image image, int size) {
        if (image != null) {
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(size);
            imageView.setFitHeight(size);
            return imageView;
        } else {
            return null;
        }
    }
}
