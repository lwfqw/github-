package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.BaseQuery;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.config.Privalige;
import com.xxxx.crm.query.RoleQuery;
import com.xxxx.crm.service.PermisionService;
import com.xxxx.crm.service.RoleService;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.TRole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author lwf
 * @title: RoleController
 * @projectName crm
 * @description: TODO
 * @date 2020/12/411:19
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;
    @Resource
    private PermisionService permisionService;
    /**
     * 查询角色列表
     * @return
     */
    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRoles(Integer userId){
        return roleService.allRoleIdAndName(userId);
    }
    /**
     * 查询用户角色列表
     * @return
     */
    @RequestMapping("queryOneRoles")
    @ResponseBody
    public List<Map<String,Object>> queryOneRoles(Integer userId){
        return roleService.userRole(userId);
    }

    /**
     * 去角色管理首页
     * @return
     */
    @Privalige("6020")
    @RequestMapping("index")
    public String goIndex(){
        return "role/role";
    }
    /**
     * 角色表数据
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(RoleQuery query){
        return roleService.list(query);
    }

    /**
     * 去修改添加页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateRolePage")
    public String toAddUpdate(Integer id, Model model){
        if(id!=null)
            model.addAttribute("role", roleService.queryById(id));
        return "role/add_update";
    }

    /**
     * 添加
     * @param role
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(TRole role){
        return roleService.save(role);
    }

    /**
     * 更新角色
     * @param role
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResultInfo update(TRole role){return roleService.update(role);}
    @RequestMapping("/delete")
    @ResponseBody
    public ResultInfo delete(Integer id){return roleService.deleteByChangeValid(id);}

    /**
     * 去授权页面
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping("/toAddGrantPage")
    public String goGrant(Integer roleId,Model model){
        AssertUtil.isTrue(roleId==null, "请选择角色");
        AssertUtil.isTrue(null==roleService.queryById(roleId), "不存在的角色id");
        model.addAttribute("roleId", roleId);
        return "role/grant";
    }

    /**
     * persion授权
     * @param mids
     * @param roleId
     * @return
     */
    @RequestMapping("addGrant")
    @ResponseBody
    public ResultInfo add(Integer[] mids,Integer roleId){
       permisionService.inserts(roleId, mids);
        return success();
    }
}
