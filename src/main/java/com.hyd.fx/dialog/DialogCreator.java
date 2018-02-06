package com.hyd.fx.dialog;

import com.hyd.fx.app.AppLogo;
import com.hyd.fx.app.AppPrimaryStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * (description)
 * created at 2018/2/5
 *
 * @author yidin
 */
public class DialogCreator {

    public static <T> T openDialog(String fxml, String title) throws IOException {
        Stage owner = AppPrimaryStage.getPrimaryStage();
        Image logo = AppLogo.getLogo();

        return openDialog(fxml, title, owner, logo);
    }

    public static <T> T openDialog(String fxml, String title, Stage owner, Image icon) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(DialogCreator.class.getResource(fxml));

        Parent root = fxmlLoader.load();
        T t = fxmlLoader.getController();

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.getIcons().add(icon);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        return t;
    }

}
