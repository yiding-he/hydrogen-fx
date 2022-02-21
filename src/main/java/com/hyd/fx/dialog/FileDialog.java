package com.hyd.fx.dialog;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件对话框
 *
 * @author yiding_he
 */
public class FileDialog {

    private static File initDirectory;

    public static void setInitDirectory(File initDirectory) {
        FileDialog.initDirectory = initDirectory;
    }

    public static File showOpenDirectory(Window owner, String title, String initPath) {
        File initialDirectory = new File(initPath);

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);

        if (initialDirectory.exists() && initialDirectory.isDirectory()) {
            directoryChooser.setInitialDirectory(initialDirectory);
        }

        return directoryChooser.showDialog(owner);
    }

    /**
     * 显示一个打开文件对话框
     *
     * @param owner   父窗口（可选）
     * @param title   标题
     * @param ext     文件扩展名
     * @param extName 文件扩展名描述
     *
     * @return 如果成功选择了文件，则返回文件对象，否则返回 null
     */
    public static File showOpenFile(Window owner, String title, String ext, String extName) {
        return showOpenFile(owner, ".", title, ext, extName);
    }

    public static File showOpenFile(Window owner, String initPath, String title, String ext, String extName) {
        Map<String, String> extAndNames = new HashMap<>();
        extAndNames.put(ext, extName);
        File initFolder = new File(initPath);
        return showFileChooser(DialogType.OpenFile, initFolder, owner, title, extAndNames, "");
    }

    /**
     * 显示一个保存文件对话框
     *
     * @param owner   父窗口（可选）
     * @param title   标题
     * @param ext     文件扩展名
     * @param extName 文件扩展名描述
     *
     * @return 如果成功选择了文件，则返回文件对象，否则返回 null
     */
    public static File showSaveFile(Window owner, String title, String ext, String extName, String defaultFileName) {
        return showSaveFile(owner, ".", title, ext, extName, defaultFileName);
    }

    public static File showSaveFile(Window owner, String initPath, String title, String ext, String extName, String defaultFileName) {
        Map<String, String> extAndNames = new HashMap<>();
        extAndNames.put(ext, extName);
        File initFolder = new File(initPath);
        return showFileChooser(DialogType.SaveFile, initFolder, owner, title, extAndNames, defaultFileName);
    }

    /**
     * 打开选择文件对话框
     *
     * @param dialogType      对话框类型
     * @param initFolder      初始目录（可选）
     * @param owner           父窗口（可选）
     * @param title           对话框标题
     * @param extAndNames     文件扩展名及对应的描述（可选）
     * @param defaultFileName 缺省文件名（可选）
     *
     * @return 用户选择的文件
     */
    public static File showFileChooser(
            DialogType dialogType, File initFolder, Window owner,
            String title, Map<String, String> extAndNames, String defaultFileName) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);

        if (defaultFileName != null) {
            fileChooser.setInitialFileName(defaultFileName);
        }

        if (initFolder != null) {
            fileChooser.setInitialDirectory(initFolder);
        }

        if (extAndNames != null) {
            extAndNames.forEach((ext, name) ->
                    fileChooser.getExtensionFilters().add(new ExtensionFilter(name, ext)));
        }

        File selectedFile = null;

        if (dialogType == DialogType.OpenFile) {

            if (initDirectory != null && initDirectory.exists()) {
                fileChooser.setInitialDirectory(initDirectory);
            }

            selectedFile = fileChooser.showOpenDialog(owner);
            if (selectedFile != null) {
                initDirectory = selectedFile.getParentFile();
            }

        } else if (dialogType == DialogType.SaveFile) {

            if (initDirectory != null && initDirectory.exists()) {
                fileChooser.setInitialDirectory(initDirectory);
            }

            selectedFile = fileChooser.showSaveDialog(owner);

            if (selectedFile != null) {

                // Windows XP 下保存文件不带扩展名，需要手工加上
                // substring(1) 是为了去掉开头的 *
                String ext = "";
                ExtensionFilter filter = fileChooser.getSelectedExtensionFilter();
                if (filter != null) {
                    ext = filter.getExtensions().get(0).substring(1);
                }

                if (!selectedFile.getName().endsWith(ext)) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + ext);
                }
                initDirectory = selectedFile.getParentFile();
            }
        }

        return selectedFile;
    }

    //////////////////////////////////////////////////////////////

    public enum DialogType {
        OpenFile, SaveFile
    }
}
