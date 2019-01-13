package com.hyd.fx.attachable;

import com.hyd.fx.NodeUtils;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.hyd.fx.NodeUtils.*;

/**
 * 让一个（或一组）Node 可以被拖动位置
 *
 * @author yidin
 */
public class Draggable extends Attachable<Node> {

    private DraggingStatus draggingStatus = DraggingStatus.MouseReleased;

    private double[] startMousePosition;

    private Map<Node, double[]> startNodePositions = new HashMap<>();

    private Bounds startSurroundBounds;         // 起始包围矩形框（包围所有 node 的矩形）

    private double[] draggingNodeInnerOffset;   // 被拖拽的 Node 在包围矩形框中的位置

    private List<Node> nodes;

    private boolean outOfRangeEnabled = false;

    private Predicate<Node> draggable;

    private Runnable onDragFinished;

    private Runnable onDragStarted;

    private final EventHandler<MouseEvent> onMousePressed = this::onMousePressed;

    private final EventHandler<MouseEvent> onMouseDragged = this::onMouseDragged;

    private final EventHandler<MouseEvent> onMouseReleased = this::onMouseReleased;

    //////////////////////////////////////////////////////////////

    public static Draggable attachTo(Node... nodes) {
        return attachTo(Arrays.asList(nodes));
    }

    public static Draggable attachTo(Collection<? extends Node> nodes) {
        return new Draggable(nodes);
    }

    public static boolean isAttached(Node node) {
        return getAttachable(node, Draggable.class) != null;
    }

    public static void detach(Node node) {
        Draggable draggable = of(node);
        if (draggable != null) {
            draggable.detach();
        }
    }

    public static Draggable of(Node node) {
        return getAttachable(node, Draggable.class);
    }

    //////////////////////////////////////////////////////////////

    // 为一组 Node 创建一个统一的 Draggable 对象（亦可用于单个 Node）
    private Draggable(Collection<? extends Node> nodes) {
        super(nodes);
        this.nodes = new ArrayList<>(nodes);
        init();
    }

    // 设置是否允许将 Node 拖动到父元素范围之外
    public Draggable outOfRangeEnabled(boolean outOfRangeEnabled) {
        this.outOfRangeEnabled = outOfRangeEnabled;
        return this;
    }

    // 设置当拖动开始时要做什么
    public Draggable onDragStarted(Runnable onDragStarted) {
        this.onDragStarted = onDragStarted;
        return this;
    }

    // 设置当拖动结束时要做什么
    public Draggable onDragFinished(Runnable onDragFinished) {
        this.onDragFinished = onDragFinished;
        return this;
    }

    // 设置如何判断一个 Node 能否拖动（仅用于之前组建 Draggable 对象的 Node）
    public Draggable draggable(Predicate<Node> draggable) {
        this.draggable = draggable;
        return this;
    }

    ///////////////////////////////////////////////

    private boolean draggable(Node node) {
        return this.draggable == null || this.draggable.test(node);
    }

    private void init() {
        this.nodes.forEach(node -> {
            node.addEventFilter(MouseEvent.MOUSE_PRESSED, onMousePressed);
            node.addEventFilter(MouseEvent.MOUSE_DRAGGED, onMouseDragged);
            node.addEventFilter(MouseEvent.MOUSE_RELEASED, onMouseReleased);
        });
    }

    private void detach() {
        this.nodes.forEach(node -> {
            node.removeEventFilter(MouseEvent.MOUSE_PRESSED, onMousePressed);
            node.removeEventFilter(MouseEvent.MOUSE_DRAGGED, onMouseDragged);
            node.removeEventFilter(MouseEvent.MOUSE_RELEASED, onMouseReleased);
            unregister(node, Draggable.class);
        });
    }

    private boolean checkStatus(MouseEvent event) {
        Node node = (Node) event.getSource();

        return node.getParent() != null &&
                this.draggable(node) &&
                !node.isDisabled() &&
                !this.isDisabled() &&
                event.getButton() == MouseButton.PRIMARY;
    }

    private void forOtherNodes(Node eventNode, Consumer<Node> action) {
        for (Node node : nodes) {
            if (node != eventNode && !node.isDisabled() && draggable(node)) {
                action.accept(node);
            }
        }
    }

    //////////////////////////////////////////////////////////////

    private void onMousePressed(MouseEvent event) {
        if (!checkStatus(event)) {
            return;
        }

        Node node = (Node) event.getSource();
        if (!NodeUtils.isInNode(event.getX(), event.getY(), node)) {
            return;
        }

        double[] nodePosition = getNodePosition(node);
        this.startNodePositions.put(node, nodePosition);

        this.startSurroundBounds = getSurroundBounds(nodes);
        this.draggingNodeInnerOffset = new double[] {
                nodePosition[0] - startSurroundBounds.getMinX(),
                nodePosition[1] - startSurroundBounds.getMinY()
        };

        this.startMousePosition = getMousePositionInParent(event);
        this.draggingStatus = DraggingStatus.MouseDragging;

        forOtherNodes(node, _node ->
                startNodePositions.put(_node, NodeUtils.getNodePosition(_node)));

        if (this.onDragStarted != null) {
            this.onDragStarted.run();
        }
    }

    private void onMouseDragged(MouseEvent event) {
        if (!checkStatus(event)) {
            return;
        }

        if (this.draggingStatus != DraggingStatus.MouseDragging) {
            return;
        }

        Node node = (Node) event.getSource();
        double[] mousePositionInParent = getMousePositionInParent(event);
        double[] startNodePosition = startNodePositions.get(node);

        // 根据鼠标位置计算原始偏移
        double[] offset = {
                mousePositionInParent[0] - this.startMousePosition[0],
                mousePositionInParent[1] - this.startMousePosition[1]
        };

        double nodeLayoutX = startNodePosition[0] + offset[0];
        double nodeLayoutY = startNodePosition[1] + offset[1];

        // 根据边界限制计算最终位置
        if (!outOfRangeEnabled) {
            Bounds parentBounds = node.getParent().getLayoutBounds();

            nodeLayoutX = Math.max(draggingNodeInnerOffset[0], nodeLayoutX);
            nodeLayoutY = Math.max(draggingNodeInnerOffset[1], nodeLayoutY);
            nodeLayoutX = Math.min(parentBounds.getWidth() - startSurroundBounds.getWidth() + draggingNodeInnerOffset[0], nodeLayoutX);
            nodeLayoutY = Math.min(parentBounds.getHeight() - startSurroundBounds.getHeight() + draggingNodeInnerOffset[1], nodeLayoutY);
        }

        // 根据最终位置重新计算偏移
        offset[0] = nodeLayoutX - startNodePosition[0];
        offset[1] = nodeLayoutY - startNodePosition[1];

        node.setLayoutX(nodeLayoutX);
        node.setLayoutY(nodeLayoutY);

        forOtherNodes(node, _node -> {
            _node.setLayoutX(startNodePositions.get(_node)[0] + offset[0]);
            _node.setLayoutY(startNodePositions.get(_node)[1] + offset[1]);
        });
    }

    private void onMouseReleased(MouseEvent event) {
        if (!checkStatus(event)) {
            return;
        }

        this.draggingStatus = DraggingStatus.MouseReleased;

        if (this.onDragFinished != null) {
            this.onDragFinished.run();
        }
    }
}
