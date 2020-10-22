package com.inet.codebase.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 评论
 * @author HCY
 * @since 2020-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论序号
     */
    @TableId("comment_id")
    private String commentId;

    /**
     * 评论昵称
     */
    private String commentNickname;

    /**
     * 评论邮箱
     */
    private String commentMailbox;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commentEstablish;

    /**
     * 评论的博客序号
     */
    private String commentBlogid;

    /**
     * 评论的父博客序号
     */
    private String commentParent;

    /**
     * 是否是管理员评论
     */
    private Boolean commentAdmin;

    @TableField(exist = false)
    private List<Comment> commentTwo;
}
