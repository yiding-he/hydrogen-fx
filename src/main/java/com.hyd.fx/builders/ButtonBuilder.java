package com.hyd.fx.builders;

import javafx.scene.control.Button;

/**
 * @author SYSTEM
 */
public class ButtonBuilder {

    public static Button button(String text, Runnable onAction) {
        Button button = new Button(text);
        button.setOnAction(event -> onAction.run());
        return button;
    }
}
