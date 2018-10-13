package com.hyd.fx.components;

import com.hyd.fx.attachable.Selectable;
import com.hyd.fx.style.FxStyle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SelectionAreaTest extends Application {

    public static final Color DEFAULT_FILL = Color.web("#777777");
    public static final Color SELECTED_FILL = Color.web("#AA7777");

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(root(), 500, 350));
        primaryStage.show();
    }

    private Pane root() {
        Pane pane = new Pane();
        Rectangle[] rectangles = {
                rect(50),
                rect(200),
                rect(350)
        };

        for (Rectangle rectangle : rectangles) {
            Selectable.attachTo(rectangle)
                    .selectedProperty().addListener(
                            (_ob, _old, _new) -> rectangle.setFill(_new ? SELECTED_FILL : DEFAULT_FILL));
        }

        pane.getChildren().addAll(rectangles);

        SelectionArea.createWith(pane)
                .border(FxStyle.dashedBorder("#FF0000"))
                .onNodeSelected(node -> {
                    if (node != null) {
                        Selectable.of(node).selected(true);
                    }
                })
                .onNodeUnselected(node -> {
                    if (node != null) {
                        Selectable.of(node).selected(false);
                    }
                });

        return pane;
    }

    private Rectangle rect(int x) {
        Rectangle rectangle = new Rectangle(x, 200, 100, 100);
        rectangle.setFill(DEFAULT_FILL);
        return rectangle;
    }
}