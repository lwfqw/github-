package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.config.Privalige;
import com.xxxx.crm.query.SaleQuery;
import com.xxxx.crm.service.SaleService;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.PhoneUtil;
import com.xxxx.crm.vo.TSaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lwf
 * @title: SaleChangeController
 * @projectName crm
 * @description: TODO
 * @date 2020/12/111:39
 */
@Controller
@RequestMapping("sale_chance")
public class SaleChangeController extends BaseController {
    @Resource
    private SaleService saleService;
    /**
     * 进入营销机会页面
     * @return
     */
    @Privalige("1010")
    @RequestMapping("index")
    public String index () {
        return "saleChance/sale_chance";
    }

    /**
     * 多条件分页查询营销机会
     * @param query
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> querySaleChanceByParams (SaleQuery query) {
        Map<String, Object> map= saleService.indexQuery(query);
     System.out.println(map);
        return map;
    }

    //全局添加
    @GetMapping("toAddUpdatePage")
    public String toAdd(){
        return "saleChance/add_update";
    }
    //更新
    @GetMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Integer id, HttpServletRequest req){
        AssertUtil.isTrue(id==null, "id不为空");
        req.setAttribute("sale", saleService.getById(id));
        return "saleChance/add_update";
    }
    @PostMapping("save")
    @ResponseBody
    public ResultInfo save(TSaleChance saleChance, HttpServletRequest req){
        AssertUtil.isTrue(!PhoneUtil.isMobile(saleChance.getLinkPhone()), "电话号码格式错误");
        if(saleChance.getId()!=null&&saleService.getById(saleChance.getId())!=null){//有id做更新，无id插入
            return saleService.update(saleChance);
        }
        if(StringUtils.isBlank(saleChance.getAssignMan())){
            saleChance.setAssignTime(null);
        }
        return saleService.save(saleChance,req);
    }
    /**
     * 机会数据添加与更新表单页面视图转发
     * id为空 添加操作
     * id非空 修改操作
     * @param model
     * @return
//     */
//    @RequestMapping("update")
//    @ResponseBody
//    public ResultInfo addOrUpdateSaleChancePage(TSaleChance saleChance, Model model){
//        return saleService.update(saleChance);
//    }
    /**
     * 删除营销机会数据
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance (Integer[] ids) {
        AssertUtil.isTrue(ids.length<1, "请输入删除id");
        // 删除营销机会的数据
        return saleService.deleteBatch(ids);
    }

}

