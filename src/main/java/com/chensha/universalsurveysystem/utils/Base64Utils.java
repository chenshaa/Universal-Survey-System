package com.chensha.universalsurveysystem.utils;

import java.util.Base64;

public class Base64Utils {

    /**
     * Base64编码
     *
     * @param str
     * @return
     */
    public static String encode(String str) {
        // 将源字符串转为byte数组
        byte[] strBytes = str.getBytes();
        // 链式调用,返回结果
        return Base64.getEncoder().encodeToString(strBytes);
    }

    /**
     * Base64解码
     *
     * @param str
     * @return
     */
    public static String decode(String str) {
        // 将Base64编码转为byte数组
        byte[] base64Bytes = Base64.getDecoder().decode(str);
        // 将Byte数组转为String,返回结果
        return new String(base64Bytes);
    }

}
