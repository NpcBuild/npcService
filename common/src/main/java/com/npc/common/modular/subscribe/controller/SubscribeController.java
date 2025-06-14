package com.npc.common.modular.subscribe.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.subscribe.dto.SubscribeDto;
import com.npc.common.modular.subscribe.vo.SubscribeVO;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.subscribe.service.ISubscribeService;
import com.npc.common.modular.subscribe.entity.Subscribe;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 订阅表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-02-24
 */
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
    
    private static final Logger logger = LoggerFactory.getLogger(SubscribeController.class);

    @Autowired
    public ISubscribeService subscribeService;


    /**
     * 保存、修改 【区分id即可】
     * @param subscribe 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated Subscribe subscribe) {
        try {
            Boolean obj = subscribeService.saveOrUpdate(subscribe);
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
    @GetMapping("deleteSubscribeById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean subscribe =subscribeService.removeById(id);
            return ServerResponseVO.success(subscribe);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 订阅表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteSubscribeByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteSubscribeByIdList(@RequestParam("ids") Integer[] ids) {
        subscribeService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getSubscribeById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Subscribe subscribe =subscribeService.getById(id);
        return ServerResponseVO.success(subscribe);
    }


    /**
     * 分页查询数据：
     * @param subscribeDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getSubscribeList", method = RequestMethod.GET)
    public ServerResponseVO<?> getSubscribeList(@Validated SubscribeDto subscribeDto) {
        Page page = new Page(subscribeDto.getPageNum(), subscribeDto.getPageSize());
        QueryWrapper<Subscribe> queryWrapper = new QueryWrapper(subscribeDto);
        Page<Subscribe> pages = subscribeService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }

    @GetMapping("getNextSubscribeList")
    public ServerResponseVO<?> getNextSubscribeList(@Validated SubscribeDto subscribeDto) {
        List<SubscribeVO> list = subscribeService.getNextSubscribeList(subscribeDto);
        return ServerResponseVO.success(list);
    }
}
