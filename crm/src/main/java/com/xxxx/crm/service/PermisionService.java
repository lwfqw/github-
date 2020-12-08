package com.xxxx.crm.service;

import com.xxxx.crm.dao.TModuleDao;
import com.xxxx.crm.dao.TPermissionDao;
import com.xxxx.crm.exceptions.SqlFoundOrNo;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.TModule;
import com.xxxx.crm.vo.TPermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lwf
 * @title: PermisionService
 * @projectName crm
 * @description: TODO
 * @date 2020/12/515:07
 */
@Service
public class PermisionService {
    @Resource
    private TPermissionDao tPermissionDao;
    @Resource
    private TModuleDao tModuleDao;
    public Integer queryNumByRid(Integer roleId){
        AssertUtil.isTrue(roleId==null, "roleId空");
        return tPermissionDao.queryNumByRid(roleId);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public TPermission queryById(Integer id){
        AssertUtil.isTrue(id==null, "id空");
        return tPermissionDao.queryById(id);
    }

    /**
     * 用户拥有权限码
     * @param userId
     * @return
     */
    public List<String> privaligeByUser(Integer userId){
        return tPermissionDao.privaligeByUser(userId);
    }
    /**
     * 批量插入
     * @param rid
     * @param mid
     * @return
     */
    @Transactional
    public void inserts(Integer rid,Integer[] mid){
        List<TPermission> list=new ArrayList<>();
        AssertUtil.isTrue(rid==null, "rid  empty");
        AssertUtil.isTrue(mid.length<1||mid==null, "no mid");
        deleteById(rid);
        for(Integer integer:mid){
            TPermission permission=new TPermission();
            permission.setRoleId(rid);
            permission.setModuleId(integer);
            permission.setUpdateDate(new Date());
            permission.setCreateDate(new Date());
            TModule tModule = tModuleDao.queryById(integer);
            AssertUtil.isTrue(tModule==null, "模块id不合法");
            permission.setAclValue(tModule.getOptValue());
            list.add(permission);
        }
        if(tPermissionDao.inserts(list)<mid.length){
            throw new SqlFoundOrNo(300, "授权失败");
        }
    }
    /**
     * 新增数据
     *
     * @param tPermission 实例对象
     * @return 影响行数
     */
    @Transactional
    public void insert(TPermission tPermission){
        //do none
    }

    /**
     * 修改数据
     *
     * @param tPermission 实例对象
     * @return 影响行数
     */
    @Transactional
    public void update(TPermission tPermission){
        //do none
    }

    /**
     * 通过roleId删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Transactional
    public int deleteById(Integer id){
        return tPermissionDao.deleteById(id);
    }
}
