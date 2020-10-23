package com.inet.codebase.controller.demonstrate;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.inet.codebase.entity.Blog;
import com.inet.codebase.entity.Comment;
import com.inet.codebase.service.BlogService;
import com.inet.codebase.service.CommentService;
import com.inet.codebase.utils.RegesUtils;
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
 * Discuss
 *
 * @author HCY
 * @since 2020/10/21
 */
@RestController
@RequestMapping("/discuss")
@CrossOrigin
@Api(tags = {"评论接口"} , description = "用户模块")
public class Discuss {
    @Resource
    private CommentService commentService;

    @Resource
    private BlogService blogService;

    /**
     * 添加评论
     * @param nickName 评论这得昵称
     * @param mailBox 评论者得邮箱
     * @param content 评论者得内容
     * @param blogId 评论得博客序号
     * @param parent 二级评论得序号
     * @return Result风格的返回值
     */
    @ApiOperation("添加评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name="NickName",value="评论者昵称",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="MailBox",value="评论者邮箱",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="Content",value="评论内容",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="BlogId",value="评论得博客序号",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="Parent",value="二级评论得一级评论序号",dataType="String", paramType = "query",example=""),
    })
    @PostMapping("/addition")
    public Result PostAddition(@RequestParam(value = "NickName",defaultValue = "") String nickName,
                               @RequestParam(value = "MailBox",defaultValue = "")String mailBox,
                               @RequestParam(value = "Content",defaultValue = "")String content,
                               @RequestParam(value = "BlogId",defaultValue = "")String blogId,
                               @RequestParam(value = "Parent",defaultValue = "")String parent){

        System.out.println(mailBox);

        //创建评论对象
        Comment comment = new Comment();
        //设置评论序号
        comment.setCommentId(UUIDUtils.getId());
        //设置评论得时间
        comment.setCommentEstablish(new Date());
        //评论并不是管理员
        comment.setCommentAdmin(false);
        //判断用户名是否合法,是否含有敏感词
        boolean nickNameIs = SensitiveWordBs.newInstance().contains(nickName);
        //判断是否是敏感词
        if (nickNameIs){
            return new Result("添加失败,昵称中含有敏感词","添加评论得请求",101);
        }else {
            comment.setCommentNickname(nickName);
        }
        //判断邮箱是否合法
        boolean email = RegesUtils.isEmail(mailBox);
        if (!email){
            return new Result("添加失败,邮箱不正确","添加评论得请求",101);
        }else {
            comment.setCommentMailbox(mailBox);
        }
        //判断内容是否合法,是否含有敏感词
        boolean contentIs = SensitiveWordBs.newInstance().contains(content);
        if (contentIs){
            return new Result("添加失败,评论内容中含有敏感词","添加评论得请求",101);
        }else {
            comment.setCommentContent(content);
        }
        //判断博客得序号是否为空
        Blog blog = blogService.getById(blogId);
        if (blog == null){
            return new Result("添加失败,评论得博客已经被删除","添加评论得请求",101);
        }else {
            comment.setCommentBlogid(blogId);
        }
        //判断父评论序号是否存在
        if (!parent.equals("")){
            Comment discuss = commentService.getById(parent);
            if (discuss == null){
                return new Result("添加失败,评论得评论已经被删除了","添加评论得请求",101);
            }else {
                comment.setCommentParent(parent);
            }
        }
        //进行保存
        boolean judgment = commentService.save(comment);
        //判断是否保存成功
        if (judgment){
            return new Result("添加成功","添加评论得请求",100);
        }else {
            return new Result("添加失败","添加评论得请求",104);
        }
    }
}
