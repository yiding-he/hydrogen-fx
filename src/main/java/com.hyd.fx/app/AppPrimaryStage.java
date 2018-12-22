package com.hyd.fx.app;

import javafx.stage.Stage;

/**
 * 存放主窗体的静态变量。没有其他的作用。
 *
 * @author yidin
 */
public class AppPrimaryStage {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage primaryStage) {
        AppPrimaryStage.primaryStage = primaryStage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
