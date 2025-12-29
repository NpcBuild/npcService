package com.npc.common.modular.serverErrorLog.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.ServerErrorLogDto;


import com.npc.common.modular.serverErrorLog.entity.ServerErrorLog;
import com.npc.common.modular.serverErrorLog.mapper.ServerErrorLogMapper;
import com.npc.common.modular.serverErrorLog.service.IServerErrorLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-10-25
 */
@Service
public class ServerErrorLogServiceImpl extends ServiceImpl<ServerErrorLogMapper, ServerErrorLog> implements IServerErrorLogService {

    private static final Logger logger = LoggerFactory.getLogger(ServerErrorLogServiceImpl.class);
}
