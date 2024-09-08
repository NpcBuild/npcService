package com.npc.common.modular.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.chat.entity.ChatBuddy;
import com.npc.common.modular.chat.mapper.ChatBuddyMapper;
import com.npc.common.modular.chat.service.IChatBuddyService;
import com.npc.common.modular.chat.vo.BuddyVO;
import com.npc.common.modular.dailySchedule.vo.DailyScheduleVO;
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

        IPage<ChatBuddy> dailyScheduleIPage = this.baseMapper.getList(page, buddyVO);
        return dailyScheduleIPage;

//        QueryWrapper<ChatBuddy> queryWrapper = new QueryWrapper<>(); // 使用实体类作为泛型参数
//        // 假设你希望查询 hasContact 为 true 的记录（即 hasContact 不为 0）
//        queryWrapper.ne("has_contact", 0); // ne 表示 not equals
//
//        // 如果你想包含 hasContact 为 null 的情况（如果数据库允许该字段为 null）
//        queryWrapper.or().isNull("has_contact");
//
//        queryWrapper.orderByAsc("(case when sort is null then 1 else 0 end)");
//        queryWrapper.orderByAsc("sort");
//        // 执行分页查询，将查询结果封装到分页对象中
//        // 注意这里应该使用 BuddyEntity 的 Mapper 而不是 VO 的 Mapper，因为 VO 通常不直接与数据库表对应
//        IPage<ChatBuddy> buddyIPage = this.baseMapper.selectPage(page, queryWrapper);
//        return buddyIPage;
    }
}
