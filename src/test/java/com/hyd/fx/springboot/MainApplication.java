package com.hyd.fx.springboot;

import com.hyd.fx.app.AppLogo;
import com.hyd.fx.dialog.AlertDialog;
import com.hyd.fx.dialog.DialogBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button = new Button("打开对话框");
        button.setOnAction(event -> new DialogBuilder()
            .title("你好")
            .body("/components/spring-boot-test.fxml")
            .onButtonClicked(ButtonType.OK, e -> AlertDialog.info("OK", "You clicked OK."))
            .onCancelButtonClicked(e -> {
                AlertDialog.error("No Cancel", "不能取消！");
                e.consume();
            })
            .build().show()
        );

        AppLogo.setStageLogo(primaryStage);

        primaryStage.setScene(new Scene(new BorderPane(button), 500, 400));
        primaryStage.setTitle("Hello");
        primaryStage.show();
    }
}
