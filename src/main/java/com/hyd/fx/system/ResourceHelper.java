package com.hyd.fx.system;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ResourceHelper {

    // 合并字节数组
    private static byte[] concat(byte[] content, byte[] buffer, int len) {
        byte[] result = new byte[content.length + len];
        System.arraycopy(content, 0, result, 0, content.length);
        System.arraycopy(buffer, 0, result, content.length, len);
        return result;
    }

    public static String readAsString(String classpath, Charset charset) throws IOException {
        InputStream inputStream = ResourceHelper.class.getResourceAsStream(classpath);
        if (inputStream == null) {
            return null;
        } else {
            return readAsString(inputStream, charset);
        }
    }

    public static String readAsString(InputStream inputStream, Charset charset) throws IOException {
        return new String(readAsBytes(inputStream), charset);
    }

    public static byte[] readAsBytes(InputStream inputStream) throws IOException {
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
}
