package com.lhd.miaosha.service;

import com.lhd.miaosha.mapper.UserMapper;
import com.lhd.util.Md5Util;
import com.lhd.vo.CodeMsg;
import com.lhd.vo.RedisKey;
import com.lhd.vo.Result;
import com.lhd.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
   UserMapper userMapper;
    @Autowired
    RedisService redisService;

    @Transactional
    public Result<User> login(String tel, String pass) {

        User user = null;
        user = userMapper.getUserByTel(tel);

        if (user == null) {
            return Result.error(CodeMsg.USER_NOT_FOUND_ERROR);
        }
        // System.out.println("找到用户"+user);
        //判断密码
        String salt = user.getSalt();
        String passwd = Md5Util.fromPassToDbPass(pass, salt);
        System.out.println(passwd);
        if (passwd.equals(user.getPassword())) {
            //登陆次数加1；
            //更新最后登陆时间

            return Result.success(user);

        } else {
            return Result.error(CodeMsg.USER_PASSWORD_ERROR);
        }

    }

   /* @Transactional
    public User updatePassword(String token, String password) {
//1.查找用户；
        User user = getUserForToken(token);
        if (user == null) {
            return null;
        }
        //2.使用md5进行加密
        User newUser = new User();
        newUser.setUserid(user.getUserid());
        String md5Password = Md5Util.fromPassToDbPass(password, user.getSalt());
        newUser.setPassword(md5Password);


        //3.更新数据库
        userMapper.updateByPrimaryKeySelective(newUser);

        //4.将用户对象写回到缓存
        user.setPassword(password);
        redisService.set(RedisKey.USER_LOGIN, token, user, 30 * 60);
        return user;
    }

    private User getUserForToken(String token) {
        User user = new User();
        user = redisService.get(RedisKey.USER_LOGIN + ":" + token, User.class);
        if (user != null) {
            redisService.set(RedisKey.USER_LOGIN, token, user, 1800);
        }

        return user;
    }*/
}