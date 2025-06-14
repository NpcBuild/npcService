package com.npc.common.modular.read.notes.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.read.notes.entity.Notes;
import com.npc.common.modular.read.notes.mapper.NotesMapper;
import com.npc.common.modular.read.notes.service.INotesService;
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
public class NotesServiceImpl extends ServiceImpl<NotesMapper, Notes> implements INotesService {

    private static final Logger logger = LoggerFactory.getLogger(NotesServiceImpl.class);
}
