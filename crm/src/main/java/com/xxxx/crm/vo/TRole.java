package com.xxxx.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.io.Serializable;

/**
 * (TRole)实体类
 *
 * @author makejava
 * @since 2020-12-04 11:08:54
 */
@Data
public class TRole implements Serializable {
    private static final long serialVersionUID = 815184522782030604L;
    
    private Integer id;
    @NotBlank(message = "角色名不为空")
    private String roleName;
    /**
    * 备注
    */
    @NotBlank(message = "备注不为空")
    private String roleRemark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
    
    private Integer isValid;

}