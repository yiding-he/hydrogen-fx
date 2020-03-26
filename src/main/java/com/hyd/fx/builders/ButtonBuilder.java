package com.hyd.fx.builders;

import com.hyd.fx.Direction;
import com.hyd.fx.utils.Str;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.Region;

/**
 * @author SYSTEM
 */
public class ButtonBuilder {

    public static Button iconButton(
        String text, Node graph, Direction direction, Runnable onAction
    ) {
        Button button = new Button();
        if (Str.isNotBlank(text)) {
            button.setText(text);
        }
        if (graph != null) {
            button.setGraphic(graph);
            if (direction == Direction.Vertical) {
                button.setContentDisplay(ContentDisplay.TOP);
            }
        }

        button.setOnAction(event -> onAction.run());
        button.setMinWidth(Region.USE_PREF_SIZE);
        return button;

    }

    public static Button iconButton(String text, Node graph, Runnable onAction) {
        return iconButton(text, graph, null, onAction);
    }

    public static Button button(String text, Runnable onAction) {
        return iconButton(text, null, null, onAction);
    }
}
