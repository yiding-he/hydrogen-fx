package com.hyd.fx.builders;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.InputStream;

/**
 * 用于快速创建一些 Node 对象
 */
public class LayoutBuilder {

    protected String text;

    protected Node graph;

    protected double spacing;

    protected Insets padding;

    protected Pos alignment;

    protected Parent parent;

    protected Runnable action;

    public Parent build() {
        return this.parent;
    }

    protected Insets all(double d) {
        return new Insets(d);
    }

    protected Insets all(double top, double right, double bottom, double left) {
        return new Insets(top, right, bottom, left);
    }

    protected void vBox(Insets padding, double spacing, Pos alignment, Node... children) {
        VBox vBox = new VBox();
        parent = vBox;

        vBox.setPadding(padding);
        vBox.setSpacing(spacing);
        vBox.setAlignment(alignment);
        vBox.getChildren().setAll(children);
    }

    public static VBox vbox(double padding, double spacing, Pos alignment, Node... children) {
        VBox vBox = new VBox(spacing, children);
        if (padding > 0) {
            vBox.setPadding(new Insets(padding));
        }
        if (alignment != null) {
            vBox.setAlignment(alignment);
        }
        return vBox;
    }

    public static VBox vbox(double padding, double spacing, Node... children) {
        return vbox(padding, spacing, null, children);
    }

    public static HBox hbox(double padding, double spacing, Pos alignment, Node... children) {
        HBox hBox = new HBox(spacing, children);
        if (padding > 0) {
            hBox.setPadding(new Insets(padding));
        }
        if (alignment != null) {
            hBox.setAlignment(alignment);
        }
        return hBox;
    }

    public static HBox hbox(double padding, double spacing, Node... children) {
        return hbox(padding, spacing, null, children);
    }

    public static ImageView icon(String resourcePath, double width, double height) {
        InputStream inputStream = LayoutBuilder.class.getResourceAsStream(resourcePath);
        if (inputStream == null) {
            return new ImageView();
        }
        ImageView iv = new ImageView(new Image(inputStream));
        iv.setFitWidth(width);
        iv.setFitHeight(height);
        return iv;
    }

    public static Label label(String text) {
        return new Label(text);
    }

    public static Button button(String text, Node graph, Runnable action) {
        Button button = new Button(text, graph);
        button.setOnAction(event -> action.run());
        return button;
    }
}
