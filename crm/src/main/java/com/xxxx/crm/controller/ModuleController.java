package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.config.Privalige;
import com.xxxx.crm.query.TreeDto;
import com.xxxx.crm.service.ModuleService;
import com.xxxx.crm.vo.TModule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lwf
 * @title: ModuleController
 * @projectName crm
 * @description: TODO
 * @date 2020/12/59:02
 */
@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {
    @Resource
    private ModuleService moduleService;

    /**
     * 角色授权，返回角色权限信息
     * @param roleId
     * @return
     */
    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<Map<String,Object>> list(Integer roleId){return moduleService.list(roleId);}

    /**
     * 去模块首页
     * @return
     */
    @Privalige("6030")
    @RequestMapping("index")
    public String goIndex(){
        return "module/module";
    }

    /**
     * 模块首页数据
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> indexList(){
        return moduleService.indexData();
    }

    /**
     * 去添加模块
     * @param grade 模块等级
     * @param parentId 父模块id
     * @param model 数据
     * @return
     */
    @RequestMapping("addModulePage")
    public String goAdd(Integer grade, Integer parentId, Model model){
        model.addAttribute("grade",grade);
        model.addAttribute("parentId",parentId);
        return "module/add";
    }

    /**
     * 保存
     * @param module
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(TModule module){
        moduleService.add(module);
       return success("模块添加成功");
    }
    //修改界面
    @RequestMapping("updateModulePage")
    public String toUpdate(@NotNull(message = "id不为空") Integer id, Model model){
        model.addAttribute("module",moduleService.queryById(id));
        return "module/update";
    }
    //修改
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(TModule module){
        moduleService.update(module);
        return success("修改成功");
    }
    //修改
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer id){
        moduleService.delete(id);
        return success("删除成功");
    }
}
