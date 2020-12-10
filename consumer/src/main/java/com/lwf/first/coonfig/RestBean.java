package com.lwf.first.coonfig;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author lwf
 * @title: RestBean
 * @projectName spring_cloud_first
 * @description: TODO
 * @date 2020/12/921:11
 */
@Configuration
public class RestBean {
    @Bean
    @LoadBalanced
    public RestTemplate getRest(){
        return new RestTemplate();
    }
}
