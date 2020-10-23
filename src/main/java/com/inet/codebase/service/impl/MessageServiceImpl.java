package com.inet.codebase.service.impl;

import com.inet.codebase.entity.Message;
import com.inet.codebase.mapper.MessageMapper;
import com.inet.codebase.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HCY
 * @since 2020-09-26
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Resource
    private MessageMapper messageMapper;


    /**
     * 获取留言
     * @author HCY
     * @since 2020-10-23
     * @return 集合
     */
    @Override
    public List<Message> getMessage() {
        List<Message> messageList = messageMapper.getListMessage();
        for (int i = 0 ; i < messageList.size() ; i++){
            Message message = messageList.get(i);
            //判断是否有子集留言
            if (isThereASubset(message.getMessageId())){
                message.setMessageList( getMessageList(message.getMessageId()) );
            }
            //替换
            Collections.replaceAll(messageList, messageList.get(i), message);
        }
        return messageList;
    }

    /**
     * 判断是否有子集
     * @author HCY
     * @since 2020-10-23
     * @param messageId 留言序号
     * @return 布尔值
     */
    private Boolean isThereASubset(String messageId){
        //判断是否为子集
        return messageMapper.getCountMessage(messageId) != 0;
    }

    /**
     * 获取子集留言
     * @param messageId
     * @return
     */
    private List<Message> getMessageList(String messageId){
        //获取到下一级的子集
        List<Message> messageList = messageMapper.getListTwoMessage(messageId);
        //判断是否还有子集
        for (int i = 0; i < messageList.size() ; i++) {
            //获取需要修改的留言
            Message message = messageList.get(i);
            //如果有
            if (isThereASubset(message.getMessageId())){
                message.setMessageList( getMessageList(message.getMessageId()) );
            }
            //替换
            Collections.replaceAll(messageList, messageList.get(i), message);
        }
        return messageList;
    }
}
