package com.xxxx.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.io.Serializable;

/**
 * (TSaleChance)实体类
 *
 * @author makejava
 * @since 2020-12-01 10:38:26
 */
@Data
public class TSaleChance implements Serializable {
    private static final long serialVersionUID = 700531642246502408L;
    
    private Integer id;
    /**
    * 机会来源
    */
    private String chanceSource;
    @NotBlank(message = "客户名不为空")
    private String customerName;
    private Integer cgjl;
    private String overview;
    @NotBlank(message = "联系人不为空")
    private String linkMan;
    @NotBlank(message = "联系电话不为空")
    @Length(min = 11,max = 11,message = "这是来自天国的号码吗")
    private String linkPhone;
    private String description;
    private String createMan;
    private String assignMan;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date assignTime;
    
    private Integer state;
    
    private Integer devResult;
    
    private Integer isValid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;


}