package com.inet.codebase.service;

import com.inet.codebase.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HCY
 * @since 2020-09-26
 */
public interface CategoryService extends IService<Category> {
    List<Category> listAddCount();
}
