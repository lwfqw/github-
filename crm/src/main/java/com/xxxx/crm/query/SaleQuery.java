package com.xxxx.crm.query;

import com.xxxx.crm.base.BaseQuery;
import lombok.Data;

/**
 * @author lwf
 * @title: SaleQuery
 * @projectName crm
 * @description: TODO
 * @date 2020/12/111:24
 */
@Data
public class SaleQuery extends BaseQuery {
    private String customerName;
    private String createMan;
    private Integer state;
}
