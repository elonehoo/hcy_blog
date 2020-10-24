package com.inet.codebase.controller.admin;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.inet.codebase.entity.Blog;
import com.inet.codebase.entity.Category;
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
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.PublicKey;
import java.util.*;

/**
 * 博客模块
 * @author HCY
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/blog")
@CrossOrigin
@Api(value = "管理模块",tags = {"博客的模块"},description = "管理模块")
public class BlogController {

    @Resource
    private BlogService blogService;
    @Resource
    private UserService userService;
    @Resource
    private CommentService commentService;

    /**
     * 进行新增博客
     * @author HCY
     * @since 2020-09-29
     * @param map 前端传递的data数据集合
     * @return Result风格的对象
     */
    @ApiOperation("进行新增博客")
    @PostMapping("/append")
    public Result PostAppend(@RequestBody HashMap<String, Object> map){
        //获取token
        String token = (String) map.get("Token");
        //判断token是否存在
        Result result = decideToken(token, "新增博客请求");
        if (result.getCode() != 100){
            return result;
        }
        //设置实体类
        Blog blog = new Blog();
        //设置博客得序号
        blog.setBlogId(UUIDUtils.getId());
        //设置博客得创建时间
        blog.setBlogEstablish(new Date());
        //设置博客得修改时间
        blog.setBlogModification(new Date());
        //获取博客得标记
        String sign = (String) map.get("Sign");
        //判断标记是否为空
        if (sign.equals("")){
            blog.setBlogSign("ADEA56A89524409B81F9F913C633408B");
        }else {
            blog.setBlogSign(sign);
        }
        //获取博客标题
        String title = (String) map.get("Title");
        //判断title是否为空
        if (title.equals("")) {
            return new Result("新增失败,标题为空", "新增博客请求", 101);
        }
        blog.setBlogTitle(title);
        //获取宣传图片
        String advertising = (String) map.get("Advertising");
        //判断宣传图片是否为空
        if (!advertising.equals("")){
            //设置宣传图
            blog.setBlogAdvertising(advertising);
        }
        //获取内容
        String content = (String) map.get("Content");
        //判断内容是否为空
        if (content.equals("")){
            return new Result("新增失败,内容为空","新增博客请求",101);
        }else {
            //设置博客得内容
            blog.setBlogContent(content);
        }
        //获取种类
        String category = (String) map.get("Category");
        //判断种类是否为空
        if (category.equals("")){
            return new Result("新增失败,种类为空","新增博客请求",101);
        }else {
            //设置一下种类
            blog.setBlogCategory(category);
        }
        //获取描述
        String describe = (String) map.get("Describe");
        //判断描述是否为空
        if ( !describe.equals("")){
            //设置描述
            blog.setBlogDescribe(describe);
        }
        //设置浏览次数
        blog.setBlogViews(0);
        //获取赞赏
        Boolean appreciate = (Boolean) map.get("Appreciate");
        //设置赞赏
        blog.setBlogAppreciate(appreciate);
        //获取版权
        Boolean copyright = (Boolean) map.get("Copyright");
        //设置版权
        blog.setBlogCopyright(copyright);
        //获取评论是否开启
        Boolean comment = (Boolean) map.get("Comment");
        //设置评论是否开启
        blog.setBlogComment(comment);
        //获取博客是否发布
        Boolean release = (Boolean) map.get("Release");
        //设置博客是否发布
        blog.setBlogRelease(release);
        //进行保存
        boolean judgment = blogService.save(blog);
        //判断是否保存成功
        if (judgment){
            return new Result("添加成功","新增博客请求",100);
        }else {
            return new Result("添加失败","新增博客请求",104);
        }
    }

    /**
     * 更新博客得操作
     * @author HCY
     * @since 2020-09-30
     * @param map 前端传递来的map集合
     * @return Result风格的对象
     */
    @ApiOperation("更新博客得操作")
    @PutMapping("/amend")
    public Result PutAmend(@RequestBody HashMap<String, Object> map){
        //获取token
        String token = (String) map.get("Token");
        Result result = decideToken(token, "修改博客请求");
        //判断token是否失效
        if (result.getCode() != 100){
            return result;
        }
        //获取需要修改得博客得序号
        String blogId = (String) map.get("BlogId");
        //通过序号查找具体博客消息
        Blog blog = blogService.getById(blogId);
        //判断博客是否存在
        if (blog == null){
            return new Result("修改失败,修改得博客不存在","修改博客请求",101);
        }
        //获取博客标题
        String title = (String) map.get("Title");
        //判断博客是否为空,如果不是空,则进行更新,如果为空,则不更新
        if ( ! title.equals("")){
            blog.setBlogTitle(title);
        }
        //获取博客宣传图片得地址
        String advertising = (String) map.get("Advertising");
        //判断博客宣传图地址是否为空,如果不是空,则进行更新,如果为空,则不更新
        if ( ! advertising.equals("")){
            blog.setBlogAdvertising(advertising);
        }
        //获取博客内容
        String content = (String) map.get("Content");
        //判断内容是否为空,如果不是空,则进行更新,如果为空,则不更新
        if ( ! content.equals("")){
            blog.setBlogContent(content);
        }
        //获取博客得种类
        String category = (String) map.get("Category");
        //判断种类是否为空,如果不是空,则进行更新,如果为空,则不更新
        if ( ! category.equals("")){
            blog.setBlogCategory(category);
        }
        //获取博客得标记
        String sign = (String) map.get("Sign");
        //判断标记是否为空,如果不是空,则进行更新,如果为空,则不更新
        if ( ! sign.equals("")){
            blog.setBlogSign(sign);
        }
        //获取博客得描述
        String describe = (String) map.get("Describe");
        //判断是否为空,如果不是空,则进行更新,如果为空,则不更新
        if ( ! describe.equals("")){
            blog.setBlogDescribe(describe);
        }
        //获取博客得赞赏是否需要更改
        Boolean appreciate = (Boolean) map.get("Appreciate");
        //进行更改
        blog.setBlogAppreciate(appreciate);
        //获取博客得版权是否需要开启
        Boolean copyright = (Boolean) map.get("Copyright");
        //进行更改
        blog.setBlogCopyright(copyright);
        //获取博客得评论是否需要开启
        Boolean comment = (Boolean) map.get("Comment");
        //进行更改
        blog.setBlogComment(comment);
        //获取博客是否需要发布
        Boolean release = (Boolean) map.get("Release");
        //进行更改
        blog.setBlogRelease(release);
        //进行修改得时间进行更改
        blog.setBlogModification(new Date());
        //进行更改操作
        boolean judgment = blogService.updateById(blog);
        //判断是否更新成功
        if (judgment){
            return new Result("修改成功","修改博客请求",100);
        }else {
            return new Result("修改失败","修改博客请求",104);
        }
    }

    /**
     * 分页展示所有博客
     * @author HCY
     * @since 2020-09-30
     * @param token 令牌
     * @param release 发布状态
     * @param category 种类,类别
     * @param currentPage 当前页数
     * @param total 每条页数得条目数
     * @return Result风格的对象
     */
    @ApiOperation("分页展示所有博客")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="Release",value="发布的状态",dataType="Boolean", paramType = "query",example=""),
            @ApiImplicitParam(name="Category",value="种类,类别",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="CurrentPage",value="每条页数得条目数",dataType="Integer", paramType = "query",example="1"),
            @ApiImplicitParam(name="Total",value="每条页数得条目数",dataType="Integer", paramType = "query",example="10"),
    })
    @GetMapping("/list")
    public Result GetList(@RequestParam(value = "Token",defaultValue = "") String token,
                          @RequestParam(value = "Release",defaultValue = "true") Boolean release ,
                          @RequestParam(value = "Category",defaultValue = "") String  category,
                          @RequestParam(value = "CurrentPage",defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "Total",defaultValue = "10") Integer total){
        //判断token是否存在
        Result result = decideToken(token, "分页博客请求");
        if (result.getCode() != 100){
            return result;
        }
        //判断是否发布
        Map<String,Object> map = new HashMap<>();
        //进行数据得填充
        if ( ! category.equals("")){
            map.put("blog_category",category);
        }
        map.put("blog_release",release);
        //数据得设计
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(map);
        //进行页数
        Page<Blog> blogPage = new Page<>(currentPage, total);
        //进行分页操作
        IPage<Blog> page = blogService.page(blogPage, queryWrapper);
        return new Result(page,"分页博客请求",100);
    }

    /**
     * 删除博客请求
     * @author HCY
     * @since map 前端传递得map集合数组
     * @return Result风格的对象
     */
    @ApiOperation("删除博客请求")
    @PostMapping("/delete")
    public Result PostDelete(@RequestBody HashMap<String, Object> map){
        //获取token
        String token = (String) map.get("Token");
        //判断token是否失效
        Result result = decideToken(token, "删除博客请求");
        if (result.getCode() != 100){
            return result;
        }
        //获取value
        String value = (String) map.get("Value");
        if (value.equals("[]")){
            return new Result("删除失败,未找到删除得对象","删除博客请求",101);
        }
        //将value变成集合
        List<Blog> blogs = JSON.parseArray(value, Blog.class);
        List<String> list = new ArrayList<>();
        //取出他们得id
        for (Blog blog : blogs){
            list.add(blog.getBlogId());
        }
        //进行删除操作
        boolean judgment = blogService.removeByIds(list);
        if (judgment){
            //删除评论
            for (String blogId : list){
                //设置删除评论的条件
                QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("comment_blogid",blogId);
                //进行删除操作
                commentService.remove(queryWrapper);
            }
            return new Result("删除成功","删除博客请求",100);
        }else {
            return new Result("删除失败","删除博客请求",104);
        }
    }

    /**
     * 批量修改博客得发布得状态
     * @author HCY
     * @since 2020-10-01
     * @param map 前端传递得map数据集合
     * @return Result风格的对象
     */
    @ApiOperation("修改博客的发布状态(可批量修改)博客得发布得状态")
    @PutMapping("/state")
    public Result PutState(@RequestBody HashMap<String, Object> map){
        //获取token
        String token = (String) map.get("Token");
        //判断token是否过期
        Result result = decideToken(token, "改变博客得状态");
        if (result.getCode() != 100){
            return result;
        }
        //获取需要修改得博客序号
        String value = (String) map.get("Value");
        //判断value是否为空
        if (value.equals("[]")){
            return new Result("修改失败,未找到修改得对象","改变博客得状态",101);
        }
        List<Blog> blogs = JSON.parseArray(value, Blog.class);
        //填充集合
        for (Blog blog : blogs) {
            blog.setBlogRelease(! blog.getBlogRelease());
        }
        //进行修改操作
        boolean judgment = blogService.updateBatchById(blogs);
        if (judgment){
            return new Result("修改成功","改变博客得状态",100);
        }else {
            return new Result("修改失败","改变博客得状态",104);
        }
    }

    /**
     * 查看所有已经发布得博客得评论数目
     * @author HCY
     * @since 2020-10-3
     * @param token 令牌
     * @param category 种类
     * @param currentPage 当前页数
     * @param total 每页显示得条目数
     * @return Result风格的集合对象
     */
    @ApiOperation("查看所有已经发布得博客得评论数目")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="Category",value="种类,类别",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="CurrentPage",value="每条页数得条目数",dataType="Integer", paramType = "query",example="1"),
            @ApiImplicitParam(name="Total",value="每条页数得条目数",dataType="Integer", paramType = "query",example="10"),
    })
    @GetMapping("/discuss")
    public Result GetDiscuss(@RequestParam(value = "Token",defaultValue = "") String token,
                             @RequestParam(value = "Category",defaultValue = "") String  category,
                             @RequestParam(value = "CurrentPage",defaultValue = "1") Integer currentPage,
                             @RequestParam(value = "Total",defaultValue = "10") Integer total){
        //判断token是否存在
        Result result = decideToken(token, "查看评论数");
        if (result.getCode() != 100){
            return result;
        }
        PageUtils page = blogService.numberOfBlogsAndComments(category, currentPage, total);
        return new Result(page , "查看评论数",100);
    }



    /**
     * 判断是否登录已经失效
     * @author HCY
     * @since 2020-9-28
     * @param token 用户登录之后的令牌
     * @param message 什么请求信息
     * @return Result风格的对象
     */
    public Result decideToken(String token,String message){
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
