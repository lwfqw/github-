package com.xxxx.crm.query;

import com.xxxx.crm.base.BaseQuery;
import lombok.Data;

/**
 * @author lwf
 * @title: UserQuery
 * @projectName crm
 * @description: TODO
 * @date 2020/12/319:35
 */
@Data
public class UserQuery extends BaseQuery {
    // 用户名
    private String userName;
    // 邮箱
    private String email;
    // 电话
    private String phone;
}
