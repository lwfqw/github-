package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User,Integer> {

    /**
     * 通过用户名查询用户信息
     * @param userName
     * @return
     */
     User selectUserByName(String userName);
     List<Map<String, Object>> queryAllSales();
}