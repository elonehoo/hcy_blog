package com.inet.codebase.controller.demonstrate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inet.codebase.entity.*;
import com.inet.codebase.service.*;
import com.inet.codebase.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private CommentService commentService;

    @Resource
    private LinkService linkService;

    @Resource
    private PictureService pictureService;

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
    @ApiImplicitParams({
            @ApiImplicitParam(name="CurrentPage",value="页数",dataType="Interger", paramType = "query",example="1"),
            @ApiImplicitParam(name="Total",value="条目数",dataType="Interger", paramType = "query",example="10")
    })
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
     * 获取某一个博客+评论
     * @author HCY
     * @since 2020-10-05
     * @param blogId 博客得序号
     * @return Result风格的对象
     */
    @ApiOperation("获取某一个博客")
    @ApiImplicitParams({
            @ApiImplicitParam(name="BlogId",value="博客的序号",dataType="String", paramType = "query")
    })
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
        }
        Map<String, Object> map = new HashMap<>(2);
        //进行博客得浏览次数 + 1
        blog.setBlogViews( blog.getBlogViews() + 1 );
        //进行修改
        blogService.updateById(blog);
        if (blog.getBlogComment()){
            //查看博客的评论请求
            List<Comment> comment = commentService.getComment(blogId);
            map.put( "comment" , comment );
        }
        map.put( "blog" , blog );
        //返回值
        return new Result(map,"查看博客得请求",100);
    }

    /**
     * 查看所有的友链
     * @author HCY
     * @since 2020-10-24
     * @return Result风格的对象
     */
    @ApiOperation("查看所有的友链")
    @GetMapping("/link")
    public Result GetLink(){
        return new Result(linkService.list() , "查看友链的请求",100);
    }

    /**
     * 查看照片墙
     * @author HCY
     * @since 2020-10-25
     * @param current 页数
     * @param size 条目数
     * @return Result风格的对象
     */
    @ApiOperation("获取照片,分页模式的")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Current",value="页数",dataType="Integer", paramType = "query",defaultValue = "1"),
            @ApiImplicitParam(name="Size",value="条目数",dataType="Integer", paramType = "query",defaultValue = "10"),
    })
    @GetMapping("/picture")
    public Result getPicture(@RequestParam(value = "Current",defaultValue = "1") Integer current,
                             @RequestParam(value = "Size",defaultValue = "10") Integer size){
        Page<Picture> picturePage = new Page<>(current, size);
        IPage<Picture> page = pictureService.page(picturePage);
        return new Result(page,"查看照片墙",100);
    }

}
