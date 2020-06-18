package com.hyd.fx.components;

import com.hyd.fx.NodeUtils;
import com.hyd.fx.Size;
import com.hyd.fx.style.FxBorderBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * 附加到任意一个 Pane 上，使其具有框选功能
 *
 * @author yidin
 */
public class SelectionArea extends Pane {

    public static final MouseButton DEFAULT_EFFECTIVE_MOUSE_BUTTON = MouseButton.PRIMARY;

    public static final double DEFAULT_MIN_SELECTION_SIZE = 5;

    public static Optional<SelectionArea> getPaneSelectionOf(Pane parent) {
        return parent.getChildren().stream()
                .filter(n -> n instanceof SelectionArea)
                .map(SelectionArea.class::cast)
                .findFirst();
    }

    public static List<SelectionArea> getPaneSelectionsOf(Pane parent) {
        return parent.getChildren().stream()
                .filter(n -> n instanceof SelectionArea)
                .map(SelectionArea.class::cast)
                .collect(Collectors.toList());
    }

    public static SelectionArea createWith(Pane parent) {
        return new SelectionArea().attachTo(parent);
    }

    ///////////////////////////////////////////////

    private Pane parent;

    private double minSelectionSize = DEFAULT_MIN_SELECTION_SIZE;

    private MouseButton effectiveMouseButton = DEFAULT_EFFECTIVE_MOUSE_BUTTON;

    private boolean autoHide = true;

    public SelectionArea() {
        initControl();
    }

    public SelectionArea attachTo(Pane parent) {
        this.parent = parent;
        initParent();
        return this;
    }

    public void detach() {
        if (this.parent != null) {
            this.parent.getChildren().remove(this);
        }
    }

    public SelectionArea border(Border border) {
        setBorder(border);
        return this;
    }

    public SelectionArea minSelectionSize(double minSelectionSize) {
        this.minSelectionSize = minSelectionSize;
        return this;
    }

    public SelectionArea autoHide(boolean autoHide) {
        this.autoHide = autoHide;
        return this;
    }

    public double getMinSelectionSize() {
        return minSelectionSize;
    }

    public SelectionArea effectiveMouseButton(MouseButton effectiveMouseButton) {
        this.effectiveMouseButton = effectiveMouseButton;
        return this;
    }

    public MouseButton getEffectiveMouseButton() {
        return effectiveMouseButton;
    }

    ///////////////////////////////////////////////

    private void initParent() {
        parent.getChildren().add(this);
        parent.addEventFilter(MouseEvent.MOUSE_PRESSED, this::parentMousePressed);
        parent.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::parentMouseDragged);
        parent.addEventFilter(MouseEvent.MOUSE_RELEASED, this::parentMouseReleased);
    }

    private void initControl() {
        this.setBackground(Background.EMPTY);
        this.setBorder(FxBorderBuilder.border(Color.WHITE, 1, BorderStrokeStyle.DASHED));
        this.setVisible(false);
        this.disabledProperty().addListener((_ob, _old, _new) -> {
            if (_new) {
                setVisible(false);
            }
        });
    }

    //////////////////////////////////////////////////////////////

    private double[] startPos;

    private boolean dragging;

    private Consumer<Bounds> onSelectionCreated;

    private Consumer<Node> onNodeSelected;

    private Consumer<Node> onNodeUnselected;

    public SelectionArea disable(boolean disable) {
        this.setDisable(disable);
        return this;
    }

    public SelectionArea onNodeSelected(Consumer<Node> onNodeSelected) {
        this.onNodeSelected = onNodeSelected;
        return this;
    }

    public SelectionArea onNodeUnselected(Consumer<Node> onNodeUnselected) {
        this.onNodeUnselected = onNodeUnselected;
        return this;
    }

    public SelectionArea onSelectionCreated(Consumer<Bounds> onSelectionCreated) {
        this.onSelectionCreated = onSelectionCreated;
        return this;
    }

    private void onSelectionCreated() {
        if (this.onSelectionCreated != null) {
            Bounds layoutBounds = this.getLayoutBounds();

            this.onSelectionCreated.accept(new BoundingBox(
                    layoutBounds.getMinX(),
                    layoutBounds.getMinY(),
                    layoutBounds.getWidth(),
                    layoutBounds.getHeight()
            ));
        }
    }

    private void parentMouseReleased(MouseEvent event) {
        if (this.isDisabled()) {
            return;
        }

        if (event.getButton() != effectiveMouseButton) {
            return;
        }

        this.dragging = false;

        if (validateSize()) {
            onSelectionCreated();
        }

        if (autoHide) {
            this.setVisible(false);
        }
    }

    private void parentMouseDragged(MouseEvent event) {
        if (this.isDisabled()) {
            return;
        }

        if (event.getButton() != effectiveMouseButton) {
            return;
        }

        if (this.dragging) {
            calculateBounds(event);
            calculateNodeSelection();
        }
    }

    private void parentMousePressed(MouseEvent event) {
        if (this.isDisabled()) {
            return;
        }

        if (event.getButton() != effectiveMouseButton) {
            return;
        }

        this.startPos = new double[]{event.getX(), event.getY()};
        this.dragging = true;
        this.setLayoutX(event.getX());
        this.setLayoutY(event.getY());
        this.setPrefWidth(0);
        this.setPrefHeight(0);
        this.setVisible(true);
        this.toFront();
    }

    private void calculateNodeSelection() {
        this.parent.getChildren().forEach(node -> {

            if (node == this) {
                return;
            }

            if (this.onNodeSelected != null && isSelected(node)) {
                this.onNodeSelected.accept(node);
            } else if (this.onNodeUnselected != null) {
                this.onNodeUnselected.accept(node);
            }
        });
    }

    private boolean isSelected(Node node) {
        double[] thisPosition = NodeUtils.getNodePosition(this);
        double[] nodePosition = NodeUtils.getNodePosition(node);
        Size thisSize = NodeUtils.getNodeSize(this);
        Size nodeSize = NodeUtils.getNodeSize(node);

        double xm1 = thisPosition[0], xm2 = nodePosition[0],
                ym1 = thisPosition[1], ym2 = nodePosition[1];

        double xx1 = xm1 + thisSize.getWidth(), xx2 = xm2 + nodeSize.getWidth(),
                yx1 = ym1 + thisSize.getHeight(), yx2 = ym2 + nodeSize.getHeight();

        return xm1 <= xm2 && xx1 >= xx2 && ym1 <= ym2 && yx1 >= yx2;
    }

    private void calculateBounds(MouseEvent event) {
        double deltaX = event.getX() - startPos[0];
        double deltaY = event.getY() - startPos[1];

        if (deltaX < 0) {
            this.setLayoutX(Math.max(0, event.getX()));
            this.setPrefWidth(Math.min(startPos[0], -deltaX));
        } else {
            double parentWidth = getParent().getLayoutBounds().getWidth();
            this.setPrefWidth(Math.min(deltaX, parentWidth - startPos[0]));
        }

        if (deltaY < 0) {
            this.setLayoutY(Math.max(0, event.getY()));
            this.setPrefHeight(Math.min(startPos[1], -deltaY));
        } else {
            double parentHeight = getParent().getLayoutBounds().getHeight();
            this.setPrefHeight(Math.min(deltaY, parentHeight - startPos[1]));
        }
    }

    private boolean validateSize() {
        double nodeWidth = NodeUtils.getNodeWidth(this);
        double nodeHeight = NodeUtils.getNodeHeight(this);
        return nodeWidth >= minSelectionSize && nodeHeight >= minSelectionSize;
    }
}
