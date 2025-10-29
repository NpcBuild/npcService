package com.npc.common.modular.read.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.ReadContentDto;


import com.npc.common.modular.read.content.entity.ReadContent;
import com.npc.common.modular.read.content.mapper.ReadContentMapper;
import com.npc.common.modular.read.content.service.IReadContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-10-12
 */
@Service
public class ReadContentServiceImpl extends ServiceImpl<ReadContentMapper, ReadContent> implements IReadContentService {

    private static final Logger logger = LoggerFactory.getLogger(ReadContentServiceImpl.class);
}
