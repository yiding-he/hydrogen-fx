package com.hyd.fx.attachable;

import com.hyd.fx.NodeUtils;
import com.hyd.fx.Position;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.Collections;

/**
 * 让一个节点可选择
 *
 * @author yidin
 */
public class Selectable extends Attachable<Node> {

    private final EventHandler<MouseEvent> onMouseClicked = this::onMouseClicked;

    private final EventHandler<MouseEvent> onMousePressed = this::onMousePressed;

    private final EventHandler<MouseEvent> onMouseReleased = this::onMouseReleased;

    private BooleanProperty selected = new SimpleBooleanProperty();

    private BooleanProperty selectable = new SimpleBooleanProperty(true);

    private int clickCount = 1;

    private boolean switchOnClick = true;

    public static Selectable attachTo(Node node) {
        return new Selectable(node);
    }

    public static Selectable of(Node node) {
        return getAttachable(node, Selectable.class);
    }

    public static boolean isAttached(Node node) {
        return getAttachable(node, Draggable.class) != null;
    }

    public static void detach(Node node) {
        Selectable selectable = of(node);
        if (selectable != null) {
            selectable.detach();
        }
    }

    //////////////////////////////////////////////////////////////

    private Selectable(Node node) {
        super(Collections.singleton(node));
        this.init();
    }

    // 用于临时设置 Node 的可选状态
    public Selectable selectable(boolean selectable) {
        this.selectable.set(selectable);
        return this;
    }

    // 设置点击已选状态的 Node 时是否将其切换到未选状态
    public Selectable switchOnClick(boolean switchOnClick) {
        this.switchOnClick = switchOnClick;
        return this;
    }

    public Selectable clickCount(int clickCount) {
        this.clickCount = clickCount;
        return this;
    }

    public Selectable selected(boolean selected) {
        this.selected.set(selected);
        return this;
    }

    public void detach() {
        Node node = getAttachedOne();
        node.removeEventFilter(MouseEvent.MOUSE_CLICKED, onMouseClicked);
        node.removeEventFilter(MouseEvent.MOUSE_PRESSED, onMousePressed);
        node.removeEventFilter(MouseEvent.MOUSE_RELEASED, onMouseReleased);
        unregister(node, Selectable.class);
    }

    public boolean isSelectable() {
        return selectable.get();
    }

    public boolean isSelected() {
        return selectable.get() && selected.get();
    }

    public BooleanProperty selectableProperty() {
        return selectable;
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    private Position downMousePos;

    private Position upMousePos;

    private void init() {
        Node node = getAttachedOne();
        node.addEventFilter(MouseEvent.MOUSE_CLICKED, onMouseClicked);
        node.addEventFilter(MouseEvent.MOUSE_PRESSED, onMousePressed);
        node.addEventFilter(MouseEvent.MOUSE_RELEASED, onMouseReleased);
    }

    private void onMouseReleased(MouseEvent event) {
        upMousePos = new Position(event.getScreenX(), event.getScreenY());
    }

    private void onMousePressed(MouseEvent event) {
        downMousePos = new Position(event.getScreenX(), event.getScreenY());
    }

    private void onMouseClicked(MouseEvent event) {
        if (!isClick()) {
            return;
        }

        Node n = (Node) event.getSource();

        if (!NodeUtils.isInNode(event, n)) {
            return;
        }

        if (event.getClickCount() == this.clickCount && isSelectable()) {
            if (!isSelected()) {
                selected(true);
            } else if (switchOnClick) {
                selected(false);
            }
        }
    }

    private boolean isClick() {
        return downMousePos == null || upMousePos == null || downMousePos.equals(upMousePos);
    }
}

