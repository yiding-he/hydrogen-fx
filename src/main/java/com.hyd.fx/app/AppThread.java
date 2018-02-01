package com.hyd.fx.app;

import javafx.application.Platform;

/**
 * (description)
 * created at 2018/2/1
 *
 * @author yidin
 */
public class AppThread {

    public static void runUIThread(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }
}
