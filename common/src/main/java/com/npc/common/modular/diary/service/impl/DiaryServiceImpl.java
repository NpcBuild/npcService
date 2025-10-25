package com.npc.common.modular.diary.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.chat.entity.ChatBuddy;
import com.npc.common.modular.diary.dto.DiaryDto;
import com.npc.common.modular.diary.entity.Diary;
import com.npc.common.modular.diary.mapper.DiaryMapper;
import com.npc.common.modular.diary.service.IDiaryService;
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
public class DiaryServiceImpl extends ServiceImpl<DiaryMapper, Diary> implements IDiaryService {

    private static final Logger logger = LoggerFactory.getLogger(DiaryServiceImpl.class);

    @Override
    public IPage<Diary> selectListByPage(DiaryDto diaryDto) {
        // 创建分页对象
        Page<Diary> page = new Page<>(diaryDto.getPageNum(), diaryDto.getPageSize());

        IPage<Diary> dailyScheduleIPage = this.baseMapper.getList(page, diaryDto);
        return dailyScheduleIPage;
    }
}
