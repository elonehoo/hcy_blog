package com.inet.codebase.mapper;

import com.inet.codebase.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author HCY
 * @since 2020-09-26
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 进行获取一级标题
     * @author HCY
     * @since 2020-10-21
     * @param blogId 博客得序号
     * @return 评论得集合
     */
    List<Comment> getFirstComment(String blogId);

    /**
     * 查询子集评论
     * @author HCY
     * @since 2020-10-22
     * @param blogId 博客的序号
     * @param parentId 父级评论的序号
     * @return 集合
     */
    List<Comment> getComment(String blogId , String parentId);

    /**
     * 查询是否有子集评论
     * @author HCY
     * @since 2020-10-22
     * @param parentId 父级评论的序号
     * @return 整形
     */
    Integer judgmentSecondaryReview(String parentId);

    /**
     * 分页查询评论
     * @author HCY
     * @since 2020-10-25
     * @param blogId 博客序号
     * @param current 页数
     * @param size 条目数
     * @return 集合
     */
    List<Comment> getPageComment(String blogId,Integer current,Integer size);

    /**
     * 获取所有一级评论
     * @author HCY
     * @since 2020-10-25
     * @param blogId 博客序号
     * @return 整数
     */
    Integer getTotal(String blogId);

}
