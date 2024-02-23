package com.npc.common.modular.dailySchedule.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.npc.common.modular.dailySchedule.dto.DailyScheduleDto;
import com.npc.common.modular.dailySchedule.entity.DailySchedule;
import com.npc.common.modular.dailySchedule.service.IDailyScheduleService;
import com.npc.common.modular.dailySchedule.vo.DailyScheduleVO;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 每日日程安排 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2023-12-18
 */
@RestController
@RequestMapping("/dailySchedule")
public class DailyScheduleController {
    
    private static final Logger logger = LoggerFactory.getLogger(DailyScheduleController.class);

    @Autowired
    public IDailyScheduleService dailyScheduleService;


    /**
     * 保存、修改 【区分id即可】
     * @param dailySchedule 传递的实体
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated DailySchedule dailySchedule) {
        try {
            boolean obj = dailyScheduleService.saveOrUpdate(dailySchedule);
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
    @GetMapping("deleteDailyScheduleById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            boolean dailySchedule =dailyScheduleService.removeById(id);
            return ServerResponseVO.success(dailySchedule);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 每日日程安排
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteDailyScheduleByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteDailyScheduleByIdList(@RequestParam("ids") Integer[] ids) {

        dailyScheduleService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/getDailyScheduleById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        DailySchedule dailySchedule =dailyScheduleService.getById(id);
        return ServerResponseVO.success(dailySchedule);
    }


    /**
     * 分页查询数据：
     * @param dailyScheduleDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getDailyScheduleList", method = RequestMethod.GET)
    public ServerResponseVO<?> getDailyScheduleList(@Validated DailyScheduleDto dailyScheduleDto) {
        IPage<DailyScheduleVO> page = dailyScheduleService.selectListByPage(dailyScheduleDto);
        return ServerResponseVO.success(page);
    }
}
