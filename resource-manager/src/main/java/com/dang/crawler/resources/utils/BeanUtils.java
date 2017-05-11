package com.dang.crawler.resources.utils;

import java.io.*;

/**
 * Created by mi on 2017/5/9.
 */
public class BeanUtils {
    public static Object clone(Object t) throws IOException, ClassNotFoundException {
        Object copy = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(t);
        // 将流序列化成对象
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        copy = ois.readObject();
        return copy;
    }
}
