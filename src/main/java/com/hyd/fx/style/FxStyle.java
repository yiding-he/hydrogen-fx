package com.hyd.fx.style;

import com.hyd.fx.utils.Str;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.Node;

/**
 * 帮助快速设置界面元素样式的工具类
 *
 * @author yidin
 */
public class FxStyle {

    private final Map<String, String> styleElements = new LinkedHashMap<>();

    public static FxStyle of(Node node) {
        return new FxStyle(node);
    }

    public static void apply(Node node, String styleName, String styleValue) {
        new FxStyle().putStyleValue(styleName, styleValue).setToNode(node);
    }

    public FxStyle() {
        this("");
    }

    public FxStyle(Node node) {
        this(node.getStyle());
    }

    public FxStyle(String styleString) {
        Stream.of(styleString.split(";"))
            .filter(Str::isNotBlank)
            .forEach(property -> {
                String[] kv = property.split(":", 2);
                styleElements.put(kv[0], kv[1]);
            });
    }

    public String getStyleValue(String styleName) {
        return this.styleElements.get(styleName);
    }

    public FxStyle putStyleValue(String styleName, String styleValue) {
        this.styleElements.put(styleName, styleValue);
        return this;
    }

    public FxStyle putStyleValueIf(boolean condition, String styleName, String styleValue) {
        if (condition) {
            this.styleElements.put(styleName, styleValue);
        }
        return this;
    }

    public FxStyle putStyleValueIf(boolean condition, String styleName, Number styleValue) {
        return putStyleValueIf(condition, styleName, String.valueOf(styleValue));
    }

    public FxStyle putStyleValue(String styleName, Number styleValue) {
        return putStyleValue(styleName, String.valueOf(styleValue));
    }

    public Map<String, String> getStyleElements() {
        return Collections.unmodifiableMap(styleElements);
    }

    public void mergeFrom(FxStyle fxStyle) {
        fxStyle.styleElements.forEach((k,v) -> {
            if (!this.styleElements.containsKey(k)) {
                this.styleElements.put(k, v);
            }
        });
    }

    public void setToNode(Node node) {
        this.mergeFrom(FxStyle.of(node));
        node.setStyle(this.toStyleString());
    }

    public String toStyleString() {
        return this.styleElements.entrySet().stream()
            .map(entry -> entry.getKey() + ":" + entry.getValue())
            .collect(Collectors.joining(";"));
    }
}
