package com.inet.codebase.service.impl;

import com.inet.codebase.entity.Comment;
import com.inet.codebase.mapper.CommentMapper;
import com.inet.codebase.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    /**
     * 获取该博客的序号
     * @param blogId 博客的序号
     * @return 集合
     */
    @Override
    public List<Comment> getComment(String blogId) {
        //获取到该博客的所有一级评论
        List<Comment> firstComments = commentMapper.getFirstComment(blogId);
        //进行循环,并且判断是否有子集评论
        for (int i = 0; i < firstComments.size(); i++) {
            //获取到需要修改的集合
            Comment comment = firstComments.get(i);
            //如果是true则是有子集评论
            if ( judgmentSecondaryReview(comment.getCommentId()) ) {
                comment.setCommentTwo(secondaryComments(blogId,comment.getCommentId()));
            }
            Collections.replaceAll(firstComments,firstComments.get(i),comment);
        }
        return firstComments;
    }

    @Override
    public List<Comment> getPageComment(String blogId, Integer current, Integer size) {
        //计算页数
        current = (current * size) - size;
        List<Comment> pageComment = commentMapper.getPageComment(blogId, current, size);
        //进行循环,并且判断是否有子集评论
        for (int i = 0; i < pageComment.size(); i++) {
            //获取到需要修改的集合
            Comment comment = pageComment.get(i);
            //如果是true则是有子集评论
            if ( judgmentSecondaryReview(comment.getCommentId()) ) {
                comment.setCommentTwo(secondaryComments(blogId,comment.getCommentId()));
            }
            Collections.replaceAll(pageComment,pageComment.get(i),comment);
        }
        return pageComment;
    }


    /**
     * 获取所有一级评论
     * @author HCY
     * @since 2020-10-25
     * @param blogId 博客序号
     * @return 整数
     */
    @Override
    public Integer getTotal(String blogId) {
        return commentMapper.getTotal(blogId);
    }

    /**
     * 判断是否有二级以下评论
     * @author HCY
     * @since 2020-10-22
     * @param commentId 评论序号
     * @return 布尔值
     */
    private Boolean judgmentSecondaryReview(String commentId){
        //获取到子集评论的个数,判断是否有子集评论  0 ==> false ;
        return commentMapper.judgmentSecondaryReview(commentId) != 0;
    }

    /**
     * 查询子集
     * @author HCY
     * @since 2020-10-22
     * @param blogId 博客的序号
     * @param commentId 评论的序号
     * @return 集合
     */
    private List<Comment> secondaryComments(String blogId , String commentId){
        List<Comment> comments = commentMapper.getComment(blogId, commentId);
        for (int i = 0; i < comments.size() ; i++) {
            Comment comment = comments.get(i);
            //如果是true则是有子集评论
            if ( judgmentSecondaryReview(comment.getCommentId()) ) {
                comment.setCommentTwo(secondaryComments(blogId,comment.getCommentId()));
            }
            Collections.replaceAll(comments, comments.get(i), comment);
        }
        return comments;
    }
}
