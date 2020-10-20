package com.inet.codebase.controller.admin;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inet.codebase.entity.Category;
import com.inet.codebase.entity.Link;
import com.inet.codebase.entity.User;
import com.inet.codebase.service.CategoryService;
import com.inet.codebase.service.UserService;
import com.inet.codebase.utils.Result;
import com.inet.codebase.utils.SensitiveUtil;
import com.inet.codebase.utils.UUIDUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 种类模块
 * @author HCY
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*")
@Api(value = "管理模块",tags = {"分类(种类)模块"})
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @Resource
    private UserService userService;

    /**
     * 进行种类的添加
     * @author HCY
     * @since 2020-09-27
     * @param map 前端传递的data数据集合
     * @return Result风格的JSON集合对象
     */
    @ApiOperation("进行种类的添加")
    @PostMapping("/addition")
    public Result PostAddition(@RequestBody HashMap<String, Object> map){
        //获取token
        String token = (String) map.get("Token");
        //判断token是否失效
        Result result = decideToken(token, "种类添加请求");
        if (result.getCode() != 100){
            return result;
        }
        //设置种类的实体类
        Category category = new Category();
        //设置种类的序号
        category.setCategoryId(UUIDUtils.getId());
        //设置创建时间
        category.setCategoryEstablish(new Date());
        //设置修改时间
        category.setCategoryModification(new Date());
        //获取种类的名称
        String categoryName = (String) map.get("CategoryName");
        //判断种类名称是否为空
        if (categoryName.equals("")){
            return new Result("种类的添加失败,种类的名称为空","种类的添加请求",101);
        }
        //设置种类的名称
        category.setCategoryName(categoryName);
        //进行保存操作
        boolean judgment = categoryService.save(category);
        //判断是否保存成功
        if (judgment){
            return new Result("添加成功","种类的添加请求",100);
        }else {
            return new Result("添加失败","种类的添加请求",104);
        }
    }

    /**
     * 更新种类信息
     * @author HCY
     * @since 2020-09-27
     * @param map 前端传递的data数据集合
     * @return Result风格的JSON集合对象
     */
    @ApiOperation("更新种类信息")
    @PutMapping("/amend")
    public Result PutAmend(@RequestBody HashMap<String, Object> map){
        //获取token
        String token = (String) map.get("Token");
        //判断token是否失效
        Result result = decideToken(token, "修改种类请求");
        if (result.getCode() != 100){
            return result;
        }
        //获取需要修改的种类序号
        String categoryId = (String) map.get("CategoryId");
        //判断种类是否为空
        if (categoryId.equals("")){
            return new Result("修改失败,修改的类别为空","修改种类请求",104);
        }
        //获取种类
        Category category = categoryService.getById(categoryId);
        //判断种类是否为空
        if (category == null){
            return new Result("修改失败,修改的类别不存在","修改种类请求",101);
        }
        //设置种类的修改时间
        category.setCategoryModification(new Date());
        //获取需要修改的种类的名称
        String categoryName = (String) map.get("CategoryName");
        //判断名称是否为空
        if (categoryName.equals("")){
            return new Result("修改失败,修改的类别名称为空","修改种类请求",104);
        }
        //判断是否含有敏感词
        if (SensitiveUtil.sensitives(categoryName)){
            return new Result("修改失败,修改的类别名称为敏感词","修改种类请求",104);
        }
        category.setCategoryName(categoryName);
        //进行更新
        boolean judgment = categoryService.updateById(category);
        if (judgment){
            return new Result("修改成功","修改种类请求",100);
        }else {
            return new Result("修改失败","修改种类请求",104);
        }
    }

    /**
     * 展示所有的种类,以及每个种类的博客数目
     * @author HCY
     * @since 2020-09-28
     * @param token 用户令牌
     * @return Result风格的对象
     */
    @ApiOperation("展示所有的种类,以及每个种类的博客数目")
    @GetMapping("/list")
    public Result GetList(@RequestParam(value = "Token",defaultValue = "") String token){
        //判断token是否已经失效
        Result result = decideToken(token, "展示请求");
        //判断token是否失效
        if (result.getCode() != 100){
            return result;
        }
        //获取所有的种类
        List<Category> categories = categoryService.listAddCount();
        return new Result(categories,"展示请求",100);
    }

    /**
     * 分页展示所有种类
     * @author HCY
     * @since 2020-09-28
     * @param token 令牌
     * @param currentPage 当前页
     * @param total 总条目数
     * @return Result风格的对象
     */
    @ApiOperation("分页展示所有种类")
    @GetMapping("/pagination")
    public Result GetPagination(@RequestParam(value = "Token",defaultValue = "") String token,
                                @RequestParam(value = "CurrentPage",defaultValue = "1") Integer currentPage,
                                @RequestParam(value = "Total",defaultValue = "10") Integer total){
        //判断token是否失效
        Result result = decideToken(token, "分页种类请求");
        if (result.getCode() != 100){
            return result;
        }
        Page<Category> categoryPage = new Page<>(currentPage, total);
        IPage<Category> page = categoryService.page(categoryPage);
        return new Result(page,"分页种类请求",100);
    }

    /**
     * 可多选的删除请求
     * @author HCY
     * @since 2020-09-29
     * @param map 前端传递的data数据集合
     * @return Result风格的JSON集合对象
     */
    @ApiOperation("可多选的删除请求")
    @PostMapping("/expurgate")
    public Result PostExpurgate(@RequestBody HashMap<String, Object> map){
        //获取token
        String token = (String) map.get("Token");
        //判断token是否失效
        Result result = decideToken(token, "删除分类请求");
        if (result.getCode() != 100){
            return result;
        }
        //获取需要删除的数据
        String value = (String) map.get("Values");
        if (value.equals("[]")){
            return new Result("删除失败,未找到删除得对象","删除分类请求",101);
        }
        //变成集合
        List<Category> categories = JSON.parseArray(value, Category.class);
        //空集合
        List<String> list = new ArrayList<>();
        //集合
        for (Category category : categories){
            list.add(category.getCategoryId());
        }
        boolean judgment = categoryService.removeByIds(list);
        if (judgment){
            return new Result("删除成功","删除分类请求",100);
        }else {
            return new Result("删除失败","删除分类请求",104);
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
