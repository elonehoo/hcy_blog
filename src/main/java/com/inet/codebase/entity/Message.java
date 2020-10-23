package com.inet.codebase.entity;

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
 * 留言
 * @author HCY
 * @since 2020-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 留言序号
     */
    @TableId("message_id")
    private String messageId;

    /**
     * 留言昵称
     */
    private String messageNickname;

    /**
     * 留言邮箱
     */
    private String messageMailbox;

    /**
     * 留言创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date messageEstablish;

    /**
     * 父留言序号
     */
    private String messageParent;

    /**
     * 是否是管理员
     */
    private Boolean messageAdmin;

    /**
     * 留言内容
     */
    private String messageContent;


}
