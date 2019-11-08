package com.hyd.fx.dialog;

import java.util.Collection;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public abstract class AbstractDialog extends Dialog<ButtonType> {

    public AbstractDialog() {
        setResizable(true);
        getDialogPane().getButtonTypes().addAll(getButtonTypes());
        getDialogPane().setContent(getDialogContent());
    }

    protected abstract Node getDialogContent();

    protected abstract Collection<? extends ButtonType> getButtonTypes();
}
