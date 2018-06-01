package com.hyd.fx.attachable;

import com.hyd.fx.style.FxStyle;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * @author yidin
 */
public class SelectableTest extends AttachableTestBase {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Pane pane = createDemoPane();
        pane.setBackground(FxStyle.background("#FFFFFF"));
        root.getChildren().add(pane);

        // 使 pane 可点选
        Selectable.attachTo(pane)
                .clickCount(2)          // 双击选中元素
                .switchOnClick(true)    // 允许再次双击取消选择
                .selectedProperty()
                .addListener((_ob, _old, _new) -> {
                    setBackground(pane, _new? "#888888": "#FFFFFF");
                });

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    private void setBackground(Node node, String color) {
        if (node instanceof Region) {
            ((Region) node).setBackground(FxStyle.background(color));
        }
    }
}