package com.hyd.fx.enhancements;

import javafx.scene.control.ListCell;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.*;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ListCellEnhancements {

    static <T> void setOnDoubleClicked(ListCell<T> cell, Consumer<T> consumer) {
        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                consumer.accept(cell.getItem());
                e.consume();
            }
        });
    }

    static <T> void setOnSelected(ListCell<T> cell, Consumer<T> consumer) {
        cell.selectedProperty().addListener((_ob, _old, _new) -> {
            if (_new) {
                consumer.accept(cell.getItem());
            }
        });
    }

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

    /**
     * @deprecated this is buggy
     */
    @Deprecated
    static <T> void canDrag(ListCell<T> cell, Function<ListCell<T>, Object> dragValueFunc) {
        canDrag(cell, dragValueFunc, () -> cell.getListView().getItems().remove(cell.getItem()));
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
}
