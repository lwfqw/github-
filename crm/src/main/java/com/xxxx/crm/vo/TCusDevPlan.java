package com.xxxx.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (TCusDevPlan)实体类
 *
 * @author makejava
 * @since 2020-12-03 10:02:17
 */
@Data
public class TCusDevPlan implements Serializable {
    private static final long serialVersionUID = 769429827371231951L;
    private Integer id;
    private Integer saleChanceId;
    private String planItem;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planDate;
    private String exeAffect;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
    private Integer isValid;
}