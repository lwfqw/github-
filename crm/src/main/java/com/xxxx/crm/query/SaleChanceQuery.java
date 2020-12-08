package com.xxxx.crm.query;

import com.xxxx.crm.base.BaseQuery;
import lombok.Data;

/**
 * @author lwf
 * @title: SaleChanceQuery
 * @projectName crm
 * @description: TODO
 * @date 2020/12/211:35
 */
@Data
public class SaleChanceQuery extends BaseQuery {
    private String customerName; // 客户名称
    private String createMan; // 创建人
    private String state; // 分配状态
    private Integer devResult; // 开发状态
    private Integer assignMan;// 分配人
}
