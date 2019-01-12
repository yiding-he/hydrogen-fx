package com.hyd.fx.builders;

import com.hyd.fx.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TableViewBuilderTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TableView<User> tableView = new TableView<>();
        TableViewBuilder.of(tableView)
                .addIntPropertyColumn("ID", user -> user.idProperty().asObject())
                .addStrPropertyColumn("First Name", User::firstNameProperty)
                .addStrPropertyColumn("Last Name", User::lastNameProperty);

        BorderPane borderPane = new BorderPane(tableView);
        borderPane.setTop(ButtonBuilder.button("Add User", () -> {
            tableView.getItems().add(new User(1, "aaa", "bbb"));
        }));

        primaryStage.setScene(new Scene(borderPane, 400, 400));
        primaryStage.show();
    }
}