package com.npc.common.modular.subscribe.service.impl;

import com.npc.common.modular.subscribe.dto.SubscribeDto;
import com.npc.common.modular.subscribe.vo.SubscribeVO;
import com.npc.utils.CronUtils;
import com.npc.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.npc.common.modular.subscribe.entity.Subscribe;
import com.npc.common.modular.subscribe.mapper.SubscribeMapper;
import com.npc.common.modular.subscribe.service.ISubscribeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订阅表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-02-24
 */
@Service
public class SubscribeServiceImpl extends ServiceImpl<SubscribeMapper, Subscribe> implements ISubscribeService {
    // 3天内提醒
    private static final int REMIND_MILLI_SECOND = 3 * 24 * 60 * 60;

    private static final Logger logger = LoggerFactory.getLogger(SubscribeServiceImpl.class);

    @Override
    public List<SubscribeVO> getNextSubscribeList(SubscribeDto subscribeDto) {
        List<Subscribe> enableList = getEnableList();
        List<SubscribeVO> res = new ArrayList<>();
        for (Subscribe subscribe : enableList) {
            String next = CronUtils.getNext(subscribe.getPayCron());
            int differentDaysByMillisecond = DateUtils.differentDaysByMillisecond(DateUtils.getNowDate(), DateUtils.parseDate(next));
            if (differentDaysByMillisecond <= REMIND_MILLI_SECOND) {
                SubscribeVO subscribeVO = new SubscribeVO();
                BeanUtils.copyProperties(subscribe, subscribeVO);
                subscribeVO.setSubTime(next);
                res.add(subscribeVO);
            }
        }
        return res;
    }

    private List<Subscribe> getEnableList() {
        String endTime = DateUtils.getTime();
        return this.baseMapper.getEnableList(endTime);
    }
}
