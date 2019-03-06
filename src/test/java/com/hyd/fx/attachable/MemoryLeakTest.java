package com.hyd.fx.attachable;

import static com.hyd.fx.builders.ButtonBuilder.button;

import com.hyd.fx.builders.ButtonBuilder;
import com.hyd.fx.style.FxStyle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MemoryLeakTest extends Application {

  private Pane pane;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    pane = new Pane(resizablePane());
    BorderPane root = new BorderPane(pane);
    root.setTop(new Pane(button("refresh", this::refreshClicked)));

    primaryStage.setScene(new Scene(root, 400, 300));
    primaryStage.show();
  }

  private void refreshClicked() {
    this.pane.getChildren().clear();
    this.pane.getChildren().add(resizablePane());
  }

  private MyPane resizablePane() {
    MyPane pane = new MyPane();
    pane.setPrefSize(100, 100);
    pane.setLayoutX(100);
    pane.setLayoutY(100);
    pane.setBackground(FxStyle.background("#66AAFF"));

    Draggable.attachTo(pane);

    return pane;
  }

  private static class MyPane extends Pane {

  }
}
