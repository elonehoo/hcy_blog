package com.inet.codebase.mapper;

import com.inet.codebase.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author HCY
 * @since 2020-09-26
 */
public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> listAddCount();
}
