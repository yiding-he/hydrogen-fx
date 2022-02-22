package com.hyd.fx.builders;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * (description)
 * created at 2018/2/6
 *
 * @author yidin
 */
public class MenuBuilder {

    public static Menu menu(String name, Node icon, MenuItem... menuItems) {
        return new Menu(name, icon, menuItems);
    }

    public static Menu menu(String name, MenuItem... menuItems) {
        return new Menu(name, null, menuItems);
    }

    ////////////////////////////////////////////////////////////

    public static ContextMenu contextMenu(MenuItem... menus) {
        return new ContextMenu(menus);
    }

    ////////////////////////////////////////////////////////////

    public static MenuItem menuItem(String name, Runnable action) {
        return menuItem(name, null, null, action, false);
    }

    public static MenuItem menuItem(String name, String accelerator, Runnable action) {
        return menuItem(name, null, accelerator, action, false);
    }

    public static MenuItem menuItem(String name, Node icon, Runnable action) {
        return menuItem(name, icon, null, action, false);
    }

    public static MenuItem menuItem(String name, Node icon, Runnable action, boolean disabled) {
        return menuItem(name, icon, null, action, disabled);
    }

    public static MenuItem menuItem(String name, Node icon, String accelerator, Runnable action) {
        return menuItem(name, icon, accelerator, action, false);
    }

    public static MenuItem menuItem(String name, Node icon, String accelerator, Runnable action, boolean disabled) {
        MenuItem menuItem = new MenuItem(name);
        if (action != null) {
            menuItem.setOnAction(event -> action.run());
        }

        if (icon != null) {
            menuItem.setGraphic(icon);
        }

        if (accelerator != null) {
            menuItem.setAccelerator(KeyCombination.valueOf(accelerator));
        }

        menuItem.setDisable(disabled);
        return menuItem;
    }
}
