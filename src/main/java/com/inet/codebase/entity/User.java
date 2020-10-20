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
 * 用户
 * @author HCY
 * @since 2020-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户序号
     */
    @TableId("user_id")
    private String userId;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 用户名
     */
    private String userUsername;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户的邮箱
     */
    private String userMailbox;

    /**
     * 用户的头像
     */
    private String userPortrait;

    /**
     * 用户介绍
     */
    private String userIntroduce;

    /**
     * 用户的微信二维码
     */
    @TableField("user_WeChat")
    private String userWeChat;

    /**
     * 用户的QQ二维码
     */
    @TableField("user_QQ")
    private String userQQ;

    /**
     * 用户的支付宝二维码
     */
    @TableField("user_Alipay")
    private String userAlipay;

    /**
     * 用户的GitHUB网址
     */
    @TableField("user_github")
    private String userGithub;

    /**
     * 用户的创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date userEstablish;

    /**
     * 用户的修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date userModification;


}
