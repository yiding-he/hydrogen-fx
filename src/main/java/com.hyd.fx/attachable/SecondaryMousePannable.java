package com.hyd.fx.attachable;

import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;

import java.util.Collection;
import java.util.Collections;

/**
 * 使 ScrollPane 可以用鼠标右键拖动内容
 *
 * @author yidin
 */
public class SecondaryMousePannable extends ScrollPaneDraggingAttachable {

    public static SecondaryMousePannable attachTo(ScrollPane scrollPane) {
        return new SecondaryMousePannable(Collections.singleton(scrollPane));
    }

    public static SecondaryMousePannable of(ScrollPane scrollPane) {
        return getAttachable(scrollPane, SecondaryMousePannable.class);
    }

    private SecondaryMousePannable(Collection<? extends ScrollPane> t) {
        super(t);
    }

    @Override
    protected boolean checkStatus(MouseEvent event) {
        // 当鼠标右键操作时才触发相关处理逻辑
        return super.checkStatus(event) && event.isSecondaryButtonDown();
    }

    @Override
    protected void onDragging(MouseEvent event) {
        ScrollPane scrollPane = getAttachedOne();
        Bounds viewportBounds = scrollPane.getViewportBounds();
        Bounds contentBounds = scrollPane.getContent().getLayoutBounds();
        double mouseMovedX = event.getX() - startViewportPos.getX();
        double mouseMovedY = event.getY() - startViewportPos.getY();

        scrollPane.setHvalue(startScrollPos.getX() - (mouseMovedX / (contentBounds.getWidth() - viewportBounds.getWidth())));
        scrollPane.setVvalue(startScrollPos.getY() - (mouseMovedY / (contentBounds.getHeight() - viewportBounds.getHeight())));
    }
}
