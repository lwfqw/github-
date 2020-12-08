package com.xxxx.crm.exceptions;

import lombok.Data;

/**
 * @author lwf
 * @title: SqlNotFound
 * @projectName crm
 * @description: TODO
 * @date 2020/11/3021:43
 */
@Data
public class SqlFoundOrNo extends RuntimeException{
    private Integer code=300;
    private String msg="用户不存在!";

    public SqlFoundOrNo(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
