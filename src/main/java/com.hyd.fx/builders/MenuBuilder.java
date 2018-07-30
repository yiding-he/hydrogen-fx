package com.hyd.fx.builders;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

/**
 * (description)
 * created at 2018/2/6
 *
 * @author yidin
 */
public class MenuBuilder {

    public static Menu menu(String name, Image icon, MenuItem... menuItems) {
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(16);
        imageView.setFitHeight(16);
        return new Menu(name, imageView, menuItems);
    }

    public static Menu menu(String name, MenuItem... menuItems) {
        return new Menu(name, null, menuItems);
    }

    public static ContextMenu contextMenu(MenuItem... menus) {
        return new ContextMenu(menus);
    }

    public static MenuItem menuItem(String name, Runnable action) {
        return menuItem(name, null, null, action);
    }

    public static MenuItem menuItem(String name, String accelerator, Runnable action) {
        return menuItem(name, null, accelerator, action);
    }

    public static MenuItem menuItem(String name, Image icon, Runnable action) {
        return menuItem(name, icon, null, action);
    }

    public static MenuItem menuItem(String name, Image icon, String accelerator, Runnable action) {
        MenuItem menuItem = new MenuItem(name);
        menuItem.setOnAction(event -> action.run());

        if (icon != null) {
            menuItem.setGraphic(ImageBuilder.imageView(icon, 16));
        }

        if (accelerator != null) {
            menuItem.setAccelerator(KeyCombination.valueOf(accelerator));
        }

        return menuItem;
    }
}
