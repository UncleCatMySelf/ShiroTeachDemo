package com.myself.cache;

import com.myself.util.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 13:40 2018\4\18 0018
 */
@Component
public class RedisCache<K,V> implements Cache<K,V>{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private JedisUtil jedisUtil;

    private final String cache_prefix = "myself-cache:";

    private byte[] getKey(K k){
        if (k instanceof String){
            return (cache_prefix + k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }

    public V get(K k) throws CacheException {
        logger.info("Getting permission data from Redis");
        logger.info("从redis获取权限数据");
        byte[] value = jedisUtil.get(getKey(k));
        if (value != null){
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    public V put(K k, V v) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtil.set(key,value);
        jedisUtil.expire(key,600);
        return v;
    }

    public V remove(K k) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = jedisUtil.get(key);
        jedisUtil.del(key);
        if (value != null){
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    public void clear() throws CacheException {
        // Can not be arbitrarily rewritten
        // 不可以随意重写
    }

    public int size() {
        return 0;
    }

    public Set<K> keys() {
        return null;
    }

    public Collection<V> values() {
        return null;
    }
}
