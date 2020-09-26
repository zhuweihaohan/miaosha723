package com.lhd.miaosha.handler;

import com.lhd.miaosha.service.MiaoshaGoodsService;
import com.lhd.miaosha.service.MiaoshaOrderService;
import com.lhd.miaosha.service.RedisService;
import com.lhd.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

@Controller
public class MiaoshaOrderHandler {
   @Autowired
    MiaoshaGoodsService miaoshaGoodsService;
   @Autowired
    MiaoshaOrderService miaoshaOrderService;
   @Autowired
    RedisService redisService;
    @RequestMapping("/do_miaosha")
    public String miaosha(@CookieValue(value = "token",required = false) String token, Map<String,Object> map, MiaoshaOrder order){
        System.out.println("-------------");
        System.out.println(token);
        System.out.println(map);
        System.out.println(order);
        //1.判断是否登录；
        User user=getUserForToken(token);
        if(user==null){ return "redirect:/"; }
        //2.判断是否秒杀结束
        System.out.println("商品编号："+order.getMiaoshaGoodsId());
        int count=miaoshaGoodsService.getGoodsStockCount(order.getMiaoshaGoodsId());
        System.out.println("库存："+count);
        if(count<1){ map.put("result", Result.error(CodeMsg.MIAOSHA_STOCK_ZERO));
        return "miaoshaError"; }
       // 3设置订单信息；
        order.setUserid(user.getUserid());
        order.setGoodsCount(1);
        order.setCreateTime(new Date());
        order.setPayTime(new Date());
        System.out.println(order);
        /* 4.下订单 */
        try{
            Result<MiaoshaOrder> result=miaoshaOrderService.miaosha(order);
            map.put("result",result);
            if(result.isSuccessful()){
                map.put("goods",miaoshaGoodsService.getGoodsById(order.getMiaoshaGoodsId()) );
                return "miaoshaOrder"; } }
        catch(Exception e){
            map.put("result",Result.error(CodeMsg.MIAOSHA_MANY_TIMES)); }
        return "miaoshaError"; }
    /*** 根据token来获得用户信息； * @param token * @return */
    private User getUserForToken(String token){
        User user=redisService.get(RedisKey.USER_LOGIN +":"+token,User.class);
        if(user!=null){ redisService.set(RedisKey.USER_LOGIN,token,user,1800); }
        return user; }
}
