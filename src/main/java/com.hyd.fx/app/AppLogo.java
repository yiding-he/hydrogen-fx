package com.hyd.fx.app;

import com.hyd.fx.FxUI;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.lang3.StringUtils;

/**
 * (description)
 * created at 2018/2/1
 *
 * @author yidin
 */
public class AppLogo {

    public static final String DEFAULT_LOGO_PATH = "/logo.png";

    private static String path = DEFAULT_LOGO_PATH;

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        AppLogo.path = path;
    }

    public static void setStageLogo(Window window) {
        if (window instanceof Stage) {
            setStageLogo((Stage) window);
        }
    }

    public static void setStageLogo(Stage stage) {
        if (StringUtils.isBlank(path)) {
            return;
        }

        stage.getIcons().clear();
        stage.getIcons().add(getLogo());
    }

    public static Image getLogo() {
        return FxUI.image(path);
    }
}
