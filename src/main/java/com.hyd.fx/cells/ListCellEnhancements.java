package com.hyd.fx.cells;

import java.util.function.Consumer;
import java.util.function.Function;
import javafx.scene.control.ListCell;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class ListCellEnhancements {

  public static void addClassOnMouseHover(ListCell cell, String styleClass) {
    cell.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> {
      ListCell<?> _cell = (ListCell) event.getSource();
      MultipleSelectionModel<?> sm = _cell.getListView().getSelectionModel();
      if (sm.getSelectedItem() == null || !sm.getSelectedItem().equals(_cell.getItem())) {
        _cell.getStyleClass().add(styleClass);
      }
    });
    cell.addEventFilter(MouseEvent.MOUSE_EXITED, event -> {
      ListCell<?> _cell = (ListCell) event.getSource();
      _cell.getStyleClass().remove(styleClass);
    });
  }

  public static <T> void canDrag(
      ListCell<T> cell,
      Function<ListCell<T>, Object> dragValueFunc, Runnable onDragDone) {
    cell.setOnDragDetected(event -> {
      ListCell<?> source = (ListCell) event.getSource();
      Dragboard db = source.startDragAndDrop(TransferMode.ANY);
      ClipboardContent content = new ClipboardContent();
      content.put(DataFormat.PLAIN_TEXT, dragValueFunc.apply(cell));
      db.setContent(content);
      event.consume();
    });
    cell.setOnDragDone(event -> {
      onDragDone.run();
      event.consume();
    });
  }

  public static void acceptDrag(ListCell cell, Consumer<Object> onDragDropped) {
    cell.setOnDragOver(event -> {
      event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
      event.consume();
    });
    cell.setOnDragDropped(event -> {
      Dragboard db = event.getDragboard();
      Object content = db.getContent(DataFormat.PLAIN_TEXT);
      onDragDropped.accept(content);
      event.setDropCompleted(true);
      event.consume();
    });
  }

  public static void addClassOnDragEnter(ListCell cell, String styleClass) {
    cell.addEventFilter(DragEvent.DRAG_ENTERED, event -> {
      ListCell<?> _cell = (ListCell) event.getSource();
      _cell.getStyleClass().add(styleClass);
    });
    cell.addEventFilter(DragEvent.DRAG_EXITED, event -> {
      ListCell<?> _cell = (ListCell) event.getSource();
      _cell.getStyleClass().remove(styleClass);
    });
  }
}
