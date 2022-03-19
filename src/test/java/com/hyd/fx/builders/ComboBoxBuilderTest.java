package com.hyd.fx.builders;

import com.hyd.fx.User;
import com.hyd.fx.UserType;
import com.hyd.fx.cells.ListCellFactory;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.hyd.fx.builders.ImageBuilder.imageView;

public class ComboBoxBuilderTest {

    public static void main(String[] args) {
        Application.launch(TestApp.class, args);
    }

    public static class TestApp extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {
            primaryStage.setScene(new Scene(new BorderPane(getComboBox()), 300, 100));
            primaryStage.show();
        }

        private ComboBox<User> getComboBox() {

            ComboBox<User> comboBox = new ComboBox<>();

            comboBox.getItems().addAll(
                new User(1, "user1", "_user1", UserType.Admin),
                new User(2, "user2", "_user2", UserType.User),
                new User(3, "user3", "_user3", UserType.Admin),
                new User(4, "user4", "_user4", UserType.User),
                new User(5, "user5", "_user5", UserType.Admin),
                new User(6, "user6", "_user6", UserType.User)
            );

            final Map<UserType, Supplier<ImageView>> userLogoMappings = new HashMap<>();
            userLogoMappings.put(UserType.User, () -> imageView("/music.png", 20));
            userLogoMappings.put(UserType.Admin, () -> imageView("/folder.png", 20));

            final Function<User, Node> userLogoFactory =
                user -> userLogoMappings.get(user.getUserType()).get();

            ComboBoxBuilder.of(comboBox)
                .setCellFactory(new ListCellFactory<User>()
                    .withTextProperty(User::firstNameProperty)
                    .withGraphicFunction(userLogoFactory)
                );

            comboBox.getSelectionModel().select(0);
            return comboBox;
        }
    }
}