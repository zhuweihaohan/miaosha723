package com.lhd.util;

import java.util.UUID;

public class Tools {
    /**
     * 生成uuid码
     * @return
     */
    public static String uuid(){
        String str= UUID.randomUUID().toString();
        return str.replace("-","");
    }
}
