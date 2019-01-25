package me.zhyx.proxypool.service.impl;

import me.zhyx.proxypool.common.RedisKey;
import me.zhyx.proxypool.service.IProxyIpRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProxyIpRedisServiceImpl implements IProxyIpRedisService {
    @Autowired
    RedisTemplate<String, Serializable> redisCacheTemplate;

    @Override
    public List<Serializable> findAllByPageRt(int pageNumber, int pageSize) {
        Set<Serializable> set = redisCacheTemplate.opsForZSet().range(RedisKey.PROXY_IP_KEY, pageNumber * pageSize, (pageNumber + 1) * pageSize);
        return new ArrayList<Serializable>(set);
    }
}
