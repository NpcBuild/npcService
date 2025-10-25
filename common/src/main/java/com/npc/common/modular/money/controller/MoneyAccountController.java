package com.npc.common.modular.money.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.money.dto.MoneyAccountDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.money.service.IMoneyAccountService;
import com.npc.common.modular.money.entity.MoneyAccount;

import java.util.Arrays;

/**
 * <p>
 * 金额账户 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-08-20
 */
@RestController
@RequestMapping("/moneyAccount")
// @Api(value = "/moneyAccount", description = "金额账户 相关接口")
public class MoneyAccountController {
    
    private static final Logger logger = LoggerFactory.getLogger(MoneyAccountController.class);

    @Autowired
    public IMoneyAccountService moneyAccountService;


    /**
     * 保存、修改 【区分id即可】
     * @param moneyAccount 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "金额账户 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated MoneyAccount moneyAccount) {
        try {
            Boolean obj = moneyAccountService.saveOrUpdate(moneyAccount);
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
    @GetMapping("deleteMoneyAccountById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean moneyAccount =moneyAccountService.removeById(id);
            return ServerResponseVO.success(moneyAccount);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 金额账户
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteMoneyAccountByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 金额账户")
    public ServerResponseVO<?> batchDeleteMoneyAccountByIdList(@RequestParam("ids") Integer[] ids) {
        moneyAccountService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getMoneyAccountById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 金额账户 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        MoneyAccount moneyAccount =moneyAccountService.getById(id);
        return ServerResponseVO.success(moneyAccount);
    }


    /**
     * 分页查询数据：
     * @param moneyAccountDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getMoneyAccountList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "金额账户 分页查询数据")
    public ServerResponseVO<?> getMoneyAccountList(@Validated MoneyAccountDto moneyAccountDto) {
        Page page = new Page(moneyAccountDto.getPageNum(), moneyAccountDto.getPageSize());
        QueryWrapper<MoneyAccount> queryWrapper = new QueryWrapper(moneyAccountDto);
        Page<MoneyAccount> pages = moneyAccountService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
