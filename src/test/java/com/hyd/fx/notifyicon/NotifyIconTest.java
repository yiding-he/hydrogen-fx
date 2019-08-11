package com.hyd.fx.notifyicon;

import static com.hyd.fx.builders.MenuBuilder.contextMenu;
import static com.hyd.fx.builders.MenuBuilder.menu;

import com.hyd.fx.dialog.AlertDialog;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.*;

public class NotifyIconTest extends Application {

    private Stage primaryStage;

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setOnShown(event -> onStageShown());
        primaryStage.setScene(new Scene(new BorderPane(), 300, 200));
        primaryStage.show();
    }

    private void onStageShown() {
        if (!SystemTray.isSupported()) {
            AlertDialog.error("错误", "系统不支持通知区域图标");
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();
        ContextMenu contextMenu = contextMenu(
            menu("关于...")
        );

        try {
            BufferedImage icon = ImageIO.read(NotifyIconTest.class.getResource("/favorite.png"));
            TrayIcon trayIcon = new TrayIcon(icon);
            tray.add(trayIcon);

            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(e.getButton());
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        final JPopupMenu pop = new JPopupMenu();
                        pop.add(new JMenuItem("中文", new ImageIcon(icon)));
                        pop.add(new JMenuItem("主题", new ImageIcon(icon)));
                        pop.setLocation(e.getX(), e.getY());
                        pop.setInvoker(pop);
                        pop.setVisible(true);
                    }
                }
            });

            primaryStage.setOnCloseRequest(event -> {
                tray.remove(trayIcon);
            });
        } catch (Exception e) {
            AlertDialog.error("错误", e);
        }
    }
}