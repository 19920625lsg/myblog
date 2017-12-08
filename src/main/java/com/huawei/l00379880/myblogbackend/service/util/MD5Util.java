package com.huawei.l00379880.myblogbackend.service.util;

import java.security.MessageDigest;

/***********************************************************
 * @Description : 
 * @author      : 梁山广
 * @date        : 2017/12/8 21:50
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class MD5Util {

    /**
     * 生成MD5,对外提供的MD5加密方法
     *
     * @param message 待加密内容
     * @return
     */
    public static String getMD5(String message) {
        String md5 = "";
        try {
            // 创建一个md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageByte = message.getBytes("UTF-8");
            // 获得MD5字节数组,16*8=128位
            byte[] md5Byte = md.digest(messageByte);
            // 转换为16进制字符串
            md5 = bytesToHex(md5Byte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    /**
     * 二进制转十六进制
     *
     * @param bytes 输入的二进制数
     * @return
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexStr = new StringBuilder();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if (num < 0) {
                num += 256;
            }
            if (num < 16) {
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }
}
