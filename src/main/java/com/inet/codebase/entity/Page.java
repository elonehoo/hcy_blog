package com.inet.codebase.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Pigeonhole
 * 归档得实体类
 * @author HCY
 * @since 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Page implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 时间
     */
    private String time;

    /**
     * 数据
     */
    private Object data;


}
