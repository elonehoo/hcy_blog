package com.inet.codebase.service;

import com.inet.codebase.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.inet.codebase.entity.Page;
import com.inet.codebase.utils.ArchivesUtils;
import com.inet.codebase.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HCY
 * @since 2020-09-26
 */
public interface BlogService extends IService<Blog> {
    /**
     * 获取博客和评论数目
     * @author HCY
     * @since 2020-10-04
     * @param category 种类得分类
     * @param currentPage 当前页数
     * @param total 没一页显示得条目数
     * @return 自定义得分页类
     */
    PageUtils numberOfBlogsAndComments(String category , Integer currentPage , Integer total);

    /**
     * 查看归档得具体信息
     * @author HCY
     * @since 2020-10-04
     * @return list集合
     */
    List<Page> CheckTheArchive();

    /**
     * 查看最新得博客
     * @author HCY
     * @since 2020-10-04
     * @return list集合
     */
    List<Blog> lookAtTheLatest();

    /**
     * 获取最热得博客
     * @author HCY
     * @since 2020-10-04
     * @return list集合
     */
    List<Blog> lookAtTheHottest();
}
