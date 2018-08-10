package com.hyd.fx.components;

import com.hyd.fx.attachable.Selectable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SelectionAreaTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(root(), 500, 350));
        primaryStage.show();
    }

    private Pane root() {
        Pane pane = new Pane();
        Rectangle[] rectangles = {
                new Rectangle(50, 200, 100, 100),
                new Rectangle(200, 200, 100, 100),
                new Rectangle(350, 200, 100, 100)
        };

        for (Rectangle rectangle : rectangles) {
            Selectable.attachTo(rectangle)
                    .selectableProperty().addListener((_ob, _old, _new) -> {
                if (_new) {
                    rectangle.setStyle("-fx-border-color: red;-fx-border-width: 2;-fx-border-style: solid");
                } else {
                    rectangle.setStyle("");
                }
            });
        }

        pane.getChildren().addAll(rectangles);

        SelectionArea.createWith(pane)
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
}