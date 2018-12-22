package com.hyd.fx.app;

import javafx.application.Platform;

/**
 * 在 UI 线程执行指定的任务
 *
 * @author yidin
 */
public class AppThread {

    /**
     * 如果当前线程属于 UI 线程，则执行 runnable，否则调用 Platform.runLater() 来执行 runnable。
     * 这样能保证 runnable 是在 UI 线程上执行。
     *
     * @param runnable 需要在 UI 线程执行的任务
     */
    public static void runUIThread(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }
}
