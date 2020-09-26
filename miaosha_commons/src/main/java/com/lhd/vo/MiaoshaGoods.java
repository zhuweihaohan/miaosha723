package com.lhd.vo;

import java.util.Date;

public class MiaoshaGoods extends Goods implements  java.io.Serializable {
    private Integer miaoshaGoodsId;

    private Integer goodsId;

    private Integer miaoshaPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;

    public Integer getMiaoshaGoodsId() {
        return miaoshaGoodsId;
    }

    public void setMiaoshaGoodsId(Integer miaoshaGoodsId) {
        this.miaoshaGoodsId = miaoshaGoodsId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice(Integer miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}