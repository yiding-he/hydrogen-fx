package com.hyd.fx.window;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.stage.Window;

public class WindowHelper {

    public static void relocateDialog(Dialog<?> dialog, Window owner) {
        dialog.setX(Math.max(0, owner.getX() + owner.getWidth() / 2 - dialog.getWidth() / 2));
        dialog.setY(Math.max(0, owner.getY() + owner.getHeight() / 2 - dialog.getHeight() / 2));
    }

    public static void closeContainerWindow(Object eventSource) {
        if (eventSource instanceof Node) {
            Scene scene = ((Node) eventSource).getScene();
            if (scene != null) {
                Window window = scene.getWindow();
                if (window instanceof Stage) {
                    ((Stage) window).close();
                }
            }
        }
    }
}
