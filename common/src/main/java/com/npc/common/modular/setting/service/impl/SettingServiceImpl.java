package com.npc.common.modular.setting.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.setting.entity.Setting;
import com.npc.common.modular.setting.mapper.SettingMapper;
import com.npc.common.modular.setting.service.ISettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2024-06-09
 */
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements ISettingService {

    private static final Logger logger = LoggerFactory.getLogger(SettingServiceImpl.class);
}
