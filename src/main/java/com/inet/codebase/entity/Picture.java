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
 * 照片墙
 * @author HCY
 * @since 2020-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_picture")
public class Picture implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 照片墙的序号
     */
    @TableId("picture_id")
    private String pictureId;

    /**
     * 图片的名称
     */
    private String pictureName;

    /**
     * 图片的描述
     */
    private String pictureDescribe;

    /**
     * 图片的地址
     */
    private String pictureUrl;

    /**
     * 图片的地址
     */
    private String pictureSite;

    /**
     * 图片的创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pictureEstablish;

    /**
     * 图片的修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pictureModification;


}
