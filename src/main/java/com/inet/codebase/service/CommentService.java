package com.inet.codebase.service;

import com.inet.codebase.entity.Comment;
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
public interface CommentService extends IService<Comment> {
    /**
     * 获取该博客的所有的评论
     * @author HCY
     * @since 2020-10-22
     * @param blogId 博客的序号
     * @return 集合
     */
    List<Comment> getComment(String blogId);

    /**
     * 分页获取博客的评论
     * @author HCY
     * @since 2020-10-25
     * @param blogId 博客序号
     * @param current 页数
     * @param size 条目数
     * @return 集合
     */
    List<Comment> getPageComment(String blogId,Integer current , Integer size);

    /**
     * 获取所有一级评论
     * @author HCY
     * @since 2020-10-25
     * @param blogId 博客序号
     * @return 整数
     */
    Integer getTotal(String blogId);

}
