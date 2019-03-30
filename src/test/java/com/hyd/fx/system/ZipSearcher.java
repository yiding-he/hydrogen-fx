package com.hyd.fx.system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ZipSearcher {

  public static void main(String[] args) throws Exception {
    Path dir = Paths.get("D:\\IntelliJ\\IDEA\\lib");

    List<Path> jarFiles = Files.list(dir)
        .filter(p -> Files.isRegularFile(p)
            && p.getFileName().toString().endsWith(".jar")
            && !p.getFileName().toString().contains("_zh.jar")
        )
        .collect(Collectors.toList());

    for (Path jarFile : jarFiles) {
      searchText(jarFile);
    }
  }

  private static void searchText(Path path) throws Exception {
    ZipFileReader reader = new ZipFileReader(path.toFile());
    reader.readZipEntries("*", entry -> {
      if (entry.isDirectory()) {
        return;
      }

      String entryName = entry.getName();
      if (!(
          entryName.endsWith(".properties") ||
              entryName.endsWith(".xml") ||
              entryName.endsWith(".ft") ||
              entryName.endsWith(".template")
      )) {
        return;
      }

      try {
        reader.readEntryByLine(entry, "UTF-8", line -> checkLine(path, entryName, line));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  private static void checkLine(Path path, String entryName, String line) {
    if (line.contains("Project from")) {
      System.out.println(path + " : " + entryName + " : " + line);
    }
  }
}