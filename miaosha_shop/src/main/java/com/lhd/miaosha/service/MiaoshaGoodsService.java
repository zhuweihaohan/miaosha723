package com.lhd.miaosha.service;

import com.lhd.miaosha.mapper.MiaoshaGoodsMapper;
import com.lhd.vo.MiaoshaGoods;
import com.lhd.vo.RedisKey;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MiaoshaGoodsService implements InitializingBean {
    @Autowired
    RedisService redisService;
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

    @Override
    public void afterPropertiesSet() throws Exception {
//        System.out.println("秒杀商品库存数量初始化");
        List<MiaoshaGoods> list=miaoshaGoodsMapper.getAll();
        for(MiaoshaGoods goods:list){
            redisService.set(RedisKey.MIAOSHA_GOODS_STOCK,String.valueOf(goods.getMiaoshaGoodsId()),goods.getStockCount());
        }
    }
}
