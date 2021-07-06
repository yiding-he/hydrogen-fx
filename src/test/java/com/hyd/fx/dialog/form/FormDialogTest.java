package com.hyd.fx.dialog.form;

import com.hyd.fx.app.AppPrimaryStage;
import com.hyd.fx.builders.ButtonBuilder;
import com.hyd.fx.builders.LayoutBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FormDialogTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppPrimaryStage.setPrimaryStage(primaryStage);

        primaryStage.setScene(new Scene(LayoutBuilder.vbox(50, 0,
            ButtonBuilder.button("打开对话框", this::openDialog))));

        primaryStage.show();
    }

    private void openDialog() {
        MyFormDialog myFormDialog = new MyFormDialog(AppPrimaryStage.getPrimaryStage());
        myFormDialog.setTitle("测试表单");
        myFormDialog.setWidth(500);
        myFormDialog.setHeight(400);
        myFormDialog.show();
    }

    //////////////////////////////////////////////////////////////

    public static class MyFormDialog extends FormDialog {

        public MyFormDialog(Stage owner) {
            super(owner);
            addField(new TextFormField("你好", "我好"));
            addField(new TextAreaFormField("大家好", "", 5, true));
            addField(new IntegerSpinnerFormField("菠萝啤", 1, 100, 1, true));
        }

        @Override
        protected void okButtonClicked(ActionEvent event) {
            close();
        }
    }
}
