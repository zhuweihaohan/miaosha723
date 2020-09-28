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
    public void set(String prefix,String key,Object value){
        redisTemplate.opsForValue().set(prefix+":"+key,value);

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

    /**
     * 对某个key的值做减1操作
     * @param prefix
     * @param key
     * @return
     */
    public long decrement(String prefix,String key){
        return redisTemplate.opsForValue().decrement(prefix+":"+key);
}
public String getString(String prefix,String key){
    return stringRedisTemplate.opsForValue().get(prefix+":"+key);
}

    public <T>T get(String prefix, String key, Class<T> Class) {
    T obj=(T)redisTemplate.opsForValue().get(prefix+":"+key);
    return obj;
    }

    /**
     * 判断是否存在某个key
     * @param prefix
     * @param key
     * @return
     */
    public boolean hasKey(String prefix,String key){
        return redisTemplate.hasKey(prefix+":"+key);
    }
    /**
     * 对某个key的值做加1操作
     * @param prefix
     * @param key
     * @return
     */
    public long increment(String prefix,String key){
        return redisTemplate.opsForValue().increment(prefix+":"+key);
    }

}
