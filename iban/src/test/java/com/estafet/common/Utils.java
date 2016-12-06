package com.estafet.common;


import com.mchange.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by DRamadan on 05-Dec-16.
 */
public class Utils {

    public static String convertFileToString(String fileURI) {

        File file = new File(fileURI);
        String str = null;
        try {
            str =  FileUtils.getContentsAsString(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }
}
