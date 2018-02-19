package com.hyd.fx.system;

import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author yiding.he
 */
public class ClipboardHelper {

    private static void putContent(Consumer<ClipboardContent> consumer) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        consumer.accept(content);
        clipboard.setContent(content);
    }

    public static void putString(String string) {
        putContent(clipboardContent -> clipboardContent.putString(string));
    }

    public static void putHtml(String string) {
        putContent(clipboardContent -> clipboardContent.putHtml(string));
    }

    public static void putUrl(String string) {
        putContent(clipboardContent -> clipboardContent.putUrl(string));
    }

    public static void putFiles(List<File> files) {
        putContent(clipboardContent -> clipboardContent.putFiles(files));
    }

    public static void putFilePaths(List<String> paths) {
        putContent(clipboardContent -> clipboardContent.putFilesByPath(paths));
    }

    public static void putImage(Image image) {
        putContent(clipboardContent -> clipboardContent.putImage(image));
    }
}
