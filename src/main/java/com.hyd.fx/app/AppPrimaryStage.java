package com.hyd.fx.app;

import javafx.stage.Stage;

/**
 * (description)
 * created at 2018/2/5
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
