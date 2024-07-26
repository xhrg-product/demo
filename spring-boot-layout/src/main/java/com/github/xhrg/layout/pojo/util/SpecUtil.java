package com.github.xhrg.layout.pojo.util;

public class SpecUtil {

    /**
     * 必须是小写和空格,并且第一位不能是-
     * 
     * @param str
     * @return
     */
    public static boolean lowercaseSpace(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        if (str.charAt(0) == '-' || str.charAt(str.length() - 1) == '-') {
            return false;
        }
        return str.matches("^[a-z\\-]+$");
    }
}
