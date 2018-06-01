package com.hyd.fx.attachable;

import com.hyd.fx.Position;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;

import java.util.Collection;

/**
 * (description)
 * created at 2017/10/24
 *
 * @author yidin
 */
public abstract class ScrollPaneDraggingAttachable extends DraggingAttachable<ScrollPane> {

    protected Position startScrollPos;    // 开始时的滚动条位置

    protected Position startViewportPos;  // 开始时的鼠标位置（ViewPort）

    protected Position startContentPos;   // 开始时的鼠标位置（Content）

    protected ScrollPaneDraggingAttachable(Collection<? extends ScrollPane> t) {
        super(t);
    }

    @Override
    protected void onDragStart(MouseEvent event) {
        ScrollPane scrollPane = getAttachedOne();
        startScrollPos = new Position(scrollPane.getHvalue(), scrollPane.getVvalue());
        startViewportPos = new Position(event.getX(), event.getY());
        startContentPos = new Position(toContentX(scrollPane, event.getX()), toContentY(scrollPane, event.getY()));
    }

    @Override
    protected void onDragFinish(MouseEvent event) {
    }

    protected double toContentX(ScrollPane scrollPane, double viewPortX) {
        double viewPortWidth = scrollPane.getViewportBounds().getWidth();
        double contentWidth = scrollPane.getContent().getLayoutBounds().getWidth();

        if (contentWidth <= viewPortWidth) {
            return viewPortX;
        } else {
            return viewPortX + (contentWidth - viewPortWidth) * scrollPane.getHvalue();
        }
    }

    protected double toContentY(ScrollPane scrollPane, double viewPortY) {
        double viewPortHeight = scrollPane.getViewportBounds().getHeight();
        double contentHeight = scrollPane.getContent().getLayoutBounds().getHeight();

        if (contentHeight <= viewPortHeight) {
            return viewPortY;
        } else {
            return viewPortY + (contentHeight - viewPortHeight) * scrollPane.getHvalue();
        }
    }

    @Override
    protected boolean checkStatus(MouseEvent event) {
        return super.checkStatus(event) && getAttachedOne().getContent() != null;
    }

}
