package com.hyd.fx.dialog.form;

import com.hyd.fx.app.AppLogo;
import com.hyd.fx.dialog.AlertDialog;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

/**
 * (description)
 * created at 2017/7/12
 *
 * @author yidin
 */
public abstract class FormDialog extends Stage {

    private final Button okButton = new Button("确定(_O)");

    private final Button cancelButton = new Button("取消(_C)");

    private final GridPane contentPane = new GridPane();

    private boolean ok;

    public FormDialog(Stage owner) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
            getContentPane(),
            new Separator(Orientation.HORIZONTAL),
            getButtonsPane()
        );
        root.setMinWidth(200);
        root.setMinHeight(100);
        setScene(new Scene(root));

        if (owner != null) {
            this.initModality(Modality.WINDOW_MODAL);
            this.initOwner(owner);
        }

        AppLogo.setStageLogo(this);

        okButton.setOnAction(this::okButtonClicked);
        cancelButton.setOnAction(this::cancelButtonClicked);

        this.setOnShown(this::formShown);
        this.setOnCloseRequest(this::closeButtonClicked);
    }

    public boolean isOk() {
        return ok;
    }

    //////////////////////////////////////////////////////////////

    protected abstract void okButtonClicked(ActionEvent event);

    protected void cancelButtonClicked(ActionEvent event) {
        this.close();
    }

    protected void closeButtonClicked(WindowEvent event) {
        this.close();
    }

    private void formShown(WindowEvent event) {
        Window owner = this.getOwner();

        if (owner == null) {
            return;
        }

        this.setX(owner.getX() + (owner.getWidth() - this.getWidth()) / 2);
        this.setY(owner.getY() + (owner.getHeight() - this.getHeight()) / 2);
    }

    //////////////////////////////////////////////////////////////

    private GridPane getContentPane() {
        contentPane.setHgap(10);
        contentPane.setVgap(10);

        // top-align all child nodes
        contentPane.getChildren().addListener((ListChangeListener<Node>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(
                        node -> {
                            if (GridPane.getColumnIndex(node) == 0) {
                                GridPane.setValignment(node, VPos.TOP);
                            }
                        }
                    );
                }
            }
        });

        ColumnConstraints titleCC = new ColumnConstraints();
        titleCC.setPrefWidth(USE_COMPUTED_SIZE);
        titleCC.setMinWidth(USE_COMPUTED_SIZE);

        ColumnConstraints valueCC = new ColumnConstraints();
        valueCC.setFillWidth(true);
        valueCC.setHgrow(Priority.ALWAYS);

        contentPane.getColumnConstraints().addAll(titleCC, valueCC);

        VBox.setVgrow(contentPane, Priority.ALWAYS);
        return contentPane;
    }

    private HBox getButtonsPane() {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        hBox.getChildren().addAll(okButton, cancelButton);
        return hBox;
    }

    protected void closeOK() {
        this.ok = true;
        this.close();
    }

    protected void closeNotOK() {
        this.ok = false;
        this.close();
    }

    //////////////////////////////////////////////////////////////

    private final List<FormField<?>> formFields = new ArrayList<>();

    protected void addField(FormField<?> formField) {
        this.formFields.add(formField);
        layoutFormField(formField, this.formFields.size() - 1);
    }

    private void layoutFormField(FormField<?> formField, int rowIndex) {
        formField.renderTo(this.contentPane, rowIndex);
    }

    protected boolean validateFields() {
        for (FormField<?> field : formFields) {
            try {
                field.validate();
            } catch (Exception e) {
                AlertDialog.error("错误", e.getMessage());
                return false;
            }
        }
        return true;
    }
}
