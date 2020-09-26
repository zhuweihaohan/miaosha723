package com.lhd.miaosha.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration public class RedisConfiguration {
    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<Object,Object> redisTemplate=new RedisTemplate<Object,Object>();

    redisTemplate.setConnectionFactory(factory);
    Jackson2JsonRedisSerializer<Object> serializer=jackson2JsonRedisSerializer();
    //设置redisTemplate序列化的规则；
     redisTemplate.setDefaultSerializer(serializer);
     return redisTemplate; }
/*** 通过自定义配置构建Redis的Json序列化器 * @return Jackson2JsonRedisSerializer对象 */
private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(){
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false); objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    // 此项必须配置，否则会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
    objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
    return jackson2JsonRedisSerializer; } }