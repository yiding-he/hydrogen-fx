package com.hyd.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * (description)
 * created at 2018/2/6
 *
 * @author yidin
 */
public class Fxml {

    public static FXMLLoader load(String fxml, Object controller) throws FxException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Fxml.class.getResource(fxml));
            loader.setController(controller);
            loader.load();
            return loader;
        } catch (IOException e) {
            throw new FxException(e);
        }
    }

    public static FXMLLoader load(String fxml) throws FxException {
        return load(fxml, null);
    }
}
