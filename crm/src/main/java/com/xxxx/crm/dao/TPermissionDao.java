package com.xxxx.crm.dao;

import com.xxxx.crm.vo.TPermission;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (TPermission)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-05 14:03:00
 */
public interface TPermissionDao {
    //
    Integer queryNumByRid(Integer roleId);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    //
    TPermission queryById(Integer id);
    //
    Integer inserts(List<TPermission> list);
    /**
     * 新增数据
     *
     * @param tPermission 实例对象
     * @return 影响行数
     */
    int insert(TPermission tPermission);

    /**
     * 修改数据
     *
     * @param tPermission 实例对象
     * @return 影响行数
     */
    int update(TPermission tPermission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 用户编号查权限
     * @param userId
     * @return
     */
    List<String> privaligeByUser(Integer userId);

    Integer deleteMidCase(Integer mid);
    Integer countMid(Integer mid);
}