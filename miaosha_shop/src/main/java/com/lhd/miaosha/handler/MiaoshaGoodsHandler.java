package com.lhd.miaosha.handler;

import com.lhd.miaosha.service.MiaoshaGoodsService;
import com.lhd.miaosha.service.RedisService;
import com.lhd.vo.MiaoshaGoods;
import com.lhd.vo.RedisKey;
import com.lhd.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class MiaoshaGoodsHandler {
    @Autowired
    MiaoshaGoodsService miaoshaGoodsService;
    @Autowired
    RedisService redisService;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String shopList( Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
       // System.out.println("token:"+token);
        // 从redis中查询数据
        //User user=redisService.get(RedisKey.USER_LOGIN+":"+token ,User.class);
       // if(user!=null){ redisService.set(RedisKey.USER_LOGIN ,token,user,1800);
        //1.从redis缓存中获取数据
String html=redisService.getString(RedisKey.PAGE_GOODS_LIST,"miaosha");
if(html!=null){
    return html;
}
        //2.如无缓存则取mysql的数据，并生成html页面
        List<MiaoshaGoods> list=miaoshaGoodsService.getAll();
        map.put("list",list);
        WebContext context=new WebContext(request,response,request.getServletContext(),request.getLocale(),map);
        //手动渲染一个页面
        html=thymeleafViewResolver.getTemplateEngine().process("list",context);
        //将手动渲染的页面存储在redis中
        redisService.setString(RedisKey.PAGE_GOODS_LIST,"miaosha",html,10);
        //3.返回html页

       // System.out.println(list);
        //for(MiaoshaGoods m:list){ System.out.println(m.getGoodsImg()); }
        return html; }
       // System.out.println(user);
      /*  String login = "login";
        return login; }*/

    @RequestMapping(value = "/miaoshaGoodsDetail/{miaoshaGoodsId}",produces ="text/html;charset=UTF-8")
    @ResponseBody
    public String goodsDetals(Map<String,Object> map, @PathVariable("miaoshaGoodsId") Integer miaoshaGoodsId,HttpServletResponse response,HttpServletRequest request){
        //User user=getUserForToken(token);
        //1.从缓存中获取数据；
        String html=redisService.getString(RedisKey.PAGE_GOODS_DETAILS,String.valueOf(miaoshaGoodsId));
        if(html!=null){ return html; }
        //如果缓存中无商品详情，则获得秒杀的商品；将商品详情存储到redis中
        MiaoshaGoods goods = miaoshaGoodsService.getGoodsById(miaoshaGoodsId);
        map.put("goods", goods);

      /*  //获得秒杀的商品；将商品详情存储到 map中
         MiaoshaGoods goods=miaoshaGoodsService.getGoodsById(miaoshaGoodsId);
         map.put("goods",goods);*/
         //生成状态信息，并存储到map中；
        long start=goods.getStartDate().getTime();
        long end=goods.getEndDate().getTime();
        long now=System.currentTimeMillis();
        int status=1;
        //秒杀商品的状态；1代表进行中。
        long remain=-1;
        //多久后开始秒杀
        if(now<start){
            //未开始；
            status=0;
            remain=(start-now)/1000; }
        else if(now>end){
            status=2;
        //秒杀已结束
             }
        map.put("status",status);
        map.put("remain",remain);
// 生成页面，并存到redis中；
WebContext context=new WebContext(request,response,request.getServletContext(),request.getLocale(),map) ;
//手动渲染一个页面；
html=thymeleafViewResolver.getTemplateEngine().process("miaoshaGoodsDetail",context);
//3.将html页面存储到redis中；
redisService.setString(RedisKey.PAGE_GOODS_DETAILS,String.valueOf(miaoshaGoodsId),html,10);
return html;
         }

/*** 根据token来获得用户信息； * @param token * @return */
private User getUserForToken(String token){
    User user=redisService.get(RedisKey.USER_LOGIN +":"+token,User.class);
    if(user!=null){ redisService.set(RedisKey.USER_LOGIN,token,user,1800); }
    return user; } }

