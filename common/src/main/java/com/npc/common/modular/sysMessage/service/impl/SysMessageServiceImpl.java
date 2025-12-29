package com.npc.common.modular.sysMessage.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.SysMessageDto;


import com.npc.common.modular.sysMessage.entity.SysMessage;
import com.npc.common.modular.sysMessage.mapper.SysMessageMapper;
import com.npc.common.modular.sysMessage.service.ISysMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 系统消息 / 通知中心表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-18
 */
@Service
public class SysMessageServiceImpl extends ServiceImpl<SysMessageMapper, SysMessage> implements ISysMessageService {

    private static final Logger logger = LoggerFactory.getLogger(SysMessageServiceImpl.class);
}
