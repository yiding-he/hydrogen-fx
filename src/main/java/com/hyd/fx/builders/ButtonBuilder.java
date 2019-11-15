package com.hyd.fx.builders;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;

/**
 * @author SYSTEM
 */
public class ButtonBuilder {

    public static Button iconButton(String text, Node graph, Runnable onAction) {
        Button button = new Button(text, graph);
        button.setOnAction(event -> onAction.run());
        button.setMinWidth(Region.USE_PREF_SIZE);
        return button;
    }

    public static Button button(String text, Runnable onAction) {
        Button button = new Button(text);
        button.setOnAction(event -> onAction.run());
        button.setMinWidth(Region.USE_PREF_SIZE);
        return button;
    }
}
