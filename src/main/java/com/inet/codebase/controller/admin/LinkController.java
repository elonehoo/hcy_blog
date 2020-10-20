package com.inet.codebase.controller.admin;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.inet.codebase.entity.Category;
import com.inet.codebase.entity.Link;
import com.inet.codebase.entity.User;
import com.inet.codebase.service.LinkService;
import com.inet.codebase.service.UserService;
import com.inet.codebase.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 友链
 * @author HCY
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/link")
@CrossOrigin
@Api(value = "管理模块",tags = {"分类(种类)模块"})
public class LinkController {
    @Resource
    private LinkService linkService;

    @Resource
    private UserService userService;

    /**
     * 添加友链
     * @author HCY
     * @since 2020-09-27
     * @param map 前端传递给后端的数据集合
     * @return Result风格的JSON集合对象
     */
    @ApiOperation("添加友链")
    @PostMapping("/addition")
    public Result PostAddition(@RequestBody HashMap<String, Object> map){
        //获取token
        String token = (String) map.get("Token");
        //判断token是否失效
        Result result = decideToken(token, "添加友链请求");
        if (result.getCode() != 100){
            return result;
        }
        //设置友链的实体类
        Link link = new Link();
        //设置友链的序号
        link.setLinkId(UUIDUtils.getId());
        //设置友链的创建时间
        link.setLinkEstablish(new Date());
        //设置友链的修改时间
        link.setLinkModification(new Date());
        //获取友链的网址
        String linkURL = (String) map.get("LinkURL");
        if (RegesUtils.isUrl(linkURL) == false) {
            return new Result("添加失败,因为友链不符合规则","添加友链请求",101);
        }
        //设置友链的地址URL
        link.setLinkUrl(linkURL);
        //获取友链的名称
        String linkName = (String) map.get("LinkName");
        //判断名字是否为空
        if (linkName.equals("")){
            return new Result("添加失败,因为友链名称为空","添加友链请求",101);
        }
        //判断友链是否带有敏感词句
        if (SensitiveUtil.sensitives(linkName)){
            return new Result("添加失败,友链名称为敏感词","添加友链请求",101);
        }
        //设置友链的名称
        link.setLinkName(linkName);
        //获取友链的图片地址
        String linkPicture = (String) map.get("LinkPicture");
        if (linkPicture.equals("")){
            link.setLinkPicture("http://47.104.249.85:8080/wwwww/123.png");
        }
        link.setLinkPicture(linkPicture);
        //进行保存操作
        boolean judgment = linkService.save(link);
        //判断保存是否成功
        if (judgment){
            return new Result("添加成功","添加友链请求",100);
        }else {
            return new Result("添加失败","添加友链请求",104);
        }
    }

    /**
     * 更新友情链接
     * @author HCY
     * @since 2020-09-27
     * @param map 前端传递给后端的数据集合
     * @return Result风格的JSON集合对象
     */
    @ApiOperation("更新友情链接")
    @PutMapping("/amend")
    public Result PutAmend(@RequestBody HashMap<String, Object> map){
        //获取token
        String token = (String) map.get("Token");
        //判断token是否存在
        Result result = decideToken(token, "更改友链请求");
        if (result.getCode() != 100){
            return result;
        }
        //获取需要修改的友链序号
        String linkId = (String) map.get("LinkId");
        //判断序号是否为空
        if (linkId.equals("")){
            return new Result("修改失败,修改的友链为空","更改友链请求",101);
        }
        //获取友链的具体信息
        Link link = linkService.getById(linkId);
        //判断友链是否存在
        if (link == null){
            return new Result("修改失败,修改的友链不存在","更改友链请求",101);
        }
        //设置友链的修改时间
        link.setLinkModification(new Date());
        //获取修改的友链昵称
        String linkName = (String) map.get("LinkName");
        //判断昵称是否为空
        if (linkName.equals("")){
            return new Result("修改失败,友链的昵称为空","更改友链请求",101);
        }
        //判断昵称中是否含有敏感词
        if ( SensitiveWordBs.newInstance().contains(linkName) ){
            return new Result("修改失败,友链的名称带有敏感词","更改友链请求",101);
        }
        //设置友链的昵称
        link.setLinkName(linkName);
        //获取友链的图片
        String linkPicture = (String) map.get("LinkPicture");
        //判断友链是否为空
        if (linkPicture.equals("")){
            link.setLinkPicture("http://47.104.249.85:8080/wwwww/123.png");
        }else {
            //设置友链的的图片
            link.setLinkPicture(linkPicture);
        }
        //获取友链的网址
        String linkURL = (String) map.get("LinkURL");
        //判断友链网址是否正确
        if (RegesUtils.isUrl(linkURL) == false){
            return new Result("修改失败,网址不符合规则,可能含有了端口号","更改友链请求",101);
        }
        //设置友链的网址
        link.setLinkUrl(linkURL);
        //进行修改
        boolean judgment = linkService.updateById(link);
        //判断是否修改成功
        if (judgment){
            return new Result("修改成功","更改友链请求",100);
        }else {
            return new Result("修改失败","更改友链请求",104);
        }
    }

    /**
     * 分页展示所有的友链
     * @param token 令牌
     * @param currentPage 当前页
     * @param total 总条目数
     * @return Result风格的JSON集合对象
     */
    @ApiOperation("分页展示所有的友链")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="CurrentPage",value="当前页",dataType="Integer", paramType = "query",example="1"),
            @ApiImplicitParam(name="Token",value="总条目数",dataType="Integer", paramType = "query",example="10"),
    })
    @GetMapping("/pagination")
    public Result GetList(@RequestParam(value = "Token",defaultValue = "") String token,
                          @RequestParam(value = "CurrentPage",defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "Total",defaultValue = "10") Integer total){
        //判断token是否失效
        Result result = decideToken(token, "管理员获取所有的友链");
        //判断token
        if (result.getCode() != 100){
            return result;
        }
        //设置分页
        Page<Link> linkPage = new Page<>(currentPage, total);
        IPage<Link> page = linkService.page(linkPage);
        return new Result(page,"管理员获取所有的友链",100);
    }

    /**
     * 删除友链
     * @author HCY
     * @since 2020-09-29
     * @param map 前端传递得map集合
     * @return Result风格的JSON集合对象
     */
    @ApiOperation("删除友链")
    @PostMapping("/expurgate")
    public Result PostRemove(@RequestBody HashMap<String, Object> map){
        //获取map
        String token = (String) map.get("Token");
        //判断token是否失效
        Result result = decideToken(token, "删除友链请求");
        if (result.getCode() != 100){
            return result;
        }
        //获取value
        String value = (String) map.get("Value");
        //变成集合
        List<Link> linkList = JSON.parseArray(value, Link.class);
        //主键集合
        List<String> list = new ArrayList<>();
        //获取主键集合
        for(Link link : linkList){
            list.add(link.getLinkId());
        }
        //进行删除操作
        boolean judgment = linkService.removeByIds(list);
        if (judgment){
            return new Result("删除成功","删除友链请求",100);
        }else {
            return new Result("删除失败","删除友链请求",104);
        }
    }







    /**
     * 判断是否登录已经失效
     * @author HCY
     * @since 2020-9-27
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
