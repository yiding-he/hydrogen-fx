package com.hyd.fx.app;

import com.hyd.fx.builders.ImageBuilder;
import com.hyd.fx.utils.NotNull;
import com.hyd.fx.utils.Nullable;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.lang3.StringUtils;

/**
 * 设置全局的窗体图标，调用 setStageLogo() 方法将图标应用到窗体上。
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

    public static void setStageLogo(@NotNull Stage stage) {
        if (StringUtils.isBlank(path)) {
            return;
        }

        Image logo = getLogo();
        if (logo != null) {
            stage.getIcons().clear();
            stage.getIcons().add(logo);
        }
    }

    @Nullable
    public static Image getLogo() {
        return ImageBuilder.image(path);
    }
}
