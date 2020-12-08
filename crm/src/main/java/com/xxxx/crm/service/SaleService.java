package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.dao.TSaleChanceDao;
import com.xxxx.crm.query.SaleQuery;
import com.xxxx.crm.exceptions.SqlFoundOrNo;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.CookieUtil;
import com.xxxx.crm.vo.TSaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lwf
 * @title: SaleService
 * @projectName crm
 * @description: TODO
 * @date 2020/12/111:20
 */
@Service
public class SaleService {
    @Resource
    private TSaleChanceDao tSaleChanceDao;
    public TSaleChance getById(Integer id){return tSaleChanceDao.queryById(id);}
    /*
    条件查询
     */
    public Map<String,Object> indexQuery(SaleQuery query){
        TSaleChance saleChance=new TSaleChance();
        saleChance.setCreateMan(query.getCreateMan());
        saleChance.setCustomerName(query.getCustomerName());
        saleChance.setState(query.getState());
        Map<String,Object> res=new HashMap<>();
        res.put("code", 0);
        res.put("msg", "success");
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<TSaleChance> tSaleChancePageInfo = new PageInfo<>(tSaleChanceDao.queryAll(saleChance));
        res.put("data", tSaleChancePageInfo.getList());
        return res;
    }
    //添加
    /**
     * 营销机会数据添加
     * 1.参数校验
     * customerName:非空
     * linkMan:非空
     * linkPhone:非空 11位手机号
     * 2.设置相关参数默认值
     * state:默认未分配 如果选择分配人 state 为已分配
     * assignTime:如果 如果选择分配人 时间为当前系统时间
     * devResult:默认未开发 如果选择分配人devResult为开发中 0-未开发 1-开发中 2-开发成功 3-开
     发失败
     * isValid:默认有效数据(1-有效 0-无效)
     * createDate updateDate:默认当前系统时间
     * 3.执行添加 判断结果
     */
    @Transactional
    public ResultInfo save(@Valid TSaleChance chance, HttpServletRequest req){
        chance.setCreateMan(CookieUtil.getCookieValue(req, "trueName"));
        chance.setIsValid(1);
        chance.setState(0);
        chance.setAssignTime(new Date());
        chance.setDevResult(0);
        chance.setCreateDate(new Date());
        System.out.println(chance);
        int insert = tSaleChanceDao.insert(chance);
        if(insert<1){
            throw new SqlFoundOrNo(300, "添加失败");
        }
        return new ResultInfo();
    }

/**
 * 营销机会数据更新
 * 1.参数校验
 * id:记录必须存在
 * customerName:非空
 * linkMan:非空
 * linkPhone:非空，11位手机号
 * 2. 设置相关参数值
 * updateDate:系统当前时间
 * 原始记录 未分配 修改后改为已分配(由分配人决定)
  * state 0->1
  * assginTime 系统当前时间
 * devResult 0-->1
 * 原始记录 已分配 修改后 为未分配
 * state 1-->0
 * assignTime 待定 null
  * devResult 1-->0
  * 3.执行更新 判断结果
  */
@Transactional
public ResultInfo update(@Valid TSaleChance chance){//@Valid参数验证
    AssertUtil.isTrue(chance.getId()==null,"id不为空");
    TSaleChance db = tSaleChanceDao.queryById(chance.getId());
    if(StringUtils.isBlank(db.getAssignMan())){//有联系人
       if(db.getAssignMan().equals(chance.getAssignMan())){//联系人未改变
          chance.setAssignTime(db.getAssignTime());//更新时间
       }else if(!StringUtils.isBlank(chance.getAssignMan())){//改变
           chance.setAssignTime(new Date());
           chance.setState(1);
           chance.setDevResult(1);
       }else {//未分配
           chance.setAssignMan(null);
           chance.setAssignTime(null);
           chance.setState(0);
           chance.setDevResult(0);
       }
    }else {//无联系人
       if(!StringUtils.isBlank(chance.getAssignMan())){//添加联系人
          chance.setAssignTime(new Date());
          chance.setState(1);
          chance.setDevResult(1);
       }else {
           chance.setAssignTime(null);
           chance.setState(0);
           chance.setDevResult(0);
       }
    }
    chance.setUpdateDate(new Date());
    if(tSaleChanceDao.update(chance)<1){//更新失败
        throw new SqlFoundOrNo(300, "更新失败");
    }
    return new ResultInfo();
}
//删除一个
    @Transactional
public ResultInfo delete(Integer id){
    AssertUtil.isTrue(id==null, "参数不能空");
    int delete = tSaleChanceDao.deleteById(id);
    if(delete<1){
        throw new SqlFoundOrNo(300, "删除失败");
    }
    return new ResultInfo();
}
/**
 * 营销机会数据删除 批量删除
 * @param ids
 */
@Transactional
public ResultInfo deleteBatch(Integer[] ids){
    Integer batch = tSaleChanceDao.deleteBatch(ids);
    if(batch==ids.length){
        return new ResultInfo();
    }else if(batch>0){
        throw new SqlFoundOrNo(300, "期望删除:"+ids.length+"条,实际删除:"+batch);
    }else {
        throw new SqlFoundOrNo(300, "删除失败，被删除数据:0");
    }
}
}
