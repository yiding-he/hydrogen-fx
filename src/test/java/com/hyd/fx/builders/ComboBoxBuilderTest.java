package com.hyd.fx.builders;

import com.hyd.fx.User;
import com.hyd.fx.cells.ListCellFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ComboBoxBuilderTest extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(new BorderPane(getComboBox()), 300, 100));
        primaryStage.show();
    }

    private ComboBox<User> getComboBox() {
        ComboBox<User> comboBox = new ComboBox<>();

        comboBox.getItems().addAll(
            new User(1, "user1", "_user1"),
            new User(2, "user2", "_user2"),
            new User(3, "user3", "_user3"),
            new User(4, "user4", "_user4"),
            new User(5, "user5", "_user5"),
            new User(6, "user6", "_user6")
        );

        ComboBoxBuilder.of(comboBox)
            .setCellFactory(new ListCellFactory<User>()
                .setToStringProperty(User::firstNameProperty))
            .setButtonCellToStringProperty(User::firstNameProperty);

        return comboBox;
    }
}