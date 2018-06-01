package com.hyd.fx.attachable;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author yidin
 */
public class Dockable extends Attachable<Pane> {


    public static Dockable attachTo(Pane region) {
        return new Dockable(Collections.singleton(region));
    }

    public static Dockable of(Pane region) {
        return getAttachable(region, Dockable.class);
    }

    ///////////////////////////////////////////////

    private static class Dockee<T extends Pane> {

        private Side dockSide;

        private T container;

        public Dockee(Side dockSide, T container) {
            this.dockSide = dockSide;
            this.container = container;
        }

        public void setVisible(boolean visible) {
            if (this.container != null) {
                this.container.setVisible(visible);
            }
        }

        public boolean isVisible() {
            return this.container != null && this.container.isVisible();
        }

        public T getContainer() {
            return container;
        }

        public void addNode(Node dockNode) {
            this.container.getChildren().add(dockNode);
        }
    }

    ///////////////////////////////////////////////

    private Dockee<HBox> topDockee = new Dockee<>(Side.TOP, new HBox());

    private Dockee<HBox> bottomDockee = new Dockee<>(Side.BOTTOM, new HBox());

    private Dockee<VBox> leftDockee = new Dockee<>(Side.LEFT, new VBox());

    private Dockee<VBox> rightDockee = new Dockee<>(Side.RIGHT, new VBox());

    private List<Dockee<? extends Pane>> dockees = Arrays.asList(topDockee, bottomDockee, leftDockee, rightDockee);

    private Dockable(Collection<? extends Pane> t) {
        super(t);
        setupEvents();
    }

    private void setupEvents() {
        HBox topContainer = topDockee.container;
        VBox leftContainer = leftDockee.container;
        HBox bottomContainer = bottomDockee.container;
        VBox rightContainer = rightDockee.container;

        Pane attached = getAttachedOne();
        attached.getChildren().addAll(
                topContainer, leftContainer, bottomContainer, rightContainer);

        topContainer.heightProperty().addListener((_ob, _old, _new) -> topContainer.setLayoutY(-_new.doubleValue() - 1));

        attached.heightProperty().addListener((_ob, _old, _new) -> bottomContainer.setLayoutY(_new.doubleValue() + 1));

        leftContainer.widthProperty().addListener((_ob, _old, _new) -> leftContainer.setLayoutX(-_new.doubleValue() - 1));

        attached.widthProperty().addListener((_ob, _old, _new) -> rightContainer.setLayoutX(_new.doubleValue() + 1));
    }

    public Dockable spacing(double spacing) {
        dockees.forEach(dockee -> {
            if (dockee.container instanceof HBox) {
                ((HBox) dockee.container).setSpacing(spacing);
            } else if (dockee.container instanceof VBox) {
                ((VBox) dockee.container).setSpacing(spacing);
            }
        });
        return this;
    }

    public Dockable visible(boolean visible) {
        dockees.forEach(dockee -> dockee.container.setVisible(visible));
        return this;
    }

    public Dockable dock(Side side, Node dockNode) {
        getDockee(side).addNode(dockNode);
        return this;
    }

    private Dockee<? extends Region> getDockee(Side side) {
        switch (side) {
            case TOP:
                return topDockee;
            case BOTTOM:
                return bottomDockee;
            case LEFT:
                return leftDockee;
            default:
                return rightDockee;
        }
    }
}
