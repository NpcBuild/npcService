package com.npc.common.modular.read.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.read.content.entity.Content;
import com.npc.common.modular.read.content.mapper.ContentMapper;
import com.npc.common.modular.read.content.service.IContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements IContentService {

    private static final Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);
}
