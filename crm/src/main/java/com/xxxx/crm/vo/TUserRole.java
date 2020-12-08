package com.xxxx.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (TUserRole)实体类
 *
 * @author makejava
 * @since 2020-12-04 14:40:42
 */
@Data
public class TUserRole implements Serializable {
    private static final long serialVersionUID = 252712563326646507L;
    
    private Integer id;
    
    private Integer userId;
    
    private Integer roleId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

}