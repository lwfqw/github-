package com.lwf.first.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

/**
 * @author lwf
 * @title: RedisController
 * @projectName spring_cloud_first
 * @description: TODO
 * @date 2020/12/921:49
 */
@RestController
public class RedisController {
    @Value("${redisIP}")
    private String ip;
    @RequestMapping("/redis/{key}")
    public Object get(@PathVariable("key") String key){
        Jedis jedis=new Jedis(ip);
        return jedis.get(key);
    }
}
