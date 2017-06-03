package com.dang.crawler.resources.utils;

import java.io.*;

/**
 * Created by dang on 17-6-2.
 */
public class SerializableUtil {
    public static Object unserializable(byte[] bytes) throws IOException, ClassNotFoundException {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(inputStream);
        return ois.readObject();
    }

    public static byte[] serializable(Object value) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        oos.writeObject(value);
        oos.flush();
        oos.close();
        outputStream.close();
        return outputStream.toByteArray();

    }
}
