package com.inet.codebase.service;

import com.inet.codebase.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HCY
 * @since 2020-09-26
 */
public interface MessageService extends IService<Message> {
    /**
     * 获取留言
     * @author HCY
     * @since 2020-10-23
     * @return 集合
     */
    List<Message> getMessage();

    /**
     * 分页展示所有的留言
     * @author HCY
     * @since 2020-10-25
     * @param current 页数
     * @param size 条目数
     * @return 集合
     */
    List<Message> getPageMessage(Integer current , Integer size);

    /**
     * 获取总数
     * @author HCY
     * @since 2020-10-25
     * @return 整数
     */
    Integer getTotal();
}
