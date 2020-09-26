package com.lhd.vo;

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(CodeMsg code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public Result(T data) {
        this.code = 0;
        this.msg = "成功";
        this.data = data;
    }

    public static <T> Result success(T t) {
        return new Result(t);
    }

    public static <T> Result error(CodeMsg code) {
        return new Result(code);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean isSuccessful(){
        return this.code==0;
    }
}
