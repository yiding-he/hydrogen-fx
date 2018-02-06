package com.hyd.fx.components;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        MenuItem menuItem = new MenuItem(name);
        menuItem.setOnAction(event -> action.run());
        return menuItem;
    }

    public static MenuItem menuItem(String name, Image icon, Runnable action) {
        MenuItem menuItem = new MenuItem(name);
        menuItem.setOnAction(event -> action.run());
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(16);
        imageView.setFitHeight(16);
        menuItem.setGraphic(imageView);
        return menuItem;
    }
}
