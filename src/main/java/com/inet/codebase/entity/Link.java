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
 * 友链
 * @author HCY
 * @since 2020-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_link")
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 友链的序号
     */
    @TableId("link_id")
    private String linkId;

    /**
     * 友链的网址
     */
    private String linkUrl;

    /**
     * 友链的名称
     */
    private String linkName;

    /**
     * 友链的图片地址
     */
    private String linkPicture;

    /**
     * 友链的创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date linkEstablish;

    /**
     * 友链的修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date linkModification;


}
