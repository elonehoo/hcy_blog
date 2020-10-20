package com.inet.codebase.controller.demonstrate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inet.codebase.entity.Blog;
import com.inet.codebase.entity.User;
import com.inet.codebase.service.BlogService;
import com.inet.codebase.service.UserService;
import com.inet.codebase.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 首页模块
 * @author HCY
 * @since 2020-10-04
 */
@RestController
@RequestMapping("/home")
@CrossOrigin
@Api(tags = {"大部分的用户模块接口"} , description = "用户模块")
public class Index {
    @Resource
    private BlogService blogService;

    @Resource
    private UserService userService;

    private final String ADMIN = "DED1C274D13E401196789124E7303C40";

    /**
     * 获取最新得博客
     * @author HCY
     * @since 2020-10-04
     * @return Result风格的对象
     */
    @ApiOperation("获取最新得博客")
    @GetMapping("/newest")
    public Result GetNewest(){
        List<Blog> blogs = blogService.lookAtTheLatest();
        return new Result(blogs , "查看最新得请求",100);
    }

    /**
     * 获取最热门得博客
     * @author HCY
     * @since 2020-10-04
     * @return Result风格的对象
     */
    @ApiOperation("获取最热门得博客")
    @GetMapping("/hottest")
    public Result GetHottest(){
        List<Blog> blogs = blogService.lookAtTheHottest();
        return new  Result(blogs , "查看最热得请求",100);
    }

    /**
     * 查看用户得基本信息
     * @author HCY
     * @since 2020-10-05
     * @return Result风格的对象
     */
    @ApiOperation("查看用户得基本信息")
    @GetMapping("/userinfo")
    public Result GetUserInfo(){
        User user = userService.getById(ADMIN);
        return new Result(user,"查看用户得具体信息",100);
    }

    /**
     * 分页获取博客得具体信息
     * @author HCY
     * @since 2020-10-05
     * @param currentPage 页数
     * @param total 条目数
     * @return Result风格的对象
     */
    @ApiOperation("分页获取博客得具体信息")
    @GetMapping("/list")
    public Result GetList(@RequestParam(value = "CurrentPage",defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "Total",defaultValue = "10") Integer total){
        //设置分页对象
        Page<Blog> blogPage = new Page<>(currentPage, total);
        //设置条件构造器
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_release",true);
        queryWrapper.orderByDesc("blog_establish");
        //进行分页操作
        IPage<Blog> blogIPage = blogService.page(blogPage, queryWrapper);
        return new Result(blogIPage,"查看博客得请求",100);
    }

    /**
     * 获取某一个博客
     * @author HCY
     * @since 2020-10-05
     * @param blogId 博客得序号
     * @return Result风格的对象
     */
    @ApiOperation("获取某一个博客")
    @GetMapping("/blog")
    public Result GetBlog(@RequestParam(value = "BlogId",defaultValue = "") String blogId){
        //判断博客序号是否为空
        if (blogId.equals("")){
            return new Result("未找到博客","查看博客得请求",101);
        }
        Blog blog = blogService.getById(blogId);
        //判断blog是否为空
        if (blog == null){
            return new Result("博客可能已经被删除了,刷新试试看","查看博客得请求",101);
        }else {
            //进行博客得浏览次数 + 1
            blog.setBlogViews( blog.getBlogViews() + 1 );
            //进行修改
            blogService.updateById(blog);
            return new Result(blog,"查看博客得请求",101);
        }
    }



}
