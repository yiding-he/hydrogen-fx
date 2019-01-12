package com.hyd.fx.system;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 遍历和读取 zip 文件的帮助类
 *
 * @author yiding_he
 */
public class ZipFileReader implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(ZipFileReader.class);

    private File file;

    private ZipFile zipFile;

    private boolean deleteAfterRead;

    /**
     * 读取输入流内容并写入到输出流
     *
     * @param is 输入流
     * @param os 输出流
     *
     * @throws IOException 如果读取或写入失败
     */
    public static void transfer(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[32 * 1024];
        int count;

        while ((count = is.read(buffer)) != -1) {
            os.write(buffer, 0, count);
        }
    }

    /**
     * 打开 zip 文件
     *
     * @param file 文件
     *
     * @return 打开的 ZipFile 对象
     *
     * @throws IOException 如果打开文件失败
     */
    public static ZipFile openZipFile(File file) throws IOException {
        return openZipFile(file, Charset.forName("GBK"), Charset.forName("UTF-8"));
    }

    /**
     * 尝试用可能的编码打开 zip 文件
     *
     * @param path     文件路径
     * @param charsets 可能的编码
     *
     * @return 打开的 ZipFile 对象
     *
     * @throws IOException 如果打开文件失败
     */
    public static ZipFile openZipFile(String path, Charset... charsets) throws IOException {
        return openZipFile(new File(path), charsets);
    }

    /**
     * 尝试用可能的编码打开 zip 文件
     *
     * @param file     文件
     * @param charsets 可能的编码
     *
     * @return 打开的 ZipFile 对象
     *
     * @throws IOException 如果打开文件失败
     */
    public static ZipFile openZipFile(File file, Charset... charsets) throws IOException {
        Charset acceptedCharset = null;

        // 尝试找到能够读取内容的编码
        for (Charset charset : charsets) {
            try {
                ZipFile zipFile = new ZipFile(file, charset);
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                if (entries.hasMoreElements()) {
                    entries.nextElement();
                }
                acceptedCharset = charset;
                break;
            } catch (IllegalArgumentException e) {
                if (e.getMessage().contains("MALFORMED")) {
                    LOG.warn("文件 " + file + " 不适合编码 " + charset);
                }
            }
        }

        // 返回用找到的编码读取的 ZipFile
        if (acceptedCharset == null) {
            throw new IllegalStateException("无法读取该文件");
        } else {
            return new ZipFile(file, acceptedCharset);
        }
    }

    /**
     * 读取输入流的内容
     *
     * @param inputStream 输入流
     *
     * @return 输入流的内容
     *
     * @throws IOException 如果读取失败
     */
    public static byte[] toBytes(InputStream inputStream) throws IOException {
        try {
            byte[] content = new byte[0];
            int len;
            byte[] buffer = new byte[40960];
            while ((len = inputStream.read(buffer)) != -1) {
                content = concat(content, buffer, len);
            }

            return content;
        } finally {
            inputStream.close();
        }
    }

    // 合并字节数组
    private static byte[] concat(byte[] content, byte[] buffer, int len) {
        byte[] result = new byte[content.length + len];
        System.arraycopy(content, 0, result, 0, content.length);
        System.arraycopy(buffer, 0, result, content.length, len);
        return result;
    }

    /**
     * 从文本输入流中读取每一行并进行处理
     *
     * @param stream       输入流
     * @param charset      字符编码
     * @param lineConsumer 对每行进行的处理
     */
    public static void readLines(InputStream stream, String charset, Consumer<String> lineConsumer) {
        try (Scanner scanner = new Scanner(stream, charset)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (lineConsumer != null) {
                    lineConsumer.accept(line);
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////

    /**
     * 构造方法
     *
     * @param filePath zip 文件路径
     *
     * @throws IOException 如果读取内容失败
     */
    public ZipFileReader(String filePath) throws IOException {
        this(new File(filePath));
    }

    /**
     * 构造方法
     *
     * @param file zip 文件
     *
     * @throws IOException 如果读取内容失败
     */
    public ZipFileReader(File file) throws IOException {
        this.file = file;
        this.zipFile = openZipFile(file);
    }

    /**
     * 获取是否在遍历完毕后删除 zip 文件
     *
     * @return 是否在遍历完毕后删除 zip 文件
     */
    public boolean isDeleteAfterRead() {
        return deleteAfterRead;
    }

    /**
     * 设置是否在遍历完毕后删除 zip 文件
     *
     * @param deleteAfterRead 是否在遍历完毕后删除 zip 文件
     */
    public void setDeleteAfterRead(boolean deleteAfterRead) {
        this.deleteAfterRead = deleteAfterRead;
    }

    /**
     * 读取包内指定文件的内容
     *
     * @param entryName 包内文件位置
     *
     * @return 文件内容。如果 entryName 不是一个文件，则返回 null
     */
    public byte[] readZipEntryBytes(String entryName) throws IOException {
        ZipEntry zipEntry = zipFile.getEntry(entryName);

        if (zipEntry == null || zipEntry.isDirectory()) {
            return null;
        } else {
            return toBytes(zipFile.getInputStream(zipEntry));
        }
    }

    /**
     * 读取包内指定文件的内容
     *
     * @param entryName 包内文件位置
     * @param charset   文本编码
     *
     * @return 文件内容。如果 entryName 不是一个文件，则返回 null
     */
    public String readZipEntryString(String entryName, String charset) throws IOException {
        byte[] content = readZipEntryBytes(entryName);
        if (content == null) {
            return null;
        } else {
            return new String(content, charset);
        }
    }

    /**
     * 以 UTF-8 编码读取包内指定文件的内容
     *
     * @param entryName 包内文件位置
     *
     * @return 文件内容。如果 entryName 不是一个文件，则返回 null
     */
    public String readZipEntryString(String entryName) throws IOException {
        return readZipEntryString(entryName, "UTF-8");
    }

    /**
     * 遍历并处理 zip 中的每个路径。注意路径不一定都是文件
     * 如果设置了 deleteAfterRead，则程序将会在遍历完成后，尝试删除 zip 文件。
     *
     * @param entryNamePattern 路径模板（完整路径或者带通配符的路径，null 或 "*" 表示读取所有）
     * @param entryConsumer    处理对象
     */
    public void readZipEntries(String entryNamePattern, Consumer<ZipEntry> entryConsumer) throws IOException {

        if (this.zipFile == null) {
            return;
        }

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();

            String zipEntryName = zipEntry.getName();
            if (entryNamePattern != null && !match(entryNamePattern, zipEntryName)) {
                continue;
            }

            if (entryConsumer != null) {
                entryConsumer.accept(zipEntry);
            }
        }

        if (deleteAfterRead) {
            this.zipFile.close();
            if (!this.file.delete()) {
                this.file.deleteOnExit();
            }
        }
    }

    private boolean match(String entryNamePattern, String zipEntryName) {
        return zipEntryName.equals(entryNamePattern) ||
                FilenameUtils.wildcardMatch(zipEntryName, entryNamePattern);
    }

    /**
     * 读取并处理指定文件中的每一行
     *
     * @param zipEntry     压缩包内的文件
     * @param charset      读取时使用的编码
     * @param lineConsumer 处理对象
     *
     * @throws IOException 如果读取失败
     */
    public void readEntryByLine
    (ZipEntry zipEntry, String charset, Consumer<String> lineConsumer) throws IOException {
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        readLines(inputStream, charset, lineConsumer);
    }

    /**
     * 读取压缩包内的文件流
     *
     * @param zipEntry 压缩包内的文件
     *
     * @return 压缩包内的文件流
     *
     * @throws IOException 如果读取失败
     */
    public InputStream getInputStream(ZipEntry zipEntry) throws IOException {
        return this.zipFile.getInputStream(zipEntry);
    }

    public void saveEntryToFile(ZipEntry zipEntry, String filePath) throws IOException {
        File file = ZipFileCreator.getOrCreateFile(new File(filePath));

        try (
                FileOutputStream fos = new FileOutputStream(file);
                InputStream zis = getInputStream(zipEntry)
        ) {
            transfer(zis, fos);
        }
    }

    @Override
    public void close() throws IOException {
        if (this.zipFile != null) {
            this.zipFile.close();
        }
    }
}
