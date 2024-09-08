package com.npc.common.modular.chat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.npc.common.modular.chat.entity.ChatBuddy;
import com.npc.common.modular.chat.service.IChatBuddyService;
import com.npc.common.modular.chat.vo.BuddyVO;
import com.npc.common.monitor.server.ServerService;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import com.npc.core.constant.Constants;
import com.npc.core.encrypt.base64.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2023-12-17
 */
@RestController
@RequestMapping("/chatBuddy")
public class ChatBuddyController {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyController.class);

    private static final String WIN_DIR = "C:\\Users\\NPC\\Pictures\\";
    private static final String LIUNX_DIR = "/home/npc/Desktop/do/";

    @Autowired
    private IChatBuddyService chatBuddyService;


    /**
     * 保存、修改 【区分id即可】
     * @param chatBuddy 传递的实体
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated ChatBuddy chatBuddy) {
        try {
            boolean obj = chatBuddyService.saveOrUpdate(chatBuddy);
            return ServerResponseVO.success(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.SAVE_FAILED);
        }
    }
    /**
     * 修改备注
     * @param chatBuddy 传递的实体
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/saveNotes", method = RequestMethod.POST)
    public ServerResponseVO<?> saveNotes(@RequestBody @Validated ChatBuddy chatBuddy) {
        try {
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("id",chatBuddy.getId());
            boolean update = chatBuddyService.update(chatBuddy, updateWrapper);
            return ServerResponseVO.success(update);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.SAVE_FAILED);
        }
    }


    /**
     * 通过Id 删除对象
     * @param id 要删除的实体
     * @return ResponseDataModel转换结果
     */
    @GetMapping("deleteChatBuddyById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            boolean chatBuddy =chatBuddyService.removeById(id);
            return ServerResponseVO.success(chatBuddy);
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
    @GetMapping("batchDeleteChatBuddyByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteChatBuddyByIdList(@RequestParam("ids") Integer[] ids) {

        chatBuddyService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/getChatBuddyById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ChatBuddy chatBuddy =chatBuddyService.getById(id);
        return ServerResponseVO.success(chatBuddy);
    }


    /**
     * 通过姓名 获取对象
     * @param buddyVO
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/getChatBuddyByName", method = RequestMethod.GET)
    public ServerResponseVO<?> getByName(BuddyVO buddyVO) {
        ChatBuddy buddy = new ChatBuddy();
        buddy.setName(buddyVO.getName());
        QueryWrapper queryWrapper = new QueryWrapper(buddy);
        ChatBuddy chatBuddy =chatBuddyService.getOne(queryWrapper);
        return ServerResponseVO.success(chatBuddy);
    }


    /**
     * 分页查询数据：
     * @param buddyVO 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getChatBuddyList", method = RequestMethod.GET)
    public ServerResponseVO<?> getChatBuddyList(@Validated BuddyVO buddyVO) {
        IPage<ChatBuddy> page = chatBuddyService.selectListByPage(buddyVO);
        return ServerResponseVO.success(page);
    }


    /**
     * 上传图片
     * @param obj 参数对象
     * @return ResponseDataModel转换结果
     */
    @PostMapping("uploadPic")
    public ServerResponseVO<?> uploadPic(@RequestBody Map<String, Object> obj) throws IOException {
        String fileDir = ServerService.IS_LINUX?LIUNX_DIR:WIN_DIR;
        String dir = fileDir + obj.get("buddyId").toString();
        File dirFile = new File(dir);
        if (dirFile != null && !dirFile.exists()) {
            boolean created = dirFile.mkdirs(); // 创建父目录
            if (!created) {
                throw new IOException("无法创建目录：" + dirFile.getAbsolutePath());
            }
        }
        File parentDir = dirFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs(); // 创建父目录
            if (!created) {
                throw new IOException("无法创建目录：" + parentDir.getAbsolutePath());
            }
        }
        String imgFileName =  dir + (ServerService.IS_LINUX? Constants.LIUNX_SP:Constants.WIN_SP) + obj.get("fileName").toString();
        ImageUtil.convertBase64StrToImage(obj.get("pic").toString(),imgFileName);
        return ServerResponseVO.success("成功");
    }

    /**
     * 查看图片
     * @return ResponseDataModel转换结果
     */
    @GetMapping("loadPicList")
    public ServerResponseVO<?> loadPicList(String buddyId) {
        String fileDir = ServerService.IS_LINUX?LIUNX_DIR:WIN_DIR;
        String dir = fileDir + buddyId;
        File all = new File(dir);
        if (!all.exists()) {
            return ServerResponseVO.success(null);
        }
        List<String> allFileName = new ArrayList<>();
        for (File file : all.listFiles()) {
            allFileName.add(file.getName());
        }
        return ServerResponseVO.success(allFileName);
    }

    /**
     * 查看图片
     * @return ResponseDataModel转换结果
     */
    @GetMapping("loadPic")
    public ServerResponseVO<?> loadPic(String buddyId, String fileName) {
        String fileDir = ServerService.IS_LINUX?LIUNX_DIR:WIN_DIR;
        String dir = fileDir + buddyId + (ServerService.IS_LINUX?Constants.LIUNX_SP:Constants.WIN_SP) + fileName;
        File file = new File(dir);
        String imgFileName = file.getAbsolutePath();
        String image = ImageUtil.convertImageToBase64Str(imgFileName);
        return ServerResponseVO.success(image);
    }
}
