package com.hyd.fx.builders;

import static com.hyd.fx.builders.ButtonBuilder.button;
import static com.hyd.fx.builders.LayoutBuilder.hbox;
import static com.hyd.fx.builders.LayoutBuilder.vbox;

import com.hyd.fx.User;
import com.hyd.fx.UserType;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TableViewBuilderTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        TableView<User> tableView = new TableView<>();

        TableViewBuilder.of(tableView)
            .addIntPropertyColumn("ID", user -> user.idProperty().asObject())
            .addStrPropertyColumn("First Name", User::firstNameProperty)
            .addStrPropertyColumn("Last Name", User::lastNameProperty)
            .addStrColumn("Type", user -> user.getUserType().name())
        ;

        AtomicInteger userIdCounter = new AtomicInteger();
        Runnable addUser = () -> tableView.getItems().add(
            new User(userIdCounter.incrementAndGet(), "aaa", "bbb", UserType.User)
        );

        Parent root = vbox(10, 10,
            hbox(0, 10,
                button("Add User", addUser)
            ),
            tableView
        );

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }
}