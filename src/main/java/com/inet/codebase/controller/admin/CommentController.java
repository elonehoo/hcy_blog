package com.inet.codebase.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inet.codebase.entity.Blog;
import com.inet.codebase.entity.Comment;
import com.inet.codebase.entity.User;
import com.inet.codebase.service.BlogService;
import com.inet.codebase.service.CommentService;
import com.inet.codebase.service.UserService;
import com.inet.codebase.utils.PageUtils;
import com.inet.codebase.utils.Result;
import com.inet.codebase.utils.UUIDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 评论模块
 * @author HCY
 * @since 2020-09-26
 */
@Api(tags = {"评论模块"} , description = "管理模块")
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private BlogService blogService;

    @Resource
    private UserService userService;

    /**
     * 获取评论
     * @author HCY
     * @since 2020-10-25
     * @param token 令牌
     * @param blogId 博客序号
     * @param current 页数
     * @param size 条目数
     * @return Result风格的对象
     */
    @ApiOperation("获取评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="BlogId",value="博客序号",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="Current",value="页数",dataType="Integer", paramType = "query",example="1"),
            @ApiImplicitParam(name="Size",value="条目数",dataType="Integer", paramType = "query",example="10"),
    })
    @GetMapping("/list")
    public Result getList(@RequestParam(value = "Token",defaultValue = "") String token,
                          @RequestParam(value = "BlogId",defaultValue = "") String blogId,
                          @RequestParam(value = "Current",defaultValue = "1") Integer current,
                          @RequestParam(value = "Size",defaultValue = "10") Integer size){
        PageUtils pageUtils = new PageUtils();
        //判断token是否过期
        Result decideToken = decideToken(token, "获取评论请求");
        if (decideToken.getCode() != 100){
            return  decideToken;
        }
        //判断博客序号是否有效
        if(blogService.getById(blogId) == null){
            return new Result("找不到该博客","获取评论请求",101);
        }
        pageUtils.setResultList(commentService.getPageComment(blogId, current, size));
        pageUtils.setCurrent(current);
        pageUtils.setPageNavSize(size);
        pageUtils.setTotalCount(commentService.getTotal(blogId));
        return new Result(pageUtils,"获取评论请求",100);
    }

    /**
     * 管理员添加评论
     * @param token 令牌
     * @param blogId 评论的博客序号
     * @param parent 父级评论序号
     * @param content 评论内容
     * @return Result风格的对象
     */
    @ApiOperation("添加评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="BlogId",value="评论的博客序号",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="Parent",value="父级评论序号",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="Content",value="评论内容",dataType="String", paramType = "query",example=""),
    })
    @PostMapping("/append")
    public Result addAttachment(@RequestParam(value = "Token",defaultValue = "") String token,
                                @RequestParam(value = "BlogId",defaultValue = "") String blogId,
                                @RequestParam(value = "Parent",defaultValue = "") String parent,
                                @RequestParam(value = "Content",defaultValue = "") String content){
        //判断token是否过期
        Result result = decideToken(token, "添加评论请求");
        if (result.getCode() != 100){
            return result;
        }
        //如果没有过期,则获取博主的主要信息
        User user = userService.getById(token);
        //创建评论的主要信息
        Comment comment = new Comment();
        //设置序号
        comment.setCommentId(UUIDUtils.getId());
        //设置为管理员
        comment.setCommentAdmin(true);
        //设置昵称
        comment.setCommentNickname(user.getUserNickname());
        //设置邮箱
        comment.setCommentMailbox(user.getUserMailbox());
        //设置发表评论的时间
        comment.setCommentEstablish(new Date());
        //判断发表的博客是否存在
        if (blogService.getById(blogId) == null){
            return new Result("评论失败,博客可能被删除了","添加评论请求",101);
        }
        //设置评论的博客序号
        comment.setCommentBlogid(blogId);
        //设置评论的内容
        comment.setCommentContent(content);
        //判断父级评论是否存在
        if (!parent.equals("")){
            if (commentService.getById(parent) == null){
                return new Result("评论失败,没有找到这个评论","添加评论请求",101);
            }
            //设置父级评论
            comment.setCommentParent(parent);
        }
        //进行保存
        boolean judgment = commentService.save(comment);
        //判断是否保存成功
        if (judgment){
            return new Result("评论成功","添加评论请求",100);
        }else {
            return new Result("评论失败","添加评论请求",104);
        }
    }

    /**
     * 删除评论,及子评论
     * @author HCY
     * @since 2020-10-24
     * @param token 令牌
     * @param commentId 评论序号
     * @return Result风格的对象
     */
    @ApiOperation("删除评论,及子评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="CommentId",value="评论的博客序号",dataType="String", paramType = "query",example=""),
    })
    @DeleteMapping("/delete")
    public Result deleteComment(@RequestParam(value = "Token",defaultValue = "") String token,
                                @RequestParam(value = "CommentId",defaultValue = "") String commentId){
        //判断token是否过期
        Result result = decideToken(token, "删除评论的请求");
        if (result.getCode() != 100){
            return result;
        }
        /*
            未过期的情况
         */
        //制作删除的条件
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id", commentId).or().eq("comment_parent", commentId);
        //进行删除
        boolean judgment = commentService.remove(queryWrapper);
        //判断是否删除成功
        if (judgment){
            return new Result("删除成功","删除评论的请求",100);
        }else {
            return new Result("删除失败","删除评论的请求",104);
        }
    }



    /**
     * 判断是否登录已经失效
     * @author HCY
     * @since 2020-9-28
     * @param token 用户登录之后的令牌
     * @param message 什么请求信息
     * @return Result风格的对象
     */
    public Result decideToken(String token, String message){
        //判断token是否存在
        if (token.equals("")){
            return new Result("未登录,请先去登录",message,103);
        }
        //获取token
        User user = userService.getById(token);
        //判断token是否过时
        if (user == null){
            return new Result("登录超时,请重新登录",message,103);
        }
        return new Result("登录成功",message,100);
    }

}
