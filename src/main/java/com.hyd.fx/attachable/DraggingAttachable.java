package com.hyd.fx.attachable;

import com.hyd.fx.NodeUtils;
import com.hyd.fx.Position;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.Collection;

/**
 * (description)
 * created at 2017/10/24
 *
 * @author yidin
 */
public abstract class DraggingAttachable<T extends Node> extends Attachable<T> {

    protected DraggingStatus draggingStatus = DraggingStatus.MouseReleased;

    protected DraggingAttachable(Collection<? extends T> t) {
        super(t);
        setupEvents();
    }

    private void setupEvents() {
        Node node = getAttachedOne();

        node.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (checkStatus(event)) {
                this.draggingStatus = DraggingStatus.MousePressed;
                this.onDragStart(event);
            }
        });

        node.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            if (checkStatus(event)) {

                if (draggingStatus == DraggingStatus.MousePressed
                        || draggingStatus == DraggingStatus.MouseDragging) {

                    this.draggingStatus = DraggingStatus.MouseDragging;
                    this.onDragging(event);
                }
            }
        });

        node.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (checkStatus(event)) {
                this.draggingStatus = DraggingStatus.MouseReleased;
                this.onDragFinish(event);
            }
        });
    }

    // 判断是否可以触发一系列事件
    protected boolean checkStatus(MouseEvent event) {
        return getAttachedOne() != null;
    }

    protected abstract void onDragStart(MouseEvent event);

    protected abstract void onDragging(MouseEvent event);

    protected abstract void onDragFinish(MouseEvent event);

    protected Position getMousePositionInParent(MouseEvent mouseEvent) {
        double[] p = NodeUtils.getMousePositionInParent(mouseEvent);
        return new Position(p[0], p[1]);
    }
}
