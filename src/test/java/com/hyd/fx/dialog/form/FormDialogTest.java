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
            addField(new TextFormField().label("名字").text("1231231").validation(f -> {
                if (f.getText().isEmpty()) {
                    throw new RuntimeException("名字不能为空");
                }
            }));
            addField(new TextAreaFormField().label("大家好").rowCount(5).vGrow(true));
            addField(new IntegerSpinnerFormField().label("大小").min(1).max(1000).value(444).editable(true).validation(f -> {
                if (f.getValue() < 300) {
                    throw new RuntimeException("大小不能小于300");
                }
            }));
        }

        @Override
        protected void okButtonClicked(ActionEvent event) {
            if (validateFields()) {
                close();
            }
        }
    }
}
