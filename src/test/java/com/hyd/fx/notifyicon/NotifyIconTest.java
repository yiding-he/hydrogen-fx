package com.hyd.fx.notifyicon;

import com.hyd.fx.dialog.AlertDialog;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class NotifyIconTest {

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Application.launch(NotifyIconTestApp.class, args);
    }

    public static class NotifyIconTestApp extends Application {

        private Stage primaryStage;

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

            try {
                BufferedImage icon = ImageIO.read(NotifyIconTest.class.getResource("/favorite.png"));
                TrayIcon trayIcon = new TrayIcon(icon);
                tray.add(trayIcon);

                final PopupMenu pop = new PopupMenu();
                MenuItem item1 = new MenuItem(new String("中文".getBytes("GBK")));
                item1.setFont(Font.getFont("Microsoft Yahei"));
                item1.addActionListener(e -> {
                    System.out.println(item1.getLabel());
                });
                pop.add(item1);
                pop.add(new MenuItem("主题"));
                trayIcon.setPopupMenu(pop);

                primaryStage.setOnCloseRequest(event -> {
                    tray.remove(trayIcon);
                });
            } catch (Exception e) {
                AlertDialog.error("错误", e);
            }
        }
    }
}