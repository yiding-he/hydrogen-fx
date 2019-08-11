package com.hyd.fx.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javafx.scene.Node;
import javafx.scene.input.*;

public class DragDropHelper {

    public static DragDropHelper of(Node node) {
        return new DragDropHelper(node);
    }

    private Node node;

    private Map<DataFormat, Consumer<Object>> consumerMap = new HashMap<>();

    private DragDropHelper(Node node) {
        this.node = node;
    }

    public DragDropHelper acceptDrop(DataFormat... dataFormats) {
        node.setOnDragOver(event -> {
            if (Stream.of(dataFormats).anyMatch(
                dataFormat -> event.getDragboard().hasContent(dataFormat))) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }
        });

        node.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            for (Entry<DataFormat, Consumer<Object>> entry : consumerMap.entrySet()) {
                if (db.hasContent(entry.getKey())) {
                    Object content = db.getContent(entry.getKey());
                    entry.getValue().accept(content);
                    event.setDropCompleted(true);
                    event.consume();
                    return;
                }
            }
        });

        return this;
    }

    public DragDropHelper whenDataDropped(DataFormat dataFormat, Consumer<Object> consumer) {
        this.consumerMap.put(dataFormat, consumer);
        return this;
    }
}
