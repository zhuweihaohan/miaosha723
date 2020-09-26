package com.lhd.miaosha.service;

import com.lhd.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
@Autowired
        @Qualifier("redisTemplate")
    RedisTemplate redisTemplate;
@Autowired
StringRedisTemplate stringRedisTemplate;
public void set(String prefix,String key,Object value,int timeout){
    redisTemplate.opsForValue().set(prefix+":"+key,value,timeout, TimeUnit.SECONDS);

}

    /**
     * 获取数据
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String key,Class<T> clazz){
    T obj=(T)redisTemplate.opsForValue().get(key);
    return obj;
}

    /**
     * 向redis中存储String类型的shuju
     * @param prefix
     * @param key
     * @param value
     * @param timeout
     */
    public void setString(String prefix,String key,String value,int timeout){
        stringRedisTemplate.opsForValue().set(prefix+":"+key,value,timeout,TimeUnit.SECONDS);
}
public String getString(String prefix,String key){
    return stringRedisTemplate.opsForValue().get(prefix+":"+key);
}
}
