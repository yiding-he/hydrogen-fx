package com.hyd.fx.builders;

import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * @author SYSTEM
 */
public class ButtonBuilder {

    public static Button iconButton(String text, Node graph, Runnable onAction) {
        Button button = new Button(text, graph);
        button.setOnAction(event -> onAction.run());
        return button;
    }

    public static Button button(String text, Runnable onAction) {
        Button button = new Button(text);
        button.setOnAction(event -> onAction.run());
        return button;
    }
}
