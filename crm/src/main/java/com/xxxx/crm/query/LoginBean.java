package com.xxxx.crm.query;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lwf
 * @title: LoginBean
 * @projectName crm
 * @description: TODO
 * @date 2020/11/3021:38
 */
@Data
public class LoginBean {
    @NotNull(message = "用户名非空")
    private String userName;
    @NotNull(message = "密码非空")
    private String userPwd;
}
