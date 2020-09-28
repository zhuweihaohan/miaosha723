package com.lhd.vo;

/**
 * 常用的错误消息的封装
 */
public class CodeMsg {
    private int code;
    private String msg;
    public static CodeMsg SERVER_ERROR = new CodeMsg(500500, "服务器内部错误");
    public static CodeMsg USER_NOT_FOUND_ERROR = new CodeMsg(500501, "用户找不到");
    public static CodeMsg USER_NOT_LOGIN = new CodeMsg(500502, "用户未登录");
    public static CodeMsg USER_PASSWORD_ERROR = new CodeMsg(500503, "用户密码错误");
   public  static CodeMsg MIAOSHA_STOCK_ZERO=new CodeMsg(500504,"库存不足，秒杀失败");
    public  static CodeMsg MIAOSHA_MANY_TIMES=new CodeMsg(500505,"已经秒杀过");
    public  static CodeMsg ACCESS_TOO_MANY=new CodeMsg(500506,"访问太频繁，休息一会吧");
    public CodeMsg() {

    }

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
