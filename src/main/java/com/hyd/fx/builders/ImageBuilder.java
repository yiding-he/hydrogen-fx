package com.hyd.fx.builders;

import com.hyd.fx.utils.Nullable;
import com.hyd.fx.utils.Str;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * @author SYSTEM
 */
public class ImageBuilder {

    @Nullable
    public static Image image(String resourcePath) {
        if (Str.isBlank(resourcePath)) {
            return null;
        }

        InputStream resource = ImageBuilder.class.getResourceAsStream(resourcePath);
        if (resource == null) {
            return null;
        }

        return new Image(resource);
    }

    public static Image imageFromFile(File file) throws IOException {
        return new Image(Files.newInputStream(file.toPath()));
    }

    public static Image imageFromFile(String filePath) throws IOException {
        return imageFromFile(new File(filePath));
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
