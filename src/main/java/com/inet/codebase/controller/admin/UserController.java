package com.inet.codebase.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.inet.codebase.entity.User;
import com.inet.codebase.service.UserService;
import com.inet.codebase.utils.RegesUtils;
import com.inet.codebase.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户模块
 * @author HCY
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(value = "管理模块",tags = {"用户模块"})
public class UserController {
    @Resource
    private UserService userService;
    private final String ADMIN = "DED1C274D13E401196789124E7303C40";


    /**
     * 登录操作
     * @author HCY
     * @since 2020-09-26
     * @param map 用户传来的集合数据
     * @return Result风格的返回值
     */
    @ApiOperation("登录操作")
    @PostMapping("/enter")
    public Result PostEnter(@RequestBody HashMap<String, Object> map){
        //获取账号
        String account = (String) map.get("Account");
        //判断密码是否为空
        if (account.equals("")){
            return new Result("登录失败,用户名为空","管理员登录请求",101);
        }
        //获取密码
        String password = (String) map.get("Password");
        //判断密码是否为空
        if (password.equals("")){
            return new Result("登录失败,密码为空","管理员登录请求",101);
        }
        //进行密码加密
        String digest = DigestUtils.md5DigestAsHex(password.getBytes());
        //进行查询条件的设置
        Map<String , Object> condition = new HashMap<>();
        map.put("user_username",account);
        map.put("user_password",digest);
        //进行条件的确定
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(condition);
        //进行查询
        User user = userService.getOne(queryWrapper);
        //判断是否是管理员
        if (!user.getUserId().equals(ADMIN)){
            return new Result("登录失败,并不是管理员的账号","管理员登录请求",101);
        }
        //设置返回值
        Map<String , Object> result = new HashMap<>();
        result.put("msg","登录成功");
        result.put("token",ADMIN);
        return new Result(result,"管理员登录请求",100);
    }

    /**
     * 登录成功,获取管理员资源
     * @author HCY
     * @since 2020-09-26
     * @param token 用户令牌
     * @return Result风格的返回值
     */
    @ApiOperation("登录成功,获取管理员资源")
    @GetMapping("/backbone")
    public Result GetBackbone(@RequestParam(value = "Token",defaultValue = "") String token){
        //判断是否登录
        if(! token.equals(ADMIN)){
            return new Result("展示失败,并未登录","管理员展示请求",101);
        }
        //进行数据库的查询
        User user = userService.getById(token);
        //返回
        return new Result(user,"管理员展示请求",100);
    }

    /**
     * 进行用户信息的更新
     * @author HCY
     * @since 2020-09-26
     * @param map 用户传来的集合数据
     * @return Result风格的返回值
     */
    @ApiOperation("进行用户信息的更新")
    @PutMapping("/amend")
    public Result PutAmend(@RequestBody HashMap<String, Object> map){
        //获取token
        String token = (String) map.get("Token");
        //判断token是否正确
        if (!token.equals(ADMIN)){
            return new Result("修改失败,未登录或者登录账号不是管理员","管理员修改请求",101);
        }
        //获取用户
        User user = userService.getById(ADMIN);
        //获取用户需要修改的昵称
        String nickName = (String) map.get("NickName");
        //判断昵称是否为空
        if (nickName.equals("")){
            return new Result("修改失败,昵称不能为空","管理员修改请求",101);
        }
        //修改用户昵称
        user.setUserNickname(nickName);
        //获取邮箱
        String mailbox = (String) map.get("Mailbox");
        //判断邮箱是否正确
        if ( ( RegesUtils.isEmail(mailbox) ) == false){
            return new Result("修改失败,邮箱不正确","管理员修改请求",101);
        }
        //修改用户邮箱
        user.setUserMailbox(mailbox);
        //获取用户头像
        String portrait = (String) map.get("portrait");
        //判断是否为空
        if (! portrait.equals("")){
            user.setUserPortrait(portrait);
        }
        //获取用户的微信二维码
        String weChat = (String) map.get("WeChat");
        //判断微信二维码是否为空
        if ( ! weChat.equals("")){
            user.setUserWeChat(weChat);
        }
        //获取用户的QQ二维码
        String qq = (String) map.get("QQ");
        //判断QQ二维码是否为空
        if ( ! qq.equals("")){
            user.setUserQQ(qq);
        }
        //获取支付宝二维码
        String alipay = (String) map.get("Alipay");
        //判断支付宝二维码是否为空
        if ( !alipay.equals("")){
            user.setUserAlipay(alipay);
        }
        //判断用户的github网址
        String gitHub = (String) map.get("GitHub");
        //判断github是否存在
        if (!gitHub.equals("")){
            user.setUserGithub(gitHub);
        }
        //获取旧密码和新密码
        String oldPassword = (String) map.get("oldPassword");
        String newPassword = (String) map.get("newPassword");
        //判断旧密码是否一样
        if ( user.getUserPassword().equals( DigestUtils.md5DigestAsHex( oldPassword.getBytes() ) ) ) {
            user.setUserPassword( DigestUtils.md5DigestAsHex( newPassword.getBytes() ) );
        }else {
            return new Result("修改失败,旧密码错误","管理员修改请求",101);
        }
        //设置更新时间
        user.setUserModification(new Date());
        //进行更新操作
        boolean judgment = userService.updateById(user);
        if (judgment){
            return new Result("修改成功","管理员修改请求",100);
        }else {

            return new Result("修改失败","管理员修改请求",104);
        }
    }

    /**
     * 新增用户得介绍
     * @author HCY
     * @since 2020-10-05
     * @param map 前端传递得数据集合
     * @return Result风格的返回值
     */
    @ApiOperation("新增用户得介绍")
    @PostMapping("/introduce")
    public Result PostIntroduce(@RequestBody HashMap<String, Object> map){
        //获取token
        String token = (String) map.get("Token");
        //判断token是否失效
        Result result = decideToken(token, "新增用户得介绍请求");
        if (result.getCode() != 100){
            return result;
        }
        //获取用户得具体信息
        User user = userService.getById(ADMIN);
        //获取介绍
        String introduce = (String) map.get("Introduce");
        //判断介绍是否为空
        if (introduce.equals("")){
            introduce = "# 博主很懒没有介绍";
        }
        user.setUserIntroduce(introduce);
        //进行修改
        boolean judgment = userService.updateById(user);
        if (judgment){
            return new Result("成功","新增用户得介绍请求",100);
        }else {
            return new Result("失败","新增用户得介绍请求",104);
        }
    }

    /**
     * 展示用户的头像
     * @author HCY
     * @since 2020-09-27
     * @return Result风格的返回值
     */
    @ApiOperation("展示用户的头像")
    @GetMapping("/profile")
    public Result GetProfile(){
        User user = userService.getById(ADMIN);
        return new Result(user.getUserPortrait(),"展示用户的头像",100);
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
