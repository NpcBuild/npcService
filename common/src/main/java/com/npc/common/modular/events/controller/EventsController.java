package com.npc.common.modular.events.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.npc.common.modular.events.dto.EventsDto;
import com.npc.common.modular.holiday.vo.CalendarEventVO;
import com.npc.common.modular.problem.entity.Problem;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.events.service.IEventsService;
import com.npc.common.modular.events.entity.Events;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-06-27
 */
@RestController
@RequestMapping("/events")
// @Api(value = "/events", description = " 相关接口")
public class EventsController {
    
    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);

    @Autowired
    public IEventsService eventsService;


    /**
     * 保存、修改 【区分id即可】
     * @param events 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = " 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated Events events) {
        try {
            Boolean obj = eventsService.saveOrUpdate(events);
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
    @GetMapping("deleteEventsById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean events =eventsService.removeById(id);
            return ServerResponseVO.success(events);
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
    @GetMapping("batchDeleteEventsByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 ")
    public ServerResponseVO<?> batchDeleteEventsByIdList(@RequestParam("ids") Integer[] ids) {
        eventsService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getEventsById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取  ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Events events =eventsService.getById(id);
        return ServerResponseVO.success(events);
    }


    /**
     * 分页查询数据：
     * @param eventsDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getEventsList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = " 分页查询数据")
    public ServerResponseVO<?> getEventsList(@Validated EventsDto eventsDto) {
        IPage<Events> page = eventsService.selectListByPage(eventsDto);
        return ServerResponseVO.success(page);
    }

    @GetMapping("/list")
    public ServerResponseVO<?> list(@Validated EventsDto eventsDto) {
        List<CalendarEventVO> pages = eventsService.getCalendarEventList(eventsDto);
        return ServerResponseVO.success(pages);
    }
    @GetMapping("/add")
    public ServerResponseVO<?> add(@Validated CalendarEventVO eventVO) {
        CalendarEventVO vo = eventsService.addCalendarEvent(eventVO);
        return ServerResponseVO.success(vo);
    }
}
