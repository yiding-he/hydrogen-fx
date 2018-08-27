package com.hyd.fx.attachable;

import com.hyd.fx.style.FxStyle;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * 综合示例
 *
 * @author yidin
 */
public class ScrollPaneDraggableTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ImageView imageView = new ImageView(new Image("https://desk-fd.zol-img.com.cn/t_s1024x768c5/g5/M00/0F/07/ChMkJlauymeIYMI3AAhS3aKLXp4AAH8tQD5_3oACFL1729.jpg"));
        Pane content = new Pane(imageView);
        imageView.toBack();

        // 创建一个普通的 Pane
        Pane draggablePane = createDemoPane();

        // 使得这个 Pane 可以被拖动、改变大小，并附加其他元素
        Draggable.attachTo(draggablePane);
        Resizable.attachTo(draggablePane);
        Dockable.attachTo(draggablePane).dock(Side.TOP, new Button("BUTTON"));

        content.getChildren().add(draggablePane);

        // 创建一个普通的 ScrollPane
        ScrollPane root = new ScrollPane(content);

        // 使得这个 ScrollPane 能够用右键拖动，并拖拽内部元素时能自动滚动
        SecondaryMousePannable.attachTo(root);
        AutoScrollable.attachTo(root);

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    private Pane createDemoPane() {
        Pane draggablePane = new Pane();
        draggablePane.setPrefSize(200, 200);
        draggablePane.setBorder(FxStyle.dashedBorder("#6666FF"));
        draggablePane.setBackground(FxStyle.background(new Color(0.7, 0.7,1, 0.5)));
        draggablePane.setLayoutX(100);
        draggablePane.setLayoutY(100);
        return draggablePane;
    }
}
