package org.thekiddos.manager;

import java.net.URL;

public class Util {
    public static URL getResource( String fileName) {
        ClassLoader classLoader = Util.class.getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return resource;
        }
    }
}
