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
 * 博客的主要内容
 * @author HCY
 * @since 2020-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 博客序号
     */
    @TableId("blog_id")
    private String blogId;

    /**
     * 博客标题
     */
    private String blogTitle;

    /**
     * 博客宣传图片的地址
     */
    private String blogAdvertising;

    /**
     * 博客的内容
     */
    private String blogContent;

    /**
     * 博客的种类
     */
    private String blogCategory;

    /**
     * 博客的标记
     */
    private String blogSign;

    /**
     * 博客的描述
     */
    private String blogDescribe;

    /**
     * 博客的浏览次数
     */
    private Integer blogViews;

    /**
     * 博客的赞赏是否开启
     */
    private Boolean blogAppreciate;

    /**
     * 博客的版权是否开启
     */
    private Boolean blogCopyright;

    /**
     * 博客的评论是否开启
     */
    private Boolean blogComment;

    /**
     * 博客是否发布
     */
    private Boolean blogRelease;

    /**
     * 博客的创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date blogEstablish;

    /**
     * 博客的修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date blogModification;

    @TableField(exist = false)
    private Integer commentCount;

}
