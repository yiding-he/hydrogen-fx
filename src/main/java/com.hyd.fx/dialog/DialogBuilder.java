package com.hyd.fx.dialog;

import com.hyd.fx.Fxml;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.*;
import java.util.function.Consumer;

/**
 * (description)
 * created at 2018/2/6
 *
 * @author yidin
 */
public class DialogBuilder {

    private Parent dialogBody;

    private String dialogBodyFxml;

    private String css;

    private String title;

    private Image logo;

    private Stage owner;

    private boolean resizable;

    private Object controller;

    private Map<ButtonType, Consumer<ActionEvent>> buttonHandlerMap = new HashMap<>();

    private EventHandler<DialogEvent> onStageShown;

    private List<ButtonType> buttons = new ArrayList<>();

    public DialogBuilder() {
    }

    public DialogBuilder logo(Image logo) {
        this.logo = logo;
        return this;
    }

    public DialogBuilder onStageShown(EventHandler<DialogEvent> onStageShown) {
        this.onStageShown = onStageShown;
        return this;
    }

    public DialogBuilder resizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public DialogBuilder onOkButtonClicked(Consumer<ActionEvent> onButtonClicked) {
        return onButtonClicked(ButtonType.OK, onButtonClicked);
    }

    public DialogBuilder onCancelButtonClicked(Consumer<ActionEvent> onButtonClicked) {
        return onButtonClicked(ButtonType.CANCEL, onButtonClicked);
    }

    public DialogBuilder onYesButtonClicked(Consumer<ActionEvent> onButtonClicked) {
        return onButtonClicked(ButtonType.YES, onButtonClicked);
    }

    public DialogBuilder onNoButtonClicked(Consumer<ActionEvent> onButtonClicked) {
        return onButtonClicked(ButtonType.NO, onButtonClicked);
    }

    public DialogBuilder onButtonClicked(ButtonType buttonType, Consumer<ActionEvent> onButtonClicked) {
        this.buttonHandlerMap.put(buttonType, onButtonClicked);
        return this;
    }

    public DialogBuilder body(Parent dialogBody) {
        this.dialogBody = dialogBody;
        return this;
    }

    public DialogBuilder body(String fxml) {
        this.dialogBodyFxml = fxml;
        return this;
    }

    public DialogBuilder body(String fxml, Object controller) {
        this.dialogBodyFxml = fxml;
        this.controller = controller;
        return this;
    }

    public DialogBuilder title(String title) {
        this.title = title;
        return this;
    }

    public DialogBuilder css(String css) {
        this.css = css;
        return this;
    }

    public DialogBuilder owner(Stage owner) {
        this.owner = owner;
        return this;
    }

    public DialogBuilder buttons(ButtonType... buttons) {
        this.buttons = Arrays.asList(buttons);
        return this;
    }

    //////////////////////////////////////////////////////////////

    public Optional<ButtonType> showAndWait() {
        return build().showAndWait();
    }

    public BasicDialog build() {
        BasicDialog dialog = new BasicDialog();
        applyTo(dialog);
        return dialog;
    }

    public void applyTo(BasicDialog dialog) {

        if (title != null) {
            dialog.setTitle(title);
        }

        if (owner != null) {
            dialog.initOwner(owner);
            dialog.initModality(Modality.APPLICATION_MODAL);
        }

        if (dialogBody != null) {
            dialogBody.getStyleClass().add("dialog-body");
            dialog.getDialogPane().setContent(dialogBody);

        } else if (dialogBodyFxml != null) {
            FXMLLoader loader = controller == null?
                    Fxml.load(dialogBodyFxml): Fxml.load(dialogBodyFxml, controller);

            Parent _dialogBody = loader.getRoot();
            _dialogBody.getStyleClass().add("dialog-body");
            dialog.getDialogPane().setContent(_dialogBody);
        }

        if (css != null) {
            dialog.getDialogPane().getStylesheets().add(css);
        }

        if (buttons.isEmpty()) {
            buttons.addAll(Arrays.asList(
                    ButtonType.OK, ButtonType.CANCEL
            ));
        }

        if (onStageShown != null) {
            dialog.setOnShown(event -> {
                new Thread(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // 如果执行得过快会导致一些操作无法生效
                    }
                    Platform.runLater(() -> onStageShown.handle(event));
                }).start();
            });
        }

        Window window = dialog.getDialogPane().getScene().getWindow();
        if (window instanceof Stage && logo != null) {
            ((Stage) window).getIcons().add(logo);
        }

        dialog.setOnButtonClicked(this.buttonHandlerMap);
        dialog.getDialogPane().getButtonTypes().addAll(buttons);
        dialog.setResizable(resizable);
    }
}
