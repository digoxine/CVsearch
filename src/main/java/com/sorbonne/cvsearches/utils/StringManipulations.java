package com.sorbonne.cvsearches.utils;

public class StringManipulations {
    public static boolean isPdf(String filename) {
        int length = filename.length();
        String ext = filename.substring(length - 3);
        return ext.toLowerCase().equals("pdf");
    }
}
