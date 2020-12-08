package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.dao.TCusDevPlanDao;
import com.xxxx.crm.dao.TSaleChanceDao;
import com.xxxx.crm.query.CusDevPlanQuery;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.exceptions.SqlFoundOrNo;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.TCusDevPlan;
import com.xxxx.crm.vo.TSaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lwf
 * @title: CusDevPlanService
 * @projectName crm
 * @description: TODO
 * @date 2020/12/219:25
 */
@Service
public class CusDevPlanService {
    @Resource
    private TSaleChanceDao tSaleChanceDao;
    @Resource
    private TCusDevPlanDao tCusDevPlanDao;
    public Map<String,Object> saleChancePageQuery(SaleChanceQuery query){
        Map<String,Object> res=new HashMap<>();
        res.put("code", 0);
        res.put("msg", "success");
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<TSaleChance> re=new PageInfo<>(tSaleChanceDao.selectByParams(query));
        res.put("data",re.getList());
        return res;
    }
    public TSaleChance queryByPrimaryKey(Integer id){
        return tSaleChanceDao.queryById(id);
    }
    public TCusDevPlan query(Integer id){return tCusDevPlanDao.queryById(id);}
    public Map<String,Object> cusDevPageQuery(CusDevPlanQuery query){
        Map<String,Object> res=new HashMap<>();
        res.put("code", 0);
        res.put("msg", "success");
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<TCusDevPlan> re=new PageInfo<>(tCusDevPlanDao.queryAll(query));
        res.put("data",re.getList());
        return res;
    }
    //更新
    @Transactional
    public ResultInfo update(TCusDevPlan data){
        AssertUtil.isTrue(data.getId()==null, "id空");
        data.setUpdateDate(new Date());
        if(null==tCusDevPlanDao.queryById(data.getId())){
            throw new SqlFoundOrNo(300,"数据不存在，更新失败");
        }
        if(tCusDevPlanDao.update(data)<1){
            throw new SqlFoundOrNo(300,"更新失败");
        }
        return new ResultInfo();
    }
    //添加
    @Transactional
    public ResultInfo insert(TCusDevPlan data){
        AssertUtil.isTrue(data.getId()!=null, "id不需要");
        AssertUtil.isTrue(StringUtils.isBlank(data.getPlanItem()), "计划内容空");
        AssertUtil.isTrue(StringUtils.isBlank(data.getExeAffect()), "效果空");
        AssertUtil.isTrue(data.getPlanDate()==null, "时间不为空");
        data.setUpdateDate(new Date());
        data.setCreateDate(new Date());
        data.setIsValid(1);
        if(tCusDevPlanDao.insert(data)<1){
            throw new SqlFoundOrNo(300,"插入失败");
        }
        return new ResultInfo();
    }
    //删除
    @Transactional
    public ResultInfo delete(Integer id){
        AssertUtil.isTrue(id==null, "id为空");
        int i = tCusDevPlanDao.deleteById(id);
        if(i<0){
            throw new SqlFoundOrNo(300,"删除开发计划失败");
        }
        return new ResultInfo();
    }
    //开发成功，失败
    @Transactional
    public ResultInfo updateRes(TSaleChance data){
        AssertUtil.isTrue(data.getId()==null, "id空");
        AssertUtil.isTrue(data.getDevResult()==null, "请选择操作");
        data.setUpdateDate(new Date());
        if(null==tSaleChanceDao.queryById(data.getId())){
            throw new SqlFoundOrNo(300,"数据不存在");
        }
        if(tSaleChanceDao.updateRes(data)<1){
            throw new SqlFoundOrNo(300,"更新失败");
        }
        return new ResultInfo();
    }
}
