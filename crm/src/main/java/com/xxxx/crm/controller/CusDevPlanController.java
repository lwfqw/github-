package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.config.Privalige;
import com.xxxx.crm.query.CusDevPlanQuery;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.service.CusDevPlanService;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.TCusDevPlan;
import com.xxxx.crm.vo.TSaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lwf
 * @title: CusDevPlanController
 * @projectName crm
 * @description: TODO
 * @date 2020/12/217:01
 */
@Controller
@RequestMapping("cus_dev_plan")
public class CusDevPlanController extends BaseController {
    @Resource
    private CusDevPlanService cusDevPlanService;
    /***
     * 客户开发主页面
     * @return
     * */
    @Privalige("1020")
    @GetMapping("index")
    public String toIndex(){
        return "cusDevPlan/cus_dev_plan";
    }

    /**
     * 多条件分页查询营销机会
     * @param query
     * @return
     */
    @RequestMapping("list1")
    @ResponseBody
    public Map<String, Object> querySaleChanceByParams (SaleChanceQuery query, HttpServletRequest request) {
        Integer uid= LoginUserUtil.releaseUserIdFromCookie(request);
        AssertUtil.isTrue(uid==null||uid==0, "未登录");
        //  AssertUtil.isTrue(query.getState()==null, "state没有接收到");
        query.setAssignMan(uid);
        return cusDevPlanService.saleChancePageQuery(query);
    }
    /**
     * 多条件分页查询营销机会
     * @param query
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryDev (CusDevPlanQuery query) {
        System.out.println(query);
        if(query.getSaleChanceId()!=null){
            return cusDevPlanService.cusDevPageQuery(query);
        }
        return null;
    }
    /**
     * 进入开发计划项数据页面
     * @param model
     * @param sid
     * @return
     */
    @RequestMapping("toCusDevPlanDataPage")
    public String toCusDevPlanDataPage (Model model, Integer sid) {
     // 通过id查询营销机会数据
      AssertUtil.isTrue(sid==null||sid==0, "参数");
      // 将数据存到作用域中
        model.addAttribute("saleChance", cusDevPlanService.queryByPrimaryKey(sid));
        return "cusDevPlan/cus_dev_plan_data";
    }
    //添加修改开发计划
    @RequestMapping("addOrUpdateCusDevPlanPage")
    public ModelAndView goAddOrUp(TCusDevPlan q, ModelAndView mv){
        if(q.getId()!=null){
           mv.addObject("cusDevPlan", cusDevPlanService.query(q.getId()));
        }else {
            mv.addObject("sid", q.getSaleChanceId());
        }
        mv.setViewName("cusDevPlan/add_update");
        return mv;
    }
    //保存
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(TCusDevPlan plan){
        ResultInfo info = cusDevPlanService.insert(plan);
        return info;
    }
    //修改
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(TCusDevPlan plan){
        return cusDevPlanService.update(plan);
    }
    //删除
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer id){
        return cusDevPlanService.delete(id);
    }
    //状态修改
    @RequestMapping("updateResult")
    @ResponseBody
    public ResultInfo update(TSaleChance plan){
        return cusDevPlanService.updateRes(plan);
    }
}
