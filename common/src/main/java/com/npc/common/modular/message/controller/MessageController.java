package com.npc.common.modular.message.controller;

import com.npc.common.modular.message.entity.Message;
import com.npc.common.modular.message.mapper.MessageMapper;
import com.npc.common.modular.message.service.IMessageService;
import com.npc.common.modular.message.vo.ChatMessageVO;
import com.npc.common.modular.message.vo.UserMessageVO;
import com.npc.common.modular.user.model.result.UserResult;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
 * @since 2023-05-02
 */
@CrossOrigin
@RestController
@RequestMapping("/messages")
public class MessageController {
    
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    public MessageMapper messageMapper;
    @Autowired
    public IMessageService messageService;


    /**
     * 通过userId 获取联系人列表
     * @return ServerResponseVO转换结果
     */
    @PostMapping("/getUserMessage")
    public ServerResponseVO<?> findUserMessageByUserId() {
        // 获取当前用户的 Subject
        Subject currentUser = SecurityUtils.getSubject();
        UserResult userInfo = new UserResult();
        if (currentUser.isAuthenticated()) {
            userInfo = (UserResult)currentUser.getPrincipal();
            System.out.println(userInfo.getId());
        }
        List<UserMessageVO> message =messageService.findUserMessageByUserId(Long.valueOf(userInfo.getId()));
        return ServerResponseVO.success(message);
    }


    /**
     * 通过userId 获取联系人消息列表
     * @param tId
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getMessageByUserId", method = RequestMethod.GET)
    public ServerResponseVO<?> findChatMessageById(@RequestParam("tId") Long tId) {
        // 获取当前用户的 Subject
        Subject currentUser = SecurityUtils.getSubject();
        UserResult userInfo = new UserResult();
        if (currentUser.isAuthenticated()) {
            userInfo = (UserResult)currentUser.getPrincipal();
            System.out.println(userInfo.getId());
            // 在这里可以继续处理用户信息
        }
        Long userId = Long.valueOf(userInfo.getId());
        List<ChatMessageVO> message = messageService.findChatMessageById(userId,tId);
        messageMapper.readMessage(userId,tId);
        return ServerResponseVO.success(message);
    }


    /**
     * 保存、修改 【区分id即可】
     * 添加 修改接口, 填入Id为更新, 不填Id为新增
     * @param message 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated Message message) {
        try {
            messageService.save(message);
            return ServerResponseVO.success(message);
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
    @GetMapping("deleteMessageById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            messageService.removeById(id);
            return ServerResponseVO.success();
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
    @GetMapping("batchDeleteMessageByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteMessageByIdList(@RequestParam("ids") Integer[] ids) {
        messageService.removeByIds(Arrays.asList(ids));
        return ServerResponseVO.success("批量删除成功");
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getMessageById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Message message =messageService.getById(id);
        return ServerResponseVO.success(message);
    }


//    /**
//     * 分页查询数据：
//     * @param messageDto 查询对象
//     * @return PageList 分页对象
//     */
//    @RequestMapping(value = "/getMessageList", method = RequestMethod.GET)
//    public ServerResponseVO<?> getMessageList(@Validated MessageDto messageDto) {
//        Page<Message> page = messageService.getMessageList(messageDto);
//        return ServerResponseVO.success(page.getTotal(), page.getRecords());
//    }
}
