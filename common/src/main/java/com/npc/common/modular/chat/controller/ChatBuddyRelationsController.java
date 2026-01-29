package com.npc.common.modular.chat.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.chat.dto.ChatBuddyRelationsDto;
import com.npc.common.modular.chat.entity.ChatBuddy;
import com.npc.common.modular.chat.service.IChatBuddyService;
import com.npc.common.modular.chat.service.impl.ChatBuddyRelationsServiceImpl;
import com.npc.common.modular.chat.vo.SocialGraphEdge;
import com.npc.common.modular.chat.vo.SocialGraphNode;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import com.npc.core.utils.kinship.service.KinshipInferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.chat.service.IChatBuddyRelationsService;
import com.npc.common.modular.chat.entity.ChatBuddyRelations;

import java.util.*;

/**
 * <p>
 * 朋友关系 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-11-26
 */
@RestController
@RequestMapping("/chatBuddyRelations")
// @Api(value = "/chatBuddyRelations", description = "朋友关系 相关接口")
public class ChatBuddyRelationsController {

    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyRelationsController.class);

    @Autowired
    public IChatBuddyRelationsService chatBuddyRelationsService;
    @Autowired
    public ChatBuddyRelationsServiceImpl chatBuddyRelationsServiceImpl;
    @Autowired
    private IChatBuddyService chatBuddyService;
    @Autowired
    private KinshipInferenceService kinshipInferenceService;


    /**
     * 保存、修改 【区分id即可】
     * @param chatBuddyRelations 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "朋友关系 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated ChatBuddyRelations chatBuddyRelations) {
        try {
            Boolean obj = chatBuddyRelationsService.saveOrUpdate(chatBuddyRelations);
            return ServerResponseVO.success(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.SAVE_FAILED);
        }
    }


    /**
     * 通过Id 删除对象
     * @param id 要删除的实体
     * @return ServerResponseVO转换结果
     */
    @GetMapping("deleteChatBuddyRelationsById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean chatBuddyRelations =chatBuddyRelationsService.removeById(id);
            return ServerResponseVO.success(chatBuddyRelations);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 朋友关系
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteChatBuddyRelationsByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 朋友关系")
    public ServerResponseVO<?> batchDeleteChatBuddyRelationsByIdList(@RequestParam("ids") Integer[] ids) {
        chatBuddyRelationsService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getChatBuddyRelationsById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 朋友关系 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ChatBuddyRelations chatBuddyRelations =chatBuddyRelationsService.getById(id);
        return ServerResponseVO.success(chatBuddyRelations);
    }


    /**
     * 分页查询数据：
     * @param chatBuddyRelationsDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getChatBuddyRelationsList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "朋友关系 分页查询数据")
    public ServerResponseVO<?> getChatBuddyRelationsList(@Validated ChatBuddyRelationsDto chatBuddyRelationsDto) {
        Page page = new Page(chatBuddyRelationsDto.getPageNum(), chatBuddyRelationsDto.getPageSize());
        QueryWrapper<ChatBuddyRelations> queryWrapper = new QueryWrapper(chatBuddyRelationsDto);
        Page<ChatBuddyRelations> pages = chatBuddyRelationsService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }

    /**
     * 获取社交关系网络图数据
     * @return ServerResponseVO 包含节点和边的数据
     */
    @RequestMapping(value = "/getSocialNetworkGraphData", method = RequestMethod.GET)
// @ApiOperation(response = ServerResponseVO.class, value = "获取社交关系网络图数据")
    public ServerResponseVO<?> getSocialNetworkGraphData() {
        try {
            // 查询所有有效的用户关系
            List<ChatBuddyRelations> relations = chatBuddyRelationsService.list(new QueryWrapper<ChatBuddyRelations>()
                            .eq("status", "1")
//                    .eq("from_id", 1)
            ); // 只查询已生效的关系

            // 收集所有涉及的用户ID
            Set<Integer> userIds = new HashSet<>();
            for (ChatBuddyRelations relation : relations) {
                userIds.add(relation.getFromId());
                userIds.add(relation.getToId());
            }

            // 查询所有相关用户的基本信息
            List<ChatBuddy> buddies = new ArrayList<>();
            if (!userIds.isEmpty()) {
                buddies = chatBuddyService.list(new QueryWrapper<ChatBuddy>()
                        .in("id", userIds));
            }

            // 构建用户ID到用户信息的映射
            Map<Integer, ChatBuddy> buddyMap = new HashMap<>();
            for (ChatBuddy buddy : buddies) {
                buddyMap.put(buddy.getId(), buddy);
            }

            // 构建节点和边数据
            List<SocialGraphNode> nodes = new ArrayList<>();
            List<SocialGraphEdge> edges = new ArrayList<>();

            // 创建节点集合
            Map<Integer, SocialGraphNode> nodeMap = new HashMap<>();
            Random random = new Random();

            // 先添加所有节点，并根据关系类别给它们不同的初始位置分布
            Map<String, List<SocialGraphNode>> nodesByCategory = new HashMap<>();

            for (ChatBuddy buddy : buddies) {
                SocialGraphNode node = new SocialGraphNode();
                node.setId(buddy.getId());
                node.setLabel(buddy.getName());
                node.setRadius(20); // 默认半径

                // 根据不同类别设置初始位置范围，避免所有节点堆叠在一起
                String category = chatBuddyRelationsServiceImpl.getRelationCategory(buddy.getId(), relations);
                double angle = random.nextDouble() * 2 * Math.PI;
                double radius = 100 + random.nextDouble() * 300; // 半径范围100-400

                switch (category.substring(0,1)) {
                    case "A":
//                    case "family":
                        // 家人分布在左上区域
                        node.setX(Math.cos(angle) * radius - 200);
                        node.setY(Math.sin(angle) * radius - 200);
                        break;
                    case "C":
//                    case "friend":
                        // 朋友分布在右上区域
                        node.setX(Math.cos(angle) * radius + 200);
                        node.setY(Math.sin(angle) * radius - 200);
                        break;
//                    case "colleague":
                    case "B":
                        // 同事分布在左下区域
                        node.setX(Math.cos(angle) * radius - 200);
                        node.setY(Math.sin(angle) * radius + 200);
                        break;
                    case "Z":
//                    case "important":
                        // 重要联系人分布在右下区域
                        node.setX(Math.cos(angle) * radius + 200);
                        node.setY(Math.sin(angle) * radius + 200);
                        break;
                    default:
                        // 其他类别随机分布
                        node.setX(Math.cos(angle) * radius);
                        node.setY(Math.sin(angle) * radius);
                        break;
                }

                node.setVx(0.0); // 初始速度
                node.setVy(0.0);
                node.setMass(1.0); // 默认质量

                // 设置元数据
                Map<String, Object> metadata = new HashMap<>();
                metadata.put("category", category);
                metadata.put("intimacy", buddy.getIntimacyLevel() != null ? buddy.getIntimacyLevel().toString() : "0");
                metadata.put("description", buddy.getBio() != null ? buddy.getBio() : "");
                metadata.put("tags", buddy.getTags() != null ? buddy.getTags() : "");
                node.setMetadata(metadata);

                // 根据关系类型设置颜色标签
                node.setColorTag(chatBuddyRelationsServiceImpl.getColorTag(buddy.getId(), relations));
                nodes.add(node);
                nodeMap.put(buddy.getId(), node);

                // 按类别分类存储节点，方便后续调整
                nodesByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(node);
            }

            // 添加边
            int i = 1;
            for (ChatBuddyRelations relation : relations) {
                SocialGraphEdge edge = new SocialGraphEdge();
                edge.setId(i++);
                edge.setSourceID(relation.getFromId());
                edge.setTargetID(relation.getToId());

                // 根据关系强度设置权重
                ChatBuddy buddy = buddyMap.get(relation.getToId());
                if (buddy != null && buddy.getIntimacyLevel() != null) {
                    edge.setWeight(chatBuddyRelationsServiceImpl.calculateEdgeWeight(buddy.getIntimacyLevel()));
                } else {
                    edge.setWeight(1.0);
                }

                edges.add(edge);
                kinshipInferenceService
                        .infer(relation.getFromId(), relation.getToId(), relations)
                        .ifPresent(result -> {
                            System.out.println(result.relationName);
                            System.out.println(result.explanation);
                        });
            }



            // 构建返回数据结构
            Map<String, Object> result = new HashMap<>();
            result.put("nodes", nodes);
            result.put("edges", edges);

            return ServerResponseVO.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
