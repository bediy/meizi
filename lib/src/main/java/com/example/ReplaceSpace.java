package com.example;

/**
 * Created by Ye on 2017/3/13/0013.
 */

public class ReplaceSpace {

    public String replaceSpace(StringBuffer str) {
        char[] chars = str.toString().toCharArray();
        int n = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                n++;
            }
        }

        char[] newChars = new char[chars.length + n * 2];
        int newCharsLength = 0;

        for (int i = 0; i <chars.length; i++) {
            if (chars[i] == ' ') {
                newChars[newCharsLength++] = '%';
                newChars[newCharsLength++] = '2';
                newChars[newCharsLength++] = '0';
            } else {
                newChars[newCharsLength++] = chars[i];
            }

        }
        return new String(newChars);
    }

    public static void main(String args[]) {
        StringBuffer buffer = new StringBuffer("We Are Happy");
        String s = new ReplaceSpace().replaceSpace(buffer);
        System.out.print(s);
    }

}
