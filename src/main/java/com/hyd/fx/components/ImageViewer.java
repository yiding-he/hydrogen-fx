package com.hyd.fx.components;

import javafx.beans.value.ObservableValue;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static com.hyd.fx.style.FxBackgroundBuilder.background;

public class ImageViewer extends Pane {

    public enum ViewMode {
        Fit, OriginalSize, Scaled
    }

    private ImageView imageView = new ImageView();

    private ViewMode viewMode = ViewMode.Fit;

    private Bounds imageBounds;

    public ImageViewer() {
        this.setBackground(background("#002244"));

        this.getChildren().add(this.imageView);
        this.imageView.setManaged(false);
        this.imageView.setPreserveRatio(true);
        this.imageView.setSmooth(true);
        this.imageView.setCache(true);

        this.widthProperty().addListener(this::onImageViewResized);
        this.heightProperty().addListener(this::onImageViewResized);
    }

    public void setImage(Image image) {
        this.imageView.setImage(image);
        this.imageBounds = new BoundingBox(0, 0, image.getWidth(), image.getHeight());
        this.zoomFit();
    }

    public void setViewMode(ViewMode viewMode) {
        this.viewMode = viewMode;
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

    }

    public void zoomOut() {

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

    private void onImageViewResized(ObservableValue<? extends Number> ob, Number oldValue, Number newValue) {
        if (!this.isVisible() || imageBounds == null) {
            return;
        }

        if (viewMode == ViewMode.Fit) {
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
    }


}
