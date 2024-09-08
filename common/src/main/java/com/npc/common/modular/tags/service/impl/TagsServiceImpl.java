package com.npc.common.modular.tags.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.tags.entity.Tags;
import com.npc.common.modular.tags.mapper.TagsMapper;
import com.npc.common.modular.tags.service.ITagsService;
import com.npc.common.modular.tags.vo.TagsVO;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public List<TagsVO> getTree(Integer id, Integer level) {
        List<Tags> tagList = this.getBaseMapper().getTree(id, level);
        return buildTree(tagList, id);
    }

    public static List<TagsVO> buildTree(List<Tags> list, Integer rootId) {
        // 数据类型转换
        List<TagsVO> voList = new ArrayList<>();
        for (Tags tags : list) {
            TagsVO vo = new TagsVO();
            vo.setId(tags.getId());
            vo.setName(tags.getName());
            vo.setParentId(tags.getParentId());
            voList.add(vo);
        }

        Map<Integer, TagsVO> nodeMap = new HashMap<>();
        List<TagsVO> rootNodes = new ArrayList<>();
        for (TagsVO node : voList) {
            nodeMap.put(node.getId(), node);
        }

        for (TagsVO node : voList) {
            if (Objects.equals(node.getParentId(), rootId)) {
                rootNodes.add(node);
            } else {
                TagsVO parentNode = nodeMap.get(node.getParentId());
                if (parentNode!= null) {
                    if (parentNode.getChildren() == null) {
                        List<TagsVO> tagsVOList = new ArrayList<>();
                        parentNode.setChildren(tagsVOList);
                    }
                    parentNode.getChildren().add(node);
                }
            }
        }
        return rootNodes;
    }
}
