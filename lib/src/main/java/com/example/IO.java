package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Ye on 2017/3/29/0029.
 */

public class IO {

    public static void main(String[] args) throws IOException {
        File 源文件 = new File("D:\\字幕\\1.txt");
        File 新文件 = new File("D:\\字幕\\2.txt");

        FileInputStream inputStream = new FileInputStream(源文件);
        FileOutputStream outputStream = new FileOutputStream(新文件);

        byte[] bytes = new byte[1024];

        while (-1 != inputStream.read(bytes)) {
            outputStream.write(bytes);
        }

        if (inputStream != null) {
            inputStream.close();
        }

        if (outputStream != null) {
            outputStream.close();
        }

    }
}
