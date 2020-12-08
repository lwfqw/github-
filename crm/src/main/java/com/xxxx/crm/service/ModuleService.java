package com.xxxx.crm.service;

import com.xxxx.crm.dao.TModuleDao;
import com.xxxx.crm.dao.TPermissionDao;
import com.xxxx.crm.exceptions.ParamsException;
import com.xxxx.crm.exceptions.SqlFoundOrNo;
import com.xxxx.crm.query.TreeDto;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.TModule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.tools.tree.NewArrayExpression;

import javax.annotation.Resource;
import javax.swing.text.Keymap;
import javax.validation.Valid;
import java.util.*;

/**
 * @author lwf
 * @title: ModuleService
 * @projectName crm
 * @description: TODO
 * @date 2020/12/422:09
 */
@Service
public class ModuleService {
    //todo 权限树
    @Resource
    private TModuleDao tModuleDao;
    @Resource
    private TPermissionDao tPermissionDao;
    /**
     * 授权显示树
     * @return
     */
    public List<Map<String,Object>> list(Integer rid){
        List<Map<String,Object>> list=tModuleDao.queryAllModules(rid);
        list.forEach(System.out::println);
        return list;
    }

    /**
     * 模块管理首页
     * @return
     */
    public Map<String,Object> indexData(){
        Map<String,Object> map=new HashMap<>();
        map.put("code", 0);
        map.put("msg", "模块表展示");
        map.put("data", tModuleDao.queryListToIndex());
        return map;
    }

    /**
     * 添加模块
     * * 1.参数校验
     * * 模块名-module_name 非空 同一层级下模块名    唯一
     * * url 二级菜单 非空                          不可重复
     * * 上级菜单-parent_id   !(grade==0&&parentId==null)
     * * 一级菜单 null        !(grade>0&&parentId!=null)
     * * 二级|三级菜单 parent_id 非空 必须存在
     * * 层级-grade 非空 0|1|2
     * * 权限码 optValue 非空                      不可重复
     * * 2.参数默认值设置
     * * is_valid create_date update_date
     * * 3.执行添加 判断结果
     *
     * @param module
     */
    @Transactional
    public void add(@Valid TModule module) {
        //验证
        AssertUtil.isTrue(null!=tModuleDao.queryByMnameAndPId(module.getModuleName(),module.getParentId()), "模块名存在");
        AssertUtil.isTrue(module.getGrade()==1&&null!=tModuleDao.queryUrl(module.getUrl()), "url存在");
        if(module.getGrade()==0){
            if(module.getParentId()!=-1){
                throw new ParamsException("grade:0,pid须为-1");
            }
        }else {
            if(module.getParentId()==null){
                throw new ParamsException("grade>0,pid不为空");
            }
        }
        //默认设置
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        module.setIsValid(1);
        //添加
        if(tModuleDao.insert(module)<1){
            throw new SqlFoundOrNo(300, "添加模块失败");
        }
    }
    //id查询
    public TModule queryById(Integer id) {
        return tModuleDao.queryById(id);
    }
     //更新
    @Transactional
    public void update(TModule module) {
        AssertUtil.isTrue(module.getId()==null, "id need");
        module.setUpdateDate(new Date());
        if (tModuleDao.update(module)<1) {
            throw new SqlFoundOrNo(300, "更新失败");
        }
    }
    //删除
    @Transactional
    public void delete(Integer mid){
        AssertUtil.isTrue(mid==null||tModuleDao.queryById(mid)==null, "不存在");
        AssertUtil.isTrue(tModuleDao.queryByPid(mid).size()!=0, "有子目录");
        AssertUtil.isTrue(tPermissionDao.deleteMidCase(mid)!=tPermissionDao.countMid(mid), "权限删除失败");
        AssertUtil.isTrue(tModuleDao.update(new TModule(mid,null , null, null, null, null,null , null, null, 0,null,new Date()))<1, "模块删除失败");

    }
}
