package com.lwf.first.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lwf
 * @title: User
 * @projectName spring_cloud_first
 * @description: TODO
 * @date 2020/12/921:15
 */
    @Data
    public class User implements Serializable {
        private static final long serialVersionUID = 886286383218853916L;
        private Integer id;
        private String name;
        private Integer age;
        private String city;

    }

