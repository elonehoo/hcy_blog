package com.inet.codebase.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 种类
 * @author HCY
 * @since 2020-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 种类的序号
     */
    @TableId("category_id")
    private String categoryId;

    /**
     * 种类的名称
     */
    private String categoryName;

    /**
     * 种类的创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date categoryEstablish;

    /**
     * 种类的修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date categoryModification;

    /**
     * 种类的博客数目
     */
    @TableField(exist = false)
    private Integer blogCount;
}
