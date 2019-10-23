package com.hyd.fx.system;

import com.hyd.fx.utils.Str;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.function.Supplier;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 创建新的 zip 文件。下面是一个例子：
 * <pre>
 * ZipFileCreator creator = new ZipFileCreator("1.zip");
 * creator.putEntry("1.txt", "hello", "UTF-8");         // 在包根目录下写入文件
 * creator.putEntry("subdir/2.txt", "world", "UTF-8");  // 在包子目录下写入文件
 * creator.close();
 * </pre>
 */
public class ZipFileCreator implements Closeable {

    private boolean ignoreBlankLine;

    private ZipOutputStream zipOutputStream;

    ////////////////////////////////////////////////////////////// 构造方法

    /**
     * 构造方法
     *
     * @param filePath 要保存到的文件路径
     *
     * @throws IOException 如果创建文件失败
     */
    public ZipFileCreator(String filePath) throws IOException {
        this(new File(filePath));
    }

    /**
     * 构造方法
     *
     * @param file 要保存到的文件
     *
     * @throws IOException 如果创建文件失败
     */
    public ZipFileCreator(File file) throws IOException {
        file = getOrCreateFile(file);
        this.zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
    }

    static File getOrCreateFile(File file) throws IOException {
        Path path = Paths.get(file.toURI());
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
        }
        return new File(file.getAbsolutePath());
    }

    ////////////////////////////////////////////////////////////// 配置项

    public void setIgnoreBlankLine(boolean ignoreBlankLine) {
        this.ignoreBlankLine = ignoreBlankLine;
    }

    ////////////////////////////////////////////////////////////// 创建压缩包

    /**
     * 写入一个文本文件
     *
     * @param entryName 包内路径
     * @param content   文件内容
     * @param charset   字符编码
     *
     * @throws IOException 如果写入失败
     */
    public void putEntry(String entryName, String content, String charset) throws IOException {
        putEntry(entryName, content.getBytes(charset));
    }

    /**
     * 写入一个文本文件
     *
     * @param entryName 包内路径
     * @param content   文件内容
     * @param charset   字符编码
     *
     * @throws IOException 如果写入失败
     */
    public void putEntry(String entryName, String content, Charset charset) throws IOException {
        putEntry(entryName, content.getBytes(charset));
    }

    /**
     * 写入一个从磁盘读取的文件
     *
     * @param entryName 包内路径
     * @param file      要读取的文件
     *
     * @throws IOException 如果写入失败
     */
    public void putEntry(String entryName, File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            putEntry(entryName, fis);
        }
    }

    /**
     * 写入二进制文件内容
     *
     * @param entryName 包内路径
     * @param content   文件内容
     *
     * @throws IOException 如果写入失败
     */
    public void putEntry(String entryName, byte[] content) throws IOException {
        ZipEntry zipEntry = new ZipEntry(entryName);
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(content);
        zipOutputStream.closeEntry();
    }

    /**
     * 从输入流中读取内容并写入文件
     *
     * @param entryName   包内路径
     * @param inputStream 输入流
     *
     * @throws IOException 如果读取或写入失败
     */
    public void putEntry(String entryName, InputStream inputStream) throws IOException {
        ZipEntry zipEntry = new ZipEntry(entryName);
        zipOutputStream.putNextEntry(zipEntry);

        byte[] buffer = new byte[10240];
        int count;
        while ((count = inputStream.read(buffer)) != -1) {
            zipOutputStream.write(buffer, 0, count);
        }

        zipOutputStream.closeEntry();
    }

    /**
     * 从 LineIterator 读取内容并写入文件。当遇到 null 时终止写入该 Entry。
     * 当遇到空白行时，根据配置决定是否写入该空白行
     *
     * @param entryName    包内路径
     * @param lineSuppiler 每次读取一行的对象
     *
     * @throws IOException 如果读取或写入失败
     */
    public void putEntry(String entryName, Supplier<String> lineSuppiler) throws IOException {
        ZipEntry zipEntry = new ZipEntry(entryName);
        zipOutputStream.putNextEntry(zipEntry);

        String line;
        while ((line = lineSuppiler.get()) != null) {

            if (Str.isBlank(line) && ignoreBlankLine) {
                continue;
            }

            zipOutputStream.write(line.getBytes("UTF-8"));
            zipOutputStream.write("\n".getBytes("UTF-8"));
        }

        zipOutputStream.closeEntry();
    }

    //////////////////////////////////////////////////////////////

    /**
     * 关闭文件输出流
     *
     * @throws IOException 如果出现错误
     */
    @Override
    public void close() throws IOException {
        this.zipOutputStream.close();
    }
}
