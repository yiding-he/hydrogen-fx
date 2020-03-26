package com.hyd.fx.builders;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.hyd.fx.Direction.Vertical;
import static com.hyd.fx.builders.ButtonBuilder.button;
import static com.hyd.fx.builders.ButtonBuilder.iconButton;
import static com.hyd.fx.builders.IconBuilder.icon;
import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.AMAZON;

public class ButtonBuilderTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Runnable action = () -> System.out.println("............");

        primaryStage.setScene(new Scene(LayoutBuilder.vbox(30, 15,
            button("Amazon", action),
            iconButton(null, icon(AMAZON, "36", "red"), action),
            iconButton("Amazon", icon(AMAZON, "16", "red"), action),
            iconButton("Amazon", icon(AMAZON, "48", "red"), Vertical, action)
        ), 500, 500));

        primaryStage.show();
    }
}