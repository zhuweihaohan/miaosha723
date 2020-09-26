package com.lhd.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * md5的加密
 * 客户端加密一次，服务端加密一次
 */
public class Md5Util {
    private static String salt = "lhdlikeit";

    public static String md5(String password) {
        return DigestUtils.md5Hex(password);
    }

    /**
     * 客户端密码转成服务端的密码（第一次加密）
     *
     * @param pass
     * @return
     */
    public static String inputPassToFromPass(String pass) {
        String password = "" + salt.charAt(4) + salt.charAt(7) + pass + salt.charAt(5) + salt.charAt(1);
        return md5(password);
    }

    /**
     * 服务端的密码转成数据库的密码
     *
     * @param pass
     * @param salt
     * @return
     */
    public static String fromPassToDbPass(String pass, String salt) {
        String password = "" + salt.charAt(2) + salt.charAt(0) + pass + salt.charAt(3);
        return md5(password);
    }

    public static String inputPassToDbPass(String pass, String salt) {
        return fromPassToDbPass(inputPassToFromPass(pass), salt);
    }

    public static void main(String[] args) {
        System.out.println(md5("123"));
    }

}


