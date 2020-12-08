package com.xxxx.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.io.Serializable;

/**
 * (TUser)实体类
 *
 * @author makejava
 * @since 2020-12-03 19:30:54
 */
@Data
public class TUser implements Serializable {
    private static final long serialVersionUID = 380669159177274048L;
    /**
     * 添加用户
     * 1. 参数校验
     * 用户名 非空
     * 邮箱 非空
     * 手机号 非空
    * 主键
    */
    private Integer id;
    @NotBlank(message = "用户名不能空")
    private String userName;
    private String userPwd;
    private String trueName;
    @NotBlank(message = "邮箱不能空")
    @Email(message = "email格式错误")
    private String email;
    @NotBlank(message = "号码不能空")
    private String phone;
    private Integer isValid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
}