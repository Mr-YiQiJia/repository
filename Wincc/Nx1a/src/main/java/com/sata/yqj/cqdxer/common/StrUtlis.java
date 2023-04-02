package com.sata.yqj.cqdxer.common;

public class StrUtlis {
    public static String toLowerFirst(String str) {
        char[] chars = str.toCharArray();
        if (Character.isLowerCase(chars[0])) {
            return str;
        } else {
            chars[0] += 32;
            return String.valueOf(chars);
        }
    }
}
