package com.inet.codebase.mapper;

import com.inet.codebase.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author HCY
 * @since 2020-09-26
 */
public interface MessageMapper extends BaseMapper<Message> {
    /**
     * 获取到所有的留言,以及多级评论
     * @author HCy
     * @since 2020-10-23
     * @return 集合
     */
    List<Message> getListMessage();
}
