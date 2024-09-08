package com.npc.common.modular.setting.controller;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.setting.service.ISettingService;
import com.npc.common.modular.setting.entity.Setting;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2024-06-09
 */
@RestController
@RequestMapping("/setting")
public class SettingController {
    
    private static final Logger logger = LoggerFactory.getLogger(SettingController.class);

    @Autowired
    public ISettingService settingService;


    /**
     * 保存、修改 【区分id即可】
     * @param setting 传递的实体
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated Setting setting) {
        try {
            Boolean obj = settingService.save(setting);
            return ServerResponseVO.success(obj);
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
    @GetMapping("deleteSettingById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean setting =settingService.removeById(id);
            return ServerResponseVO.success(setting);
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
    @GetMapping("batchDeleteSettingByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteSettingByIdList(@RequestParam("ids") Integer[] ids) {
        settingService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/getSettingById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Setting setting =settingService.getById(id);
        return ServerResponseVO.success(setting);
    }

    @PostMapping("/update")
    public ServerResponseVO<?> update(@RequestBody Setting setting) {
        return ServerResponseVO.success(settingService.updateById(setting));
    }


//    /**
//     * 分页查询数据：
//     * @param settingDto 查询对象
//     * @return PageList 分页对象
//     */
//    @RequestMapping(value = "/getSettingList", method = RequestMethod.GET)
//    public ResponseDataModel<?> getSettingList(@Validated SettingDto settingDto) {
//        Page<Setting> page = settingService.getSettingList(settingDto);
//        return new ResponseDataModel<>(page.getTotal(), page.getRecords());
//    }
}
