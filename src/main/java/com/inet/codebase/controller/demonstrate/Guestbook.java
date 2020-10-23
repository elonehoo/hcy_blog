package com.inet.codebase.controller.demonstrate;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.inet.codebase.entity.Message;
import com.inet.codebase.service.MessageService;
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
 * Guestbook
 *
 * @author HCY
 * @since 2020/10/23
 */
@RestController
@RequestMapping("/Guestbook")
@CrossOrigin
@Api(tags = {"留言接口"} , description = "用户模块")
public class Guestbook {
    @Resource
    private MessageService messageService;

    /**
     * 添加留言
     * @author HCY
     * @since 2020-10-23
     * @param nickName 留言者昵称
     * @param mailBox 留言者邮箱
     * @param content 留言内容
     * @param parent 二级留言得一级留言序号
     * @return Result风格的返回值
     */
    @ApiOperation("添加留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name="NickName",value="留言者昵称",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="MailBox",value="留言者邮箱",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="Content",value="留言内容",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="Parent",value="二级留言得一级留言序号",dataType="String", paramType = "query",example=""),
    })
    @PostMapping("/append")
    public Result PostAppend(@RequestParam(value = "NickName",defaultValue = "") String nickName,
                             @RequestParam(value = "MailBox",defaultValue = "") String mailBox,
                             @RequestParam(value = "Content",defaultValue = "") String content,
                             @RequestParam(value = "Parent",defaultValue = "") String parent){
        //创建留言
        Message message = new Message();
        //设置序号
        message.setMessageId(UUIDUtils.getId());
        //设置留言时间
        message.setMessageEstablish(new Date());
        //设置是否是管理员的评价
        message.setMessageAdmin(false);
        //判断昵称是否合法
        if (SensitiveWordBs.newInstance().contains(nickName)){
            return new Result("添加失败,昵称中含有敏感词","添加留言得请求",101);
        }else {
            message.setMessageNickname(nickName);
        }
        //判断邮箱是否合法
        if (!RegesUtils.isEmail(mailBox)){
            return new Result("添加失败,邮箱不正确","添加留言得请求",101);
        }else {
            message.setMessageMailbox(mailBox);
        }
        //判断内容是否合法
        if (SensitiveWordBs.newInstance().contains(content)){
            return new Result("添加失败,内容中有敏感词","添加留言得请求",101);
        }else {
            message.setMessageContent(content);
        }
        //判断父级评论
        if (! parent.equals("")){
            Message guestbook = messageService.getById(parent);
            if (guestbook == null){
                return new Result("添加失败,该留言可能被删除","添加留言得请求",101);
            }else {
                message.setMessageParent(parent);
            }
        }
        boolean judgment = messageService.save(message);
        //判断是否保存成功
        if (judgment){
            return new Result("添加成功","添加留言得请求",100);
        }else {
            return new Result("添加失败","添加留言得请求",104);
        }
    }

    @ApiOperation("获取所有的留言")
    @GetMapping("/guestbook")
    public Result GetGuestbook(){
        return new Result(messageService.getMessage(),"获取所有的留言",100);
    }


}
