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
     * 获取该博客的序号
     * @author HCY
     * @since 2020-10-22
     * @param blogId 博客的序号
     * @return 集合
     */
    List<Comment> getComment(String blogId);

}
