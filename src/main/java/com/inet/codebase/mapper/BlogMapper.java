package com.inet.codebase.mapper;

import com.inet.codebase.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inet.codebase.utils.ArchivesUtils;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author HCY
 * @since 2020-09-26
 */
public interface BlogMapper extends BaseMapper<Blog> {
    /**
     * 获取博客和评论数目
     * @param category 种类
     * @param currentPage 当前页
     * @param total 每一页得条目数
     * @return list集合
     */
    List<Blog> numberOfBlogsAndComments(String category , Integer currentPage , Integer total);

    /**
     * 查看归档
     * @author HCY
     * @since 2020-10-04
     * @return list集合
     */
    List<ArchivesUtils> CheckTheArchive();

    /**
     * 查看最新得博客
     * @author HCY
     * @since 2020-10-04
     * @return list集合
     */
    List<Blog> lookAtTheLatest();

    /**
     * 查看最热得博客
     * @author HCY
     * @since 2020-10-04
     * @return list集合
     */
    List<Blog> lookAtTheHottest();
}
