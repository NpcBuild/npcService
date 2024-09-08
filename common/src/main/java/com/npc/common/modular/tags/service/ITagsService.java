package com.npc.common.modular.tags.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.tags.entity.Tags;
import com.npc.common.modular.tags.vo.TagsVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-20
 */
public interface ITagsService extends IService<Tags> {
    List<TagsVO> getTree(Integer id, Integer level);
}
