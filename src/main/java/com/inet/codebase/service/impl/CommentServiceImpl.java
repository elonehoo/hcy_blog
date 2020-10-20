package com.inet.codebase.service.impl;

import com.inet.codebase.entity.Comment;
import com.inet.codebase.mapper.CommentMapper;
import com.inet.codebase.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
