package com.lwf.first.controller;

import com.lwf.first.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lwf
 * @title: RpcForUser
 * @projectName spring_cloud_first
 * @description: TODO
 * @date 2020/12/921:13
 */
@RestController
public class RpcForUser {
    @Resource
    private RestTemplate restTemplate;
    @RequestMapping("/rpcToUser")
    public List<User> rpc(){
        return restTemplate.getForObject("http://lwf:8001/user/", List.class);
    }
    @RequestMapping("/redis/{key}")
    public Object jdis(@PathVariable("key") String key){
        return restTemplate.getForObject("http://lwf:8011/redis/"+key, String.class);
    }
}
