package com.xxxx.crm.dao;

import com.xxxx.crm.query.RoleQuery;
import com.xxxx.crm.vo.TRole;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * (TRole)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-04 11:08:54
 */
public interface TRoleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TRole queryById(Integer id);

    /**
     * 角色名查询角色
     * @param name
     * @return
     */
    TRole queryByName(String name);
    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TRole> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);
    //条件查询
    List<TRole> query(RoleQuery query);
    /**
     * 通过实体作为筛选条件查询
     *
     * @param tRole 实例对象
     * @return 对象列表
     */
    List<TRole> queryAll(TRole tRole);
    List<Map<String,Object>> queryAllRoles(Integer userId);

    List<Map<String,Object>> whoRole(Integer userId);
    /**
     * 新增数据
     *
     * @param tRole 实例对象
     * @return 影响行数
     */
    int insert(TRole tRole);

    /**
     * 修改数据
     *
     * @param tRole 实例对象
     * @return 影响行数
     */
    int update(TRole tRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}