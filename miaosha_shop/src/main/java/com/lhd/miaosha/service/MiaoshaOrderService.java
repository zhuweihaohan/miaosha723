package com.lhd.miaosha.service;

import com.lhd.miaosha.mapper.MiaoshaOrderMapper;
import com.lhd.miaosha.mapper.OrderInfoMapper;
import com.lhd.miaosha.rabbitmq.MQSender;
import com.lhd.vo.CodeMsg;
import com.lhd.vo.MiaoshaOrder;
import com.lhd.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaOrderService {
   @Autowired
    MiaoshaOrderMapper miaoshaOrderMapper;
   @Autowired
    MiaoshaGoodsService miaoshaGoodsService;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    MQSender mqSender;
   @Transactional
    public Result<MiaoshaOrder> miaosha(MiaoshaOrder order){
       //1.减库存
       if(!miaoshaGoodsService.reduceStockCount(order.getMiaoshaGoodsId())) {
           return Result.error(CodeMsg.MIAOSHA_STOCK_ZERO); }
       //2.产生订单；
       orderInfoMapper.insert(order);
       //3.产生秒杀订单；如抛出异常，回退事务
      // try{
       miaoshaOrderMapper.insert(order);//}
      // catch (Exception e){
//return Result.error(CodeMsg.MIAOSHA_MANY_TIMES);

       //}
       //mqSender.sendMessage(order);
       //4.秒杀成功；
       return Result.success(order);
   }
}
