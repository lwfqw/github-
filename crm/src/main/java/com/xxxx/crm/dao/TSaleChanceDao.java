package com.xxxx.crm.dao;

import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.vo.TSaleChance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TSaleChance)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-01 10:38:27
 */
public interface TSaleChanceDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TSaleChance queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TSaleChance> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tSaleChance 实例对象
     * @return 对象列表
     */
    List<TSaleChance> queryAll(TSaleChance tSaleChance);

    /**
     * 新增数据
     *
     * @param tSaleChance 实例对象
     * @return 影响行数
     */
    int insert(TSaleChance tSaleChance);

    /**
     * 修改数据
     *
     * @param tSaleChance 实例对象
     * @return 影响行数
     */
    int update(TSaleChance tSaleChance);
    /**
     * 修改开发结果
     *
     * @param tSaleChance 实例对象
     * @return 影响行数
     */
    int updateRes(TSaleChance tSaleChance);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);
    /**
     * 营销机会数据删除
     * @param ids
     */
    Integer deleteBatch(Integer[] ids);
    //todo 客户开发计划
    List<TSaleChance> selectByParams(SaleChanceQuery query);
}