package com.xxxx.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (TPermission)实体类
 *
 * @author makejava
 * @since 2020-12-05 14:03:00
 */
@Data
public class TPermission implements Serializable {
    private static final long serialVersionUID = -54968738534826070L;
    
    private Integer id;
    /**
    * 角色ID
    */
    private Integer roleId;
    /**
    * 模块ID
    */
    private Integer moduleId;
    /**
    * 权限值
    */
    private String aclValue;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
}