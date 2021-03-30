package com.hyd.fx.system;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static Path getOrCreatePath(String _path) throws IOException {
        Path path = Paths.get(_path);
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        }
        return path;
    }

    public static File getOrCreateFile(String filePath) throws IOException {
        return getOrCreatePath(filePath).toFile();
    }

    public static File getOrCreateFile(File file) throws IOException {
        return getOrCreatePath(file.getAbsolutePath()).toFile();
    }
}
