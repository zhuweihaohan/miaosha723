package com.lhd.vo;

public class MiaoshaOrder extends OrderInfo implements  java.io.Serializable{
    private Integer miaoshaOrderId;

    private Integer userid;

    private Integer miaoshaGoodsId;

    private Integer orderId;

    public Integer getMiaoshaOrderId() {
        return miaoshaOrderId;
    }

    public void setMiaoshaOrderId(Integer miaoshaOrderId) {
        this.miaoshaOrderId = miaoshaOrderId;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getMiaoshaGoodsId() {
        return miaoshaGoodsId;
    }

    public void setMiaoshaGoodsId(Integer miaoshaGoodsId) {
        this.miaoshaGoodsId = miaoshaGoodsId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "MiaoshaOrder{" +
                "miaoshaOrderId=" + miaoshaOrderId +
                ", userid=" + userid +
                ", miaoshaGoodsId=" + miaoshaGoodsId +
                ", orderId=" + orderId +
                '}';
    }
}