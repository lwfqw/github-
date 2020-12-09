package com.lwf.first.entity;
import lombok.Data;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-12-09 17:38:54
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 886286383218853916L;
    private Integer id;
    private String name;
    private Integer age;
    private String city;

}