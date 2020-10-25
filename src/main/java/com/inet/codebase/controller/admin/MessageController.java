package com.inet.codebase.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.inet.codebase.entity.Message;
import com.inet.codebase.entity.User;
import com.inet.codebase.service.MessageService;
import com.inet.codebase.service.UserService;
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
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HCY
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/message")
@Api(tags = {"留言模块"},description = "管理模块")
public class MessageController {
    @Resource
    private MessageService messageService;

    @Resource
    private UserService userService;

    /**
     * 留言的添加
     * @author HCY
     * @since 2020-10-24
     * @param token 令牌
     * @param content 内容
     * @param messageId 留言序号
     * @return Result风格的对象
     */
    @ApiOperation("添加的留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="Content",value="内容",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="MessageId",value="留言序号",dataType="String", paramType = "query",example="")
    })
    @PostMapping("/append")
    public Result postAppend(@RequestParam(value = "Token",defaultValue = "") String token,
                             @RequestParam(value = "Content",defaultValue = "") String content,
                             @RequestParam(value = "MessageId",defaultValue = "") String messageId){
        //判断token是否过期
        Result result = decideToken(token, "添加留言的请求");
        if (result.getCode() != 100){
            return result;
        }
        //获取管理员的信息
        User user = userService.getById(token);
        //设置留言的实体
        Message message = new Message();
        //设置序号
        message.setMessageId(UUIDUtils.getId());
        //设置昵称
        message.setMessageNickname(user.getUserNickname());
        //设置邮箱
        message.setMessageMailbox(user.getUserMailbox());
        //设置内容
        message.setMessageContent(content);
        //设置时间
        message.setMessageEstablish(new Date());
        //判断是否有父留言
        if (!messageId.equals("")){
            if (messageService.getById(messageId) == null) {
                return new Result("添加失败,留言可能被删除","添加留言的请求",101);
            }else {
                message.setMessageParent(messageId);
            }
        }
        //设置是否是管理员
        message.setMessageAdmin(true);
        //进行保存
        boolean judgment = messageService.save(message);
        //判断是否保存成功
        if (judgment){
            return new Result("添加成功","添加留言的请求",100);
        }else {
            return new Result("添加失败","添加留言的请求",104);
        }
    }

    /**
     * 删除留言及其子集留言
     * @author HCY
     * @since 2020-10-24
     * @param token 令牌
     * @param messageId 留言序号
     * @return Result风格的对象
     */
    @ApiOperation("删除留言及其子集留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="MessageId",value="留言序号",dataType="String", paramType = "query",example=""),
    })
    @DeleteMapping("/delete")
    public Result Delete(@RequestParam(value = "Token",defaultValue = "") String token,
                         @RequestParam(value = "MessageId",defaultValue = "") String messageId){
        //判断令牌是否失效
        Result result = decideToken(token, "删除留言请求");
        if (result.getCode() != 100){
            return result;
        }
        //创建删除的条件
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_id",messageId).or().eq("message_parent",messageId);
        //删除
        boolean judgment = messageService.remove(queryWrapper);
        //判断是否删除成功
        if (judgment){
            return new Result("删除成功","删除留言请求",100);
        }else {
            return new Result("删除失败","删除留言请求",104);
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
