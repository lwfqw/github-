package com.xxxx.crm.dao;

import com.xxxx.crm.query.TreeDto;
import com.xxxx.crm.vo.TModule;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * (TModule)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-04 21:55:46
 */
public interface TModuleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TModule queryById(Integer id);

    /**
     * 模块名-module_name 非空 同一层级下模块名    唯一
     * @param moduleName
     * @param parentId
     * @return
     */
    TModule queryByMnameAndPId(String moduleName,Integer parentId);
    /**
     * 查询某角色拥有模块
     * @param rid
     * @return
     */
    List<Map<String,Object>> queryAllModules(Integer rid);
    List<TModule>  queryListToIndex();
    /**
     * 所有Id，辅助生成树结构
     * @return
     */
    List<Integer> allId();
    /**
     * 新增数据
     *
     * @param tModule 实例对象
     * @return 影响行数
     */
    int insert(TModule tModule);

    /**
     * 修改数据
     *
     * @param tModule 实例对象
     * @return 影响行数
     */
    int update(TModule tModule);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * url查模块
     * @param url
     * @return
     */
    TModule queryUrl(String url);

    List<TModule> queryByPid(Integer mid);
}