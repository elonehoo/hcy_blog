package com.inet.codebase.controller.admin;


import com.inet.codebase.entity.Sign;
import com.inet.codebase.entity.User;
import com.inet.codebase.service.SignService;
import com.inet.codebase.service.UserService;
import com.inet.codebase.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标记模块
 * @author HCY
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/sign")
@CrossOrigin
@Api(value = "管理模块",tags = {"标记模块"},description = "管理模块")
public class SignController {
    @Resource
    private SignService signService;

    @Resource
    private UserService userService;

    /**
     * 查询到所有的标记
     * @author HCY
     * @since 2020-09-27
     * @param token 令牌
     * @return Result风格的JSON集合对象
     */
    @ApiOperation("查询到所有的标记")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query",example=""),
    })
    @GetMapping("/list")
    public Result GetList(@RequestParam(value = "Token",defaultValue = "") String token){
        //判断token是否失效
        Result result = decideToken(token, "获取所有标记");
        if (result.getCode() != 100){
            return result;
        }
        //获取所有的标记
        List<Sign> signList = signService.list();
        return new Result(signList,"查询所有的标记",100);
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
