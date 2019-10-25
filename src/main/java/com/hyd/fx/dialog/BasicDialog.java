package com.hyd.fx.dialog;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 基础对话框的抽象类。子类可以在构造方法中使用 DialogBuilder 来初始化自己。
 * TODO 这个使用方式需要修改
 *
 * @author yidin
 */
public class BasicDialog extends Dialog<ButtonType> {

    private Map<ButtonType, Consumer<ActionEvent>> buttonHandlerMap = new HashMap<>();

    public BasicDialog() {
        DialogPane dialogPane = getDialogPane();

        dialogPane.getButtonTypes().addListener((ListChangeListener<ButtonType>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (ButtonType buttonType : c.getAddedSubList()) {
                        Consumer<ActionEvent> handler = buttonHandlerMap.get(buttonType);
                        if (handler != null) {
                            Node button = dialogPane.lookupButton(buttonType);
                            if (button != null) {
                                button.addEventFilter(ActionEvent.ACTION, handler::accept);
                            }
                        }
                    }
                } else if (c.wasRemoved()) {
                    for (ButtonType buttonType : c.getRemoved()) {
                        Consumer<ActionEvent> handler = buttonHandlerMap.get(buttonType);
                        if (handler != null) {
                            Node button = dialogPane.lookupButton(buttonType);
                            if (button != null) {
                                button.removeEventFilter(ActionEvent.ACTION, handler::accept);
                            }
                        }
                    }
                }
            }
        });
    }

    public void setOnButtonClicked(ButtonType buttonType, Consumer<ActionEvent> onButtonClicked) {
        this.buttonHandlerMap.put(buttonType, onButtonClicked);
    }

    public void setOnButtonClicked(Map<ButtonType, Consumer<ActionEvent>> buttonHandlerMap) {
        this.buttonHandlerMap = buttonHandlerMap;
    }
}
