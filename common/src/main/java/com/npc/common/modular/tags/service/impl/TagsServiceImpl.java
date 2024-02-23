package com.npc.common.modular.tags.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.tags.entity.Tags;
import com.npc.common.modular.tags.mapper.TagsMapper;
import com.npc.common.modular.tags.service.ITagsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-20
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements ITagsService {

    private static final Logger logger = LoggerFactory.getLogger(TagsServiceImpl.class);
}
