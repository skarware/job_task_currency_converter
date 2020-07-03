package org.martynas.currencyconverter.util;

import java.io.*;

public class FileUtil {

    public static BufferedReader readFile(File file) {
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//            return new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            System.err.println("ERROR: Caught IOException while trying to read CSV formatted FILE: + " + file + ", Error message: \n" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
