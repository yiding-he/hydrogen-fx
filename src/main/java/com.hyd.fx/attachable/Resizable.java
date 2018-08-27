package com.hyd.fx.attachable;

import com.hyd.fx.NodeUtils;
import com.hyd.fx.utils.Nums;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

import static com.hyd.fx.NodeUtils.getMousePositionInParent;
import static javafx.scene.input.MouseEvent.*;

/**
 * @author yidin
 */
public class Resizable extends Attachable<Region> {

    public static final double MOUSE_SENSOR_MARGIN = 5;

    public static final int MIN_HEIGHT = 3;

    private EventHandler<MouseEvent> onMouseMoved;

    private EventHandler<MouseEvent> onMousePressed;

    private EventHandler<MouseEvent> onMouseDragged;

    private EventHandler<MouseEvent> onMouseReleased;

    public static Resizable attachTo(Region node) {
        return new Resizable(Collections.singleton(node));
    }

    public static Resizable of(Region node) {
        return getAttachable(node, Resizable.class);
    }

    public static boolean isAttached(Region node) {
        return getAttachable(node, Draggable.class) != null;
    }

    public static void detach(Region node) {
        Resizable resizable = of(node);
        if (resizable != null) {
            resizable.detach();
        }
    }

    ///////////////////////////////////////////////

    private Side draggingSide = null;

    private double[] dragStartMousePosition;

    private double[] dragStartNodePosition;

    private double[] dragStartNodeSize;

    private Consumer<Region> onResizeStarted;

    private Consumer<Region> onResizeFinished;

    private Resizable(Collection<? extends Region> t) {
        super(t);
        t.forEach(this::initNode);
    }

    public Resizable onResizeStarted(Consumer<Region> onResizeStarted) {
        this.onResizeStarted = onResizeStarted;
        return this;
    }

    public Resizable onResizeFinished(Consumer<Region> onResizeFinished) {
        this.onResizeFinished = onResizeFinished;
        return this;
    }

    private void detach() {
        Region region = this.getAttachedOne();

        region.removeEventFilter(MOUSE_MOVED, onMouseMoved);
        region.removeEventFilter(MOUSE_PRESSED, onMousePressed);
        region.removeEventFilter(MOUSE_DRAGGED, onMouseDragged);
        region.removeEventFilter(MOUSE_RELEASED, onMouseReleased);

        unregister(region, Resizable.class);
    }

    private void initNode(Region node) {
        onMouseMoved = mouseEvent -> onMouseMoved(mouseEvent, node);
        onMousePressed = mouseEvent -> onMousePressed(mouseEvent, node);
        onMouseDragged = mouseEvent -> onMouseDragged(mouseEvent, node);
        onMouseReleased = mouseEvent -> onMouseReleased(mouseEvent, node);

        node.addEventFilter(MOUSE_MOVED, onMouseMoved);
        node.addEventFilter(MOUSE_PRESSED, onMousePressed);
        node.addEventFilter(MOUSE_DRAGGED, onMouseDragged);
        node.addEventFilter(MOUSE_RELEASED, onMouseReleased);
    }

    private boolean checkStatus(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        return !this.isDisabled() &&
                !node.isDisabled() &&
                mouseEvent.getButton() == MouseButton.PRIMARY;
    }

    private boolean checkNodeStatus(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        return !this.isDisabled() &&
                !node.isDisabled();
    }

    private boolean isInResizeArea(MouseEvent mouseEvent, Region node) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();

        double maxX = node.getPrefWidth() - MOUSE_SENSOR_MARGIN;
        double maxY = node.getPrefHeight() - MOUSE_SENSOR_MARGIN;

        return NodeUtils.isInArea(x, y, 0, node.getPrefWidth(), 0, node.getPrefHeight()) &&
                !NodeUtils.isInArea(x, y, MOUSE_SENSOR_MARGIN, maxX, MOUSE_SENSOR_MARGIN, maxY);
    }

    private void onMouseMoved(MouseEvent mouseEvent, Region node) {
        if (!checkNodeStatus(mouseEvent)) {
            return;
        }

        double x = mouseEvent.getX();

        if (!isInResizeArea(mouseEvent, node)) {
            if (node.getCursor() != Cursor.DEFAULT) {
                node.setCursor(Cursor.DEFAULT);
            }
            return;
        }

        if (x <= MOUSE_SENSOR_MARGIN || x >= node.getPrefWidth() - MOUSE_SENSOR_MARGIN) {
            if (node.getCursor() != Cursor.H_RESIZE) {
                node.setCursor(Cursor.H_RESIZE);
            }
        } else if (node.getCursor() != Cursor.V_RESIZE) {
            node.setCursor(Cursor.V_RESIZE);

        }
    }

    private void onMousePressed(MouseEvent mouseEvent, Region node) {
        if (!checkStatus(mouseEvent)) {
            return;
        }

        double x = mouseEvent.getX();
        double y = mouseEvent.getY();

        double maxX = node.getPrefWidth() - MOUSE_SENSOR_MARGIN;

        if (!isInResizeArea(mouseEvent, node)) {
            this.draggingSide = null;
            return;
        }

        this.dragStartNodePosition = new double[]{node.getLayoutX(), node.getLayoutY()};
        this.dragStartNodeSize = new double[]{node.getPrefWidth(), node.getPrefHeight()};
        this.dragStartMousePosition = getMousePositionInParent(mouseEvent);

        if (x <= MOUSE_SENSOR_MARGIN) {
            this.draggingSide = Side.LEFT;
        } else if (x >= maxX) {
            this.draggingSide = Side.RIGHT;
        } else if (y <= MOUSE_SENSOR_MARGIN) {
            this.draggingSide = Side.TOP;
        } else {
            this.draggingSide = Side.BOTTOM;
        }

        Draggable draggable = Draggable.of(node);
        if (draggable != null) {
            draggable.setDisabled(true);
        }

        if (this.onResizeStarted != null) {
            this.onResizeStarted.accept(node);
        }
    }

    private void onMouseDragged(MouseEvent mouseEvent, Region node) {
        if (!checkStatus(mouseEvent)) {
            return;
        }

        if (this.draggingSide == null) {
            return;
        }

        double[] mousePosition = getMousePositionInParent(mouseEvent);
        Bounds parentBounds = node.getParent().getLayoutBounds();

        double minDeltaX;
        double minDeltaY;
        double maxDeltaX;
        double maxDeltaY;

        double deltaX = mousePosition[0] - this.dragStartMousePosition[0];
        double deltaY = mousePosition[1] - this.dragStartMousePosition[1];


        if (this.draggingSide == Side.BOTTOM) {
            minDeltaY = -dragStartNodeSize[1];
            maxDeltaY = parentBounds.getHeight() - dragStartNodePosition[1] - dragStartNodeSize[1];
            deltaY = Nums.between(deltaY, minDeltaY, maxDeltaY);
            double newHeight = this.dragStartNodeSize[1] + deltaY;
            node.setPrefHeight(Math.max(newHeight, MIN_HEIGHT));

        } else if (this.draggingSide == Side.TOP) {
            minDeltaY = -dragStartNodePosition[1];
            maxDeltaY = dragStartNodeSize[1];
            deltaY = Nums.between(deltaY, minDeltaY, maxDeltaY);
            double newHeight = Math.max(dragStartNodeSize[1] - deltaY, MIN_HEIGHT);
            node.setLayoutY(dragStartNodePosition[1] + dragStartNodeSize[1] - newHeight);
            node.setPrefHeight(newHeight);

        } else if (this.draggingSide == Side.RIGHT) {
            minDeltaX = -dragStartNodeSize[0];
            maxDeltaX = parentBounds.getWidth() - dragStartNodePosition[0] - dragStartNodeSize[0];
            deltaX = Nums.between(deltaX, minDeltaX, maxDeltaX);
            double newWidth = this.dragStartNodeSize[0] + deltaX;
            node.setPrefWidth(Math.max(newWidth, MIN_HEIGHT));

        } else if (this.draggingSide == Side.LEFT) {
            minDeltaX = -dragStartNodePosition[0];
            maxDeltaX = dragStartNodeSize[0];
            deltaX = Nums.between(deltaX, minDeltaX, maxDeltaX);
            double newWidth = Math.max(dragStartNodeSize[0] - deltaX, MIN_HEIGHT);
            node.setLayoutX(dragStartNodePosition[0] + dragStartNodeSize[0] - newWidth);
            node.setPrefWidth(newWidth);
        }
    }

    private void onMouseReleased(MouseEvent mouseEvent, Region node) {
        if (!checkStatus(mouseEvent)) {
            return;
        }

        if (this.draggingSide != null) {
            this.draggingSide = null;

            Draggable draggable = Draggable.of(node);
            if (draggable != null) {
                draggable.setDisabled(false);
            }

            if (this.onResizeFinished != null) {
                this.onResizeFinished.accept(node);
            }
        }
    }

}
