package com.hyd.fx;

import com.hyd.fx.app.AppLogo;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;

/**
 * 帮助快速构建一些界面元素的工具类
 *
 * @author yiding.he
 */
public class FxUI {

    public static Button button(String text, Runnable onAction) {
        Button button = new Button(text);
        button.setOnAction(event -> onAction.run());
        return button;
    }

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
