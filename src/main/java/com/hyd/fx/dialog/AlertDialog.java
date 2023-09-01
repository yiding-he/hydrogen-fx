package com.hyd.fx.dialog;

import com.hyd.fx.app.AppLogo;
import com.hyd.fx.app.AppPrimaryStage;
import com.hyd.fx.app.AppThread;
import com.hyd.fx.utils.Str;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * 提示/警告/错误对话框
 *
 * @author yiding_he
 */
public class AlertDialog {

    public static void error(String message) {
        alert(Alert.AlertType.ERROR, "错误", message);
    }

    public static void error(String title, String message) {
        alert(Alert.AlertType.ERROR, title, message);
    }

    public static void error(String title, Throwable throwable) {
        boolean noMessage = Str.isBlank(throwable.getMessage());
        String message = noMessage ? throwable.toString() : throwable.getMessage();
        error(title, message, Str.getStackTrace(throwable));
    }

    public static void info(String title, String message) {
        alert(Alert.AlertType.INFORMATION, title, message);
    }

    public static void warn(String title, String message) {
        alert(Alert.AlertType.WARNING, title, message);
    }

    public static void alert(Alert.AlertType alertType, String title, String message) {
        AppThread.runUIThread(() -> {
            Alert alert = new Alert(alertType, message, ButtonType.OK);
            alert.setTitle(title);
            alert.setHeaderText(null);

            initOwnerAndModality(alert);

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            AppLogo.setStageLogo(stage);

            alert.showAndWait();
        });
    }

    // 打开一个展示了详细错误信息的错误对话框
    public static void error(String title, String message, String details) {
        AppThread.runUIThread(() -> error0(title, message, details));
    }

    private static void error0(String title, String message, String details) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message.trim());

        initOwnerAndModality(alert);

        TextArea textArea = new TextArea(details);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);

        alert.getDialogPane().setExpandableContent(expContent);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        AppLogo.setStageLogo(stage);

        alert.showAndWait();
    }

    //////////////////////////////////////////////////////////////

    public static boolean confirmOkCancel(String title, String message) {
        return confirm(Alert.AlertType.CONFIRMATION, title, message, ButtonType.OK, ButtonType.CANCEL) == ButtonType.OK;
    }

    public static boolean confirmYesNo(String title, String message) {
        return confirm(Alert.AlertType.WARNING, title, message, ButtonType.YES, ButtonType.NO) == ButtonType.YES;
    }

    public static ButtonType confirmYesNoCancel(String title, String message) {
        return confirm(Alert.AlertType.WARNING, title, message, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
    }

    public static ButtonType confirm(Alert.AlertType alertType, String title, String message, ButtonType... buttonTypes) {
        Alert alert = new Alert(alertType, message, buttonTypes);
        alert.setTitle(title);
        alert.setHeaderText(null);

        initOwnerAndModality(alert);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        AppLogo.setStageLogo(stage);

        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(ButtonType.CANCEL);
    }

    //////////////////////////////////////////////////////////////

    public static String input(String title, String message, boolean multiLine) {
        VBox vBox = new VBox(5);
        if (message != null) {
            vBox.getChildren().add(new Label(message));
        }

        TextInputControl control = multiLine ? new TextArea() : new TextField();
        control.setPrefWidth(300);
        vBox.getChildren().add(control);

        if (multiLine) {
            VBox.setVgrow(control, Priority.ALWAYS);
        }

        Alert alert = new Alert(AlertType.INFORMATION, null, ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(null);
        alert.getDialogPane().setContent(vBox);
        alert.setResizable(true);

        initOwnerAndModality(alert);

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK) {
            return control.getText();
        } else {
            return null;
        }
    }

    private static void initOwnerAndModality(Alert alert) {
        var primaryStage = AppPrimaryStage.getPrimaryStage();
        if (primaryStage != null && primaryStage.getScene() != null) {
            alert.initOwner(primaryStage);
            alert.initModality(Modality.APPLICATION_MODAL);
        }
    }
}
