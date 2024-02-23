package com.npc.common.modular.chat.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.npc.common.modular.chat.entity.ChatTopic;
import com.npc.common.modular.chat.service.IChatBuddyService;
import com.npc.common.modular.chat.service.IChatTopicService;
import com.npc.common.modular.chat.vo.TopicVO;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2023-12-17
 */
@RestController
@RequestMapping("/chatTopic")
public class ChatTopicController {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatTopicController.class);

    @Autowired
    public IChatTopicService chatTopicService;

    @Autowired
    public IChatBuddyService chatBuddyService;


    /**
     * 保存、修改 【区分id即可】
     * @param topicVO 传递的实体
     * @return ServerResponseVO
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated TopicVO topicVO) {
        try {
            ChatTopic chatTopic = new ChatTopic();
            BeanUtil.copyProperties(topicVO,chatTopic);
            boolean obj = chatTopicService.saveOrUpdate(chatTopic);
            return ServerResponseVO.success(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.SAVE_FAILED);
        }
    }


    /**
     * 通过Id 删除对象
     * @param id 要删除的实体
     * @return ServerResponseVO
     */
    @GetMapping("deleteChatTopicById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            boolean chatTopic =chatTopicService.removeById(id);
            return ServerResponseVO.success(chatTopic);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteChatTopicByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteChatTopicByIdList(@RequestParam("ids") Integer[] ids) {

        chatTopicService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO
     */
    @RequestMapping(value = "/getChatTopicById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ChatTopic chatTopic =chatTopicService.getById(id);
        return ServerResponseVO.success(chatTopic);
    }


    /**
     * 通过条件 获取对象
     * @param topicVO
     * @return ServerResponseVO
     */
    @RequestMapping(value = "/getChatTopics", method = RequestMethod.POST)
    public ServerResponseVO<?> getChatTopics(@RequestBody TopicVO topicVO) {
        QueryWrapper queryWrapper = new QueryWrapper(topicVO);
        List<ChatTopic> chatTopics =chatTopicService.list(queryWrapper);
        return ServerResponseVO.success(chatTopics);
    }


    /**
     * 通过条件 获取对象
     * @param topicVO
     * @return ServerResponseVO
     */
    @RequestMapping(value = "/changeUsed", method = RequestMethod.POST)
    public ServerResponseVO<?> changeUsed(@RequestBody TopicVO topicVO) {
        ChatTopic topic = new ChatTopic();
        topic.setUsed(true);
        UpdateWrapper<ChatTopic> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("buddy_id", topicVO.getBuddyId());
        updateWrapper.eq("topic", topicVO.getTopic());
        boolean update = chatTopicService.update(topic, updateWrapper);
        return ServerResponseVO.success(update);
    }


//    /**
//     * 分页查询数据：
//     * @param chatTopicDto 查询对象
//     * @return PageList 分页对象
//     */
//    @RequestMapping(value = "/getChatTopicList", method = RequestMethod.GET)
//    public ServerResponseVO<?> getChatTopicList(@Validated ChatTopicDto chatTopicDto) {
//        Page<ChatTopic> page = chatTopicService.getChatTopicList(chatTopicDto);
//        return ServerResponseVO.success(page);
//    }
}
