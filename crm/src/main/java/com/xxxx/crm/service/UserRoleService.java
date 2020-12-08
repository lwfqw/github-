package com.xxxx.crm.service;

import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.dao.TUserRoleDao;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.TUserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lwf
 * @title: UserRoleService
 * @projectName crm
 * @description: TODO
 * @date 2020/12/416:11
 */
@Service
public class UserRoleService {
    @Resource
    private TUserRoleDao tUserRoleDao;
    @Transactional
    public ResultInfo inserts(Integer uid,String roleIds){
        AssertUtil.isTrue(uid==null, "用户id为空");
        List<TUserRole> list=new ArrayList<>();
        for(String rid:roleIds.trim().split(",")){
            TUserRole role=new TUserRole();
            role.setCreateDate(new Date());
            role.setUpdateDate(new Date());
            role.setUserId(uid);
            role.setRoleId(Integer.parseInt(rid));
            list.add(role);
        }
        AssertUtil.isTrue(tUserRoleDao.inserts(list)!=list.size(), "用户权限添加failed");
        return new ResultInfo();
    }
    @Transactional
    public boolean delete(Integer uids){
        return tUserRoleDao.deleteByUid(uids)==1;
    }
}
