package com.npc.common.modular.holiday.controller;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.npc.common.modular.holiday.service.IHolidayService;
import com.npc.common.modular.holiday.entity.Holiday;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-02-16
 */
@RestController
@RequestMapping("/holiday")
public class HolidayController {
    
    private static final Logger logger = LoggerFactory.getLogger(HolidayController.class);

    @Autowired
    public IHolidayService holidayService;


    /**
     * 保存、修改 【区分id即可】
     * @param holiday 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated Holiday holiday) {
        try {
            Boolean obj = holidayService.saveOrUpdate(holiday);
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
    @GetMapping("deleteHolidayById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean holiday =holidayService.removeById(id);
            return ServerResponseVO.success(holiday);
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
    @GetMapping("batchDeleteHolidayByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteHolidayByIdList(@RequestParam("ids") Integer[] ids) {
        holidayService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getHolidayById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Holiday holiday =holidayService.getById(id);
        return ServerResponseVO.success(holiday);
    }


//    /**
//     * 分页查询数据：
//     * @param holidayDto 查询对象
//     * @return PageList 分页对象
//     */
//    @RequestMapping(value = "/getHolidayList", method = RequestMethod.GET)
//    public ServerResponseVO<?> getHolidayList(@Validated HolidayDto holidayDto) {
//        Page page = new Page(holidayDto.getPageNum(), holidayDto.getPageSize());
//        QueryWrapper<Holiday> queryWrapper = new QueryWrapper(holidayDto);
//        Page<Holiday> pages = holidayService.page(page, queryWrapper);
//        return ServerResponseVO.success(pages);
//    }
}
