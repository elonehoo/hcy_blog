package com.inet.codebase.service.impl;

import com.inet.codebase.entity.Category;
import com.inet.codebase.mapper.CategoryMapper;
import com.inet.codebase.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HCY
 * @since 2020-09-26
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> listAddCount() {
        return categoryMapper.listAddCount();
    }
}
