package org.martynas.currencyconverter.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtil {

    public static BufferedReader readFile(File file) {
        try {
            /*
            FileInputStream reads file by byte by byte (byte stream) and is suitable for any type of file except for text files.
            Problem will arise if the text file is using a Unicode encoding where character represented with two or more bytes.
            The byte stream will read those separately therefore manual byte stream to characters conversion necessary.
             */
//            return new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            /*
            FileReader reads file char by char and should ne the prime choice working wth text files, reading or writing,
            but a Charset encoding scheme must be set for character stream in order to work properly. Character Stream under
            the hood is Byte Stream that has been wrapped with logic to output characters from a file with specific encoding.
             */
            return new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));

        } catch (IOException e) {
            System.err.println("ERROR: Caught IOException while trying to read CSV formatted FILE: + " + file + ", Error message: \n" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
