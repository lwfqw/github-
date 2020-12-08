package com.xxxx.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.io.Serializable;

/**
 * (TModule)实体类
 *
 * @author makejava
 * @since 2020-12-04 21:55:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TModule implements Serializable {
    private static final long serialVersionUID = 938745735815624214L;
    private Integer id;
    /**
    * 资源名称
    */
    @NotBlank(message = "模块名不为空")
    private String moduleName;
    /**
    * 模块样式
    */
    private String moduleStyle;
    /**
    * 地址
    */
    @NotBlank(message = "url need")
    private String url;
    
    private Integer parentId;

    private String parentOptValue;
    /**
    * 等级
    */
    @NotNull(message = "grade need")
    @Max(value = 2,message = "等级过大，不合法")
    @Min(value = 0,message = "等级过小，不合法")
    private Integer grade;
    /**
    * 权限值
    */
    @NotBlank(message = "权限码非空")
    private String optValue;
    
    private Integer orders;
    
    private Integer isValid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

}