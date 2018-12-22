package com.hyd.fx.attachable;

import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;

import java.util.Collection;
import java.util.Collections;

/**
 * 当鼠标拖拽时自动滚动 ScrollPane
 *
 * @author yidin
 */
public class AutoScrollable extends ScrollPaneDraggingAttachable {

    public static final double DEFAULT_SCROLL_STEP = 10.0;

    public static final double DEFAULT_SCROLL_EDGE = 5;

    public static AutoScrollable attachTo(ScrollPane scrollPane) {
        return new AutoScrollable(Collections.singleton(scrollPane));
    }

    public static AutoScrollable of(ScrollPane scrollPane) {
        return getAttachable(scrollPane, AutoScrollable.class);
    }

    private double scrollStep = DEFAULT_SCROLL_STEP;

    private double scrollEdge = DEFAULT_SCROLL_EDGE;

    private AutoScrollable(Collection<? extends ScrollPane> t) {
        super(t);
    }

    public AutoScrollable scrollStep(double scrollStep) {
        this.scrollStep = scrollStep;
        return this;
    }

    public AutoScrollable scrollEdge(double scrollEdge) {
        this.scrollEdge = scrollEdge;
        return this;
    }

    @Override
    protected void onDragging(MouseEvent event) {
        ScrollPane scrollPane = getAttachedOne();
        Bounds viewportBounds = scrollPane.getViewportBounds();

        if (event.getX() < scrollEdge) {
            scroll(scrollPane, Side.LEFT);
        } else if (event.getX() > viewportBounds.getWidth() - scrollEdge) {
            scroll(scrollPane, Side.RIGHT);
        }

        if (event.getY() < scrollEdge) {
            scroll(scrollPane, Side.TOP);
        } else if (event.getY() > viewportBounds.getHeight() - scrollEdge) {
            scroll(scrollPane, Side.BOTTOM);
        }
    }

    private void scroll(ScrollPane scrollPane, Side side) {
        double step;
        if (side == Side.LEFT || side == Side.RIGHT) {
            step = getScrollStepH(scrollPane);
            if (side == Side.LEFT) {
                scrollPane.setHvalue(Math.max(0.0, scrollPane.getHvalue() - step));
            } else {
                scrollPane.setHvalue(Math.min(1.0, scrollPane.getHvalue() + step));
            }
        } else {
            step = getScrollStepV(scrollPane);
            if (side == Side.TOP) {
                scrollPane.setVvalue(Math.max(0.0, scrollPane.getVvalue() - step));
            } else {
                scrollPane.setVvalue(Math.min(1.0, scrollPane.getVvalue() + step));
            }
        }
    }

    private double getScrollStepH(ScrollPane scrollPane) {
        double viewPortWidth = scrollPane.getViewportBounds().getWidth();
        double contentWidth = scrollPane.getContent().getLayoutBounds().getWidth();

        return getScrollStep0(viewPortWidth, contentWidth);
    }

    private double getScrollStepV(ScrollPane scrollPane) {
        double viewPortHeight = scrollPane.getViewportBounds().getHeight();
        double contentHeight = scrollPane.getContent().getLayoutBounds().getHeight();

        return getScrollStep0(viewPortHeight, contentHeight);
    }

    private double getScrollStep0(double viewPortLength, double contentLength) {
        if (contentLength <= viewPortLength) {
            return  0;
        } else {
            return scrollStep / (contentLength - viewPortLength);
        }
    }

    @Override
    protected boolean checkStatus(MouseEvent event) {
        return super.checkStatus(event) && event.isPrimaryButtonDown();
    }
}
