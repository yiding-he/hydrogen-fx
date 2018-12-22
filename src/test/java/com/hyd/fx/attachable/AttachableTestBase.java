package com.hyd.fx.attachable;

import javafx.application.Application;
import javafx.scene.layout.Pane;

import static com.hyd.fx.style.FxStyle.background;
import static com.hyd.fx.style.FxStyle.dashedBorder;

/**
 * @author yidin
 */
public abstract class AttachableTestBase extends Application {

    protected Pane createDemoPane(int x, int y) {
        Pane pane = new Pane();
        pane.setBorder(dashedBorder("#FF0000"));
        pane.setPrefSize(100, 100);
        pane.setLayoutX(x);
        pane.setLayoutY(y);
        return pane;
    }

    protected Pane square(double x, double y, double size, String color) {
        Pane pane = new Pane();
        pane.setBackground(background(color));
        pane.setPrefSize(size, size);
        pane.setLayoutX(x);
        pane.setLayoutY(y);
        return pane;
    }
}
