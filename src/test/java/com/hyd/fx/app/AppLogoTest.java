package com.hyd.fx.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AppLogoTest extends Application {

    static {
        // set global app logo path
        AppLogo.setPath("/music.png");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // apply app logo using 'music.png'
        AppLogo.setStageLogo(primaryStage);

        primaryStage.setScene(new Scene(new Pane(), 300, 300));
        primaryStage.show();
    }
}