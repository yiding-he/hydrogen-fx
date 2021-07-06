package com.hyd.fx.dialog.form;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * (description)
 * created at 2017/7/12
 *
 * @author yidin
 */
public class TextAreaFormField extends FormField {

    private final VBox vBox = new VBox();

    private final TextArea textArea = new TextArea();

    private final CheckBox chbWrapText = new CheckBox("自动换行");

    private final boolean vGrow;

    private final int rowCount;

    public TextAreaFormField(String labelName, String defaultValue, int rowCount, boolean vGrow) {
        super(labelName);
        this.rowCount = rowCount;
        this.vGrow = vGrow;
        this.textArea.setText(defaultValue);
        this.textArea.wrapTextProperty().bind(this.chbWrapText.selectedProperty());

        this.vBox.setSpacing(5);
        this.vBox.getChildren().addAll(textArea, chbWrapText);

        VBox.setVgrow(textArea, Priority.ALWAYS);
    }

    public String getText() {
        return this.textArea.getText();
    }

    public void setText(String text) {
        this.textArea.setText(text);
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setEditable(boolean editable) {
        this.textArea.setEditable(editable);
    }

    @Override
    public void renderTo(GridPane contentPane, int rowIndex) {
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        if (vGrow) {
            GridPane.setVgrow(vBox, Priority.ALWAYS);
        } else {
            this.textArea.setPrefRowCount(rowCount);
        }

        contentPane.add(getLabel(), 0, rowIndex);
        contentPane.add(vBox, 1, rowIndex);

        // 默认自动换行
        this.chbWrapText.setSelected(true);
    }
}
