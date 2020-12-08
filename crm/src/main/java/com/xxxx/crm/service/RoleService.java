package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.dao.TRoleDao;
import com.xxxx.crm.exceptions.SqlFoundOrNo;
import com.xxxx.crm.query.RoleQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.TRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lwf
 * @title: RoleService
 * @projectName crm
 * @description: TODO
 * @date 2020/12/411:17
 */
@Service
public class RoleService {
    @Resource
    private TRoleDao tRoleDao;
    public List<Map<String,Object>> allRoleIdAndName(Integer userId){
        return tRoleDao.queryAllRoles(userId);
    }

    /**
     * 用户id查角色
     * @param userId
     * @return
     */
    public List<Map<String,Object>> userRole(Integer userId){
        return tRoleDao.whoRole(userId);
    }

    /**
     * 查角色表分页
     * @param query
     * @return
     */
    public Map<String,Object> list(RoleQuery query){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(query.getPage(), query.getLimit());
        map.put("code", 0);
        map.put("msg", "角色表数据");
        map.put("data",new PageInfo<TRole>(tRoleDao.query(query)).getList());
        return map;
    }

    /**
     * 具体某角色
     * @param id
     * @return
     */
    public TRole queryById(Integer id){
        AssertUtil.isTrue(id==null, "角色id不为空");
        return tRoleDao.queryById(id);
    }

    /**
     * 保存
     * @param role
     * @return
     */
    @Transactional
    public ResultInfo save(@Valid TRole role){
        AssertUtil.isTrue(role.getId()!=null, "不需要id");
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        role.setIsValid(1);
        //角色名唯一验证
        if(tRoleDao.queryByName(role.getRoleName())!=null){
            throw new SqlFoundOrNo(300, "角色名已存在");
        }
        if(tRoleDao.insert(role)<1){
            throw new SqlFoundOrNo(300, "角色添加失败");
        }
        return new ResultInfo();
    }

    /**
     * 更新
     * @param role
     * @return
     */
    @Transactional
    public ResultInfo update(TRole role){
      AssertUtil.isTrue(role.getId()==null, "更新需要主键");
      AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()), "未输入角色名");
      AssertUtil.isTrue(StringUtils.isBlank(role.getRoleRemark()), "未输入备注");
      role.setUpdateDate(new Date());
      role.setIsValid(1);
      if (tRoleDao.update(role)<1) {
            throw new SqlFoundOrNo(300,"更新角色失败");
        }
      return new ResultInfo();
    }

    /**
     * 删除角色，非物理删除，状态变不可用
     * @param id
     * @return
     */
    @Transactional
    public ResultInfo deleteByChangeValid(Integer id){
        AssertUtil.isTrue(id==null, "未输入删除id");
        TRole role=new TRole();
        role.setId(id);
        role.setIsValid(0);
        role.setUpdateDate(new Date());
        if (tRoleDao.update(role)<1) {
            throw new SqlFoundOrNo(300,"删除角色失败");
        }
        return new ResultInfo();
    }
}
