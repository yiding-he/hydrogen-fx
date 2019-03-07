package com.hyd.fx.enhancements;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.application.Platform;
import javafx.scene.control.ListCell;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public interface ListCellEnhancements {

  static void addClassOnMouseHover(ListCell cell, String styleClass) {
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

  static <T> void canDrag(ListCell<T> cell, Function<ListCell<T>, Object> dragValueFunc) {
    canDrag(cell, dragValueFunc, () -> {
      cell.getListView().getItems().remove(cell.getIndex());
    });
  }

  static <T> void canDrag(
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
      if (event.isAccepted()) {
        onDragDone.run();
      }
      event.consume();
    });
  }

  static void acceptDrag(ListCell cell, Consumer<Object> onDragDropped) {
    cell.setOnDragOver(event -> {
      event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
      event.consume();
    });
    cell.setOnDragDropped(event -> {
      ListCell<?> _cell = (ListCell) event.getSource();
      Dragboard db = event.getDragboard();
      Object content = db.getContent(DataFormat.PLAIN_TEXT);
      onDragDropped.accept(content);
      _cell.getListView().requestFocus();
      event.setDropCompleted(true);
      event.consume();
    });
  }

  static void addClassOnDragEnter(ListCell cell, String styleClass) {
    cell.addEventFilter(DragEvent.DRAG_ENTERED, event -> {
      ListCell<?> _cell = (ListCell) event.getSource();
      if (!_cell.isEmpty()) {
        _cell.getStyleClass().add(styleClass);
      }
    });
    cell.addEventFilter(DragEvent.DRAG_EXITED, event -> {
      ListCell<?> _cell = (ListCell) event.getSource();
      if (!_cell.isEmpty()) {
        _cell.getStyleClass().remove(styleClass);
      }
    });
  }

  static <T> void addAfterCell(ListCell<T> cell, T data) {
    List<T> items = cell.getListView().getItems();
    int index = cell.getIndex();

    if (cell.isEmpty()) {
      index = items.isEmpty() ? -1 : items.size() - 1;
    }

    items.add(index + 1, data);

    final int _index = index + 1;
    Platform.runLater(() ->
        cell.getListView().getSelectionModel().select(_index + 1));
  }
}
