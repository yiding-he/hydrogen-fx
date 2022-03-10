package com.hyd.fx.dialog;

import com.hyd.fx.FxException;
import com.hyd.fx.Fxml;
import com.hyd.fx.app.AppLogo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import java.net.URL;
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

    private URL fxmlUrl;

    private String css;

    private String title;

    private Image logo;

    private Stage owner;

    private boolean resizable;

    private boolean noDefaultButtons;

    private Object controller;

    private Callback<Class<?>, Object> controllerFactory;

    private ResourceBundle resources;

    private final Map<ButtonType, Consumer<ActionEvent>> buttonHandlerMap = new HashMap<>();

    private EventHandler<DialogEvent> onStageShown;

    private EventHandler<DialogEvent> onCloseRequest;

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

    public DialogBuilder onCloseRequest(EventHandler<DialogEvent> onCloseRequest) {
        this.onCloseRequest = onCloseRequest;
        return this;
    }

    public DialogBuilder resources(ResourceBundle resources) {
        this.resources = resources;
        return this;
    }

    public DialogBuilder resizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public DialogBuilder noDefaultButtons() {
        this.noDefaultButtons = true;
        return this;
    }

    public DialogBuilder controllerFactory(Callback<Class<?>, Object> controllerFactory) {
        this.controllerFactory = controllerFactory;
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

    public DialogBuilder body(URL resourceUrl) {
        this.fxmlUrl = resourceUrl;
        return this;
    }

    public DialogBuilder body(URL resourceUrl, Object controller) {
        this.fxmlUrl = resourceUrl;
        this.controller = controller;
        return this;
    }

    @Deprecated
    public DialogBuilder body(String fxml) {
        return this;
    }

    @Deprecated
    public DialogBuilder body(String fxml, Object controller) {
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
            dialog.initModality(Modality.WINDOW_MODAL);
        }

        if (dialogBody != null) {
            dialogBody.getStyleClass().add("dialog-body");
            dialog.getDialogPane().setContent(dialogBody);

        } else if (fxmlUrl != null) {

            FXMLLoader loader;
            if (controllerFactory != null) {
                try {
                    loader = Fxml.createFXMLLoader(fxmlUrl, resources, controllerFactory);
                    loader.load();
                } catch (Exception e) {
                    throw FxException.wrap(e);
                }
            } else if (controller != null) {
                loader = Fxml.load(fxmlUrl, resources, controller);
            } else {
                loader = Fxml.load(fxmlUrl, resources);
            }

            Parent _dialogBody = loader.getRoot();
            _dialogBody.getStyleClass().add("dialog-body");
            dialog.getDialogPane().setContent(_dialogBody);
        }

        if (css != null) {
            dialog.getDialogPane().getStylesheets().add(css);
        }

        if (buttons.isEmpty()) {
            if (!noDefaultButtons) {
                buttons.addAll(Arrays.asList(
                    ButtonType.OK, ButtonType.CANCEL
                ));
            } else {
                final Node buttonBar = dialog.getDialogPane().lookup("ButtonBar");
                if (buttonBar != null) {
                    buttonBar.setVisible(false);
                    buttonBar.setManaged(false);
                    ((ButtonBar) buttonBar).setPrefHeight(0);
                }
            }
        }

        if (onStageShown != null) {
            dialog.setOnShown(event -> new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // 如果执行得过快会导致一些操作无法生效
                }
                Platform.runLater(() -> onStageShown.handle(event));
            }).start());
        }

        dialog.setOnCloseRequest(Objects.requireNonNullElseGet(
            onCloseRequest,
            () -> event -> ((Dialog<?>) event.getSource()).close()
        ));

        Window window = dialog.getDialogPane().getScene().getWindow();
        if (window instanceof Stage && logo != null) {
            ((Stage) window).getIcons().add(logo);
        } else {
            AppLogo.setStageLogo(window);
        }

        dialog.setOnButtonClicked(this.buttonHandlerMap);
        dialog.getDialogPane().getButtonTypes().addAll(buttons);
        dialog.setResizable(resizable);
    }

}
