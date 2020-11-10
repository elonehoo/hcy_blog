package com.inet.codebase.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inet.codebase.entity.Picture;
import com.inet.codebase.entity.User;
import com.inet.codebase.service.PictureService;
import com.inet.codebase.service.UserService;
import com.inet.codebase.utils.RegesUtils;
import com.inet.codebase.utils.Result;
import com.inet.codebase.utils.UUIDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HCY
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/picture")
@Api(tags = {"照片模块"},description = "管理模块")
@CrossOrigin
public class PictureController {
    @Resource
    private PictureService pictureService;

    @Resource
    private UserService userService;

    /**
     * 添加照片
     * @author HCY
     * @since 2020-10-25
     * @param token 令牌
     * @param name 照片的名字
     * @param describe 照片的描述
     * @param url 照片的网络地址
     * @param site 照片的拍摄地址
     * @return Result风格的对象
     */
    @ApiOperation("添加照片")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query"),
            @ApiImplicitParam(name="Name",value="照片的名字",dataType="String", paramType = "query"),
            @ApiImplicitParam(name="Describe",value="照片的描述",dataType="String", paramType = "query"),
            @ApiImplicitParam(name="URL",value="照片的网络地址",dataType="String", paramType = "query"),
            @ApiImplicitParam(name="Site",value="照片的拍摄地址",dataType="String", paramType = "query"),
    })
    @PostMapping("/append")
    public Result postAppend(@RequestParam(value = "Token",defaultValue = "") String token,
                             @RequestParam(value = "Name",defaultValue = "")String name,
                             @RequestParam(value = "Describe",defaultValue = "")String describe,
                             @RequestParam(value = "URL",defaultValue = "")String url,
                             @RequestParam(value = "Site",defaultValue = "")String site){
        //魔法值
        int code = 100;
        String value = "";
        //创建实体对象
        Picture picture = new Picture();
        //判断token是否失效
        Result result = decideToken(token,"添加图片的请求");
        if (result.getCode() != code){
            return result;
        }
        //判断照片的名称是否为空
        if (name.equals(value)){
            return  new Result("添加失败,图片的名称为空","添加图片的请求",101);
        }
        picture.setPictureName(name);
        //判断图片的描述是否为空
        if (describe.equals(value)){
            return  new Result("添加失败,图片的描述为空","添加图片的请求",101);
        }
        picture.setPictureDescribe(describe);
        //判断图片的网络地址是否正确
        if(!RegesUtils.isUrl(url)){
            return  new Result("添加失败,图片的网络地址不正确","添加图片的请求",101);
        }
        picture.setPictureUrl(url);
        //判断图片的地址是否为空
        if(site.equals(value)){
            return  new Result("添加失败,图片的拍摄地址为空","添加图片的请求",101);
        }
        picture.setPictureSite(site);
        //设置创建的时间
        picture.setPictureEstablish(new Date());
        //设置修改的时间
        picture.setPictureModification(new Date());
        //设置序号
        picture.setPictureId(UUIDUtils.getId());
        //进行存储
        boolean judgment = pictureService.save(picture);
        //判断是否添加成功
        if (judgment){
            return new Result("添加成功","添加图片的请求",100);
        }else {
            return new Result("添加失败","添加图片的请求",104);
        }
    }

    /**
     * 修改图片的具体的信息
     * @param token 令牌
     * @param pictureId 图片序号
     * @param name 名字
     * @param describe 描述
     * @param url url地址
     * @param site 拍摄地址
     * @return Result风格的对象
     */
    @ApiOperation("修改照片")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query"),
            @ApiImplicitParam(name="PictureId",value="修改的图片序号",dataType="String", paramType = "query"),
            @ApiImplicitParam(name="Name",value="照片的名字",dataType="String", paramType = "query"),
            @ApiImplicitParam(name="Describe",value="照片的描述",dataType="String", paramType = "query"),
            @ApiImplicitParam(name="URL",value="照片的网络地址",dataType="String", paramType = "query"),
            @ApiImplicitParam(name="Site",value="照片的拍摄地址",dataType="String", paramType = "query"),
    })
    @PutMapping("/update")
    public Result putUpdate(@RequestParam(value = "Token",defaultValue = "") String token,
                            @RequestParam(value = "PictureId",defaultValue = "") String pictureId,
                            @RequestParam(value = "Name",defaultValue = "")String name,
                            @RequestParam(value = "Describe",defaultValue = "")String describe,
                            @RequestParam(value = "URL",defaultValue = "")String url,
                            @RequestParam(value = "Site",defaultValue = "")String site){
        //魔法值
        int code = 100;
        String value = "";
        //判断token是否过期
        Result decideToken = decideToken(token, "修改图片的请求");
        if (decideToken.getCode() != code){
            return decideToken;
        }
        //判断图片的序号
        Picture picture = pictureService.getById(pictureId);
        if (picture == null){
            return new Result("未找到该图片","修改图片的请求",101);
        }
        //判断name是否为空
        if (name.equals(value)){
            return new Result("图片名字为空","修改图片的请求",101);
        }
        picture.setPictureName(name);
        //判断图片的描述是否为空
        if (describe.equals(value)){
            return new Result("图片的描述是否为空","修改图片的请求",101);
        }
        picture.setPictureDescribe(describe);
        //判断图片的url地址是否正确
        if (!RegesUtils.isUrl(url)){
            return new Result("图片的url不正确","修改图片的请求",101);
        }
        picture.setPictureUrl(url);
        //判断地址是否为空
        if (site.equals(value)){
            return new Result("图片的拍摄地址为空","修改图片的请求",101);
        }
        picture.setPictureSite(site);
        //设置修改时间
        picture.setPictureModification(new Date());
        //进行修改
        boolean judgment = pictureService.updateById(picture);
        //判断是否修改成功
        if (judgment){
            return new Result("修改成功","修改图片的请求",100);
        }else {
            return new Result("修改失败","修改图片的请求",104);
        }
    }

    /**
     * 获取照片
     * @author HCY
     * @since 2020-10-25
     * @param token 令牌
     * @param current 页数
     * @param size 条目数
     * @return Result风格的对象
     */
    @ApiOperation("获取照片,分页模式的")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query"),
            @ApiImplicitParam(name="Current",value="页数",dataType="Integer", paramType = "query",defaultValue = "1"),
            @ApiImplicitParam(name="Size",value="条目数",dataType="Integer", paramType = "query",defaultValue = "10"),
    })
    @GetMapping("/list")
    public Result getList(@RequestParam(value = "Token",defaultValue = "") String token,
                          @RequestParam(value = "Current",defaultValue = "1") Integer current,
                          @RequestParam(value = "Size",defaultValue = "10") Integer size){
        //判断token是否过期
        Result decideToken = decideToken(token, "查看所有的照片");
        if (decideToken.getCode() != 100){
            return decideToken;
        }
        //设置页数的条件
        Page<Picture> picturePage = new Page<>(current, size);
        //进行分页
        IPage<Picture> iPage = pictureService.page(picturePage);
        return new Result(iPage,"查看所有的照片",100);
    }

    /**
     * 删除图片的请求
     * @author Hcy
     * @since 2020-10-25
     * @param token 令牌
     * @param pictureId 删除的图片序号
     * @return Result风格的对象
     */
    @ApiOperation("删除图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query"),
            @ApiImplicitParam(name="PictureId",value="图片序号",dataType="String", paramType = "query"),
    })
    @DeleteMapping("/delete")
    public Result Delete(@RequestParam(value = "Token",defaultValue = "") String token,
                         @RequestParam(value = "PictureId",defaultValue = "") String pictureId){
        //判断token是否过期
        Result decideToken = decideToken(token, "删除照片的请求");
        if (decideToken.getCode() != 100){
            return decideToken;
        }
        //判断图片序号是否有效
        if (pictureService.getById(pictureId) == null) {
            return new Result("删除失败,删除的图片找不到","删除照片的请求",100);
        }
        boolean judgment = pictureService.removeById(pictureId);
        //判断是否删除成功
        if (judgment){
            return new Result("删除成功","删除照片的请求",100);
        }else {
            return new Result("删除失败","删除照片的请求",104);
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
