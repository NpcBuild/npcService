package com.npc.common.modular.diary.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.chat.entity.ChatBuddy;
import com.npc.common.modular.diary.dto.DiaryDto;
import com.npc.common.modular.diary.entity.Diary;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-17
 */
public interface IDiaryService extends IService<Diary> {
    IPage<Diary> selectListByPage(DiaryDto diaryDto);
}
