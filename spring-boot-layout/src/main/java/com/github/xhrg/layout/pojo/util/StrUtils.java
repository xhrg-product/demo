package com.github.xhrg.layout.pojo.util;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;

public class StrUtils {

    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public static String trim(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        return str.trim();
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.isEmpty() || str.trim().isEmpty()) {
            return true;
        }
        if (str.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean haveEmpty(String... list) {
        if (list == null || list.length == 0) {
            throw new IllegalArgumentException("param cant null or size 0");
        }
        for (String s : list) {
            if (isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    public static String lower(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.toLowerCase();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String ramdomStr(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(alphabet.charAt(new Random().nextInt(alphabet.length())));
        }
        return sb.toString();
    }

    public static String uuid() {
        String s = UUID.randomUUID().toString().replaceAll("-", "");
        return s;
    }

    public static String byte2Str(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        System.out.println(uuid());
    }
}
