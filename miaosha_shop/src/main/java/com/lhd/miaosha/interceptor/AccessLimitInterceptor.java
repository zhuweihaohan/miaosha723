package com.lhd.miaosha.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhd.miaosha.service.RedisService;
import com.lhd.util.AccessLimit;
import com.lhd.util.Tools;
import com.lhd.vo.CodeMsg;
import com.lhd.vo.RedisKey;
import com.lhd.vo.Result;
import com.lhd.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// 定义Interceptor
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {
    @Autowired
    RedisService redisService;
    @Override public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //思路： /*** 1.获得方法的Annotation;是否存在AccessLimit注解；如不存在，放行；如果存在，则拦 截；
        // * 2.获得注解信息；seconds,maxCount,needLogin; **/
        if (handler instanceof HandlerMethod) {
            //转成Hander方法；
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //1.是否使用了AccessLimit注解
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null)
            { return true;
            }
            //2.获得AccessLimit属性；


int seconds = accessLimit.seconds();
    int maxCount = accessLimit.maxCount();
    boolean needLogin = accessLimit.needLogin();
    //如果需要登录，则判断是否已经登录；
    if (needLogin) {
        User user = getUser(request);
        if (user == null) {
            //用户还没登录； //渲染；
            Result result = Result.error(CodeMsg.USER_NOT_LOGIN);
            render(request, response, result);
            return false; } }
    //限流； //思路：
    // /*** 1.redis是否存在limitToken+url,如存在，获取访问次数，并与注解中的maxCount 比较；如大于maxCount,则限流；
    // *2.如果不存在，将访问次数的数据存在redis中 */
    // 1.获得limitToken,
    String limitToken =getCookie(request,"limitToken");
    String url=request.getRequestURI().replace('/','_');
    if(limitToken==null){ //第一次访问
         limitToken= Tools.uuid();
         Cookie c=new Cookie("limitToken",limitToken);
         c.setPath("/");
         c.setMaxAge(60*60*24*2);
         response.addCookie(c); }//判断redis中是否布在此 key,如存在，则获取值
     if(redisService.hasKey(RedisKey.LIMIT_TOKEN,limitToken+":"+url)) { //获得 值，是否大于等于maxCount，则限流
          Integer count=redisService.get(RedisKey.LIMIT_TOKEN,limitToken+":"+url,Integer.class);
          if(count<maxCount){//放行；
               redisService.increment(RedisKey.LIMIT_TOKEN,limitToken+":"+url);
               return true; }else{
              render(request,response,Result.error(CodeMsg.ACCESS_TOO_MANY));
              return false; } }else{ redisService.set(RedisKey.LIMIT_TOKEN,limitToken+":"+url,1,seconds); } }
              return true; }
/*** 获得某个cookie值 ** @param request * @param key * @return */
private String getCookie(HttpServletRequest request, String key) {
    Cookie[] cookies = request.getCookies();
    for (Cookie c : cookies) {
        if (key.equals(c.getName())) {
            return c.getValue(); } }
    return null; }
    /*** 根据request来获得登录对象信息；
     *  ** @param request
     *  * @return */
    private User getUser(HttpServletRequest request) {
        String token = getCookie(request, "token");
        if (token != null) {
            User user = redisService.get(RedisKey.USER_LOGIN, token, User.class);
            if (user != null) { redisService.set(RedisKey.USER_LOGIN, token, user, 60 * 30); }
            return user; }return null; }
            /*** 渲染视图
             *  ** @param request
             *  * @param response
             *  * @param result */
            private void render(HttpServletRequest request, HttpServletResponse response, Result result) {
                response.setContentType("application/json;charset=UTF-8");
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String json = mapper.writeValueAsString(result);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.close(); }
                catch (Exception e) {
                    e.printStackTrace(); } }}

