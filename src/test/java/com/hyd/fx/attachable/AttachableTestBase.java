package com.hyd.fx.attachable;

import javafx.application.Application;
import javafx.scene.layout.Pane;

import static com.hyd.fx.style.FxStyle.dashedBorder;

/**
 * @author yidin
 */
public abstract class AttachableTestBase extends Application {

    protected Pane createDemoPane() {
        Pane resizablePane = new Pane();
        resizablePane.setBorder(dashedBorder("#FF0000"));
        resizablePane.setPrefSize(100, 100);
        resizablePane.setLayoutX(110);
        resizablePane.setLayoutY(110);
        return resizablePane;
    }
}
