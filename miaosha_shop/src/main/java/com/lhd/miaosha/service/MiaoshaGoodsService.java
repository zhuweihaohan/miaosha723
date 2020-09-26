package com.lhd.miaosha.service;

import com.lhd.miaosha.mapper.MiaoshaGoodsMapper;
import com.lhd.vo.MiaoshaGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MiaoshaGoodsService {
    @Autowired
   MiaoshaGoodsMapper miaoshaGoodsMapper;
    public List<MiaoshaGoods> getAll(){
        return miaoshaGoodsMapper.getAll();
    }
    public MiaoshaGoods getGoodsById(Integer miaoshaGoodsId){
        return miaoshaGoodsMapper.getGoodsById(miaoshaGoodsId);
    }
    /*** 获得某个秒杀商品的库存
     * * @param miaoshGoodsId
     * * @return */
    public int getGoodsStockCount(Integer miaoshGoodsId){
        MiaoshaGoods goods=getGoodsById(miaoshGoodsId);
        return goods.getStockCount(); }
@Transactional
    public boolean reduceStockCount(Integer miaoshaGoodsId){

        return (miaoshaGoodsMapper.updateGoodsCount(miaoshaGoodsId)>0);
}
}
