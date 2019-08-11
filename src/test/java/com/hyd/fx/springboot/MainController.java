package com.hyd.fx.springboot;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private FormController formController;

    public Label label1;

    public Label label2;

    public Label label3;

    public TextField txtUsername;

    public void initialize() {
        this.label1.textProperty().bind(formController.txtExamNo.textProperty());
        this.label2.textProperty().bind(formController.txtStudentName.textProperty());
        this.txtUsername.setText(userService.getUserName());
    }
}
