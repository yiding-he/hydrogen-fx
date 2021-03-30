package com.hyd.fx.builders;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 用于快速创建一些 Node 对象
 */
public class LayoutBuilder {

    protected double padding, spacing;

    protected Pos alignment;

    protected Parent parent;

    public Parent build() {
        return this.parent;
    }

    protected void vBox(double padding, double spacing, Pos alignment, Node... children) {
        this.parent = LayoutBuilder.vbox(padding, spacing, alignment, children);
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
}
