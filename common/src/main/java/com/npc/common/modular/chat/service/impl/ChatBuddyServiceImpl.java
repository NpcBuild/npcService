package com.npc.common.modular.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.chat.entity.ChatBuddy;
import com.npc.common.modular.chat.mapper.ChatBuddyMapper;
import com.npc.common.modular.chat.service.IChatBuddyService;
import com.npc.common.modular.chat.vo.BuddyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-17
 */
@Service
public class ChatBuddyServiceImpl extends ServiceImpl<ChatBuddyMapper, ChatBuddy> implements IChatBuddyService {

    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyServiceImpl.class);

    @Override
    public IPage<ChatBuddy> selectListByPage(BuddyVO buddyVO) {
        // 创建分页对象
        Page<ChatBuddy> page = new Page<>(buddyVO.getPageNum(), buddyVO.getPageSize());
        QueryWrapper queryWrapper = new QueryWrapper<>(buddyVO);
        queryWrapper.orderByAsc("(case when sort is null then 1 else 0 end)");
        queryWrapper.orderByAsc("sort");
        // 执行分页查询，将查询结果封装到分页对象中
        IPage<ChatBuddy> buddyIPage = this.baseMapper.selectPage(page, queryWrapper);
        return buddyIPage;
    }
}
