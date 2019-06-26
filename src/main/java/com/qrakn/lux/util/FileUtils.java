package com.qrakn.lux.util;

import java.io.File;

public class FileUtils {

    public static String getFileExtension(File file) {
        String name = file.getName();

        return name.substring(name.lastIndexOf("."));
    }

    public static String getFileName(File file) {
        String name = file.getName();

        return name.substring(0, name.lastIndexOf("."));
    }
}
