package com.hyd.fx;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * (description)
 * created at 2018/2/6
 *
 * @author yidin
 */
public class Fxml {

    /**
     * 使用指定的 Controller 对象来加载 FXML。注意如果 controller
     * 参数不为 null，则 FXML 当中不允许使用 fx:controller。
     *
     * @param fxml       FXML 资源路径
     * @param controller Controller 对象
     *
     * @return 已加载完成的 FXMLLoader 对象，可调用 getRoot() 或 getController()
     *
     * @throws FxException 如果加载失败
     */
    public static FXMLLoader load(String fxml, ResourceBundle resourceBundle, Object controller) throws FxException {
        try {
            FXMLLoader loader = createFXMLLoader();
            loader.setLocation(Fxml.class.getResource(fxml));

            if (resourceBundle != null) {
                loader.setResources(resourceBundle);
            }

            if (controller != null) {
                // 如果 FXML 指定了 fx:controller，就不允许使用 setController
                // 仅当 FXML 指定了 fx:controller 时才会调用 controllerFactory
                loader.setController(controller);
            }

            loader.load();
            return loader;
        } catch (IOException e) {
            throw new FxException(e);
        }
    }

    private static FXMLLoader createFXMLLoader() {
        return new FXMLLoader();
    }

    public static FXMLLoader load(String fxml) throws FxException {
        return load(fxml, null, null);
    }

    public static FXMLLoader load(String fxml, ResourceBundle resourceBundle) throws FxException {
        return load(fxml, resourceBundle, null);
    }

    public static FXMLLoader createFXMLLoader(
        String fxml, ResourceBundle resourceBundle, Callback<Class<?>, Object> controllerFactory
    ) {
        FXMLLoader fxmlLoader = new FXMLLoader(Fxml.class.getResource(fxml));
        if (resourceBundle != null) {
            fxmlLoader.setResources(resourceBundle);
        }
        if (controllerFactory != null) {
            fxmlLoader.setControllerFactory(controllerFactory);
        }
        return fxmlLoader;
    }
}
