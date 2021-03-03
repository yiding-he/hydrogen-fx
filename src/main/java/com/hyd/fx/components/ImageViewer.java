package com.hyd.fx.components;

import com.hyd.fx.Position;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ImageViewer extends Pane {

    public enum ViewMode {
        Fit, OriginalSize, Scaled;
    }

    // 图片内容
    private ImageView imageView = new ImageView();

    // 防止 ImageView 内容超出边界
    private final Rectangle clip = new Rectangle();

    private SimpleObjectProperty<ViewMode> viewMode = new SimpleObjectProperty<>(ViewMode.Fit);

    private SimpleBooleanProperty pannable = new SimpleBooleanProperty(false);

    private boolean imageDropShadow;

    private Bounds imageBounds;

    public ImageViewer() {
        this.getChildren().add(this.imageView);
        this.imageView.setManaged(false);
        this.imageView.setPreserveRatio(true);

        this.widthProperty().addListener(this::onImageViewResized);
        this.heightProperty().addListener(this::onImageViewResized);

        this.viewModeProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case Fit:
                    zoomFit();
                    break;
                case OriginalSize:
                    zoomReset();
                    break;
                case Scaled:
            }
        });

        this.addEventFilter(MouseEvent.MOUSE_PRESSED, this::onMousePressed);
        this.addEventFilter(MouseEvent.MOUSE_RELEASED, this::onMouseReleased);
        this.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::onMouseDragged);

        this.clip.setWidth(this.getWidth());
        this.clip.setHeight(this.getHeight());
        this.setClip(this.clip);
    }

    //////////////////////////////////////////////////////////////

    private Position mouseDragStart, imageDragStart;

    private void onMousePressed(MouseEvent event) {
        if (!isPannable()) {
            return;
        }
        mouseDragStart = new Position(event.getScreenX(), event.getScreenY());
        imageDragStart = new Position(imageView.getLayoutX(), imageView.getLayoutY());
    }

    private void onMouseReleased(MouseEvent event) {
        if (!isPannable()) {
            return;
        }
        mouseDragStart = null;
        imageDragStart = null;
    }

    private void onMouseDragged(MouseEvent event) {
        if (!isPannable()) {
            return;
        }
        imageView.setLayoutX(imageDragStart.getX() + (event.getScreenX() - mouseDragStart.getX()));
        imageView.setLayoutY(imageDragStart.getY() + (event.getScreenY() - mouseDragStart.getY()));
    }

    //////////////////////////////////////////////////////////////

    public void setImage(Image image) {
        this.imageView.setImage(image);
        this.imageBounds = new BoundingBox(0, 0, image.getWidth(), image.getHeight());
        this.zoomFit();
    }

    public void setImageDropShadow(boolean imageDropShadow) {
        this.imageDropShadow = imageDropShadow;
        if (this.imageDropShadow) {
            this.imageView.setEffect(new DropShadow(30, Color.color(0, 0, 0, 0.5)));
        } else {
            this.imageView.setEffect(null);
        }
    }

    public boolean isImageDropShadow() {
        return imageDropShadow;
    }

    public ViewMode getViewMode() {
        return viewMode.get();
    }

    public SimpleObjectProperty<ViewMode> viewModeProperty() {
        return viewMode;
    }

    public void setViewMode(ViewMode viewMode) {
        this.viewMode.set(viewMode);
    }

    public Image getImage() {
        return this.imageView.getImage();
    }

    public double getImageScale() {
        if (this.imageView.getImage() == null) {
            return 0;
        }
        return this.imageView.getLayoutBounds().getWidth() / this.imageView.getImage().getWidth();
    }

    public boolean isSmooth() {
        return this.imageView.isSmooth();
    }

    public void zoomIn() {
        if (!this.isVisible() || imageBounds == null) {
            return;
        }

        this.setViewMode(ViewMode.Scaled);
        this.imageView.setFitWidth(this.imageView.getFitWidth() * 1.1);
        this.imageView.setFitHeight(this.imageView.getFitHeight() * 1.1);
        this.resetLocation();
    }

    public void zoomOut() {
        if (!this.isVisible() || imageBounds == null) {
            return;
        }

        this.setViewMode(ViewMode.Scaled);
        this.imageView.setFitWidth(this.imageView.getFitWidth() * 0.9);
        this.imageView.setFitHeight(this.imageView.getFitHeight() * 0.9);
        this.resetLocation();
    }

    public boolean isPannable() {
        return pannable.get();
    }

    public SimpleBooleanProperty pannableProperty() {
        return pannable;
    }

    public void setPannable(boolean pannable) {
        this.pannable.set(pannable);
    }

    public void zoomFit() {
        Bounds viewerBounds = this.getLayoutBounds();
        boolean scale = imageBounds.getWidth() > viewerBounds.getWidth()
            || imageBounds.getHeight() > viewerBounds.getHeight();

        if (scale) {
            this.imageView.setSmooth(true);
            double imageRatio = imageBounds.getWidth() / imageBounds.getHeight();
            double viewerRatio = viewerBounds.getWidth() / viewerBounds.getHeight();
            if (imageRatio > viewerRatio) {
                this.imageView.setFitWidth(viewerBounds.getWidth());
            } else {
                this.imageView.setFitHeight(viewerBounds.getHeight());
            }

            this.imageView.autosize();
        } else {
            this.imageView.setSmooth(false);
            this.imageView.setFitWidth(imageBounds.getWidth());
            this.imageView.setFitHeight(imageBounds.getHeight());
        }

        resetLocation();
    }

    public void zoomReset() {
        this.imageView.setFitWidth(imageBounds.getWidth());
        this.imageView.setFitHeight(imageBounds.getHeight());
        resetLocation();
    }

    private void onImageViewResized(ObservableValue<? extends Number> ob, Number oldValue, Number newValue) {
        this.clip.setWidth(this.getWidth());
        this.clip.setHeight(this.getHeight());

        if (!this.isVisible() || imageBounds == null) {
            return;
        }

        if (getViewMode() == ViewMode.Fit) {
            zoomFit();
        } else {
            resetLocation();
        }
    }

    private void resetLocation() {
        double layoutX = (this.getLayoutBounds().getWidth() - imageView.getLayoutBounds().getWidth()) / 2.0;
        double layoutY = (this.getLayoutBounds().getHeight() - imageView.getLayoutBounds().getHeight()) / 2.0;
        imageView.setLayoutX((int) layoutX);
        imageView.setLayoutY((int) layoutY);

        clip.setWidth(this.getWidth());
        clip.setHeight(this.getHeight());
    }


}
