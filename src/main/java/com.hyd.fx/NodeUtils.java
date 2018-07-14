package com.hyd.fx;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (description)
 * created at 2018/3/6
 *
 * @author yidin
 */
public class NodeUtils {

    public static double[] getMousePositionInParent(MouseEvent event) {
        Node node = (Node) event.getSource();
        Bounds parentBounds = node.getParent().localToScene(node.getLayoutBounds());
        double mouseX = event.getSceneX() - parentBounds.getMinX();
        double mouseY = event.getSceneY() - parentBounds.getMinY();
        return new double[]{mouseX, mouseY};
    }

    public static double getNodeWidth(Node node) {
        double width = node.getLayoutBounds().getWidth();

        if (width <= 0 && node instanceof Region) {
            width = ((Region) node).getPrefWidth();
            if (width <= 0) {
                width = ((Region) node).getWidth();
            }
        }

        if (width <= 0 && node instanceof Shape) {
            width = node.getBoundsInLocal().getWidth();
        }

        return width;
    }

    public static double getNodeHeight(Node node) {
        double height = node.getLayoutBounds().getHeight();

        if (height <= 0 && node instanceof Region) {
            height = ((Region) node).getPrefHeight();
            if (height <= 0) {
                height = ((Region) node).getHeight();
            }
        }

        if (height <= 0 && node instanceof Shape) {
            height = node.getBoundsInLocal().getHeight();
        }

        return height;
    }

    public static boolean isInArea(
            double x, double y, double minX, double maxX, double minY, double maxY) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    public static boolean isInNode(MouseEvent mouseEvent, Node node) {
        return isInArea(mouseEvent.getX(), mouseEvent.getY(),
                0, getNodeWidth(node), 0, getNodeHeight(node));
    }

    public static Size getNodeSize(Node node) {
        return new Size(getNodeWidth(node), getNodeHeight(node));
    }

    // 根据一组 node 计算其外围框
    public static Bounds getSurroundBounds(List<Node> nodeList) {

        if (nodeList == null || nodeList.isEmpty()) {
            return new BoundingBox(0, 0, 0, 0);
        }

        double minx = Double.POSITIVE_INFINITY,
                miny = Double.POSITIVE_INFINITY,
                maxx = Double.NEGATIVE_INFINITY,
                maxy = Double.NEGATIVE_INFINITY;

        for (Node node : nodeList) {
            Bounds bounds = node.getBoundsInParent();
            minx = Math.min(minx, bounds.getMinX());
            miny = Math.min(miny, bounds.getMinY());
            maxx = Math.max(maxx, bounds.getMaxX());
            maxy = Math.max(maxy, bounds.getMaxY());
        }

        return new BoundingBox(minx, miny, maxx - minx, maxy - miny);
    }

    public static boolean isInNode(double x, double y, Node node) {
        return isInArea(x, y, 0, getNodeWidth(node), 0, getNodeHeight(node));
    }

    public static double[] getNodePosition(Node node) {
        Bounds bounds = node.getBoundsInParent();
        return new double[]{bounds.getMinX(), bounds.getMinY()};
    }

    @SuppressWarnings("unchecked")
    public static <T> T getUserData(Node node, String key) {
        if (node == null || node.getUserData() == null || !(node.getUserData() instanceof Map)) {
            return null;
        }

        return (T) ((Map) node.getUserData()).get(key);
    }

    @SuppressWarnings("unchecked")
    public static void setUserData(Node node, String key, Object value) {

        if (key == null || node == null) {
            return;
        }

        Map<String, Object> map;

        if (node.getUserData() == null || !(node.getUserData() instanceof Map)) {
            if (value != null) {
                map = new HashMap<>();
                node.setUserData(map);
            } else {
                return;
            }
        } else {
            map = (Map<String, Object>) node.getUserData();
        }

        if (value != null) {
            map.put(key, value);
        } else {
            map.remove(key);
        }
    }
}
