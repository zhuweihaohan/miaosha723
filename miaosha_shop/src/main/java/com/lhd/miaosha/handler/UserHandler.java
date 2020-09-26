package com.lhd.miaosha.handler;

import com.lhd.miaosha.mapper.UserMapper;
import com.lhd.miaosha.service.RedisService;
import com.lhd.miaosha.service.UserService;
import com.lhd.util.Md5Util;
import com.lhd.util.Tools;
import com.lhd.vo.RedisKey;
import com.lhd.vo.Result;
import com.lhd.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.net.httpserver.HttpServerImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;


@Controller
public class UserHandler {
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @RequestMapping("/")
public String to_login(){
return "login";
    }
    @RequestMapping("/login")
    @ResponseBody
    public  Result<User> login(User user, HttpServletResponse response) {

        Result<User> result = userService.login(user.getTel(), user.getPassword());
        if (result.isSuccessful()) {
            //生成一个唯一的sessionId
            String sessionId = Tools.uuid();

            //将用户写到redis中
            redisService.set(RedisKey.USER_LOGIN, sessionId, result.getData(), 1800);

//生成Cookie
            Cookie cookie=new Cookie("token",sessionId);
            cookie.setMaxAge(3600*24*2);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

return result;
    }
   /* @ResponseBody
    @RequestMapping("/list")
    public User shopList(@CookieValue("token") String token){
       // System.out.println("token:"+token);
    // 从redis中查询数据
        User user=redisService.get(RedisKey.USER_LOGIN+":"+token ,User.class);
        if(user==null)
        {
            redisService.set(RedisKey.USER_LOGIN,token,user,1800);
        }
       // System.out.println(user);
        return user; }*/
}
